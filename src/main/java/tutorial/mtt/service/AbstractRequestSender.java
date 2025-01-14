package tutorial.mtt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.web.client.RestTemplate;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;

@RequiredArgsConstructor
public abstract class AbstractRequestSender<E> {

    @Value("${MT.token}")
    protected String token;
    protected final Class<E> responseType;
    protected final MonkeyTypeTestMapper monkeyTypeTestMapper;

    public E sendRequest(String url, HttpMethod method) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Accept", "application/json");
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, method, entity, responseType).getBody();
    }

}
