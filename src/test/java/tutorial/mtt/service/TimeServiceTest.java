package tutorial.mtt.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class TimeServiceTest {

    @InjectMocks
    private TimeService timeService;
    private LocalDateTime localDateTime;
    private long timestamp;
    private long anotherTimestamp;

    @BeforeEach
    public void setUp() {
        localDateTime = LocalDateTime.of(2024,11,11, 1,1, 0);
        timestamp = 1731276060L;
        anotherTimestamp = 1731024000L;
    }

    @Test
    public void testConvertLocalDateTimeToLong() {
        long actualResult = timeService.convertLocalDateTimeToLong(localDateTime);
        Assertions.assertEquals(timestamp, actualResult);
    }

    @Test
    public void testConvertDateToLong() {
        LocalDate localDate = LocalDate.of(2024,11,8);
        long actualResult = timeService.getMidnightOfTheDay(localDate);
        Assertions.assertEquals(anotherTimestamp, actualResult);
    }
}
