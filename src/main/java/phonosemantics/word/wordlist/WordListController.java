package phonosemantics.word.wordlist;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import phonosemantics.data.PortConfig;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class WordListController {
    private static final Logger userLogger = Logger.getLogger(WordListController.class);
    /**
     * GETTING WORDLIST BY MEANING
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlist")
    public WordList2022 getWordlistByMeaning(@RequestParam(value = "wordlistMeaning") String wordlistMeaning) {
        return WordListService.getWordlist2022(wordlistMeaning);
    }


    /**
     * GETTING ALL WORDLISTS
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists")
    public List<WordList2022> getAllWordlists() {
        List<WordList2022> allWordlists = WordListService.getAllWordLists2022();
        return allWordlists;
    }


    /**
     * GETTING ALL MEANINGS THAT ARE PRESENT IN INPUT FILE ( == IN DATABASE)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/meanings")
    public List<String> getAllMeanings() {
        List<String> meaningsList = new ArrayList<>();

        for (WordList2022 wl : WordListService.getAllWordLists2022()) {
            meaningsList.add(wl.getMeaning());
        }
        return meaningsList;
    }

    /**
     * GETTING ALL PHONEMES FOR A CERTAIN WORDLIST
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/{wordlistMeaning}/phonemes")
    public List<PhonemeInTable> getPhonemesCoverageForWordlist(@PathVariable(value="wordlistMeaning") String wordlistMeaning) {
        WordList2022 wrdl = WordListService.getWordlist2022(wordlistMeaning);
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
        WordList2022 wl = WordListService.getWordlist2022(wordlistMeaning);
        return wl.getStats().getPhonemeStatsMap().get(phoneme);
    }

    /**
     * GETTING FEATURES STATS FOR A CERTAIN WORDLIST
     *
     * how to get raw "number of feature instances" --> wl.calculateFeaturesStats(type)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/{wordlistMeaning}/features/stats")
    // TODO type available values: all / general / vowel / consonant
    public Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> getFeaturesStats(
            @RequestParam(value="type") String type,
            @PathVariable(value = "wordlistMeaning") String wordlistMeaning) {
        WordList2022 wl = WordListService.getWordlist2022(wordlistMeaning);
        return wl.getStats().getDistFeatureStats();
    }

    /**
     * GETTING CERTAIN FEATURE STATS FOR ALL WORDLISTS
     **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/wordlists/features/{feature}/stats")
    // type available values: all / general / vowel / consonant
    // Meaning: { featureValue, Stats }
    public Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> getFeaturesStats(
            @PathVariable(value = "feature") String feature) {

        List<WordList2022> allWordlists = WordListService.getAllWordLists2022();
        Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> resultMap = new HashMap<>();
        feature = feature.toLowerCase();

        for (WordList2022 wl : allWordlists) {
            Map<Object, PhonemeInTable.DistFeatureStats> statsForOneWordlist = wl.getStats().getDistFeatureStats().get(feature);
            if (statsForOneWordlist != null) {
                resultMap.put(wl.getMeaning(), statsForOneWordlist);
            } else {
                userLogger.info("feature " + feature + " is not a Distinctive Feature");
            }
        }
        return resultMap;
    }
}

