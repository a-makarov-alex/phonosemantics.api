package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.*;
import phonosemantics.data.Port;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.HashMap;

@RestController
public class DistinctiveFeaturesController {

    /**
     * RETURNS THE MAP WITH 4 BIG CLASSES OF DISTINCTIVE FEATURES
     **/
    @CrossOrigin(origins = Port.FRONTEND_URL)
    @GetMapping("/features/{type}/structure")
    // type available values: all / general / vowel / consonant
    public HashMap<String, Object[]> getFeatures(@PathVariable(value="type") String type) {
        return DistinctiveFeatures.getFeaturesForAPI(type);
    }

    @CrossOrigin(origins = Port.FRONTEND_URL)
    @GetMapping("/features/{type}/structure-stats-draft")
    // type available values: all / general / vowel / consonant
    public HashMap<String, HashMap<Object, Integer>> getFeaturesStatsDraft(@PathVariable(value="type") String type) {
        return DistinctiveFeatures.getFeaturesStructureDraft(type);
    }
}
