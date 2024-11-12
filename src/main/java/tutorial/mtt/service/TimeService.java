package tutorial.mtt.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {

    private static final ZoneId ZONE_ID_MOSCOW = ZoneId.of("Europe/Moscow");

    public long getMidnightTimestamp() {
        LocalDate today = LocalDate.now(ZONE_ID_MOSCOW);
        ZonedDateTime zdt = today.atStartOfDay(ZONE_ID_MOSCOW);
        return zdt.toInstant().toEpochMilli();
    }

    public long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_ID_MOSCOW).toInstant().toEpochMilli();
    }

    public long getMidnightOfTheDay(LocalDate date) {
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZONE_ID_MOSCOW);
        return zdt.toInstant().toEpochMilli();
    }

    public String ConvertLongToString(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_ID_MOSCOW);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public LocalDateTime ConvertLongToLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_ID_MOSCOW);
    }
}