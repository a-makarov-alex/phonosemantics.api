package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.*;
import phonosemantics.data.PortConfig;

import java.util.Map;

@RestController
public class DistinctiveFeaturesController {

    /**
     * RETURNS THE MAP WITH 4 BIG CLASSES OF DISTINCTIVE FEATURES
     **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/features/{type}/structure")
    // type available values: all / general / vowel / consonant
    public Map<String, Object[]> getFeatures(@PathVariable(value="type") String type) {
        return DistinctiveFeatures.getFeaturesForAPI(type);
    }

    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/features/{type}/structure-stats-draft")
    // type available values: all / general / vowel / consonant
    public Map<String, Map<String, Integer>> getFeaturesStatsDraft(@PathVariable(value="type") String type) {
        return DistinctiveFeatures.getFeaturesStructureDraftStringKeys(type);
    }
}
