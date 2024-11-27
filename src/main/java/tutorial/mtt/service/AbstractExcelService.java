package tutorial.mtt.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractExcelService<T> {

    protected final String path;

    public void save(List<T> tList) throws IOException {
        Workbook wb = getWorkbookFromFile();
        Sheet sheet;
        int rowIndex = 0;
        if (!ifSheetExists(wb)) {
            sheet = createSheetAndHeader(wb, getSheetName());
            rowIndex++;
            for (T t : tList) {
                Row row = sheet.createRow(rowIndex);
                writeToRow(row, t);
                rowIndex++;
            }
            writeWorkbookToFile(wb);
        } else {
            sheet = wb.getSheet(getSheetName());
            rowIndex = sheet.getLastRowNum() + 1;
            for (int i = rowIndex - 1; i <= tList.size(); i++) {
                Row row = sheet.createRow(rowIndex);
                writeToRow(row, tList.get(i));
            }
            writeWorkbookToFile(wb);
        }
    }

    protected Workbook getWorkbookFromFile() throws IOException {
        FileInputStream fis = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fis);
        fis.close();
        return wb;
    }

    protected void writeWorkbookToFile(Workbook wb) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        wb.write(fos);
        wb.close();
    }

    protected void alignToCenter(Row row) {
        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        row.setRowStyle(style);
    }

    protected abstract Sheet createSheetAndHeader(Workbook workbook, String name);

    protected abstract boolean ifSheetExists(Workbook workbook);

    protected abstract void writeToRow(Row row, T t);

    protected abstract T readFromRow(Row row);

    protected abstract String getSheetName();
}
