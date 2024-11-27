package tutorial.mtt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.entity.JsonTestResponse;
import tutorial.mtt.entity.MonkeyTypeTest;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;

@Service
public class MonkeyTypeRequestSender extends AbstractRequestSender<JsonTestResponse> {

    @Value("${MT.getLastTest.url}")
    private String getLastTestUrl;
    private final MTTExcelService excelService;

    public MonkeyTypeRequestSender(MonkeyTypeTestMapper monkeyTypeTestMapper,
                                   MTTExcelService excelService) {
        super(JsonTestResponse.class, monkeyTypeTestMapper);
        this.excelService = excelService;
    }

    public MonkeyTypeTestDTO getLastTest() {
        MonkeyTypeTest result = sendRequest(getLastTestUrl, HttpMethod.GET).getData();
        return monkeyTypeTestMapper.toDto(result);
    }

}