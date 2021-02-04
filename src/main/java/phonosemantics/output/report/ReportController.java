package phonosemantics.output.report;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.PortConfig;
import phonosemantics.word.wordlist.WordListService;

@RestController
public class ReportController {

    /**
     * CREATING GENERAL WORDLIST REPORT
     * Отчет создается в указанной (захардкоженой) директории
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/report/general")
    public boolean createGeneralWordlistReport() {
        OutputFile generalWordlistReport = new OutputFile("GeneralWordlistReport");
        generalWordlistReport.fillWith(WordListService.getAllWordLists());
        return true;
    }

}
