package tutorial.mtt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonkeyTypeTestCache {

    private static final Map<Long, MonkeyTypeTest> CACHE = new TreeMap<>();
    private final TimeService timeService;

    public void addToCache(MonkeyTypeTest monkeyTypeTest) {
        CACHE.putIfAbsent(monkeyTypeTest.getTimestamp(), monkeyTypeTest);
    }

    public void addListToCache(List<MonkeyTypeTest> monkeyTypeTestList) {
        for (MonkeyTypeTest monkeyTypeTest : monkeyTypeTestList) {
            addToCache(monkeyTypeTest);
        }
        log.info("tests were added to Cache, current size is {}", CACHE.size());
    }

    public MonkeyTypeTest getFromCacheByTimestamp(long timestamp) {
        return CACHE.get(timestamp);
    }

    public List<MonkeyTypeTest> getAllFromCache() {
        return new ArrayList<>(CACHE.values());
    }

    public List<MonkeyTypeTest> getTestsDoneToday() {
        Long timestamp = timeService.getMidnightTimestamp();
        return CACHE.entrySet().stream().filter(entry -> entry.getKey() >= timestamp).map(Map.Entry::getValue).toList();
    }

    public List<MonkeyTypeTest> getTestsDoneYesterday() {
        long today = timeService.getMidnightTimestamp();
        long yesterday = timeService.getMidnightTimestamp() - 24 * 60 * 60 * 1000;
        return CACHE.entrySet().stream().filter(entry -> yesterday <= entry.getKey() && entry.getKey() < today)
                .map(Map.Entry::getValue).toList();
    }

    public int getSize() {
        return CACHE.size();
    }

    public MonkeyTypeTest getLastTest() {
        return CACHE.values().stream().skip(CACHE.size() - 1).findFirst().orElse(null);
    }

}