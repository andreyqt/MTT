package tutorial.mtt.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class TimeService {

    public long getMidnightTimestamp() {
        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        ZonedDateTime zdt = today.atStartOfDay(ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zdt.toInstant().getEpochSecond();
    }

    public long getMidnightOfTheDay(LocalDate date) {
        LocalDateTime localDateTime = LocalDateTime.of(date.getYear(),
                date.getMonth(), date.getDayOfMonth(), 0, 0);
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zdt.toInstant().getEpochSecond();
    }
}
