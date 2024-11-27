package tutorial.mtt.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.io.IOException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MTTExcelServiceTest {

    private MTTExcelService excelService;
    private MonkeyTypeTest test;

    @BeforeEach
    public void setUp() {
        String testPath = "C:\\Users\\User\\Documents\\MTT\\mockito.xlsx";
        TimeService timeService = new TimeService();
        excelService = new MTTExcelService(timeService);
        excelService.setPath(testPath);
        test = MonkeyTypeTest.builder()
                .wpm(100)
                .acc(95)
                .mode("quote")
                .mode2("1400")
                .charStats(List.of(100))
                .timestamp(1731400141000L)
                .testDuration(30.)
                .build();
    }

    @Test
    public void testAddTest() throws IOException {
        excelService.addTest(test);
    }

    @Test
    public void testGetTest() throws IOException {
        MonkeyTypeTest actualResult = excelService.getTest(1);
        Assertions.assertEquals(test.getWpm(), actualResult.getWpm());
        Assertions.assertEquals(test.getAcc(), actualResult.getAcc());
        Assertions.assertEquals(test.getMode(), actualResult.getMode());
        Assertions.assertEquals(test.getMode2(), actualResult.getMode2());
        Assertions.assertEquals(test.getCharStats(), actualResult.getCharStats());
        Assertions.assertEquals(test.getTimestamp(), actualResult.getTimestamp());
        Assertions.assertEquals(test.getTestDuration(), actualResult.getTestDuration());
    }
}