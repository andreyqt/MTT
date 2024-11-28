package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.entity.MonkeyTypeTest;
import tutorial.mtt.service.MTTExcelService;
import tutorial.mtt.service.MonkeyTypeTestCache;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class MonkeyTypeCacheController {

    private final MonkeyTypeTestCache monkeyTypeTestCache;
    private final MTTExcelService excelService;

    @GetMapping("/size")
    public int getSize() {
        return monkeyTypeTestCache.getSize();
    }

    @GetMapping("/last")
    public MonkeyTypeTest getLastTest() {
        return monkeyTypeTestCache.getLastTest();
    }

    @GetMapping("/all")
    public List<MonkeyTypeTest> getAllTest() {
        return monkeyTypeTestCache.getAllFromCache();
    }

    @PostMapping()
    public String saveTestsFromCacheToExcel() throws IOException {
        List<MonkeyTypeTest> tests = monkeyTypeTestCache.getAllFromCache();
        excelService.save(tests);
        return "tests from cache were saved successfully";
    }

}
