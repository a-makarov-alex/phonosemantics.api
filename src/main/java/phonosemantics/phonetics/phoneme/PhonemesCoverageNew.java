package phonosemantics.phonetics.phoneme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import phonosemantics.data.SoundsBank;
import phonosemantics.output.header.Header;

public class PhonemesCoverageNew {
    static final Logger userLogger = LogManager.getLogger(PhonemesService.class);

    //путь к шаблону всех согласных фонем
    private static final String INPUT_FILE_PATH = "D:\\JavaProjects2019\\word\\src\\main\\java\\output\\PhonemesCoverageExample.xlsx";


    public static ArrayList<Header> getPlaceHeaders() {
        ArrayList<Header> placeHeaders = new ArrayList<>();
        Workbook wb = null;
        try {
            InputStream inputStream = new FileInputStream(INPUT_FILE_PATH);
            wb = WorkbookFactory.create(inputStream);
            userLogger.info("example file is opened");
            inputStream.close();
        } catch (
                IOException e) {
            userLogger.error(e.toString());
        }

        Sheet sheet = wb.getSheetAt(1);
        Header previousHeader = null;
        int width = 1;

        for (int i = 0; i < 2; i++) {
            Row r = sheet.getRow(i);
            for (int j=1; j <= 24; j++) {
                Cell c = r.getCell(j);
                if (c == null) {
                    width++;
                } else {
                    if (c.getCellType().equals(CellType.BLANK)) {
                        width++;
                    } else {
                        if (previousHeader != null) {

                            previousHeader.setWidth(width);
                            width = 1;
                            placeHeaders.add(previousHeader);
                        }
                        previousHeader = new Header(i, j, c.getStringCellValue());
                    }
                }
            }
        }
        previousHeader.setWidth(width);
        placeHeaders.add(previousHeader);
        return placeHeaders;
    }

    public static ArrayList<Header> getMannerHeaders() {
        ArrayList<Header> mannerHeaders = new ArrayList<>();
        Workbook wb = null;
        try {
            InputStream inputStream = new FileInputStream(INPUT_FILE_PATH);
            wb = WorkbookFactory.create(inputStream);
            userLogger.info("example file is opened");
            inputStream.close();
        } catch (
                IOException e) {
            userLogger.error(e.toString());
        }

        Sheet sheet = wb.getSheetAt(1);

        for (int i = 2; i <= 14; i++) {
            Row r = sheet.getRow(i);
            Cell c = r.getCell(0);
            mannerHeaders.add(new Header(i, 0, c.getStringCellValue()));
        }
        return mannerHeaders;
    }

    public static ArrayList<Object> getConsonantsParameters() {
        ArrayList<Object> phTypes = new ArrayList<>();
        for (Map.Entry<Object, Integer> entry : SoundsBank.getAllPhonotypes().entrySet()) {
            phTypes.add(entry.getKey());
        }
        return phTypes;
    }

    public static ArrayList<PhonemeInTable> getAllPhonemesList() {
        Workbook wb = null;
        try {
            InputStream inputStream = new FileInputStream(INPUT_FILE_PATH);
            wb = WorkbookFactory.create(inputStream);
            userLogger.info("example file is opened");
            inputStream.close();
        } catch (
                IOException e) {
            userLogger.error(e.toString());
        }

        CoverageSheet vowelsSheet = new CoverageSheet(wb.getSheetAt(0), 2, 8, 1, 6);
        CoverageSheet consonantsSheet = new CoverageSheet(wb.getSheetAt(1), 2, 14, 1, 24);

        ArrayList<PhonemeInTable> allPhonemesInTable = new ArrayList<>();

        for (int i = consonantsSheet.firstRow; i <= consonantsSheet.lastRow; i++) {
            Row r = consonantsSheet.sheet.getRow(i);
            for (int j = consonantsSheet.firstCol; j <= consonantsSheet.lastCol; j++) {
                Cell c = r.getCell(j);
                if (c == null) {
                    allPhonemesInTable.add(new PhonemeInTable("", i, j));
                    //userLogger.warn("coord null" + i + " " + j);
                }
                else {
                    PhonemeInTable ph = new PhonemeInTable(c.getStringCellValue(), i, j);
                    // define if phoneme is recognized by program
                    if (SoundsBank.getInstance().find(c.getStringCellValue()) != null) {
                        ph.setRecognized(true);
                    }
                    allPhonemesInTable.add(ph);
                }
            }
        }
        return allPhonemesInTable;
    }
}

class CoverageSheet {
    Sheet sheet;
    // Границы таблицы звуков для осмотра
    int firstRow;
    int lastRow;
    int firstCol;
    int lastCol;

    public CoverageSheet(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        this.sheet = sheet;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }
}
