package phonosemantics.controller;

import org.springframework.web.bind.annotation.*;
import phonosemantics.model.Meaning;
import phonosemantics.model.Word;
import phonosemantics.model.WordList;
import phonosemantics.service.WordListService;
import phonosemantics.service.WordService;

import java.util.ArrayList;

@RestController
public class WordController {

    @GetMapping("/words")
    public WordList getWordlist(@RequestParam(value = "meaning") String meaning) {

        WordListService wls = new WordListService("Input.xlsx");
        ArrayList<WordList> allWordlists = wls.getAllWordLists();

        for (WordList wl : allWordlists) {
            if (wl.getMeaning().equals(meaning)) {
                return wl;
            }
        }
        return null;
    }

    @GetMapping("/tes")
    public String getFp() {
        WordListService wls = new WordListService("Input.xlsx");
        ArrayList<WordList> allWordlists = wls.getAllWordLists();
        String w = allWordlists.get(0).getMeaning();
        return w;
    }
}

