package tutorial.mtt.service;

import org.springframework.stereotype.Service;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonkeyTypeTestCache {
    private static final Map<Long, MonkeyTypeTest> CACHE = new LinkedHashMap<>();

    public void addToCache(MonkeyTypeTest monkeyTypeTest) {
        CACHE.putIfAbsent(monkeyTypeTest.getTimestamp(), monkeyTypeTest);
    }

    public MonkeyTypeTest getFromCache(long timestamp) {
        return CACHE.get(timestamp);
    }

    public List<MonkeyTypeTest> getAllFromCache() {
        return new ArrayList<>(CACHE.values());
    }
}
