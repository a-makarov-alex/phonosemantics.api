package phonosemantics.language.languageReduced;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.language.Language;

import java.util.ArrayList;

@RestController
public class LanguageReducedController {

    /**
     * GETTING ALL LANGUAGES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/languages")
    public ArrayList<String> getAllLanguages() {
        return LanguageReducedService.getAllLanguageNames();
    }

    /**
     * GETTING LANGUAGE BY ITS NAME (TITLE)
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/languages/{language}")
    public Language getLanguageByName(@PathVariable(value="language") String title) {
        return Language.getLanguage(title);
    }
}
