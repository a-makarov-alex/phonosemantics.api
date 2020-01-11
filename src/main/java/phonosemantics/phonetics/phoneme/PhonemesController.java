package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.GetMapping;

public class PhonemesController {
    @GetMapping("/phonemes/coverage")
    public String getPhonemesCoverage() {
        return null;
    }
}
