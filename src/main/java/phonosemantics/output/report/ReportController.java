package phonosemantics.output.report;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.PortConfig;
import phonosemantics.word.wordlist.WordListService;

@RestController
public class ReportController {
    private static OutputFile generalWordlistReport;

    /**
     * CREATING GENERAL REPORT FOR ALL WORDLISTS
     * Отчет создается в указанной (захардкоженой) директории
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/report/general")
    public boolean createGeneralWordlistReport() {
        if (generalWordlistReport == null) {
            generalWordlistReport = new OutputFile("GeneralWordlistReport");
        }
        generalWordlistReport.fillWithAll(WordListService.getAllWordLists());
        return true;

    }

    /**
     * CREATING GENERAL REPORT FOR A CERTAIN WORDLIST STATS (adding if report already exists)
     * Отчет создается в указанной (захардкоженой) директории, если ещё не создан
     * Если отчёт уже существует, данные дописываются. Можно вызывать эндпойнт многократно.
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/report/general/{meaning}")
    public boolean addWordlistStatsToGeneralReport(@PathVariable(value="meaning") String meaning) {
        if (generalWordlistReport == null) {
            generalWordlistReport = new OutputFile("GeneralWordlistReport");
        }
        generalWordlistReport.fillWith(WordListService.getWordlist(meaning));
        return true;
    }

    /**
     * Отчет создается в указанной (захардкоженой) директории, если ещё не создан
     * Если отчёт уже существует, данные дописываются. Можно вызывать эндпойнт многократно.
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/report/square/{meaning}")
    public boolean createPatternSquareReport(@PathVariable(value="meaning") String meaning) {
        PatternSquareFile patternSquareFile = new PatternSquareFile(WordListService.getWordlist(meaning));
        return true;
    }
}
