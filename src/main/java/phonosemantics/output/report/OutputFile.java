package phonosemantics.output.report;

import lombok.Data;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Sample;
import phonosemantics.statistics.Statistics;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OutputFile {
    private static final Logger userLogger = Logger.getLogger(OutputFile.class);

    private String title;
    private String filePath;
    private List<OutputFilePage> filePages;
    private int recordsCounter;
    private Workbook wb;
    private HashMap<Object, Sample> allSamplesSheet1 = null;

    private static final String OUTPUT_DIRECTORY = Paths.get("src", "main", "java", "phonosemantics", "output").toString();
    private static CellStyle headerCellStyle;
    public static CellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }
    public static void setHeaderCellStyle(CellStyle headerCellStyle) {
        OutputFile.headerCellStyle = headerCellStyle;
    }

    // TODO to enum
    private static final String SHEET_VOW = "Vowels";
    private static final String SHEET_CONS_MANNER = "Cons manner";
    private static final String SHEET_CONS_PLACE = "Cons place";

    public OutputFile(String title) {
        this.title = title;
        this.recordsCounter = 0;
        this.filePath = OUTPUT_DIRECTORY + title + ".xlsx";
        this.wb = new XSSFWorkbook();
        this.filePages = new ArrayList<>();
        this.createOutputFile();
        userLogger.info("output file created");
    }

    private void createOutputFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);

            userLogger.info("specifying styles for headers");
            // Specify a style for headers
            headerCellStyle = wb.createCellStyle();
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Creating sheets
            userLogger.info("creating sheets");
            OutputFilePage page = new OutputFilePage(SHEET_VOW, 0, wb.createSheet(SHEET_VOW));
            //TODO добавить хедеры в page
            filePages.add(page);
            page = new OutputFilePage(SHEET_CONS_MANNER, 1, wb.createSheet(SHEET_CONS_MANNER));
            filePages.add(page);
            page = new OutputFilePage(SHEET_CONS_PLACE, 2, wb.createSheet(SHEET_CONS_PLACE));
            filePages.add(page);

            // Adding headers and saving them in variable
            userLogger.info("start adding headers");
            for (OutputFilePage pg : filePages) {
                pg.addCommonHeader();
            }
            //GeneralReportHeader.addVowelsHeader(filePages.get(0));
            //GeneralReportHeader.addMannerHeader(sheets.get(1));
            //GeneralReportHeader.addPlaceHeader(sheets.get(2));
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            userLogger.info("IOException caught in output");
        }
    }

    // Writes the worlist parsing and statistics counting result into the output file
    public void fillWithAll(List<WordList> wordLists) {
        writeFeatureHeadersToGeneralFile();
        for (WordList wordList : wordLists) {
            userLogger.info("writing to general file...");
            writeToGeneralFileAlternative(wordList);
        }
        //addMeanAndAverage(Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST);
    }

    // Заполнение отчёта данными
    public void fillWith(WordList wordList) {
        OutputFilePage page = this.getFilePages().get(0);
        page.getWordlists().add(wordList);
        if (!page.isHasHeaders()) {
            writeFeatureHeadersToGeneralFile();
        }
        userLogger.info("writing stats for wl " + wordList.getMeaning() + " to general file...");
        writeToGeneralFileAlternative(wordList);
        addMeanAndAverage(Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST);
    }

    /**
     * Добавляем заголовки фонетических признаков в отчёт
     */
    //TODO сюда же можно вынести и Common Headers
    private void writeFeatureHeadersToGeneralFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);

            // Берем любой вордлист и согласно структуре DistFeature вписываем заголовки
            WordList wordList = WordListService.getAllWordLists().get(0);
            Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> wlDistFeaturesStats = wordList.getDistFeatureStats();
            OutputFilePage vowelPage = this.getFilePages().get(0);
            Sheet sheet = vowelPage.getSheet();
            Map<Object, GeneralReportHeader> headersMap = vowelPage.getHeaders();
            int startCol = 3;
            int incrementCol = 0;
            int col = startCol + incrementCol;
            int row = 0;

            userLogger.info("start adding vowel headers");
            // Проходим по верхнему уровню заголовков
            for (Map.Entry<String, Map<Object, PhonemeInTable.DistFeatureStats>> highLevelEntry : wlDistFeaturesStats.entrySet()) {
                String highLevelHeader = highLevelEntry.getKey();
                headersMap.put(highLevelHeader, new GeneralReportHeader(row, col, highLevelHeader));
                this.createCell(0, row, col, highLevelHeader);
                int colMergeFrom = col;

                // Проходим по нижнему уровню заголовков
                for (Map.Entry<Object, PhonemeInTable.DistFeatureStats> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                    row+=2;
                    String lowLevelHeader = String.valueOf(lowLevelEntry.getKey());
                    headersMap.put(lowLevelHeader, new GeneralReportHeader(row, col, lowLevelHeader));
                    this.createCell(0, row, col, lowLevelHeader);

                    incrementCol++;
                    if (row > vowelPage.getLastRowNum()) {
                        vowelPage.setLastRowNum(row);
                    }
                    row = 0;
                    col = startCol + incrementCol;
                }
                sheet.addMergedRegion(new CellRangeAddress(0,1, colMergeFrom, col - 1));
            }
            wb.write(fileOut);
            userLogger.info("headers are written to general file");
            fileOut.close();
            vowelPage.setHasHeaders(true);

        } catch (IOException e) {
            userLogger.error("IOException caught: " + e.getStackTrace());
        }
    }

    /**
     * Вписываем данные для всех фонетических признаков в отчёт
     * @param wordList
     */
    private void writeToGeneralFileAlternative(WordList wordList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);

            Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> wlDistFeaturesStats = wordList.getDistFeatureStats();
            OutputFilePage vowelPage = this.getFilePages().get(0);
            Map<Object, GeneralReportHeader> headersMap = vowelPage.getHeaders();

            vowelPage.createVerticalHeaders(wordList);
            int startCol = 3;
            int incrementCol = 0;
            int col = startCol + incrementCol;
            int startRow = vowelPage.getLastRowNum();
            int row = startRow;

            userLogger.info("добавляем статистику DistFeatures в отчёт для вордлиста " + wordList.getMeaning() + "...");
            // Проходим по верхнему уровню заголовков
            for (Map.Entry<String, Map<Object, PhonemeInTable.DistFeatureStats>> highLevelEntry : wlDistFeaturesStats.entrySet()) {
                String highLevelHeader = highLevelEntry.getKey();
                int highLevelCol = col;

                // Проходим по нижнему уровню заголовков
                // Проверяем, что записываем данные в нужный столбик
                for (Map.Entry<Object, PhonemeInTable.DistFeatureStats> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                    String lowLevelHeader = String.valueOf(lowLevelEntry.getKey());

                    /* для дебага, ещё может пригодиться
                    userLogger.info(highLevelHeader + " " + lowLevelHeader);
                    userLogger.info("has in map high? " + headersMap.get(highLevelHeader));
                    userLogger.info("has in map low? " + headersMap.get(lowLevelHeader));
                    userLogger.info("high col=" + headersMap.get(highLevelHeader).getColumn());
                    userLogger.info("low col=" + headersMap.get(lowLevelHeader).getColumn());*/

                    // Все значения, кроме true/false/NOT_APPLICABLE уникальны, принадлежат разным Enum
                    // TODO не, ну это дичь, надо переписать!
                    if (headersMap.get(lowLevelHeader).getColumn() == col ||
                        (headersMap.get(highLevelHeader).getColumn() == highLevelCol &&
                            (lowLevelHeader.equals("true") ||
                             lowLevelHeader.equals("false")) ||
                             lowLevelHeader.equals("NOT_APPLICABLE") ||
                             lowLevelHeader.equals("MID")))
                    {
                        row++;
                        Double cellValue = lowLevelEntry.getValue().getPercentOfWordsWithFeature();
                        this.createCell(0, row, col, cellValue, "0.0%");

                        row++;
                        cellValue = lowLevelEntry.getValue().getPercentOfAllPhonemes();
                        this.createCell(0, row, col, cellValue, "0.0%");

                        row++;
                        cellValue = lowLevelEntry.getValue().getAverageFeatureInstancesPerWord();
                        this.createCell(0, row, col, cellValue, "0.0");
                    }
                    incrementCol++;
                    if (row > vowelPage.getLastRowNum()) {
                        vowelPage.setLastRowNum(row);
                    }
                    row = startRow;
                    col = startCol + incrementCol;
                }
            }
            // добавляем разделительную строку
            this.createCell(0, vowelPage.getLastRowNum() + 1, 0, " ");
            vowelPage.setLastRowNum(vowelPage.getLastRowNum() + 1);

            wb.write(fileOut);
            userLogger.info("vowel stats are written to general file");
            fileOut.close();

        } catch (IOException e) {
            userLogger.error("IOException caught: " + e.getStackTrace());
        }
    }

    public void createCell(int sheetNum, int row, int col, String value) {
        Sheet sheet = this.getFilePages().get(sheetNum).getSheet();
        if (sheet.getRow(row) == null) {
            sheet.createRow(row);
        }
        sheet.getRow(row).createCell(col);
        sheet.getRow(row).getCell(col).setCellStyle(OutputFile.getHeaderCellStyle());
        sheet.getRow(row).getCell(col).setCellValue(value);
        this.getFilePages().get(sheetNum).setSheet(sheet);
    }

    public void createCell(int sheetNum, int row, int col, Double value, String styleFormat) {
        Sheet sheet = this.getFilePages().get(sheetNum).getSheet();
        if (sheet.getRow(row) == null) {
            sheet.createRow(row);
        }
        sheet.getRow(row).createCell(col);
        sheet.getRow(row).getCell(col).setCellStyle(this.createCellStyleWithDataFormat(styleFormat));
        sheet.getRow(row).getCell(col).setCellValue(value);
        this.getFilePages().get(sheetNum).setSheet(sheet);
    }

    // TODO: удалить в GENERAL файле
    public CellStyle createCellStyleWithDataFormat(String format) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(wb.createDataFormat().getFormat(format));
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }

    private void writeBasicStats(Sheet sh, Statistics.KindOfStats kindOfStats, WordList wordList) {
        String meaning = wordList.getMeaning();
        int size = wordList.getList().size();

        int row = 3 + recordsCounter;
        int column = 2;
        if (row != 3) {
            sh.createRow(row);
        }

        Row r = sh.getRow(row);
        r.createCell(0).setCellValue(meaning);
        r.createCell(1).setCellValue(size);
        r.createCell(2);

        // TODO add more maps here
        ArrayList<HashMap<Object, GeneralReportHeader>> maps = new ArrayList<>();
        maps.add(GeneralReportHeader.vowSh);
        maps.add(GeneralReportHeader.consMannerSh);

        for (HashMap<Object, GeneralReportHeader> map : maps) {
            for (Map.Entry<Object, GeneralReportHeader> entry : map.entrySet()) {
                column = entry.getValue().getColumn();
                Cell c = sh.getRow(row).createCell(column);

                // Make cells format (percents etc.)
                DecimalFormat df = new DecimalFormat("#.#");
                String s1 = "";

                switch (kindOfStats) {
                    case WORDS_WITH_PHTYPE_PER_LIST: {
                        s1 = df.format(wordList.getPhTypeStatsMap().get(entry.getKey()).getPercentOfWordsWithPhType() * 100) + "% ";
                        break;
                    }
                    case PHTYPES_PER_LIST: {
                        s1 = df.format(wordList.getPhTypeStatsMap().get(entry.getKey()).getPercentOfPhonemesWithPhType() * 100) + "% ";
                        break;
                    }
                    case PHTYPES_AVERAGE_PER_WORD: {
                        s1 = df.format(wordList.getPhTypeStatsMap().get(entry.getKey()).getAveragePhTypePerWord()) + " ";
                        break;
                    }
                }

                String s2 = String.valueOf(wordList.getPhTypeStatsMap().get(entry.getKey()).getPotentialWordsWithPhType());
                Font font = wb.createFont();
                font.setTypeOffset(Font.SS_SUPER);
                RichTextString richString = new XSSFRichTextString(s1 + s2);
                richString.applyFont(s1.length(), s1.length() + s2.length(), font);

                c.setCellValue(richString);
            }
        }
    }

    // Добавляем медиану и среднее арифметическое
    private void addMeanAndAverage(Statistics.KindOfStats kindOfStats) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);
            OutputFilePage vowelPage = this.filePages.get(0);
            Sheet sh = vowelPage.getSheet();

            userLogger.info("подсчёт медианы и среднего арифметического для " + kindOfStats);
            // для экономии времени при ресчете статистики. DistFeatureName(Key)--List<Double>stats (Value)
            // структура аналогична wl.getDistFeatureStats()
            Map<String, Map<Object, List<Double>>> statsListsForDistFeatures = new HashMap<>();

            userLogger.info("инициализируем структуру для статистики, чтобы поместить туда данные");
            Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> map = vowelPage.getWordlists().get(0).getDistFeatureStats();
            for (Map.Entry<String, Map<Object, PhonemeInTable.DistFeatureStats>> highLevelEntry : map.entrySet()) {
                Map<Object, List<Double>> resultMapLow = new HashMap<>();
                for (Map.Entry<Object, PhonemeInTable.DistFeatureStats> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                    resultMapLow.put(lowLevelEntry.getKey(), new ArrayList<>());
                }
                statsListsForDistFeatures.put(highLevelEntry.getKey(), resultMapLow);
            }

            userLogger.info("заполняем структуру данными статистики для всех вордлистов в отчёте");
            for (WordList wl : vowelPage.getWordlists()) {
                for (Map.Entry<String, Map<Object, PhonemeInTable.DistFeatureStats>> highLevelEntry : wl.getDistFeatureStats().entrySet()) {
                    String highKey = highLevelEntry.getKey();
                    for (Map.Entry<Object, PhonemeInTable.DistFeatureStats> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                        Object lowKey = lowLevelEntry.getKey();
                        Double lowValue = lowLevelEntry.getValue().getPercentOfWordsWithFeature();
                        //userLogger.info("adding " + highKey + " " + lowKey + " " + lowValue);
                        statsListsForDistFeatures.get(highKey).get(lowKey).add(lowValue);
                    }
                }
            }

            // Самплы для ОДНОГО типа статистики
            userLogger.info("рассчёт сэмплов");
            Map<String, Map<Object, Sample>> allSamples = new HashMap<>();
            if (vowelPage.getWordlists().size() >= 4) {
                for (Map.Entry<String, Map<Object, List<Double>>> highLevelEntry : statsListsForDistFeatures.entrySet()) {
                    String highKey = highLevelEntry.getKey();
                    Map<Object, Sample> lowMap = new HashMap<>();
                    for (Map.Entry<Object, List<Double>> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                        Object lowKey = lowLevelEntry.getKey();
                        lowMap.put(lowKey, new Sample(lowLevelEntry.getValue(), lowKey, Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST));
                    }
                    allSamples.put(highKey, lowMap);
                }
            }

            userLogger.info("выводим данные статистики в отчёт");
            int shiftForHeaders = 3;
            int rowMeanNum = shiftForHeaders + vowelPage.getLastRowNum() + 2;
            createCell(0, rowMeanNum, 2, "MEAN:");
            int rowAverNum = rowMeanNum + 1;
            createCell(0, rowAverNum, 2, "AVERAGE:");

            Map<Object, GeneralReportHeader> headers = vowelPage.getHeaders();
            DecimalFormat df = new DecimalFormat("#.#");

            userLogger.info("RESULT");
            for (Map.Entry<String, Map<Object, Sample>> highLevelEntry : allSamples.entrySet()) {
                // берем внешний ключ и ищем его в заголовках
                for (Map.Entry<Object, Sample> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                    userLogger.info(highLevelEntry.getKey() + " " + lowLevelEntry.getKey() + " " + lowLevelEntry.getValue().getMean());
                }
            }


            /*for (Map.Entry<Object, GeneralReportHeader> entry : headers.entrySet()) {
                Cell cell = sh.getRow(rowMeanNum).createCell(entry.getValue().getColumn());
                cell.setCellValue(df.format(samples.get(entry.getKey()).getMean()) + "%");

                cell = rowAver.createCell(entry.getValue().getColumn());
                cell.setCellValue(df.format(samples.get(entry.getKey()).getAverage()) + "%");
            }
            userLogger.debug("vowels manner stats added");*/


            wb.write(fileOut);
            userLogger.info("mean and average are written to file");
            fileOut.close();

        } catch (IOException e) {
            userLogger.error(e.toString());
        }
    }

    /*public List<Sample> readAllSamples(Statistics.KindOfStats kindOfStats) {
        OutputFilePage page = this.getFilePages().get(0);
        if (page.getStatsColumnsList() != null) {
            return page.getStatsColumnsList();
        }

        List<Sample> result = new ArrayList<>();
        Sheet sh = page.getSheet();;
        Cell cell;

        ArrayList<Double> dList;
        //VOWELS
        for (Map.Entry<Object, GeneralReportHeader> entry : page.getHeaders().entrySet()) {
            dList = new ArrayList<>();

            for (int i = 3; i < page.getLastRowNum(); i++) {
                Row r = sh.getRow(i);
                int col = entry.getValue().getColumn();
                // Если значение имеется, суммируем
                if (r.getCell(col) != null) {
                    dList.add(parseNormalityCell(r.getCell(col)));
                }
            }

            Sample sample = new Sample(dList, entry.getKey());
            result.put(entry.getKey(), sample);
        }

        // CONSONANTS MANNER
        for (Map.Entry<Object, GeneralReportHeader> entry : GeneralReportHeader.consMannerSh.entrySet()) {
            dList = new ArrayList<>();

            for (int i = 3; i < this.recordsCounter + 3; i++) {
                // TODO: костыли запилены за неимением времени
                cell = sh.getRow(i).getCell(entry.getValue().getColumn());
                dList.add(parseNormalityCell(cell));
            }

            Sample sample = new Sample(dList, entry.getKey());
            result.put(entry.getKey(), sample);
        }

        allSamplesSheet1 = result;
        return result;
    }*/

    // Считывает столбик каждого фонотипа как выборку для расчета статистики
    /*public HashMap<Object, Sample> readAllSamples(Statistics.KindOfStats kindOfStats) {
        if (allSamplesSheet1 != null) {
            return allSamplesSheet1;
        }

        HashMap<Object, Sample> result = new HashMap<>();
        Sheet sh = null;
        Cell cell;
        if (kindOfStats == Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST) {
            sh = this.getFilePages().get(0).getSheet();
        }

        ArrayList<Double> dList;
        //VOWELS
        for (Map.Entry<Object, GeneralReportHeader> entry : GeneralReportHeader.vowSh.entrySet()) {
            dList = new ArrayList<>();

            for (int i = 3; i < this.recordsCounter + 3; i++) {
                // TODO: костыли запилены за неимением времени
                cell = sh.getRow(i).getCell(entry.getValue().getColumn());
                dList.add(parseNormalityCell(cell));
            }

            Sample sample = new Sample(dList, entry.getKey());
            result.put(entry.getKey(), sample);
        }

        // CONSONANTS MANNER
        for (Map.Entry<Object, GeneralReportHeader> entry : GeneralReportHeader.consMannerSh.entrySet()) {
            dList = new ArrayList<>();

            for (int i = 3; i < this.recordsCounter + 3; i++) {
                // TODO: костыли запилены за неимением времени
                cell = sh.getRow(i).getCell(entry.getValue().getColumn());
                dList.add(parseNormalityCell(cell));
            }

            Sample sample = new Sample(dList, entry.getKey());
            result.put(entry.getKey(), sample);
        }

        allSamplesSheet1 = result;
        return result;
    }*/

    // Считывает столбик каждого фонотипа как выборку для расчета статистики
    /*public HashMap<Object, Double[]> readAllSamplesAsArray(Statistics.KindOfStats kindOfStats) {

        HashMap<Object, Double[]> allSamples = new HashMap<>();
        HashMap<Object, Sample> dLists = readAllSamples(kindOfStats);
        for (Map.Entry<Object, Sample> entry : dLists.entrySet()) {
            ArrayList<Double> dList = entry.getValue().getSample();
            Double[] dArr = new Double[dList.size()];
            for (int i = 0; i < dList.size(); i++) {
                dArr[i] = dList.get(i);
            }
            allSamples.put(entry.getKey(), dArr);
        }
        return allSamples;
    }*/

    // Возвращает первое число (процент, среднее...) из ячейки файла Normality
    // второе число - это делитель
    private double parseNormalityCell(Cell cell) {
        try {
            String s = cell.getStringCellValue();
            String[] str = s.split("%")[0].split(",");
            if (str.length == 1) {
                s = str[0];
            } else {
                s = str[0] + "." + str[1];
            }
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            userLogger.debug(e.toString());
            return 0.0;
        }
    }
}