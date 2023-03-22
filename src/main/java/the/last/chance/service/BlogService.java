package the.last.chance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import the.last.chance.dto.*;
import the.last.chance.entity.KeywordCountEntity;
import the.last.chance.repository.KeywordCountRepository;
import the.last.chance.utils.KakaoSearchApiClient;
import the.last.chance.utils.NaverSearchApiClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {

    @Value("${url.kakao.search.blog}")
    private String SEARCH_KAKAO_BLOG_API_URL;

    @Value("${url.naver.search.blog}")
    private String SEARCH_NAVER_BLOG_API_URL;

    @Value("${kakao.api.enable}")
    private boolean EXIST_KAKAO_API;

    @Autowired
    private KakaoSearchApiClient kakaoSearchApiClient;

    @Autowired
    private NaverSearchApiClient naverSearchApiClient;

    @Autowired
    private KeywordCountRepository keywordCountRepository;

    public Map<String, Object> searchBlog(RequestKakaoSearchDTO dto) {
        Map<String, Object> result = new HashMap<>();

        try {
            ResponseKakaoSearchDTO responseKakaoSearchDTO = kakaoSearchApiClient.searchBlog(SEARCH_KAKAO_BLOG_API_URL, dto.getQuery(), dto.getSort(), dto.getSize(), dto.getPage());
            //장애 테스트용 코드 application.yml에서 설정할 수 있습니다.
            if(!EXIST_KAKAO_API) throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

            // 페이지 넘김, sorting등의 데이터 갱신에서 허수 제거
            // 데이터 수집을 위한 추가적인 로직이 들어갈 수 있습니다.(서버에 접근한 클라이언트 정보 등)
            // ex) Util.getRemoteInfo(); ...
            if(dto.getSearch()) saveSearchKeyword(dto.getQuery(), "ko", "127.0.0.1");

            result = this.procBlogSearchInfo(responseKakaoSearchDTO, responseKakaoSearchDTO.getMeta().getTotalCount(), responseKakaoSearchDTO.getMeta().getPageableCount(), dto.getSize(), dto.getPage(), dto.getSort());

            return result;

        } catch(HttpServerErrorException e) {
            //카카오 블로그 검색 OpenAPI에 장애가 생겼을 경우, 네이버 API로 검색
            String text = null;
            try {
                text = URLEncoder.encode(dto.getQuery(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException("검색어 인코딩 실패",e);
            }

            RequestNaverSearchDTO newDto = new RequestNaverSearchDTO();
            newDto.setQuery(SEARCH_NAVER_BLOG_API_URL + "?query=" + text);
            newDto.setDisplay(dto.getSize());
            newDto.setStart(dto.getPage());
            newDto.setSort(dto.getSort());

            ResponseNaverSearchDTO responseNaverSearchDTO = naverSearchApiClient.searchBlog(newDto);

            if(dto.getSearch()) saveSearchKeyword(dto.getQuery(), "ko", "127.0.0.1");

            result = this.procBlogSearchInfo(responseNaverSearchDTO, Integer.parseInt(responseNaverSearchDTO.getTotal()),
                    Integer.parseInt(responseNaverSearchDTO.getTotal()), Integer.parseInt(responseNaverSearchDTO.getDisplay()),
                    Integer.parseInt(responseNaverSearchDTO.getStart()), newDto.getSort());

            return result;
        }

    }

    @Transactional
    public void saveSearchKeyword(String keyword, String locale, String ip) {
        KeywordCountEntity entity = new KeywordCountEntity();
        entity.setKeyword(keyword);
        entity.setDate();
        entity.setLocale(locale);
        entity.setIp(ip);

        keywordCountRepository.save(entity);
    }

    private Map<String, Object> procBlogSearchInfo(Object dto, Integer totalCount, Integer pageableCount, Integer size, Integer currentPage, String sortType) {
        Map<String, Object> result = new HashMap<>();

        if(dto instanceof ResponseKakaoSearchDTO) {
            result.put("documents", ((ResponseKakaoSearchDTO) dto).getDocuments());
        } else if(dto instanceof ResponseNaverSearchDTO){
            List list = new ArrayList<>();
            for(Object object : ((ResponseNaverSearchDTO) dto).getItems()) {
                ResponseNaverSearchDTO.Item item = (ResponseNaverSearchDTO.Item) object;

                Map<String, Object> documents = new HashMap<>();

                documents.put("blogname", item.getBloggername());
                documents.put("contents", item.getDescription());
                documents.put("datetime", item.getPostdate());
                documents.put("thumbnail", null);
                documents.put("title", item.getTitle());
                documents.put("url", item.getBloggerlink());

                list.add(documents);
            }

            result.put("documents", list);
        }//새로운 검색 소스가 추가될 경우 조건문을 늘려서 처리할 수 있습니다...

        //공통 파싱
        int totalPage = (pageableCount % size) == 0 ? (pageableCount / size) : (pageableCount / size) + 1;
        Map<String, Object> pageInfo = Map.of(
                "current_page", currentPage,
                "total_page", totalPage,
                "sort_type", sortType
        );

        List<FavoriteKeywordDTO> favoriteKeywordList = this.getFavoriteKeyword();

        Map<String, Object> meta = Map.of(
                "total_count", totalCount,
                "pageable_count", pageableCount,
                "is_end", currentPage < totalPage ? false : true,
                "pageinfo", pageInfo,
                "favorite_keywords", favoriteKeywordList
        );

        result.put("meta", meta);

        return result;
    }

    public List<FavoriteKeywordDTO> getFavoriteKeyword() {
        return keywordCountRepository.findTop10FavoriteKeyword();
    }

}
