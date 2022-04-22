package phonosemantics.word;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.PortConfig;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;

import java.util.Map;

@RestController
public class WordController {
    /**
     * GETTING WORD BY MEANING AND LANGUAGE
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/languages/{language}/meanings/{meaning}")
    public Word2022 getWordByLanguageAndMeaning(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        return Word2022.getWord(language, meaning);
    }

    /**
     * GETTING WORD's DISTINCTIVE FEATURES
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/languages/{language}/meanings/{meaning}/features")
    public Map<String, Map<String, Integer>> getWordDistinctiveFeatures(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        Word2022 w = Word2022.getWord(language, meaning);
        return w.countWordDistinctiveFeaturesStats(DistinctiveFeatures.Type.ALL);
    }

}
