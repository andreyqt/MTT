package tutorial.mtt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import tutorial.mtt.dto.DailyResult;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.entity.JsonTestResponseList;
import tutorial.mtt.entity.MonkeyTypeTest;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;

@Slf4j
@Service
public class MonkeyTypeListRequestSender extends AbstractRequestSender<JsonTestResponseList> {

    @Value("${MT.getTests.url}")
    private String getTestsUrl;
    private final TimeService timeService;

    public MonkeyTypeListRequestSender(MonkeyTypeTestMapper monkeyTypeTestMapper) {
        super(JsonTestResponseList.class, monkeyTypeTestMapper);
        this.timeService = new TimeService();
    }

    public List<MonkeyTypeTestDTO> getTestsDoneToday() {
        long afterTimestamp = timeService.getMidnightTimestamp();
        List<MonkeyTypeTest> tests = sendRequest(getTestsUrl + "?onOrAfterTimestamp=" + afterTimestamp,
                HttpMethod.GET).getData();
        log.info("Received {} test(s) done today", tests.size());
        return tests.stream().map(monkeyTypeTestMapper::toDto).toList();
    }

    public DailyResult getTodaysAverage() {
        List<MonkeyTypeTestDTO> result = getTestsDoneToday();
        int numberOfTests = result.size();
        OptionalDouble optionalDouble = result.stream().mapToDouble(MonkeyTypeTestDTO::getWpm).average();
        double avgSpeed = optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
        double avgTime = result.stream().mapToDouble(MonkeyTypeTestDTO::getTestDuration).sum()/60.;
        LocalDate date = LocalDateTime.now().toLocalDate();
        return new DailyResult(avgSpeed, avgTime, numberOfTests, date);
    }
}