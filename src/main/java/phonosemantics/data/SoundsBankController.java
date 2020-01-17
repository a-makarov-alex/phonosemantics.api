package phonosemantics.data;

import org.springframework.web.bind.annotation.*;
import phonosemantics.phonetics.phoneme.Phoneme;

import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SoundsBankController {
    /**
     * GETTING ALL PHONEMES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes")
    public HashSet<Phoneme> getAllPhonemes() {
        HashSet<Phoneme> set = new HashSet<>();
        for (Map.Entry<String, Phoneme> ph : SoundsBank.getInstance().getAllPhonemesTable().entrySet()) {
            set.add(ph.getValue());
        }
        return set;
    }

    /**
     * GETTING PHONEME BY ITS TITLE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{phoneme}")
    public Phoneme getPhonemeByName(@PathVariable(value="phoneme") String phoneme) {
        return SoundsBank.getInstance().getAllPhonemesTable().get(phoneme.toLowerCase());
    }


    /**
     * GETTING ALL PHONOTYPES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonotypes")
    public HashSet<Object> getAllPhonotypes() {
        HashSet<Object> set = new HashSet<>();
        for (Map.Entry<Object, Integer> phtype : SoundsBank.getAllPhonotypes().entrySet()) {
            set.add(phtype.getKey());
        }
        return set;
    }
}
