package phonosemantics.word;

import lombok.Data;
import org.apache.log4j.Logger;
import phonosemantics.language.Language;
import phonosemantics.language.LanguageService;
import phonosemantics.phonetics.phoneme.FeaturesPool;
import phonosemantics.phonetics.phoneme.PhonemesPool;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.List;
import java.util.Map;

@Data
public class Word2022 {
    private static final Logger userLogger = Logger.getLogger(Word.class);

    private String graphicForm;
    private PhonemesPool phonemesPool;
    private FeaturesPool featuresPool;
    private String meaning;                     // Meaning --> find(String meaning)
    private String language;    // need to find language using Language --> find(String language)
    private int length;     // рассчитываемое значение. Берется в PhonemesPool -> getTranscription().length()
    private PartOfSpeech partOfSpeech;  // TODO think about shifting field to meaning

    public enum PartOfSpeech {
        NOUN, VERB, ADJECTIVE
    }

    public Word2022(String word, String meaning, String language, PartOfSpeech partOfSpeech) {
        this.graphicForm = word;
        this.meaning = meaning;
        phonemesPool = new PhonemesPool(graphicForm);
        this.length = phonemesPool.getTranscription().size();
        this.language = language;
        this.partOfSpeech = partOfSpeech;
    }

    public Word2022(String word, String language) {
        this.graphicForm = word;
        this.language = language;
        phonemesPool = new PhonemesPool(graphicForm);
        this.length = phonemesPool.getTranscription().size();
    }

    // for jUnit tests
    public Word2022(String word) {
        this.graphicForm = word;
        phonemesPool = new PhonemesPool(graphicForm);
        this.length = phonemesPool.getTranscription().size();
    }

    public int getNumOfCertainPhoneme(String phoneme) {
        int count = 0;
        for (int i = 0; i < this.getPhonemesPool().getTranscription().size(); i++) {
            if (this.getPhonemesPool().getTranscription().get(i).equals(phoneme)) {
                count++;
            }
        }
        return count;
    }


    /**
     * RETURNS SUM OF EVERY DISTINCTIVE FEATURE FOR A CERTAIN WORD
     */
    public Map<String, Map<String, Integer>> countWordDistinctiveFeaturesStats(String type) {
        return this.featuresPool.countWordDistinctiveFeaturesStats(type, this.phonemesPool.getTranscriptionFull());
    }




    public ArticulationPattern getArticulationPattern(String base) {
        // TODO: тестовый фрагмент
        String str = "->";
        for (String s : this.phonemesPool.getTranscription()) {
            str += s;
        }
        userLogger.info("transcription " + str);
        //**********************************************//

        ArticulationPattern pattern = new ArticulationPattern(this, base);
        return pattern;
    }

    /**
     * RETURNS WORD BY LANGUAGE AND MEANING
     */
    public static Word getWord(String languageName, String meaning) {
        WordList wl = WordListService.getWordlist(meaning);
        if (wl == null) {
            userLogger.debug("requested wordlist for [" + meaning + "] meaning does not exist");
            return null;
        }

        Language language = LanguageService.getLanguage(languageName);
        if (language == null) {
            userLogger.debug("requested [" + languageName + "] language does not exist");
            return null;
        }

        List<Word> list = wl.getWords(language);
        if (list == null) {
            userLogger.debug("no words found for [" + meaning + "] meaning in [" + languageName + "] language");
            return null;
        }

        if (list.size() > 1) {
            userLogger.debug("there are some words for [" + meaning + "] meaning in [" + languageName + "] language");
            return list.get(0);
        }

        return list.get(0);
    }
}

