package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.output.header.Header;

import java.util.ArrayList;

@RestController
public class PhonemesController {
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/coverage")
    public ArrayList<PhonemeInTable> getPhonemesCoverage() {
        return PhonemesCoverageNew.getAllPhonemesList();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/headers/place")
    public ArrayList<Header> getPlaceHeaders() {
        return PhonemesCoverageNew.getPlaceHeaders();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/headers/manner")
    public ArrayList<Header> getMannerHeaders() {
        return PhonemesCoverageNew.getMannerHeaders();
    }
}
