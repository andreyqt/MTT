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

    private LocalDateTime localDateTime1;
    private LocalDateTime localDateTime2;
    private LocalDate localDate1;
    private LocalDate localDate2;
    private String dateTime1;
    private String dateTime2;
    private long timestamp1;
    private long timestamp2;

    @BeforeEach
    public void setUp() {
        localDateTime1 = LocalDateTime.of(2024, 11, 12, 11, 29, 1);
        localDateTime2 = LocalDateTime.of(2023, 11, 12, 0, 0, 0);
        localDate1 = LocalDate.of(2024, 11, 12);
        localDate2 = LocalDate.of(2023, 11, 12);
        dateTime1 = "2024-11-12 11:29:01";
        dateTime2 = "2023-11-12 00:00:00";
        timestamp1 = 1731400141000L;
        timestamp2 = 1699736400000L;
    }

    @Test
    public void testConvertLocalDateTimeToLong() {
        long actualResult1 = timeService.convertLocalDateTimeToLong(localDateTime1);
        long actualResult2 = timeService.convertLocalDateTimeToLong(localDateTime2);
        Assertions.assertEquals(timestamp1, actualResult1);
        Assertions.assertEquals(timestamp2, actualResult2);
    }

    @Test
    public void testGetMidnightOfTheDay() {
        long actualResult1 = timeService.getMidnightOfTheDay(localDate1);
        long actualResult2 = timeService.getMidnightOfTheDay(localDate2);
        Assertions.assertEquals(1731358800000L, actualResult1);
        Assertions.assertEquals(1699736400000L, actualResult2);
    }

    @Test
    public void testConvertLongToString() {
        String actualResult1 = timeService.ConvertLongToString(timestamp1);
        String actualResult2 = timeService.ConvertLongToString(timestamp2);
        Assertions.assertEquals(dateTime1, actualResult1);
        Assertions.assertEquals(dateTime2, actualResult2);
    }

    @Test
    public void testConvertLongToLocalDateTime() {
        LocalDateTime actualResult1 = timeService.ConvertLongToLocalDateTime(timestamp1);
        LocalDateTime actualResult2 = timeService.ConvertLongToLocalDateTime(timestamp2);
        Assertions.assertEquals(localDateTime1, actualResult1);
        Assertions.assertEquals(localDateTime2, actualResult2);
    }

}