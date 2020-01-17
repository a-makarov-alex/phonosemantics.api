package phonosemantics.word.wordlist.wordlistReduced;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.App;

import java.util.ArrayList;

@RestController
public class WordlistReducedController {
    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlist/reduced")
    public WordListReduced getReducedWordlistByMeaning(@RequestParam(value = "meaning") String meaning) {

        ArrayList<WordListReduced> allReducedWordlists = App.getAllReducedWordLists();

        for (WordListReduced wlr : allReducedWordlists) {
            if (wlr.getMeaning().equals(meaning)) {
                return wlr;
            }
        }
        return null;
    }

    /**
     * GETTING ALL WORDLISTS
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlists/reduced")
    public ArrayList<WordListReduced> getAllReducedWordlists() {
        return App.getAllReducedWordLists();
    }
}
