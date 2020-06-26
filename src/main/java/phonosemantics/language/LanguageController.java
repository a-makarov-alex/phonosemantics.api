package phonosemantics.language;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.PortConfig;

import java.util.ArrayList;

@RestController
public class LanguageController {

    /**
     * GETTING ALL LANGUAGES
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/languages")
    public ArrayList<String> getAllLanguages() {
        return LanguageService.getAllLanguageNames();
    }

    /**
     * GETTING LANGUAGE BY ITS NAME (TITLE)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/languages/{language}")
    public Language getLanguageByName(@PathVariable(value="language") String title) {
        return LanguageService.getLanguage(title);
    }
}
