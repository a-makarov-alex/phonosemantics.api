package phonosemantics.output.report;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.PortConfig;
import phonosemantics.language.LanguageService;
import phonosemantics.word.wordlist.WordListController;
import phonosemantics.word.wordlist.WordListService;

import java.util.List;

@RestController
public class ReportController {

    /**
     * CREATING GENERAL WORDLIST REPORT
     * Отчет создается в указанной (захардкоженой) директории
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/languages")
    public boolean createGeneralWordlistReport() {
        OutputFile generalWordlistReport = new OutputFile("General Wordlist Report");
        generalWordlistReport.fillWith(WordListService.getAllWordLists());
        return true;
    }

}
