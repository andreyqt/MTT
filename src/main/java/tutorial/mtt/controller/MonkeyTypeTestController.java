package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;
import tutorial.mtt.service.MonkeyTypeTestService;

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

    @GetMapping("/last10")
    public List<MonkeyTypeTestDTO> getLast10Tests() {
        return monkeyTypeTestService.getBatchOfTests().stream().map(monkeyTypeTestMapper::toDto).toList();
    }

    @GetMapping("/today")
    public List<MonkeyTypeTestDTO> getTodayTests() {
        return monkeyTypeTestService.getTodaysTests().stream().map(monkeyTypeTestMapper::toDto).toList();
    }
}
