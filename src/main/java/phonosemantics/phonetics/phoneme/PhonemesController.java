package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.output.header.Header;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PhonemesController {
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/coverage")
    public ArrayList<PhonemeInTable> getPhonemesCoverage() {
        return PhonemesCoverageNew.getAllPhonemesList();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/coverage/vowels")
    public ArrayList<PhonemeInTable> getVowelsCoverage() {
        return PhonemesCoverageNew.getAllVowelsList();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/coverage/{meaning}")
    public ArrayList<PhonemeInTable> getPhonemesCoverageForWordlist(@PathVariable(value="meaning") String meaning) {
        WordList wrdl = WordListService.getWordlist(meaning);

        return PhonemesCoverageNew.getAllPhonemesList(wrdl);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/headers/{distinctiveFeature}")
    public ArrayList<Header> getHeaders(@PathVariable(value="distinctiveFeature") String distinctiveFeature) {
        return PhonemesCoverageNew.getHeaders(distinctiveFeature);
    }

    /***************** METHODS FOR GETTING SOUNDS DISTINCTIVE FEATURES ************/

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/parameters/{distinctiveFeature}")
    public HashMap<String, Object[]> getFeatures(@PathVariable(value="distinctiveFeature") String distinctiveFeature) {
        return DistinctiveFeatures.getFeaturesForAPI(distinctiveFeature);
    }
}
