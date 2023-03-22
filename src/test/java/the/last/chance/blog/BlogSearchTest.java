package the.last.chance.blog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import the.last.chance.dto.FavoriteKeywordDTO;
import the.last.chance.dto.ResponseKakaoSearchDTO;
import the.last.chance.entity.KeywordCountEntity;
import the.last.chance.repository.KeywordCountRepository;
import the.last.chance.service.BlogService;
import the.last.chance.utils.KakaoSearchApiClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BlogSearchTest {

    @Autowired
    private KeywordCountRepository keywordCountRepository;

    @Autowired
    private KakaoSearchApiClient kakaoSearchApiClient;

    @Autowired
    private BlogService blogService;

    @Nested
    @DisplayName("카카오/네이버 블로그 검색 OpenAPI 테스트")
    class OpenAPI {

        @Test
        @DisplayName("카카오 블로그 검색 OpenAPI")
        public void blogSearchByKakaoAPI() {
            ResponseKakaoSearchDTO dto = kakaoSearchApiClient.searchBlog("https://dapi.kakao.com/v2/search/blog", "다음카카오", "accuracy", 10, 1);

            assertNotNull(dto);

            for(ResponseKakaoSearchDTO.Document document : dto.getDocuments()) {
                System.out.println(document.getTitle());
                System.out.println(document.getContents());
                System.out.println(document.getBlogname());
                System.out.println(document.getUrl());
                System.out.println(document.getThumbnail());
                System.out.println(document.getDatetime());
                System.out.println("-----------------------------------");
            }

            System.out.println(dto.getMeta().getTotalCount());
            System.out.println(dto.getMeta().getPageableCount());
            System.out.println(dto.getMeta().isEnd());
        }

        @Test
        @DisplayName("네이버 블로그 검색 OpenAPI")
        public void blogSearchByNaverAPI() {

        }
    }


    @Test
    @DisplayName("검색한 키워드 H2 데이터베이스에 저장 테스트")
    public void searchKeywordSave() {
        String keyword = "다음카카오";
        String locale = "ko";
        String ip = "127.0.0.1";

        KeywordCountEntity saveEntityData = new KeywordCountEntity();
        saveEntityData.setKeyword(keyword);
        saveEntityData.setDate();
        saveEntityData.setLocale(locale);
        saveEntityData.setIp(ip);

        KeywordCountEntity selectEntityData = keywordCountRepository.save(saveEntityData);

        assertNotNull(selectEntityData);
        assertEquals(selectEntityData.getKeyword(), keyword);
        assertEquals(selectEntityData.getLocale(), locale);
        assertEquals(selectEntityData.getIp(), ip);
    }


    @Test
    @DisplayName("H2 데이터베이스에 저장된 인기 검색어 리스트 불러오기 테스트")
    public void favoriteKeywordList() {
        List<FavoriteKeywordDTO> list = blogService.getFavoriteKeyword();

        assertNotNull(list);
    }
}
