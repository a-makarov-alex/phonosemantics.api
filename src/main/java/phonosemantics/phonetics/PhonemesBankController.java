package phonosemantics.phonetics;

import org.springframework.web.bind.annotation.*;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class PhonemesBankController {
    /**
     * GETTING ALL PHONEMES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes")
    public ArrayList<PhonemeInTable> getAllPhonemes() {
        ArrayList<PhonemeInTable> list = PhonemesBank.getInstance().getAllPhonemesList();
        return list;
    }


    /**
     * GETTING PHONEME BY ITS TITLE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{phoneme}")
    public PhonemeInTable getPhonemeByName(@PathVariable(value="phoneme") String phoneme) {
        ArrayList<PhonemeInTable> list = PhonemesBank.getInstance().getAllPhonemesList();
        for (PhonemeInTable ph : list) {
            if (ph.getValue().equals(phoneme.toLowerCase())) {
                return ph;
            }
        }
        return null;
    }


    /**
     * GETTING PHONEME STATS
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{phoneme}/stats")
    public PhonemeInTable.PhonemeStats getPhonemeStats(
            @RequestParam(value = "wordlistMeaning") String wordlistMeaning,
            @PathVariable(value="phoneme") String phoneme
    ) {
        WordList wl = WordListService.getWordlist(wordlistMeaning);
        return wl.getPhonemeStats().get(phoneme);
    }
}
