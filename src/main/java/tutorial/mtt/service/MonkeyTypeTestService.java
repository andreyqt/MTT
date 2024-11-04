package tutorial.mtt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tutorial.mtt.entity.JsonTestResponse;
import tutorial.mtt.entity.MonkeyTypeTest;

@Service
public class MonkeyTypeTestService {
    @Value("${MT.token}")
    private String token;
    @Value("${MT.getLastTest.url}")
    private String lastTestUrl;

    public ResponseEntity<JsonTestResponse> getLastTest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Accept", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("https://api.monkeytype.com/results/last", HttpMethod.GET, entity, JsonTestResponse.class);
    }
}
