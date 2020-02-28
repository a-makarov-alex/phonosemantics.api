package phonosemantics.phonetics;

import org.springframework.web.bind.annotation.*;
import phonosemantics.data.SoundsBank;
import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.phonetics.phoneme.PhonemesCoverageNew;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PhonemesBankController {
    /**
     * GETTING ALL PHONEMES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes")
    public ArrayList<PhonemeInTable> getAllPhonemes() {
        ArrayList<PhonemeInTable> list = PhonemesCoverageNew.getAllPhonemesList();
        return list;
    }

    /**
     * GETTING PHONEME BY ITS TITLE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{phoneme}")
    public PhonemeInTable getPhonemeByName(@PathVariable(value="phoneme") String phoneme) {
        ArrayList<PhonemeInTable> list = PhonemesCoverageNew.getAllPhonemesList();
        for (PhonemeInTable ph : list) {
            if (ph.getValue().equals(phoneme.toLowerCase())) {
                return ph;
            }
        }
        return null;
    }
}
