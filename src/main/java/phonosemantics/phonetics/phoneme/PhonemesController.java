package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.output.header.Header;
import phonosemantics.output.header.HeadersForUI;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class PhonemesController {
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{type}")
    // type available values: all / vowel / consonant
    public ArrayList<PhonemeInTable> getPhonemesCoverage(@PathVariable(value="type") String type){
        return PhonemesBank.getInstance().getAllPhonemesList(type);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/table/{type}")
    // type available values: vowel / consonant
    public ArrayList<PhonemeInTable> getPhonemesCoverageForTable(@PathVariable(value="type") String type){
        return PhonemesBank.getInstance().getVowelPhonemesForTable(type);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/wordlists/{meaning}/phonemes")
    public ArrayList<PhonemeInTable> getPhonemesCoverageForWordlist(@PathVariable(value="meaning") String meaning) {
        WordList wrdl = WordListService.getWordlist(meaning);

        return PhonemesBank.getInstance().getAllPhonemesList(wrdl);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/headers/{distinctiveFeature}")
    public ArrayList<Header> getHeaders(@PathVariable(value="distinctiveFeature") String distinctiveFeature) {
        return HeadersForUI.getHeaders(distinctiveFeature);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/test")
    public HashMap<String, DistinctiveFeatures> getHeaders() {
        return PhonemesBank.getInstance().getAllPhonemes();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/test2")
    public ArrayList<PhonemeInTable> getHead() {
        return PhonemesBank.getInstance().getAllPhonemesNew();
    }

}
