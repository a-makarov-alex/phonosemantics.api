package phonosemantics.word.wordlist;

import org.springframework.web.bind.annotation.*;
import phonosemantics.App;

import java.util.ArrayList;


@RestController
public class WordListController {
    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlist")
    public WordList getWordlistByMeaning(@RequestParam(value = "meaning") String meaning) {
        return WordListService.getWordlist(meaning);
    }


    /**
     * GETTING ALL WORDLISTS
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlists")
    public ArrayList<WordList> getAllWordlists() {
        ArrayList<WordList> allWordlists = WordListService.getAllWordLists();
        return allWordlists;
    }


    /**
     * GETTING ALL MEANINGS THAT ARE PRESENT IN INPUT FILE ( == IN DATABASE)
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/meanings")
    public ArrayList<String> getAllMeanings() {

        ArrayList<String> meaningsList = new ArrayList<>();

        for (WordList wl : WordListService.getAllWordLists()) {
            meaningsList.add(wl.getMeaning());
        }

        return meaningsList;
    }
}

