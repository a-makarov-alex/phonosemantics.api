package phonosemantics.output.report;

import lombok.Data;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import phonosemantics.statistics.Sample;
import phonosemantics.statistics.Statistics;
import phonosemantics.word.wordlist.WordList;

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
    private ArrayList<Sheet> sheets;
    private static final String OUTPUT_DIRECTORY = Paths.get("src", "main", "java", "output").toString();
    private int recordsCounter;
    private Workbook wb;
    private static CellStyle headerCellStyle;

    public static CellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }
    public static void setHeaderCellStyle(CellStyle headerCellStyle) {
        OutputFile.headerCellStyle = headerCellStyle;
    }

    private HashMap<Object, Sample> allSamplesSheet1 = null;

    // TODO to enum
    private static final String SHEET_VOW = "Vowels";
    private static final String SHEET_CONS_MANNER = "Cons manner";
    private static final String SHEET_CONS_PLACE = "Cons place";

    public OutputFile(String title) {
        this.title = title;
        this.recordsCounter = 0;
        this.filePath = OUTPUT_DIRECTORY + title + ".xlsx";
        this.wb = new XSSFWorkbook();
        this.createOutputFile();
        userLogger.info("output file created");
    }

    private void createOutputFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);

            // Create an Excel file draft
            sheets = new ArrayList<>();

            // Specify a style for headers
            headerCellStyle = wb.createCellStyle();
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Create sheets with headers
            // sheets
            sheets.add(wb.createSheet(SHEET_VOW));
            sheets.add(wb.createSheet(SHEET_CONS_MANNER));
            sheets.add(wb.createSheet(SHEET_CONS_PLACE));

            //headers
            for (Sheet sh : sheets) {
                GeneralReportHeader.addCommonHeader(sh);
            }
            GeneralReportHeader.addVowelsHeader(sheets.get(0));
            GeneralReportHeader.addMannerHeader(sheets.get(1));
            GeneralReportHeader.addPlaceHeader(sheets.get(2));
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            userLogger.info("IOException caught in output");
        }
    }

    // Writes the worlist parsing and statistics counting result into the output file
    public void fillWith(List<WordList> wordLists) {
        for (WordList wordList : wordLists) {
            userLogger.info("writing to general file...");
            writeToGeneralFile(wordList);
        }
        addMeanAndAverage(Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST);
    }

    private void writeToGeneralFile(WordList wordList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.filePath);

            // percentage or absolute
            CellStyle cellStyle = wb.createCellStyle();
            HashMap<Object, Double> mapResult = new HashMap<>();
            Sheet sh;
            int row = 0;
            int column = 2;

            // WRITE VOWELS
            sh = this.wb.getSheet(SHEET_VOW);

            sh.getRow(3).getCell(0).setCellValue(wordList.getMeaning());
            sh.getRow(3).getCell(1).setCellValue(wordList.getList().size());

            for (int i = 3; i <= 5; i++) {
                row = i;
                String styleFormat = "";
                switch (row) {
                    // WORDS_WITH_PHTYPE_PER_LIST
                    case 3 : {
                        //mapResult = wordList.getStats(Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST);
                        styleFormat = "0.0%";
                        break;
                    }
                    // PHTYPES_PER_LIST
                    case 4 : {
                        //mapResult = wordList.getStats(Statistics.KindOfStats.PHTYPES_PER_LIST);
                        styleFormat = "0.0%";
                        break;
                    }

                    // PHTYPES_AVERAGE_PER_WORD
                    case 5 : {
                        //mapResult = wordList.getStats(Statistics.KindOfStats.PHTYPES_AVERAGE_PER_WORD);
                        styleFormat = "0.0";
                        break;
                    }
                }

                // WRITE ROW BY ROW
                for (Map.Entry<Object, GeneralReportHeader> entry : GeneralReportHeader.vowSh.entrySet()) {
                    column = entry.getValue().getColumn();

                    Cell c = sh.getRow(row).createCell(column);
                    c.setCellValue(mapResult.get(entry.getKey()));
                    c.setCellStyle(createCellStyleWithDataFormat(styleFormat));
                }
            }

            wb.write(fileOut);
            userLogger.info("vowels are written to general file");
            fileOut.close();

        } catch (IOException e) {
            userLogger.error("IOException caught: " + e.getStackTrace());
        }
    }

    // TODO: удалить в GENERAL файле
    private CellStyle createCellStyleWithDataFormat(String format) {
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
            userLogger.info("adding mean and average...");

            Sheet sh = sheets.get(0);

            // HEADERS and row creation
            int shiftForHeaders = 3;
            Row rowMean = sh.createRow(shiftForHeaders + recordsCounter + 2);
            rowMean.createCell(2).setCellValue("MEAN:");
            Row rowAver = sh.createRow(shiftForHeaders + recordsCounter + 3);
            rowAver.createCell(2).setCellValue("AVERAGE:");


            HashMap<Object, Sample> samples = readAllSamples(kindOfStats);
            HashMap<Object, GeneralReportHeader> headers = GeneralReportHeader.vowSh;

            DecimalFormat df = new DecimalFormat("#.#");
            // VOWELS MANNER
            for (Map.Entry<Object, GeneralReportHeader> entry : headers.entrySet()) {
                Cell cell = rowMean.createCell(entry.getValue().getColumn());
                cell.setCellValue(df.format(samples.get(entry.getKey()).getMean()) + "%");

                cell = rowAver.createCell(entry.getValue().getColumn());
                cell.setCellValue(df.format(samples.get(entry.getKey()).getAverage()) + "%");
            }
            userLogger.debug("vowels manner stats added");

            // CONSONANT MANNER
            headers = GeneralReportHeader.consMannerSh;
            for (Map.Entry<Object, GeneralReportHeader> entry : headers.entrySet()) {
                Cell cell = rowMean.createCell(entry.getValue().getColumn());
                cell.setCellValue(df.format(samples.get(entry.getKey()).getMean()) + "%");

                cell = rowAver.createCell(entry.getValue().getColumn());
                cell.setCellValue(df.format(samples.get(entry.getKey()).getAverage()) + "%");
            }
            userLogger.debug("consonants manner stats added");

            wb.write(fileOut);
            userLogger.info("mean and average are written to file");
            fileOut.close();

        } catch (IOException e) {
            userLogger.error(e.toString());
        }
    }

    // Считывает столбик каждого фонотипа как выборку для расчета статистики
    public HashMap<Object, Sample> readAllSamples(Statistics.KindOfStats kindOfStats) {
        if (allSamplesSheet1 != null) {
            return allSamplesSheet1;
        }

        HashMap<Object, Sample> result = new HashMap<>();
        Sheet sh = null;
        Cell cell;
        if (kindOfStats == Statistics.KindOfStats.WORDS_WITH_PHTYPE_PER_LIST) {
            sh = sheets.get(0);
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
    }

    // Считывает столбик каждого фонотипа как выборку для расчета статистики
    public HashMap<Object, Double[]> readAllSamplesAsArray(Statistics.KindOfStats kindOfStats) {

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
    }

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
