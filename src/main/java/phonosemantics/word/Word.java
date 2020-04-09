package phonosemantics.word;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.LoggerConfig;
import phonosemantics.language.Language;
import phonosemantics.language.LanguageService;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Statistics;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Word {
    private static final Logger userLogger = LogManager.getLogger(Word.class);

    private String graphicForm;
    private ArrayList<String> transcription;    //phoneme may be find by PhonemesBank -> find(String phoneme)
    private String meaning;                     // the same. Meaning --> find(String meaning)
    private String language;    // need to find language using Language --> find(String language)
    private int length;
    private PartOfSpeech partOfSpeech;  // TODO think about shifting field to meaning

    public enum PartOfSpeech {
        NOUN, VERB, ADJECTIVE
    }

    public Word(String word, String meaning, String language, PartOfSpeech partOfSpeech) {
        this.graphicForm = word;
        this.meaning = meaning;
        setTranscriptionFromWord(); // length is added here also
        this.language = language;
        this.partOfSpeech = partOfSpeech;
    }

    public Word(String word, String language) {
        this.graphicForm = word;
        this.language = language;
        setTranscriptionFromWord(); // length is added here also
    }

    // for jUnit tests
    public Word(String word) {
        this.graphicForm = word;
        setTranscriptionFromWord(); // length is added here also
    }

    /**
     * GETTERS AND SETTERS
     **/
    public String getGraphicForm() {
        return graphicForm;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public ArrayList<String> getTranscription() {
        return transcription;
    }

    public int getLength() {
        return length;
    }

    public String getLanguage() {
        return language;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public int getNumOfPhonemes(String phoneme) {
        int count = 0;
        for (int i = 0; i < this.getTranscription().size(); i++) {
            if (this.getTranscription().get(i).equals(phoneme)) {
                count++;
            }
        }
        return count;
    }

    public void setTranscriptionFromWord() {
        this.transcription = new ArrayList<>();
        String word = this.getGraphicForm();

        if (word != null) {
            String[] letters = word.split("");
            // TODO remove HashMap<String, Phoneme> allPhonemes = SoundsBank.getInstance().getAllPhonemesTable();
//Language language = Language.getLanguage(this.getLanguage());

            // Phoneme might be a set of 2 symbols.
            // So we need to check the symbol after the current on every step.
            for (int i = 0; i < word.length(); i++) {

                // For extra symbols, accents, tones etc.
                if (!PhonemesBank.isExtraSign(letters[i])) {

                    // For last symbol
                    if (i == word.length() - 1) {
                        PhonemeInTable ph = PhonemesBank.getInstance().find(letters[i]);
                        if (ph != null) {
                            this.transcription.add(ph.getValue());
 //if (language != null) {
//language.categorizePh(ph);
//}
                        } else {
                            Statistics.addUnknownSymbol(letters[i]);
                        }
                        incrementLength();
                    } else {

                        // For 2-graph phoneme
                        PhonemeInTable ph = PhonemesBank.getInstance().find(letters[i] + letters[i + 1]);
                        if (ph != null) {
                            this.transcription.add(ph.getValue());
                            incrementLength();
//if (language != null) {
//language.categorizePh(ph);
//}
                            i++;
                        } else {

                            // For 1-graph phoneme
                            ph = PhonemesBank.getInstance().find(letters[i]);
                            if (ph != null) {
                                this.transcription.add(ph.getValue());
                                incrementLength();
//if (language != null) {
//language.categorizePh(ph);
//}
                            } else {
                                // Empty phoneme
                                Statistics.addUnknownSymbol(letters[i]);
                                incrementLength();
                            }
                        }
                    }
                } else {
                    if (LoggerConfig.CONSOLE_EXTRA_SYMBOLS) {
                        userLogger.debug("Extra: " + letters[i]);
                    }
                }
            }
        }
    }

    // Увеличивает как счетчик длины конкретного слова, так и сумму ВСЕХ фонем в исследовании
    private void incrementLength() {
        this.length += 1;   // это можно упростить, просто сделав transcription.size() когда она создана
        Statistics.incrementNumOfAllPhonemes();
    }


    /**
     * RETURNS SUM OF EVERY DISTINCTIVE FEATURE FOR A CERTAIN WORD
     */
    public HashMap<String, HashMap<Object, Integer>> countWordDistinctiveFeaturesStats(String type) {
        HashMap<String, HashMap<Object, Integer>> distFeaturesMap = DistinctiveFeatures.getFeaturesStructureDraft(type);

        // TODO рефакторинг. Буфер вроде уже не нужен
        HashMap<Object, Integer> bufferMap = new HashMap<>();
        for (Map.Entry<String, HashMap<Object, Integer>> phTypeHigherLevel : distFeaturesMap.entrySet()) {
            for (Map.Entry<Object, Integer> phTypeEntity : phTypeHigherLevel.getValue().entrySet()) {
                // Entity example: {true: 0} or {HIGH_MID: 0}
                // Get certain distinctive feature value from every word's phoneme
                Integer sumForWord = 0;

                for (String symbol : this.transcription) {
                    PhonemeInTable ph = PhonemesBank.getInstance().find(symbol);
                    // add 1 if phoneme has feature, add 0 if not
                    HashMap<String, HashMap<Object, Integer>> stats = ph.countPhonemeDistinctiveFeatureStats();
                    if (stats != null) {
                        sumForWord += stats.get(phTypeHigherLevel.getKey()).get(phTypeEntity.getKey());
                    } else {

                    }
                }
                phTypeEntity.setValue(sumForWord);
            }
        }
        return distFeaturesMap;
    }


    public Object[] getArticulationPattern(String base) {
        // TODO: тестовый фрагмент
        String str = "->";
        for (String s : this.getTranscription()) {
            str += s;
        }
        userLogger.info("transcription " + str);
        //**********************************************//

        Object[] pattern = new Object[this.transcription.size()];

        for (int i = 0; i < this.transcription.size(); i++) {
            PhonemeInTable ph = PhonemesBank.getInstance().find(transcription.get(i));
            if (ph == null) {
                userLogger.info("Unknown phoneme " + transcription.get(i) + " in transcription for word " + this.getGraphicForm());
                return null;
            }

            if (ph.getDistinctiveFeatures() == null) {
                userLogger.info("Distinctive features are not specified: " + ph.getValue() + " phoneme");
                pattern[i] = "NO_INFO";
            } else {
                switch (base.toLowerCase()) {
                    // Major Class
                    case "vocoid" : { pattern[i] = ph.getDistinctiveFeatures().getMajorClass().isVocoid(); break; }
                    case "approximant" : { pattern[i] = ph.getDistinctiveFeatures().getMajorClass().isApproximant(); break; }
                    // Manner
                    case "mannerprecise" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getMannerPrecise(); break; }
                    case "stricture" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getStricture(); break; }
                    case "sonorant" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isSonorant(); break; }
                    case "continuant" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isContinuant(); break; }
                    case "nasal" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isNasal(); break; }
                    case "strident" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getStrident(); break; }
                    case "sibilant" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getSibilant(); break; }
                    case "semivowel" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getSemivowel(); break; }
                    case "lateral" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getLateral(); break; }
                    case "rhotics" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getRhotics(); break; }
                    case "voiced" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isVoiced(); break; }
                    // Place
                    case "placeapproximate" : { pattern[i] = ph.getDistinctiveFeatures().getPlace().getPlaceApproximate(); break; }
                    case "placeprecise" : { pattern[i] = ph.getDistinctiveFeatures().getPlace().getPlacePrecise(); break; }
                    // Vowel Space
                    case "height" : { pattern[i] = ph.getDistinctiveFeatures().getVowelSpace().getHeight(); break; }
                    case "backness" : { pattern[i] = ph.getDistinctiveFeatures().getVowelSpace().getBackness(); break; }
                    case "roundness" : { pattern[i] = ph.getDistinctiveFeatures().getVowelSpace().getRoundness(); break; }

                    default : {
                        userLogger.info("base " + base + " has not proper value for getting articulation pattern");
                        break;
                    }
                }
            }
        }
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

        ArrayList<Word> list = wl.getWords(language);
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
