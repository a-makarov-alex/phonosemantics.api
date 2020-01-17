package phonosemantics.language.languageReduced;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.App;
import phonosemantics.language.languageReduced.LanguageReduced;
import phonosemantics.word.WordReduced;
import phonosemantics.word.wordlist.wordlistReduced.WordListReduced;

import java.util.HashSet;

@RestController
public class LanguageReducedController {

    /**
     * GETTING ALL LANGUAGES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/languages")
    public HashSet<LanguageReduced> getAllLanguages() {
        return LanguageReducedService.getAllLanguages();
    }

    /**
     * GETTING LANGUAGE BY ITS NAME (TITLE)
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/languages/{language}")
    public LanguageReduced getLanguageByName(@PathVariable(value="language") String title) {
        return LanguageReducedService.getLanguageByTitle(title);
    }
}
