package tutorial.mtt.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MTTMapperTest {

    private MonkeyTypeTestMapperImpl mapper;
    private MonkeyTypeTestDTO monkeyTypeTestDTO;
    private MonkeyTypeTest monkeyTypeTest;
    private LocalDateTime localDateTime;
    private List<String> charStats;
    private String duration;
    private String mode;
    private String mode2;
    private Long timestamp;
    private double wpm;
    private int acc;

    @BeforeEach
    void setUp() {
        mapper = new MonkeyTypeTestMapperImpl();
        charStats = List.of("50", "0", "0", "0");
        timestamp = 1700010123000L;
        duration = "5.5";
        mode = "quote";
        mode2 = "long";
        wpm = 100;
        acc = 95;
        monkeyTypeTest = MonkeyTypeTest.builder()
                .wpm(wpm).acc(acc)
                .mode(mode).mode2(mode2)
                .timestamp(timestamp)
                .testDuration(duration)
                .charStats(charStats)
                .build();
        localDateTime = LocalDateTime.of(2023, 11, 15, 4,2,3);
    }

    @Test
    public void testMapTimestampToLocalDateTime() {
        monkeyTypeTestDTO = mapper.toDto(monkeyTypeTest);
        Assertions.assertNotNull(monkeyTypeTestDTO);
        Assertions.assertEquals(wpm, monkeyTypeTestDTO.getWpm());
        Assertions.assertEquals(acc, monkeyTypeTestDTO.getAcc());
        Assertions.assertEquals(mode, monkeyTypeTestDTO.getMode());
        Assertions.assertEquals(mode2, monkeyTypeTestDTO.getMode2());
        Assertions.assertEquals(duration, monkeyTypeTestDTO.getTestDuration());
        Assertions.assertEquals(charStats.getFirst(), monkeyTypeTestDTO.getChars());
        Assertions.assertEquals(localDateTime, monkeyTypeTestDTO.getDateTime());
    }

}