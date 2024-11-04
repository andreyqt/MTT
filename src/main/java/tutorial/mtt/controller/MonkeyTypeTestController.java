package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.mapper.MonkeyTypeTestMapper;
import tutorial.mtt.service.MonkeyTypeTestService;

import java.util.List;

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
    public List<MonkeyTypeTestDTO> getLast10Test() {
        return monkeyTypeTestService.getBatchOfTests().stream().map(monkeyTypeTestMapper::toDto).toList();
    }
}
