package phonosemantics.output.header;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import phonosemantics.phonetics.PhonemesBank;


public class HeadersForUI {
    static final Logger userLogger = LogManager.getLogger(HeadersForUI.class);

    /**
     * METHOD IS REQUIRED FOR UI
     * IN ORDER TO GET HEADERS FOR CONSONANT AND VOWEL TABLES.
     * RESPONSE CONTAINS HEADERS (X,Y) POSITIONS IN TABLE.
     * **/
    public static List<Header> getHeaders(String distFeature) {
        List<Header> headers = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(PhonemesBank.INPUT_FILE_PATH);
             Workbook wb = WorkbookFactory.create(inputStream)
        ){
            userLogger.info("example file is opened");
            distFeature = distFeature.toLowerCase();
            Sheet sheet;
            Header previousHeader = null;
            int width = 1;
            int iStart = 0;
            int iFinish = 0;

            if (distFeature.equals("height") || distFeature.equals("manner")) {
                if (distFeature.equals("height")) {
                    iStart = 2;
                    iFinish = 8;
                    sheet = wb.getSheetAt(0);
                } else {
                    iStart = 2;
                    iFinish = 14;
                    sheet = wb.getSheetAt(1);
                }

                // Go through file
                for (int i = iStart; i <= iFinish; i++) {
                    Row r = sheet.getRow(i);
                    Cell c = r.getCell(0);
                    headers.add(new Header(i, 0, c.getStringCellValue()));
                }
            }

            if (distFeature.equals("backness") || distFeature.equals("place")) {
                if (distFeature.equals("backness")) {
                    iStart = 1;
                    iFinish = 6;
                    sheet = wb.getSheetAt(0);
                } else {
                    iStart = 1;
                    iFinish = 24;
                    sheet = wb.getSheetAt(1);
                }

                // Go through file
                for (int i = 0; i < 2; i++) {
                    Row r = sheet.getRow(i);
                    for (int j = iStart; j <= iFinish; j++) {
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
                                    headers.add(previousHeader);
                                }
                                previousHeader = new Header(i, j, c.getStringCellValue());
                            }
                        }
                    }
                }
                if (previousHeader != null) {
                    previousHeader.setWidth(width);
                } else {
                    userLogger.error("previous header is null");
                }
                headers.add(previousHeader);
            }
        } catch (IOException e) {
            userLogger.error(e.toString());
        }
        return headers;
    }
}



