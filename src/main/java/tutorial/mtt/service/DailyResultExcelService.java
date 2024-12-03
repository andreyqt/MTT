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
        Sheet sheet = workbook.createSheet(name);
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 3500);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Date");
        row.createCell(1).setCellValue("Average speed");
        row.createCell(2).setCellValue("Total time");
        row.createCell(3).setCellValue("Number of tests");
        row.createCell(4).setCellValue("Average accuracy");
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
        cells[4].setCellValue(result.getAverageAcc());
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
        result.setAverageAcc(row.getCell(4).getNumericCellValue());
        return result;
    }

    @Override
    protected String getSheetName() {
        return String.valueOf(LocalDate.now().getYear());
    }

    private Cell[] createCells(Row row) {
        Cell[] cells = new Cell[5];
        for (int i = 0; i < 5; i++) {
            cells[i] = row.createCell(i);
        }
        return cells;
    }

}
