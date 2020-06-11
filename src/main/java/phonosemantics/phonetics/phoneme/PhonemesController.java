package phonosemantics.phonetics.phoneme;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.Port;
import phonosemantics.output.header.Header;
import phonosemantics.output.header.HeadersForUI;
import phonosemantics.phonetics.PhonemesBank;

import java.util.ArrayList;

@RestController
public class PhonemesController {
    @CrossOrigin(origins = Port.FRONTEND_URL)
    @GetMapping("/phonemes/{type}")
    // returns only phonemes that are recognized by phonemes bank
    // type available values: all / vowel / consonant
    public ArrayList<PhonemeInTable> getPhonemesCoverage(@PathVariable(value="type") String type){
        return PhonemesBank.getInstance().getAllPhonemesList(type); // это не для UI, а вообще
    }

    @CrossOrigin(origins = Port.FRONTEND_URL)
    @GetMapping("/phonemes/table/{type}")
    // returns all phonemes and blank cells that are needed to draw a table on UI
    // type available values: vowel / consonant
    public ArrayList<PhonemeInTable> getPhonemesCoverageForTable(@PathVariable(value="type") String type){
        return PhonemesBank.getInstance().getPhonemesForTableUI(type);
    }

    @CrossOrigin(origins = Port.FRONTEND_URL)
    @GetMapping("/phonemes/headers/{distinctiveFeature}")
    public ArrayList<Header> getHeaders(@PathVariable(value="distinctiveFeature") String distinctiveFeature) {
        return HeadersForUI.getHeaders(distinctiveFeature);
    }
}
