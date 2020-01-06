package phonosemantics.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class PhonemesController {
    @GetMapping("/phonemes/coverage")
    public String getPhonemesCoverage() {
        return null;
    }
}
