package phonosemantics.word.wordlist;

import org.springframework.web.bind.annotation.*;
import phonosemantics.App;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class WordListController {
    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlist")
    public WordList getWordlistByMeaning(@RequestParam(value = "wordlistMeaning") String wordlistMeaning) {
        return WordListService.getWordlist(wordlistMeaning);
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

    /**
     * GETTING ALL PHONEMES FOR A CERTAIN WORDLIST
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlists/{wordlistMeaning}/phonemes")
    public ArrayList<PhonemeInTable> getPhonemesCoverageForWordlist(@PathVariable(value="wordlistMeaning") String wordlistMeaning) {
        WordList wrdl = WordListService.getWordlist(wordlistMeaning);

        return PhonemesBank.getInstance().getAllPhonemesList(wrdl);
    }

    /**
     * GETTING PHONEME STATS FOR A CERTAIN WORDLIST
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlists/{wordlistMeaning}/phonemes/stats")
    public PhonemeInTable.PhonemeStats getPhonemeRaw(
            @PathVariable(value = "wordlistMeaning") String wordlistMeaning,
            @RequestParam(value="phoneme") String phoneme
    ) {
        WordList wl = WordListService.getWordlist(wordlistMeaning);
        return wl.getPhonemeStats().get(phoneme);
    }

    /**
     * GETTING FEATURES STATS FOR A CERTAIN WORDLIST
     *
     * how to get raw "number of feature instances" --> wl.calculateFeaturesStats(type)
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlists/{wordlistMeaning}/features/stats")
    // type available values: all / general / vowel / consonant
    public HashMap<String, HashMap<Object, PhonemeInTable.DistFeatureStats>> getFeaturesStats(
            @RequestParam(value="type") String type,
            @PathVariable(value = "wordlistMeaning") String wordlistMeaning) {
        WordList wl = WordListService.getWordlist(wordlistMeaning);

        return wl.getDistFeatureStats();
    }
}

