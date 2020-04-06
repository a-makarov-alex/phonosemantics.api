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
    private int length = 0;
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
        this.length += 1;
        Statistics.incrementNumOfAllPhonemes();
    }


    /**
     * BUNCH OF METHODS TO COUNT PHONOTYPES
     **/
//    public int countPhonotype(Object object) {
//        Class objClass = object.getClass();
//
//        if (objClass.equals(SoundsBank.MannerApproximate.class)) {
//            return countConsPhonotypeBy(cons -> cons.getMannerApproximate().equals((SoundsBank.MannerApproximate) object));
//        } else if (objClass.equals(SoundsBank.MannerPricise.class)) {
//            if (object.equals(LoggerConfig.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Manner Precise :   ");
//            }
//            return countConsPhonotypeBy(cons -> cons.getMannerPricise().equals((SoundsBank.MannerPricise) object));
//        } else if (objClass.equals(SoundsBank.Phonation.class)) {
//            return countConsPhonotypeBy(cons -> cons.isVoiced().equals((SoundsBank.Phonation) object));
//        }

        //* ************************** VOWELS **********************************//
//        else if (objClass.equals(SoundsBank.Height.class)) {
//            if (object.equals(LoggerConfig.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Height :   ");
//            }
//            return countVowPhonotypeBy(vow -> vow.getHeight().equals((SoundsBank.Height) object));
//        } else if (objClass.equals(SoundsBank.Backness.class)) {
//            return countVowPhonotypeBy(vow -> vow.getBackness().equals((SoundsBank.Backness) object));
//        } else if (objClass.equals(SoundsBank.Roundness.class)) {
//            if (object.equals(LoggerConfig.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Roundness :   ");
//            }
//            return countVowPhonotypeBy(vow -> vow.isRoundedness().equals((SoundsBank.Roundness) object));
//        } else if (objClass.equals(SoundsBank.Nasalization.class)) {
//            if (object.equals(LoggerConfig.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Nasalization :   ");
//            }
//            return countVowPhonotypeBy(vow -> vow.isNasalization().equals((SoundsBank.Nasalization) object));
//        } else {
//            return 0;
//        }
//    }
//
//    /**
//     * The main methods for counting phonotype instances in the graphicForm
//     **/
//    private int countConsPhonotypeBy(Predicate<Consonant> p) {
//        int count = 0;
//        for (String symb : this.transcription) {
//            Phoneme ph = SoundsBank.getInstance().find(symb);
//            if (ph != null) {
//                if (ph.getClass().equals(Consonant.class)) {
//                    Consonant cons = (Consonant) ph;
//                    if (p.test(cons)) {
//                        count++;
//                    }
//                }
//            }
//        }
//        return count;
//    }
//
//    private int countVowPhonotypeBy(Predicate<Vowel> p) {
//        int count = 0;
//        for (String symb : this.transcription) {
//            Phoneme ph = SoundsBank.getInstance().find(symb);
//            if (ph != null) {
//                if (ph.getClass().equals(Vowel.class)) {
//                    Vowel vow = (Vowel) ph;
//                    if (p.test(vow)) {
//                        count++;
//                    }
//                }
//            } else {
//                //System.out.println(this.getGraphicForm());
//            }
//        }
//        return count;
//    }

    /**
     * RETURNS SUM OF EVERY DISTINCTIVE FEATURE FOR A CERTAIN WORD
     */
    public HashMap<String, HashMap<Object, Integer>> countWordDistinctiveFeaturesStats() {
        HashMap<String, HashMap<Object, Integer>> distFeaturesMap = DistinctiveFeatures.getFeaturesStructureDraft("all");

        // Делаем рабочую мапу без иерархии
        // HashMap<String, HashMap<Object, Integer>> ----> HashMap<Object, Integer>
        // TODO рефакторинг. Буфер вроде уже не нужен
        HashMap<Object, Integer> bufferMap = new HashMap<>();
        for (Map.Entry<String, HashMap<Object, Integer>> phTypeHigherLevel : distFeaturesMap.entrySet()) {
            for (Map.Entry<Object, Integer> phTypeEntity : phTypeHigherLevel.getValue().entrySet()) {
                // Entity example: {true: 0} or {HIGH_MID: 0}
                //bufferMap.put(phTypeEntity.getKey(), phTypeEntity.getValue());

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
