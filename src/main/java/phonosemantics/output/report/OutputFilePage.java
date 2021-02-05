package phonosemantics.output.report;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.Map;

@Data
public class OutputFilePage {
    private String title;
    private int pageNumber;
    private Sheet sheet;
    private int lastRowNum;
    private Map<Object, GeneralReportHeader> headers;
    private boolean hasHeaders; // вписаны ли заголовки в файл или ещё нет

    public OutputFilePage(String title, int pageNumber, Sheet sheet) {
        this.title = title;
        this.pageNumber = pageNumber;
        this.sheet = sheet;
        headers = new HashMap<>();
        lastRowNum = 0;
        hasHeaders = false;
    }
}
