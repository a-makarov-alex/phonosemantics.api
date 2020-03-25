package phonosemantics.word;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

@RestController
public class WordController {
    /**
     * GETTING WORD BY MEANING AND LANGUAGE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/languages/{language}/meanings/{meaning}")
    public Word getWordByLanguageAndMeaning(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        return Word.getWordByLanguageAndMeaning(language, meaning);
    }

}
