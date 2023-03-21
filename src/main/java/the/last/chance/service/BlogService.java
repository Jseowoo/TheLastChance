package the.last.chance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import the.last.chance.dto.FavoriteKeywordDTO;
import the.last.chance.dto.ResponseKakaoSearchDTO;
import the.last.chance.dto.RequestBlogSearchDTO;
import the.last.chance.entity.KeywordCountEntity;
import the.last.chance.repository.KeywordCountRepository;
import the.last.chance.utils.KakaoSearchApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {

    @Value("${url.kakao.search.blog}")
    private String SEARCH_BLOG_API_URL;

    @Autowired
    private KakaoSearchApiClient kakaoSearchApiClient;

    @Autowired
    private KeywordCountRepository keywordCountRepository;

    public Map<String, Object> searchBlog(RequestBlogSearchDTO dto) {

        //추가적인 로직이 들어갈 수 있습니다.(서버에 접근한 클라이언트 정보 등)
        //ex) Util.getRemoteInfo(); ...

        ResponseKakaoSearchDTO responseKakaoSearchDTO = kakaoSearchApiClient.searchBlog(SEARCH_BLOG_API_URL, dto.getQuery(), dto.getSort(), dto.getSize(), dto.getPage());

        Map<String, Object> searchList = new HashMap<>();
        List<ResponseKakaoSearchDTO.Document> documents = responseKakaoSearchDTO.getDocuments();
        searchList.put("documents", documents);

        Map<String, Object> meta = new HashMap<>();
        meta.put("total_count", responseKakaoSearchDTO.getMeta().getTotalCount());
        meta.put("pageable_count", responseKakaoSearchDTO.getMeta().getPageableCount());
        meta.put("is_end", responseKakaoSearchDTO.getMeta().isEnd());

        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("current_page", dto.getPage());
        pageInfo.put("total_page", (responseKakaoSearchDTO.getMeta().getPageableCount() % dto.getSize()) == 0 ?
                (responseKakaoSearchDTO.getMeta().getPageableCount() / dto.getSize()) :
                (responseKakaoSearchDTO.getMeta().getPageableCount() / dto.getSize()) + 1);
        pageInfo.put("sort_type", dto.getSort());
        meta.put("pageinfo", pageInfo);

        List<FavoriteKeywordDTO> favoriteKeywordList = this.getFavoriteKeyword();
        meta.put("favorite_keywords", favoriteKeywordList);

        searchList.put("meta", meta);

        // 페이지 넘김, sorting등의 데이터 갱신에서 허수 제거
        if(dto.getSearch()) saveSearchKeyword(dto.getQuery(), "ko", "127.0.0.1");

        return searchList;
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

    public List<FavoriteKeywordDTO> getFavoriteKeyword() {
        return keywordCountRepository.findTop10FavoriteKeyword();
    }

    //    public NaverSearchResponse searchBlogByNaver
}
