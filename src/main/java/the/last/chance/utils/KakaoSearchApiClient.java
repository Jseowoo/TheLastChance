package the.last.chance.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import the.last.chance.dto.ResponseKakaoSearchDTO;

@Component
public class KakaoSearchApiClient {

    @Value("${rest.api.key.kakao}")
    private String REST_API_KEY;

    private final RestTemplate restTemplate;

    public KakaoSearchApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //블로그 검색
    public ResponseKakaoSearchDTO searchBlog(String url, String query, String sort, Integer size, Integer page) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + REST_API_KEY);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", query)
                .queryParam("sort", sort)
                .queryParam("size", size)
                .queryParam("page", page);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseKakaoSearchDTO> responseEntity =
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ResponseKakaoSearchDTO.class);
        return responseEntity.getBody();

    }

}
