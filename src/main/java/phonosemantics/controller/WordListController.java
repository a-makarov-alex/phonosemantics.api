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
    @GetMapping("/meanings")
    public ArrayList<String> getAllMeanings() {

        ArrayList<String> meaningsList = new ArrayList<>();

        for (WordList wl : wls.getAllWordLists()) {
            meaningsList.add(wl.getMeaning());
        }

        return meaningsList;
    }

    @GetMapping("/tes")
    public String getFp() {
        WordListService wls = new WordListService("Input.xlsx");
        ArrayList<WordList> allWordlists = wls.getAllWordLists();
        String w = allWordlists.get(0).getMeaning();
        return w;
    }
}

