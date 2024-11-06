package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.dto.DailyResult;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;
import tutorial.mtt.service.MonkeyTypeTestService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MonkeyTypeTestController {
    private final MonkeyTypeTestService monkeyTypeTestService;
    private final MonkeyTypeTestMapper monkeyTypeTestMapper;

    @GetMapping("/last")
    public MonkeyTypeTestDTO getLastTest() {
        return monkeyTypeTestMapper.toDto(monkeyTypeTestService.getLastTest().getBody().getData());
    }

    @GetMapping("/batch")
    public List<MonkeyTypeTestDTO> getBatchOfTests() {
        return monkeyTypeTestService.getBatchOfTests().stream().map(monkeyTypeTestMapper::toDto).toList();
    }

    @GetMapping("/today")
    public List<MonkeyTypeTestDTO> getTodayTests() {
        return monkeyTypeTestService.getTodaysTests().stream().map(monkeyTypeTestMapper::toDto).toList();
    }

    @GetMapping("/today/average")
    public String getTodaysAverage() {
        List<MonkeyTypeTestDTO> result = getTodayTests().stream().filter(test -> test.getMode().equals("quote")).toList();
        double avgSpeed = result.stream().mapToDouble(MonkeyTypeTestDTO::getWpm).average().getAsDouble();
        double avgTime = result.stream().mapToDouble(test->Double.parseDouble(test.getTestDuration())).sum()/60.;
        LocalDate date = result.stream().findFirst().map(test -> test.getDateTime().toLocalDate()).orElse(LocalDate.now());
        int numberOfTests = result.size();
        return new DailyResult(avgSpeed, avgTime, numberOfTests, date).toString();
    }
}
