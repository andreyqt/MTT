package tutorial.mtt.service;

import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import tutorial.mtt.dto.DailyResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DailyResultExcelService extends AbstractExcelService<DailyResult> {

    @Setter
    private static String path = "C:\\Users\\User\\Documents\\MTT\\average results.xlsx";

    public DailyResultExcelService() {
        super(path);
    }

    @Override
    protected Sheet createSheetAndHeader(Workbook workbook, String name) {
        Sheet sheet = workbook.getSheet(name);
        sheet.setColumnWidth(0, 4000);
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Date");
        row.createCell(1).setCellValue("Average speed");
        row.createCell(2).setCellValue("Total time");
        row.createCell(3).setCellValue("Number of tests");
        alignToCenter(row);
        return sheet;
    }

    @Override
    protected boolean ifSheetDoesNotExist(Workbook workbook) {
        String year = getSheetName();
        return workbook.getSheet(year) == null;
    }

    @Override
    protected void writeToRow(Row row, DailyResult result) {
        Cell[] cells = createCells(row);
        cells[0].setCellValue(result.getDate().toString());
        cells[1].setCellValue(result.getAverageSpeed());
        cells[2].setCellValue(result.getTotalTime());
        cells[3].setCellValue(result.getNumberOfTests());
        alignToCenter(row);
    }

    @Override
    protected DailyResult readFromRow(Row row) {
        DailyResult result = new DailyResult();
        String date = row.getCell(0).getStringCellValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        result.setDate(localDate);
        result.setAverageSpeed(row.getCell(1).getNumericCellValue());
        result.setTotalTime(row.getCell(2).getNumericCellValue());
        double numberTests = row.getCell(3).getNumericCellValue();
        result.setNumberOfTests((int) numberTests);
        return result;
    }

    @Override
    protected String getSheetName() {
        return String.valueOf(LocalDate.now().getYear());
    }

    private Cell[] createCells(Row row) {
        Cell[] cells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            cells[i] = row.createCell(i);
        }
        return cells;
    }

}
