package tutorial.mtt.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tutorial.mtt.dto.DailyResult;

import java.io.IOException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class DailyResultExcelServiceTest {

    private DailyResultExcelService excelService;
    private DailyResult result;
    private String sheetName;
    private String path;

    @BeforeEach
    public void setUp() {
        path = "C:\\Users\\User\\Documents\\MTT\\mockito.xlsx";
        sheetName = "avg";
        DailyResultExcelService.setPath(path);
        excelService = new DailyResultExcelService();
        result = DailyResult.builder()
                .averageSpeed(95.01)
                .date(LocalDate.of(2010, 1,1))
                .numberOfTests(100)
                .averageAcc(97.01)
                .totalTime(23.05)
                .build();
    }

    @Test
    public void testSave() throws IOException {
        excelService.addToFile(result, path, sheetName);
    }

    @Test
    public void testReadFromFile() throws IOException {
        DailyResult actualResult = excelService.readFromFile(path, sheetName, 1);
        Assertions.assertEquals(result.getDate(), actualResult.getDate());
        Assertions.assertEquals(result.getAverageSpeed(), actualResult.getAverageSpeed());
        Assertions.assertEquals(result.getNumberOfTests(), actualResult.getNumberOfTests());
        Assertions.assertEquals(result.getTotalTime(), actualResult.getTotalTime());
        Assertions.assertEquals(result.getAverageAcc(), actualResult.getAverageAcc());
    }
}
