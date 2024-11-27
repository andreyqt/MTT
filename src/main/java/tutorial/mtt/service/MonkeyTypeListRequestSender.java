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
import java.util.List;
import java.util.OptionalDouble;

@Slf4j
@Service
public class MonkeyTypeListRequestSender extends AbstractRequestSender<JsonTestResponseList> {

    @Value("${MT.getTests.url}")
    private String getTestsUrl;
    private final TimeService timeService;
    private final MonkeyTypeTestCache monkeyTypeTestCache;

    public MonkeyTypeListRequestSender(MonkeyTypeTestMapper monkeyTypeTestMapper) {
        super(JsonTestResponseList.class, monkeyTypeTestMapper);
        this.timeService = new TimeService();
        this.monkeyTypeTestCache = new MonkeyTypeTestCache();
    }

    public List<MonkeyTypeTestDTO> getTestsDoneToday() {
        long afterTimestamp = timeService.getMidnightTimestamp();
        List<MonkeyTypeTest> tests = sendRequest(createUrl(afterTimestamp), HttpMethod.GET).getData();
        log.info("Received {} test(s) done today", tests.size());
        monkeyTypeTestCache.addListToCache(filterToQuotes(tests));
        return tests.stream().map(monkeyTypeTestMapper::toDto).toList();
    }

    public DailyResult getTodayAverage() {
        List<MonkeyTypeTestDTO> result = getTestsDoneToday();
        return calculateAverage(result, LocalDate.now());
    }

    public List<MonkeyTypeTestDTO> getTestsDoneYesterday() {
        long afterTimestamp = timeService.getMidnightOfTheDay(LocalDate.now().minusDays(1));
        List<MonkeyTypeTest> tests = sendRequest(createUrl(afterTimestamp), HttpMethod.GET).getData();
        log.info("Received {} test(s) done since yesterday", tests.size());
        List<MonkeyTypeTestDTO> results = tests.stream().map(monkeyTypeTestMapper::toDto)
                .filter(dto -> dto.getDateTime().toLocalDate().isBefore(LocalDate.now())).toList();
        log.info("{} test(s) were(was) done yesterday", results.size());
        return results;
    }

    public DailyResult getYesterdaysAverage() {
        List<MonkeyTypeTestDTO> result = getTestsDoneYesterday();
        return calculateAverage(result, LocalDate.now().minusDays(1));
    }

    public DailyResult getAverageForDate(LocalDate date) {
        long timestamp = timeService.getMidnightOfTheDay(date);
        List<MonkeyTypeTest> tests = sendRequest(createUrl(timestamp), HttpMethod.GET).getData();
        List<MonkeyTypeTestDTO> filteredTests = tests.stream()
                .filter(monkeyTypeTest -> monkeyTypeTest.getTimestamp() < timestamp + 86000000L)
                .map(monkeyTypeTestMapper::toDto).toList();
        log.info("Received {} test(s) for date {}", filteredTests.size(), date);
        return calculateAverage(filteredTests, date);
    }

    public DailyResult calculateAverage(List<MonkeyTypeTestDTO> tests, LocalDate date) {
        List<MonkeyTypeTestDTO> filteredTests = tests.stream().filter(test -> test.getMode().equals("quote")).toList();
        int numberOfTests = filteredTests.size();
        OptionalDouble optionalDouble = filteredTests.stream().mapToDouble(MonkeyTypeTestDTO::getWpm).average();
        double avgSpeed = optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
        double avgTime = filteredTests.stream().mapToDouble(MonkeyTypeTestDTO::getTestDuration).sum() / 60.;
        return new DailyResult(avgSpeed, avgTime, numberOfTests, date);
    }

    public String createUrl(long afterTimestamp) {
        return getTestsUrl + "?onOrAfterTimestamp=" + afterTimestamp;
    }

    public List<MonkeyTypeTest> filterToQuotes(List<MonkeyTypeTest> tests) {
        return tests.stream().filter(test -> test.getMode().equals("quote")).toList();
    }
}