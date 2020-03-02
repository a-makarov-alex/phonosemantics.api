package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class DistinctiveFeaturesController {

    /**
     * RETURNS THE MAP WITH 4 BIG CLASSES OF DISTINCTIVE FEATURES
     **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/features/{type}")
    // type available values: all / general / vowel / consonant
    public HashMap<String, Object[]> getFeatures(@PathVariable(value="type") String type) {
        return DistinctiveFeatures.getFeaturesForAPI(type);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/features/{type}/stats")
    // type available values: all / general / vowel / consonant
    public HashMap<String, HashMap<Object, Integer>> getFeaturesStats(@PathVariable(value="type") String type) {
        return DistinctiveFeatures.getFeaturesStats(type);
    }
}
