package phonosemantics.word;

import org.springframework.web.bind.annotation.*;
import phonosemantics.data.PortConfig;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.word.wordlist.WordListService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ArticulationPatternController {

    // TODO
    // 1. хашмап - структура по всем параметрам
    // 2. одно значение - структура по одному параметру
    // 3. по написанию слова - для тестов (форма -> new Word -> получить структуру)
    // 4. получить список паттернов по заданному параметру и значению (т.е. Object[] для целого вордлиста
    // 5. добавить поиск по структурам

    /**
     * GETTING WORD ARTICULATION PATTERNS (1 WORD, ALL PATTERNS)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/patterns/words")
    public HashMap<String, ArticulationPattern> getAllArticulationPatternsForWord(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        HashMap<String, Object[]> distFeaturesDraft = DistinctiveFeatures.getFeaturesForAPI("all");
        HashMap<String, ArticulationPattern> articulationPatternMap = new HashMap<>();
        Word word = Word.getWord(language, meaning);

        for (Map.Entry<String, Object[]> entry : distFeaturesDraft.entrySet()) {
            ArticulationPattern pattern = word.getArticulationPattern(entry.getKey());
            articulationPatternMap.put(entry.getKey(), pattern);
        }
        return articulationPatternMap;
    }

    /**
     * GETTING WORD ARTICULATION PATTERN (1 WORD, 1 PATTERN)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("patterns/{patternBase}/words")
    public ArticulationPattern getOneArticulationPatternForWord(
            @PathVariable(value= "patternBase") String patternBase,
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        Word word = Word.getWord(language, meaning);
        return word.getArticulationPattern(patternBase);
    }


    /**
     * GETTING WORDLIST ARTICULATION PATTERN (1 WORDLIST, 1 PATTERN)
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("patterns/{patternBase}/wordlists")
    public ArticulationPattern[] getOneArticulationPatternForWordlist(
            @PathVariable(value= "patternBase") String patternBase,
            @RequestParam(value = "meaning") String meaning
    ) {
        ArrayList<Word> wList = WordListService.getWordlist(meaning).getList();
        ArticulationPattern[] arrPatterns = new ArticulationPattern[wList.size()];

        for (int i = 0; i < wList.size(); i++) {
            arrPatterns[i] = wList.get(i).getArticulationPattern(patternBase);
        }
        return arrPatterns;
    }

}
