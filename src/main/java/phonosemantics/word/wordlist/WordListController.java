package phonosemantics.word.wordlist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import phonosemantics.data.InputConfig;
import phonosemantics.data.PortConfig;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.util.ArrayList;
import java.util.HashMap;


@RestController
public class WordListController {
    private static final Logger userLogger = LogManager.getLogger(WordListController.class);
    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlist")
    public WordList getWordlistByMeaning(@RequestParam(value = "wordlistMeaning") String wordlistMeaning) {
        return WordListService.getWordlist(wordlistMeaning);
    }


    /**
     * GETTING ALL WORDLISTS
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists")
    public ArrayList<WordList> getAllWordlists() {
        ArrayList<WordList> allWordlists = WordListService.getAllWordLists();
        return allWordlists;
    }


    /**
     * GETTING ALL MEANINGS THAT ARE PRESENT IN INPUT FILE ( == IN DATABASE)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
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
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/{wordlistMeaning}/phonemes")
    public ArrayList<PhonemeInTable> getPhonemesCoverageForWordlist(@PathVariable(value="wordlistMeaning") String wordlistMeaning) {
        WordList wrdl = WordListService.getWordlist(wordlistMeaning);
        return PhonemesBank.getInstance().getAllPhonemesList(wrdl);
    }

    /**
     * GETTING PHONEME STATS FOR A CERTAIN WORDLIST
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/{wordlistMeaning}/phonemes/stats")
    public PhonemeInTable.PhonemeStats getPhonemeStats(
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
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/{wordlistMeaning}/features/stats")
    // type available values: all / general / vowel / consonant
    public HashMap<String, HashMap<Object, PhonemeInTable.DistFeatureStats>> getFeaturesStats(
            @RequestParam(value="type") String type,
            @PathVariable(value = "wordlistMeaning") String wordlistMeaning) {
        WordList wl = WordListService.getWordlist(wordlistMeaning);

        return wl.getDistFeatureStats();
    }

    /**
     * GETTING CERTAIN FEATURE STATS FOR ALL WORDLISTS
     **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/features/{feature}/stats")
    // type available values: all / general / vowel / consonant
    // Meaning: { featureValue, Stats }
    public HashMap<String, HashMap<Object, PhonemeInTable.DistFeatureStats>> getFeaturesStats(
            @PathVariable(value = "feature") String feature) {

        ArrayList<WordList> allWordlists = WordListService.getAllWordLists();
        HashMap<String, HashMap<Object, PhonemeInTable.DistFeatureStats>> resultMap = new HashMap<>();
        feature = feature.toLowerCase();

        for (WordList wl : allWordlists) {
            HashMap<Object, PhonemeInTable.DistFeatureStats> statsForOneWordlist = wl.getDistFeatureStats().get(feature);
            if (statsForOneWordlist != null) {
                resultMap.put(wl.getMeaning(), statsForOneWordlist);
            } else {
                userLogger.info("feature " + feature + " is not a Distinctive Feature");
            }
        }
        return resultMap;
    }
}

