package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.dto.DailyResult;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.service.MonkeyTypeListRequestSender;
import tutorial.mtt.service.MonkeyTypeRequestSender;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MonkeyTypeTestController {

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
    public DailyResult getTodaysAverage() {
        return monkeyTypeListRequestSender.getTodayAverage();
    }

    @GetMapping("/yesterday")
    public List<MonkeyTypeTestDTO> getTestsDoneYesterday() {
        return monkeyTypeListRequestSender.getTestsDoneYesterday();
    }

    @GetMapping("/yesterday/average")
    public DailyResult getYesterdaysAverage() {
        return monkeyTypeListRequestSender.getYesterdaysAverage();
    }

    @GetMapping(value = "/date", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DailyResult getAverageForDate(@RequestBody String date) {
        return monkeyTypeListRequestSender.getAverageForDate(LocalDate.parse(date));
    }

}