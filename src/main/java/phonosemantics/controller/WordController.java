package phonosemantics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.model.Meaning;
import phonosemantics.model.Word;
import phonosemantics.service.WordService;

import java.util.ArrayList;

@RestController
public class WordController {

    @GetMapping("/words")
    public ArrayList<Word> getWordlist(@RequestParam(value = "meaning") String meaning) {
        ArrayList<Word> responseList = new ArrayList<>();
        for (Word w : WordService.getAllwords()) {
            if (w.getMeaning().getDefinition().equals(meaning)) {
                responseList.add(w);
            }
        }
        return responseList;
    }
}

