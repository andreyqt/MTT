package tutorial.mtt.service;

import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MTTExcelService extends AbstractExcelService<MonkeyTypeTest> {

    @Setter
    private static String path = "C:\\Users\\User\\Documents\\MTT\\tests.xlsx";
    private final TimeService timeService;

    public MTTExcelService(TimeService timeService) {
        super(path);
        this.timeService = timeService;
    }

    public void addTestToFile(MonkeyTypeTest test, String path, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fis);
        fis.close();
        Sheet sheet = wb.getSheet(sheetName);
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum + 1);
        writeToRow(row, test);
        FileOutputStream fos = new FileOutputStream(path);
        wb.write(fos);
        fos.close();
    }

    public MonkeyTypeTest getTestFromFile(String path, String sheetName, int rowNum) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fis);
        fis.close();
        MonkeyTypeTest test = new MonkeyTypeTest();
        Sheet sheet = wb.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        return readFromRow(row);
    }

    @Override
    public void writeToRow(Row row, MonkeyTypeTest test) {
        Cell[] cells = createCells(row);
        cells[0].setCellValue(test.getWpm());
        cells[1].setCellValue(test.getAcc());
        cells[2].setCellValue(test.getMode());
        cells[3].setCellValue(test.getMode2());
        cells[4].setCellValue(test.getCharStats().getFirst());
        cells[5].setCellValue(timeService.ConvertLongToString(test.getTimestamp()));
        cells[6].setCellValue(test.getTestDuration());
        alignToCenter(row);
    }

    @Override
    public MonkeyTypeTest readFromRow(Row row) {
        MonkeyTypeTest test = new MonkeyTypeTest();
        test.setWpm(row.getCell(0).getNumericCellValue());
        test.setAcc(row.getCell(1).getNumericCellValue());
        test.setMode(row.getCell(2).getStringCellValue());
        test.setMode2(row.getCell(3).getStringCellValue());
        double charStats = row.getCell(4).getNumericCellValue();
        test.setCharStats(List.of((int) charStats));
        String str_time = row.getCell(5).getStringCellValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str_time, formatter);
        Long timestamp = timeService.convertLocalDateTimeToLong(dateTime);
        test.setTimestamp(timestamp);
        test.setTestDuration(row.getCell(6).getNumericCellValue());
        return test;
    }

    @Override
    public Sheet createSheetAndHeader(Workbook wb, String name) {
        Sheet sheet = wb.createSheet(name);
        createHeader(sheet);
        return sheet;
    }

    @Override
    public String getSheetName() {
        return LocalDateTime.now().toLocalDate().toString();
    }

    @Override
    public boolean ifSheetExists(Workbook workbook) {
        String date = LocalDateTime.now().toLocalDate().toString();
        return workbook.getSheet(date) != null;
    }

    public void createHeader(Sheet sheet) {
        sheet.setColumnWidth(5, 5500);
        sheet.setColumnWidth(6, 3500);
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Wpm");
        row.createCell(1).setCellValue("Acc");
        row.createCell(2).setCellValue("Mode");
        row.createCell(3).setCellValue("Mode2");
        row.createCell(4).setCellValue("Chars");
        row.createCell(5).setCellValue("Timestamp");
        row.createCell(6).setCellValue("Test duration");
        alignToCenter(row);
    }

    private Cell[] createCells(Row row) {
        Cell[] cells = new Cell[7];
        for (int i = 0; i < 7; i++) {
            cells[i] = row.createCell(i);
        }
        return cells;
    }
}