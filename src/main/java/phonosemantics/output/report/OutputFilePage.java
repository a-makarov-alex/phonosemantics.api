package phonosemantics.output.report;

import lombok.Data;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import phonosemantics.statistics.Sample;
import phonosemantics.word.wordlist.WordList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OutputFilePage {
    private static final Logger userLogger = Logger.getLogger(OutputFilePage.class);

    private String title;
    private int pageNumber;
    private Sheet sheet;
    private int lastRowNum;
    private Map<Object, GeneralReportHeader> headers;
    private boolean hasHeaders; // вписаны ли заголовки в файл или ещё нет
    private List<WordList> wordlists; // вордлисты, входящие в отчёт
    private List<Sample> statsSamplesList; // "вертикальный разрез" статистик в отчёте. перечень статс значений для каждого фонотипа

    public OutputFilePage(String title, int pageNumber, Sheet sheet) {
        this.title = title;
        this.pageNumber = pageNumber;
        this.sheet = sheet;
        headers = new HashMap<>();
        lastRowNum = 0;
        hasHeaders = false;
        wordlists = new ArrayList<>();
    }

    /**
     * Header that is common for all General excel sheets
     * **/
    public void addCommonHeader() {
        Sheet sheet = this.getSheet();
        int startRow = 0;
        int finishRow = 2;

        userLogger.info("инициализация ячеек горизонтальных заголовков");
        for (int i=startRow; i <= finishRow; i++ ) {
            sheet.createRow(i);
            for (int j=0; j <= 2; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        userLogger.info("добавление горизонтальных заголовков");
        Cell cell = sheet.getRow(startRow).getCell(0);
        cell.setCellValue("Semantics");
        cell = sheet.getRow(startRow).getCell(1);
        cell.setCellValue("Wordlist length");

        userLogger.info("слияние ячеек горизонтальных заголовков");
        sheet.addMergedRegion(new CellRangeAddress(startRow,finishRow,0, 0));
        sheet.addMergedRegion(new CellRangeAddress(startRow,finishRow,1, 1));
        sheet.addMergedRegion(new CellRangeAddress(startRow,finishRow,2, 2));

        //TODO определить, надо ли сохранять общие хедеры в переменную OutputFilePage

        // устанавливаем ширину ячеек
        sheet.setColumnWidth(0, 3700);
        sheet.setColumnWidth(1, 3700);
        sheet.setColumnWidth(2, 5800);
    }

    public void createVerticalHeaders(WordList wl) {
        userLogger.info("инициализация ячеек вертикальных заголовков");
        int startRow = getLastRowNum() + 1;
        int finishRow = startRow + 2;

        for (int i=startRow; i <= finishRow; i++ ) {
            sheet.createRow(i);
            for (int j=0; j <= 2; j++) {
                sheet.getRow(i).createCell(j);
                sheet.getRow(i).getCell(j).setCellStyle(OutputFile.getHeaderCellStyle());
            }
        }

        userLogger.info("добавление вертикальных заголовков");
        createCell(startRow, 0, wl.getMeaning());
        createCell(startRow, 1, String.valueOf(wl.getList().size()));
        createCell(startRow, 2, "% of words with ph-type");
        createCell(startRow + 1, 2, "% of ph with ph-type");
        createCell(startRow + 2, 2, "aver ph per word");

        userLogger.info("слияние ячеек заголовков");
        sheet.addMergedRegion(new CellRangeAddress(startRow,finishRow,0, 0));
        sheet.addMergedRegion(new CellRangeAddress(startRow,finishRow,1, 1));
    }

    private void createCell(int row, int col, String value) {
        if (sheet.getRow(row) == null) {
            sheet.createRow(row);
        }
        sheet.getRow(row).createCell(col);
        sheet.getRow(row).getCell(col).setCellStyle(OutputFile.getHeaderCellStyle());
        sheet.getRow(row).getCell(col).setCellValue(value);
    }
}
