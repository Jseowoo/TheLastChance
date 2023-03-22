package the.last.chance.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import the.last.chance.dto.RequestNaverSearchDTO;
import the.last.chance.dto.ResponseNaverSearchDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Component
public class NaverSearchApiClient {

    @Value("${rest.api.key.naver.client-id}")
    private String CLIENT_ID;

    @Value("${rest.api.key.naver.client-secret}")
    private String CLIENT_SECRET;

    public ResponseNaverSearchDTO searchBlog(RequestNaverSearchDTO dto) {

        Map<String, String> requestHeaders = Map.of(
            "X-Naver-Client-Id", CLIENT_ID,
            "X-Naver-Client-Secret", CLIENT_SECRET
        );

        String apiUrl = dto.getQuery() + "&display=" + dto.getDisplay() + "&start=" + dto.getStart() + "&sort=" + dto.getSort();

        String responseBody = get(apiUrl, requestHeaders);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseNaverSearchDTO responseDto = objectMapper.readValue(responseBody, ResponseNaverSearchDTO.class);

            return responseDto;
        } catch (IOException e) {
            throw new RuntimeException("데이터 변환 실패", e);
        }
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
