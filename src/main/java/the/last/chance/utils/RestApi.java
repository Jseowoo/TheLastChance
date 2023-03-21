package the.last.chance.utils;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Stream;
/**
 * 추후 확장성을 고려한 공통 클래스 설계 부분입니다.
 * 다양한 OpenAPI를 사용하거나 외부 API를 호출해야 한다면, 해당 공통 클래스를 사용해 보일러플레이트 코드를 줄일 수 있습니다.
 * */
@Service
public class RestApi {

    private HttpClient httpClient;

    public RestApi() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String callRestApi(String url, Map<String, String> headers, String body, String method) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers(headers.entrySet().stream()
                        .flatMap(e -> Stream.of(e.getKey(), e.getValue()))
                        .toArray(String[]::new));

        if (method.equalsIgnoreCase("POST")) {
            builder = builder.POST(HttpRequest.BodyPublishers.ofString(body));
        } else if (method.equalsIgnoreCase("GET")) {
            builder = builder.GET();
        } else {
            // 기타 HTTP 메서드 (PUT, DELETE 등) 처리
            builder = builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        }

        HttpRequest request = builder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
