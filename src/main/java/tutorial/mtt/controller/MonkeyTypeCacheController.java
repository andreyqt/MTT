package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.entity.MonkeyTypeTest;
import tutorial.mtt.service.MonkeyTypeTestCache;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class MonkeyTypeCacheController {

    private final MonkeyTypeTestCache monkeyTypeTestCache;

    @GetMapping("/size")
    public int getSize() {
        return monkeyTypeTestCache.getSize();
    }

    @GetMapping("/last")
    public MonkeyTypeTest getLastTest() {
        return monkeyTypeTestCache.getLastTest();
    }

}
