package phonosemantics.word;

import org.springframework.web.bind.annotation.*;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.word.wordlist.WordListService;

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
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/words/patterns")
    public HashMap<String, Object[]> getAllArticulationPatternsForWord(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        HashMap<String, Object[]> distFeaturesDraft = DistinctiveFeatures.getFeaturesForAPI("all");
        HashMap<String, Object[]> articulationPatternMap = new HashMap<>();
        Word word = Word.getWord(language, meaning);

        for (Map.Entry<String, Object[]> entry : distFeaturesDraft.entrySet()) {
            Object[] pattern = word.getArticulationPattern(entry.getKey());
            articulationPatternMap.put(entry.getKey(), pattern);
        }
        return articulationPatternMap;
    }

    /**
     * GETTING WORD ARTICULATION PATTERN (1 WORD, 1 PATTERN)
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/words/patterns/{patternBase}")
    public Object[] getOneArticulationPatternForWord(
            @PathVariable(value= "patternBase") String patternBase,
            @RequestParam(value = "language") String language,
            @RequestParam(value = "meaning") String meaning
    ) {
        Word word = Word.getWord(language, meaning);
        return word.getArticulationPattern(patternBase);
    }
}
