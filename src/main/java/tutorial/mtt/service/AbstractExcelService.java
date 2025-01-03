package tutorial.mtt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractExcelService<T> {

    protected final String path;

    public void save(List<T> tList) throws IOException {
        Workbook wb = getWorkbookFromFile();
        Sheet sheet;
        int rowIndex = 0;
        if (ifSheetDoesNotExist(wb)) {
            sheet = createSheetAndHeader(wb, getSheetName());
            rowIndex++;
            for (T t : tList) {
                Row row = sheet.createRow(rowIndex);
                writeToRow(row, t);
                rowIndex++;
            }
            writeWorkbookToFile(wb);
            log.info("New sheet was created and {} tests were added", tList.size());
        } else {
            sheet = wb.getSheet(getSheetName());
            rowIndex = sheet.getLastRowNum() + 1;
            for (int i = rowIndex - 1; i < tList.size(); i++) {
                Row row = sheet.createRow(rowIndex);
                writeToRow(row, tList.get(i));
            }
            writeWorkbookToFile(wb);
            log.info("Test(s) was(were) added to sheet {}", getSheetName());
        }
    }

    public void save(T t) throws IOException {
        Workbook wb = getWorkbookFromFile();
        Sheet sheet;
        if (ifSheetDoesNotExist(wb)) {
            sheet = createSheetAndHeader(wb, getSheetName());
            Row row = sheet.createRow(1);
            writeToRow(row, t);
            writeWorkbookToFile(wb);
            log.info("New sheet was created and daily result added");
        } else {
            sheet = wb.getSheet(getSheetName());
            int rowIndex = sheet.getLastRowNum();
            Row row = sheet.createRow(rowIndex + 1);
            writeToRow(row, t);
            writeWorkbookToFile(wb);
            log.info("Daily result was added to sheet {}", getSheetName());
        }
    }

    public void addToFile(T t, String path, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(fis);
        fis.close();
        Sheet sheet = workbook.getSheet(sheetName);
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRowNum + 1);
        writeToRow(row, t);
        FileOutputStream fos = new FileOutputStream(path);
        workbook.write(fos);
        fos.close();
    }

    public T readFromFile(String path, String sheetName, int rowNum) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fis);
        fis.close();
        Sheet sheet = wb.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        return readFromRow(row);
    }

    protected Workbook getWorkbookFromFile() throws IOException {
        ZipSecureFile.setMinInflateRatio(0);
        FileInputStream fis = new FileInputStream(path);
        Workbook wb = new XSSFWorkbook(fis);
        fis.close();
        return wb;
    }

    protected void writeWorkbookToFile(Workbook wb) throws IOException {
        FileOutputStream fos = new FileOutputStream(path);
        wb.write(fos);
        wb.close();
        fos.close();
    }

    protected void alignToCenter(Row row) {
        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        row.cellIterator().forEachRemaining(cell -> cell.setCellStyle(style));
    }

    protected abstract Sheet createSheetAndHeader(Workbook workbook, String name);

    protected abstract boolean ifSheetDoesNotExist(Workbook workbook);

    protected abstract void writeToRow(Row row, T t);

    protected abstract T readFromRow(Row row);

    protected abstract String getSheetName();

}
