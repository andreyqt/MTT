package tutorial.mtt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tutorial.mtt.entity.JsonTestResponse;
import tutorial.mtt.entity.JsonTestResponseList;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonkeyTypeTestService {
    @Value("${MT.token}")
    private String token;
    @Value("${MT.getLastTest.url}")
    private String lastTestUrl;
    @Value("${MT.getTests.url}")
    private String getTestsUrl;
    @Value("${batchSize}")
    private int batchSize;

    public ResponseEntity<JsonTestResponse> getLastTest() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Accept", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(lastTestUrl, HttpMethod.GET, entity, JsonTestResponse.class);
    }

    public List<MonkeyTypeTest> getBatchOfTests() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Accept", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonTestResponseList> responseEntity =
                restTemplate.exchange(getTestsUrl + "?limit=" + batchSize, HttpMethod.GET, entity, JsonTestResponseList.class);
        return responseEntity.getBody().getData();
    }
}
