package phonosemantics.output.report;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import phonosemantics.phonetics.PatternSquare;
import phonosemantics.word.wordlist.WordList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class PatternSquareFile {
    private static final Logger userLogger = Logger.getLogger(PatternSquareFile.class);

    private String title;
    private String filePath;
    private Workbook wb;

    private static final String OUTPUT_DIRECTORY = Paths.get("src", "main", "java", "phonosemantics", "output").toString();

    public PatternSquareFile(WordList wl) {
        this.title = "patternSquare_" + wl.getMeaning();
        this.filePath = OUTPUT_DIRECTORY + title + ".xlsx";
        this.wb = new XSSFWorkbook();
        createOutputFile(wl);
    }

    private void createOutputFile(WordList wl) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);
            Sheet sheet = wb.createSheet();

            // TODO это костыль
            for (int i = 0; i < 100; i++) {
                sheet.createRow(i);
                for (int j = 0; j < 100; j++) {
                    sheet.getRow(i).createCell(j);
                }
            }
            PatternSquare patternSquare = new PatternSquare(wl);
            int startCol = 1;
            int startRow = 3;
            int rowNum = startRow;

            // ЗАГОЛОВКИ
            // заголовки верт. ТИП
            for (Map.Entry<String, Map<String, Map<String, Map<String, Integer>>>> outerType : patternSquare.getPatternSquare().entrySet()) {
                int colNum = startCol;

                // заголовки верт. ПОДТИП
                for (Map.Entry<String, Map<String, Map<String, Integer>>> outerSubtype : outerType.getValue().entrySet()) {
                    // ТИП
                    if (colNum == startCol) {
                        sheet.getRow(rowNum).getCell(colNum).setCellValue(outerType.getKey());
                        sheet.getRow(colNum).getCell(rowNum).setCellValue(outerType.getKey());
                        colNum++;
                    }

                    // ПОДТИП
                    sheet.getRow(rowNum).getCell(colNum).setCellValue(String.valueOf(outerSubtype.getKey()));
                    sheet.getRow(colNum).getCell(rowNum).setCellValue(String.valueOf(outerSubtype.getKey()));
                    rowNum++;
                }
            }

            // ЗНАЧЕНИЯ
            rowNum = startRow - 1;
            for (Map.Entry<String, Map<String, Map<String, Map<String, Integer>>>> outerType : patternSquare.getPatternSquare().entrySet()) {
                for (Map.Entry<String, Map<String, Map<String, Integer>>> outerSubtype : outerType.getValue().entrySet()) {
                    int colNum = startCol + 2;
                    rowNum++;
                    userLogger.info(outerSubtype.getKey());

                    for (Map.Entry<String, Map<String, Integer>> innerType : outerSubtype.getValue().entrySet()) {
                        for (Map.Entry<String, Integer> innerSubtype : innerType.getValue().entrySet()) {
                            userLogger.info(innerSubtype.getKey() + " " + rowNum + " " + colNum);
                            sheet.getRow(rowNum).getCell(colNum).setCellValue(innerSubtype.getValue());
                            colNum++;
                        }
                    }
                }
            }

            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {

        }
    }
}
