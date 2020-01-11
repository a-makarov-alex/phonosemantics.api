package phonosemantics.word.wordlist;

import org.springframework.web.bind.annotation.*;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.ArrayList;


@RestController
public class WordListController {

    // TODO: запихнуть в контекст эти данные
    private static final WordListService wls = new WordListService("Input.xlsx");

    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlist")
    public WordList getWordlistByMeaning(@RequestParam(value = "meaning") String meaning) {

        ArrayList<WordList> allWordlists = wls.getAllWordLists();

        for (WordList wl : allWordlists) {
            if (wl.getMeaning().equals(meaning)) {
                return wl;
            }
        }
        return null;
    }


    /**
     * GETTING ALL MEANINGS THAT ARE PRESENT IN INPUT FILE ( == IN DATABASE)
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/meanings")
    public ArrayList<String> getAllMeanings() {

        ArrayList<String> meaningsList = new ArrayList<>();

        for (WordList wl : wls.getAllWordLists()) {
            meaningsList.add(wl.getMeaning());
        }

        return meaningsList;
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/language")
    public String getTestLanguage() {
        return "testlanguage";
    }





    // чтобы работало, надо аннотацию @Controller ко всему классу вместо RestController
    /*@RequestMapping("/tes")
    public String getFp() {
        return "StartPage";
    }*/
}

