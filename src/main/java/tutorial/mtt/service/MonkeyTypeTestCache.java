package tutorial.mtt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MonkeyTypeTestCache {
    private static final Map<Long, MonkeyTypeTest> CACHE = new LinkedHashMap<>();

    public void addToCache(MonkeyTypeTest monkeyTypeTest) {
        CACHE.putIfAbsent(monkeyTypeTest.getTimestamp(), monkeyTypeTest);
    }

    public void addListToCache(List<MonkeyTypeTest> monkeyTypeTestList) {
        for (MonkeyTypeTest monkeyTypeTest : monkeyTypeTestList) {
            addToCache(monkeyTypeTest);
        }
        log.info("{} was added to Cache, current size is {}", CACHE.size() - monkeyTypeTestList.size(), CACHE.size());
    }

    public MonkeyTypeTest getFromCacheByTimestamp(long timestamp) {
        return CACHE.get(timestamp);
    }

    public List<MonkeyTypeTest> getAllFromCache() {
        return new ArrayList<>(CACHE.values());
    }

    public int getSize() {
        return CACHE.size();
    }

    public MonkeyTypeTest getLastTest() {
        return CACHE.values().stream().skip(CACHE.size() - 1).findFirst().orElse(null);
    }

}