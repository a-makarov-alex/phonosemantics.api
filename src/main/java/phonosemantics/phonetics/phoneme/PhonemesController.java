package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PhonemesController {
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/coverage")
    public ArrayList<PhonemeInTable> getPhonemesCoverage() {
        return PhonemesCoverageNew.getAllPhonemesList();
    }
}
