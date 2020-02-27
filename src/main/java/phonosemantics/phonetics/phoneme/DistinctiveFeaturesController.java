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
    @GetMapping("/phonemes/parameters/{distinctiveFeature}")
    public HashMap<String, Object[]> getFeatures(@PathVariable(value="distinctiveFeature") String distinctiveFeature) {
        return DistinctiveFeatures.getFeaturesForAPI(distinctiveFeature);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/features/stats")
    public HashMap<String, HashMap<Object, Integer>> getFeaturesStats() {
        return DistinctiveFeatures.getFeaturesStats();
    }
}
