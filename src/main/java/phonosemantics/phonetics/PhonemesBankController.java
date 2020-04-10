package phonosemantics.phonetics;

import org.springframework.web.bind.annotation.*;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Statistics;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class PhonemesBankController {
    /**
     * GETTING ALL PHONEMES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes")
    public ArrayList<PhonemeInTable> getAllPhonemes() {
        ArrayList<PhonemeInTable> list = PhonemesBank.getInstance().getPhonemesListForTableUI();
        return list;
    }


    /**
     * GETTING PHONEME BY ITS TITLE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{phoneme}")
    public PhonemeInTable getPhonemeByName(@PathVariable(value="phoneme") String phoneme) {
        ArrayList<PhonemeInTable> list = PhonemesBank.getInstance().getPhonemesListForTableUI();
        for (PhonemeInTable ph : list) {
            if (ph.getValue().equals(phoneme.toLowerCase())) {
                return ph;
            }
        }
        return null;
    }


    /**
     * GETTING UNKNOWN PHONEMES
     * ugly path, but phonemes/{phoneme} is already taken
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/unknown/all")
    public HashMap<String, Integer> getUnknownPhonemes() {
        HashMap<String, Integer> unknownhonemes = Statistics.getUnknownPhonemes();
        return unknownhonemes;
    }
}
