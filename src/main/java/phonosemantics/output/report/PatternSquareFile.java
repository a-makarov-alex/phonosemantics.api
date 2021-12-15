package phonosemantics.output.report;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import phonosemantics.phonetics.PatternSquare;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.Strident;
import phonosemantics.word.Word;
import phonosemantics.word.wordlist.WordList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
        createOutputFile(wl.getList());
    }

    // Для юнит тестов
    public PatternSquareFile(List<String> words) {
        this.title = "patternSquare_test";
        this.filePath = OUTPUT_DIRECTORY + title + ".xlsx";
        this.wb = new XSSFWorkbook();
        List<Word> list = new ArrayList<>();
        for (String w : words) {
            Word word = new Word(w);
            list.add(word);
        }
        createOutputFile(list);
    }

    // Для юнит тестов
    public PatternSquareFile(String word) {
        this.title = "patternSquare_test";
        this.filePath = OUTPUT_DIRECTORY + title + ".xlsx";
        this.wb = new XSSFWorkbook();
        List<Word> list = new ArrayList<>();
        Word w = new Word(word);
        list.add(w);
        createOutputFile(list);
    }

    private void createOutputFile(List<Word> words) {
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
            PatternSquare patternSquare = new PatternSquare(words);
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
                    //userLogger.info(outerSubtype.getKey());

                    for (Map.Entry<String, Map<String, Integer>> innerType : outerSubtype.getValue().entrySet()) {
                        for (Map.Entry<String, Integer> innerSubtype : innerType.getValue().entrySet()) {
                            Cell cell = sheet.getRow(rowNum).getCell(colNum);
                            cell.setCellValue(innerSubtype.getValue());

                            // Заливка серым областей со значением NOT_APPLICABLE, т.к. это не показательно для анализа
                            if (innerSubtype.getKey().equals(String.valueOf(Strident.NOT_APPLICABLE)) ||
                                    outerSubtype.getKey().equals(String.valueOf(Strident.NOT_APPLICABLE))) {
                                CellStyle cellStyle = wb.createCellStyle();
                                short color = IndexedColors.GREY_80_PERCENT.getIndex();
                                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                cellStyle.setFillForegroundColor(color);
                                cell.setCellStyle(cellStyle);
                            }
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

    // Используется для юнит тестов.
    // Проверяется, что в файле присутствуют заданные паттерны
    public boolean checkPatternFoundInFile(Object firstParam, Object secondParam) {
        //TODO NOT_APPLICABLE и TRUE-FALSE будут конфликтовать.
        //TODO придумать, как тестировать
        int headerColumnNum = 2;
        int headerRowNum = 2;
        Sheet sheet = wb.getSheetAt(0);
        int lastRow = sheet.getLastRowNum();
        int lastCol = sheet.getRow(headerRowNum).getLastCellNum();

        int targetRowNum = 0;
        int targetColNum = 0;

        for (int i=2; i < lastRow; i++) {
            String headerVert = sheet.getRow(i).getCell(headerColumnNum).getStringCellValue();
            if (headerVert.equals(String.valueOf(firstParam))) {
                targetRowNum = i;
                userLogger.info("TARGET ROW FOR " + firstParam + " = " + (i + 1));
            }
        }

        for (int i=2; i < lastCol; i++) {
            String headerHor = sheet.getRow(headerRowNum).getCell(i).getStringCellValue();
            if (headerHor.equals(String.valueOf(secondParam))) {
                targetColNum = i;
                userLogger.info("TARGET COL FOR " + secondParam + " = " + (i + 1));
            }
        }
        if (targetColNum == 0 || targetRowNum == 0) {
            userLogger.error("HEADERS NOT FOUND FOR PATTERN " + firstParam + " " + secondParam);
        }
        double value = sheet.getRow(targetRowNum).getCell(targetColNum).getNumericCellValue();
        userLogger.info("RESULT: " + value + " FOR PATTERN " + firstParam + " " + secondParam);
        return value > 0;
    }

    public boolean assertValidResult(String word) {
        String pathToJsonDir = "src/main/resources/json/phonemes/";
        Word w = new Word(word);
        List<String> tr = w.getTranscription();

        DistinctiveFeatures df1 = null;
        DistinctiveFeatures df2 = null;

        try {
            Gson gson = new Gson();
            // Получаем параметры фонем из json файлов
            String filePath1 = pathToJsonDir + tr.get(0) + ".json";
            String filePath2 = pathToJsonDir + tr.get(1) + ".json";

            PhonemeInTable ph1 = gson.fromJson(new FileReader(filePath1), PhonemeInTable.class);
            PhonemeInTable ph2 = gson.fromJson(new FileReader(filePath2), PhonemeInTable.class);

            df1 = ph1.getDistinctiveFeatures();
            df2 = ph1.getDistinctiveFeatures();

        } catch (FileNotFoundException e) {
            userLogger.error("FILE NOT FOUND: " + e.toString());
        }


        // 1. при составлении файла запилить мапу с номерами колонок-столбцов параметров. Стандартный шаблон
        // 2. закидывать  DF объект в метод и получать в ответ массив из столбцов-колонок
        // запомнить вертикальные-горизонтальные заголовки как массивы int (номер строки-столца)
        // найти все пересечения массивов
        // TODO не забыть про результат
        return true;
    }
}
