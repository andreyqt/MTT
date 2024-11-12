package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.dto.DailyResult;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;
import tutorial.mtt.service.MonkeyTypeListRequestSender;
import tutorial.mtt.service.MonkeyTypeRequestSender;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MonkeyTypeTestController {
    private final MonkeyTypeTestMapper monkeyTypeTestMapper;
    private final MonkeyTypeRequestSender monkeyTypeRequestSender;
    private final MonkeyTypeListRequestSender monkeyTypeListRequestSender;

    @GetMapping("/last")
    public MonkeyTypeTestDTO getLastTest() {
        return monkeyTypeRequestSender.getLastTest();
    }

    @GetMapping("/today")
    public List<MonkeyTypeTestDTO> getTestsDoneToday() {
        return monkeyTypeListRequestSender.getTestsDoneToday();
    }

    @GetMapping("/today/average")
    public String getTodaysAverage() {
        return monkeyTypeListRequestSender.getTodaysAverage().toString();
    }

    @GetMapping("yesterday")
    public List<MonkeyTypeTestDTO> getTestsDoneYesterday() {
        return monkeyTypeListRequestSender.getTestsDoneYesterday();
    }
}
