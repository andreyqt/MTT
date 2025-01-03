package tutorial.mtt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tutorial.mtt.dto.DailyResult;
import tutorial.mtt.entity.MonkeyTypeTest;
import tutorial.mtt.service.DailyResultCache;
import tutorial.mtt.service.DailyResultExcelService;
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
    private final DailyResultExcelService dailyResultExcelService;
    private final DailyResultCache dailyResultCache;

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

    @PostMapping("/yesterday")
    public String saveYesterdayTestFromCacheToExcel() throws IOException {
        List<MonkeyTypeTest> tests = monkeyTypeTestCache.getAllFromYesterdayCache();
        excelService.save(tests);
        return "tests from yesterday's cache were saved successfully";
    }

    @PostMapping("/daily")
    public String saveAverageResult() throws IOException {
        DailyResult result = dailyResultCache.getToday();
        dailyResultExcelService.save(result);
        return "daily result was saved successfully";
    }

    @PostMapping("/daily/yesterday")
    public String saveYesterdayAverageResult() throws IOException {
        DailyResult result = dailyResultCache.getYesterday();
        dailyResultExcelService.save(result);
        return "daily result for yesterday was saved successfully";
    }

}
