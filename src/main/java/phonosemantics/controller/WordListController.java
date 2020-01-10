package phonosemantics.controller;

import org.springframework.web.bind.annotation.*;
import phonosemantics.model.Meaning;
import phonosemantics.model.Word;
import phonosemantics.model.WordList;
import phonosemantics.service.WordListService;
import phonosemantics.service.WordService;

import java.util.ArrayList;


@RestController
public class WordListController {

    // TODO: запихнуть в контекст эти данные
    private static final WordListService wls = new WordListService("Input.xlsx");

    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @GetMapping("/wordlist")
    public WordList getWordlist(@RequestParam(value = "meaning") String meaning) {

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

