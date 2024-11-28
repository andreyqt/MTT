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
    private String path;
    private String sheetName;

    @BeforeEach
    public void setUp() {
        path = "C:\\Users\\User\\Documents\\MTT\\mockito.xlsx";
        sheetName = "test";
        TimeService timeService = new TimeService();
        excelService = new MTTExcelService(timeService);
        test = MonkeyTypeTest.builder()
                .wpm(100.91)
                .acc(95.83)
                .mode("quote")
                .mode2("1400")
                .charStats(List.of(100))
                .timestamp(1731400141000L)
                .testDuration(30.)
                .build();
    }

    @Test
    public void testAddToFile() throws IOException {
        excelService.addToFile(test, path, sheetName);
    }

    @Test
    public void testReadFromFile() throws IOException {
        MonkeyTypeTest actualResult = excelService.readFromFile(path, sheetName, 0);
        Assertions.assertEquals(test.getWpm(), actualResult.getWpm());
        Assertions.assertEquals(test.getAcc(), actualResult.getAcc());
        Assertions.assertEquals(test.getMode(), actualResult.getMode());
        Assertions.assertEquals(test.getMode2(), actualResult.getMode2());
        Assertions.assertEquals(test.getCharStats(), actualResult.getCharStats());
        Assertions.assertEquals(test.getTimestamp(), actualResult.getTimestamp());
        Assertions.assertEquals(test.getTestDuration(), actualResult.getTestDuration());
    }
}