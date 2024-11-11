package tutorial.mtt.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension .class)
public class MTTMapperTest {

    private MonkeyTypeTestMapper mapper = Mappers.getMapper(MonkeyTypeTestMapper.class);
    private Long timeStamp;
    private MonkeyTypeTest monkeyTypeTest;
    private MonkeyTypeTestDTO monkeyTypeTestDTO;
    private String expectedResult;

    @BeforeEach
    void setUp() {
        monkeyTypeTest = new MonkeyTypeTest();
        timeStamp = 1700010123000L;
        monkeyTypeTest.setTimestamp(timeStamp);
        expectedResult = "2023-11-15 04:02:03";
    }

    @Test
    public void testMapTimestampToLocalDateTime() {
        monkeyTypeTestDTO = mapper.toDto(monkeyTypeTest);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String actualResult = monkeyTypeTestDTO.getDateTime().format(formatter);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testMapTimestampFromMonkeyTypeToLocalDateTime() {
        timeStamp = 1730713252000L;
        monkeyTypeTest.setTimestamp(timeStamp);
        expectedResult = "2024-11-04 12:40";
        monkeyTypeTestDTO = mapper.toDto(monkeyTypeTest);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String actualResult = monkeyTypeTestDTO.getDateTime().format(formatter);
        Assertions.assertEquals(expectedResult, actualResult);
    }

}
