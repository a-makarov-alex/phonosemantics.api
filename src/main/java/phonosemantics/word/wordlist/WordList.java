package phonosemantics.word.wordlist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.data.SoundsBank;
import phonosemantics.language.Language;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Statistics;
import phonosemantics.word.Word;
import phonosemantics.LoggerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  FLOW IS LIKE THAT:
 *  1. CONSTRUCTOR INITIALIZE PHTYPESTATS MAP
 *  2. METHOD calculatePotentialWordsWithPhType() PROVIDES NUM OF WORDS WHERE POTENTIALLY PHTYPE COULD BE FOUND
 *          PS: SOME PHTYPES COULD ABSENT IN SOME LANGUAGE
 *  3. METHOD countAllPhonotypesInstances() ADDS NUM OF PHTYPES INSTANCES AND NUM OF WORD WITH THEM
 *  4. METHOD calculateBasicStats() ADDS 3 BASIC STATS:
 *          - WORDS WITH PH TYPE PER ALL WORDS
 *          - PHTYPE INSTANCES PER ALL PHTYPES
 *          - AVERAGE PHTYPE INSTANCES NUM PER WORD
 **/
public class WordList {
    private static final Logger userLogger = LogManager.getLogger(WordList.class);

    private String meaning;
    private ArrayList<Word> list;
    private HashMap<Object, PhTypeStats> phTypeStatsMap = new HashMap<>();
    private HashMap<String, PhonemeInTable.PhonemeStats> phonemeStats;

    public WordList(ArrayList<Word> list) {
        this.meaning = list.get(0).getMeaning().getDefinition();
        //check that all the words in input list have the same meaning
        for (Word w : list) {
            if (w.getMeaning().getDefinition() != this.meaning) {
                userLogger.error("words in wordlist have different meanings: " + this.meaning + " != " + w.getMeaning().getDefinition());
                break;
            }
        }
        this.list = list;

        // Заполняем статсМапу парами "фонотип : пустой объект статов"
        HashMap<Object, Integer> allPhTypes = SoundsBank.getAllPhonotypes();
        for (Map.Entry<Object, Integer> phT : allPhTypes.entrySet()) {
            PhTypeStats stats = new PhTypeStats(phT.getKey());
            phTypeStatsMap.put(phT.getKey(), stats);
        }

        // Заполняем фонемМапу
        phonemeStats = new HashMap<>();
        for (Map.Entry<String, DistinctiveFeatures> entry : PhonemesBank.getInstance().getAllPhonemes().entrySet()) {
            String currentPh = entry.getKey();
            int counterPh = 0;
            int counterW = 0;
            int numOfAllPhonemes = 0;
            int numOfAllWords = list.size();
            for (Word w : list) {
                boolean wordIsCounted = false;
                for (String ph : w.getTranscription()) {
                    numOfAllPhonemes++;
                    if (ph.equals(currentPh)) {
                        counterPh++;
                        counterW++;
                        wordIsCounted = true;
                    }
                }
                wordIsCounted = false;
            }

            phonemeStats.put(entry.getKey(), new PhonemeInTable.PhonemeStats(
                    counterPh,
                    counterW,
                    numOfAllPhonemes,
                    numOfAllWords));
        }

        // рассчитываем, в скольки языках из представленных присутствует каждый фонотип
        calculatePotentialWordsWithPhType();

        // рассчитываем, сколько в WL: 1. экземпляров каждого фонотипа, 2. слов с наличием экземпляра фонотипа
        countAllPhonotypesInstances();

        // рассчитываем 3 базовых параметра для оценки результатов
        calculateBasicStats();
    }

//    public Word getGraphicForm(String language) {
//        for (Word word : this.list) {
//            if (word.getLanguage().getTitle().toLowerCase().equals(language.toLowerCase())) {
//                System.out.println("SUCCESS: Word of language " + language +
//                        " with meaning " + this.meaning + " is: " + word.getGraphicForm());
//                return word;
//            }
//        }
//        System.out.println("FAILURE: Word of language " + language +
//                " with meaning " + this.meaning + " is not found");
//        return null;
//    }


    // Counts all ph-types for further statistics and write result to the Statistics object
    public void countAllPhonotypesInstances() {
        // Все фонотипы каждой фонемы из каждого слова в вордлисте суммируем в хашмапе
        for (Map.Entry<Object, PhTypeStats> entry : phTypeStatsMap.entrySet()) {
            Object phType = entry.getKey();
            int counterPh = 0;
            int counterW = 0;
            for (Word w : this.getList()) {
                int incr = w.countPhonotype(phType);
                if (phType.equals(LoggerConfig.CONSOLE_SHOW_WORDS_OF_CLASS)) {
                    userLogger.debug(phType + " : " + w.getGraphicForm() + " " + incr);
                }
                counterPh += incr;
                if (incr > 0) {
                    counterW++;
                }
            }
            entry.getValue().phonemesWithPhTypeCounter = counterPh;
            entry.getValue().wordsWithPhTypeCounter = counterW;
        }
        userLogger.info("--- phonotypes for wordlist " + this.meaning + " are counted");
    }


    public void calculateBasicStats() {
        HashMap<Object, PhTypeStats> inputMap = phTypeStatsMap;

        for (Map.Entry<Object, PhTypeStats> stats : inputMap.entrySet()) {
            PhTypeStats st = stats.getValue();

            // PHTYPES_PER_LIST
            st.percentOfPhonemesWithPhType = (st.phonemesWithPhTypeCounter * 1.0)/ Statistics.getNumOfAllPhonemes();

            //TODO: делить на ноль нельзя
            if (st.potentialWordsWithPhType != 0) {
                // WORDS_WITH_PHTYPE_PER_LIST
                st.percentOfWordsWithPhType = (st.wordsWithPhTypeCounter * 1.0) / st.potentialWordsWithPhType;

                // PHTYPES_AVERAGE_PER_WORD
                st.averagePhTypePerWord = (st.phonemesWithPhTypeCounter * 1.0) / st.potentialWordsWithPhType;
            } else {
                st.percentOfWordsWithPhType = 0;
                st.averagePhTypePerWord = 0;
            }
        }
    }


    /**
     * Рассчитываем делитель для каждого фонотипа
     * Т.е. количество языков, в которых существуют экземпляры данного фонотипа
     * Применить ко всем листам сразу нельзя, т.к. некоторые значения в отдельных языках могут быть не зафиксированы
     */
    public void calculatePotentialWordsWithPhType() {
        HashMap<Object, Integer> mapOfDividers = SoundsBank.getAllPhonotypes();

        for (Map.Entry<Object, Integer> entry : mapOfDividers.entrySet()) {
            int count = 0;

            for (Word w : this.getList()) {
                Language language = new Language(w.getLanguage());
                HashMap<Object, Integer> phTypeCov = language.getPhTypeCoverage();

                if (phTypeCov.get(entry.getKey()) > 0) {
                    count++;
                }
            }
            // записываем полученное значение в параметры WL
            this.getPhTypeStatsMap().get(entry.getKey()).potentialWordsWithPhType = count;
            //TODO: userLogger.debug(entry.getKey() + " " + count);
        }
        userLogger.debug("calculating potential words with PhType for wordlist " + this.meaning + " is finished");
    }


    // GETTERS AND SETTERS
    public ArrayList<Word> getList() {
        return list;
    }

    public String getMeaning() {
        return meaning;
    }

    public HashMap<Object, PhTypeStats> getPhTypeStatsMap() {
        return phTypeStatsMap;
    }

    public HashMap<String, PhonemeInTable.PhonemeStats> getPhonemeStats() {
        return phonemeStats;
    }

    public class PhTypeStats {
        private Object phType;

        // 3 базовых величины для последующей оценки
        private double percentOfWordsWithPhType;
        private double percentOfPhonemesWithPhType;
        private double averagePhTypePerWord;

        // показатели для расчета базовых величин
        // делители для них:
        // либо Statistics.getNumOfAllPhonemes()
        // либо результат метода calculatePotentialWordsWithPhType()
        private int phonemesWithPhTypeCounter;
        private int wordsWithPhTypeCounter;
        // количество слов, в которых потенциально мог бы быть данный фонотип (т.е. в скольки языках он присутствует)
        // используется как делитель при получении некоторых параметров
        private int potentialWordsWithPhType;

        public PhTypeStats(Object phType) {
            this.phType = phType;
        }

        public Object getPhType() {
            return phType;
        }

        public double getPercentOfWordsWithPhType() {
            return percentOfWordsWithPhType;
        }

        public double getPercentOfPhonemesWithPhType() {
            return percentOfPhonemesWithPhType;
        }

        public double getAveragePhTypePerWord() {
            return averagePhTypePerWord;
        }

        public int getPhonemesWithPhTypeCounter() {
            return phonemesWithPhTypeCounter;
        }

        public int getWordsWithPhTypeCounter() {
            return wordsWithPhTypeCounter;
        }

        public int getPotentialWordsWithPhType() {
            return potentialWordsWithPhType;
        }
    }
}
