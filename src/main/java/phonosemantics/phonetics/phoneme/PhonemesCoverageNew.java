package phonosemantics.phonetics.phoneme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PhonemesCoverageNew {
    static final Logger userLogger = LogManager.getLogger(PhonemesService.class);

    //путь к шаблону всех согласных фонем
    private static final String INPUT_FILE_PATH = "D:\\JavaProjects2019\\word\\src\\main\\java\\output\\PhonemesCoverageExample.xlsx";



    public static ArrayList<PhonemeInTable> getAllPhonemesList() {
        Workbook wb = null;
        try {
            InputStream inputStream = new FileInputStream(INPUT_FILE_PATH);
            wb = WorkbookFactory.create(inputStream);
            userLogger.info("example file is opened");
        } catch (
                IOException e) {
            userLogger.error(e.toString());
        }

        CoverageSheet vowelsSheet = new CoverageSheet(wb.getSheetAt(0), 2, 8, 1, 6);
        CoverageSheet consonantsSheet = new CoverageSheet(wb.getSheetAt(1), 2, 14, 1, 24);

        ArrayList<PhonemeInTable> allPhonemesInTable = new ArrayList<>();

        for (int i = consonantsSheet.firstRow; i < consonantsSheet.lastRow; i++) {
            Row r = consonantsSheet.sheet.getRow(i);
            for (int j = consonantsSheet.firstCol; j < consonantsSheet.lastCol; j++) {
                Cell c = r.getCell(j);
                if (c == null) {
                    allPhonemesInTable.add(new PhonemeInTable("", i, j));
                    //userLogger.warn("coord null" + i + " " + j);
                }
                else {
                    allPhonemesInTable.add(new PhonemeInTable(c.getStringCellValue(), i, j));
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

