package phonosemantics.word.wordlist;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.language.Language;
import phonosemantics.language.LanguageService;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
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
    private HashMap<String, PhonemeInTable.PhonemeStats> phonemeStats;
    //TODO Deprecated
    private HashMap<Object, PhTypeStats> phTypeStatsMap = new HashMap<>();
    private HashMap<String, HashMap<Object, PhonemeInTable.DistFeatureStats>> distFeatureStats = new HashMap<>();
    private HashMap<String, HashMap<Object, Integer>> numOfWordsWithFeatures;
    private int numOfWords;
    private int numOfPhonemes;

    public WordList(ArrayList<Word> list) {
        this.meaning = list.get(0).getMeaning();
        //check that all the words in input list have the same meaning
        for (Word w : list) {
            if (w.getMeaning() != this.meaning) {
                userLogger.error("words in wordlist have different meanings: " + this.meaning + " != " + w.getMeaning());
                break;
            }
        }
        this.list = list;
        this.numOfWords = list.size();
        this.numOfPhonemes = 0;
        for (Word w : list) {
            numOfPhonemes += w.getTranscription().size();
        }

        // Заполняем фонемМапу
        phonemeStats = new HashMap<>();
        for (PhonemeInTable phoneme : PhonemesBank.getInstance().getPhonemesListForTableUI()) {
            String currentPh = phoneme.getValue();
            int counterPh = 0;
            int counterW = 0;

            for (Word w : list) {
                boolean wordIsCounted = false;
                for (String ph : w.getTranscription()) {
                    if (ph.equals(currentPh)) {
                        counterPh++;
                        wordIsCounted = true;
                    }
                }
                if (wordIsCounted) {
                    counterW++;
                }
            }
            phonemeStats.put(phoneme.getValue(), new PhonemeInTable.PhonemeStats(
                    counterPh,
                    counterW,
                    numOfPhonemes,
                    numOfWords));
        }

        HashMap<String, HashMap<Object, Integer>> rawStats = this.calculateFeaturesStats("all");
        for (Map.Entry<String, HashMap<Object, Integer>> highLevelEntry : rawStats.entrySet()) {
            distFeatureStats.put(highLevelEntry.getKey(), new HashMap<>());

            for (Map.Entry<Object, Integer> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                int numOfWordsWithFeature = this.numOfWordsWithFeatures.get(highLevelEntry.getKey()).get(lowLevelEntry.getKey());

                distFeatureStats.get(highLevelEntry.getKey()).put(
                        lowLevelEntry.getKey(),
                        new PhonemeInTable.DistFeatureStats(
                                lowLevelEntry.getValue(),
                                numOfWordsWithFeature,
                                numOfPhonemes,
                                numOfWords
                        ));
            }
        }
    }

    public HashMap<String, HashMap<Object, Integer>> calculateFeaturesStats(String type) {
        userLogger.info("starting calculating Wordlist " + this.getMeaning() + "features stats");
        HashMap<String, HashMap<Object, Integer>> resultMap = DistinctiveFeatures.getFeaturesStructureDraft(type);
        HashMap<String, HashMap<Object, Integer>> bufferForWordStats;
        numOfWordsWithFeatures = DistinctiveFeatures.getFeaturesStructureDraft(type);

        // TODO подумать: можно создать ещё одну вложенную мапу, в которой сохранять количество признаков в КАЖДОМ слове, а не только слов с признаком
        // TODO более продвинутый вариант - с сохранением структуры, чтобы знать где признак встречается в начале, где следует за другими признаками и т.д.
        // TODO ещё надо сделать так, чтобы данные каждый раз не пересчитывались впустую заново

        for (Word w : this.getList()) {
            bufferForWordStats = w.countWordDistinctiveFeaturesStats(type);

            for (Map.Entry<String, HashMap<Object, Integer>> entryHighLevel : bufferForWordStats.entrySet()) {
                for (Map.Entry<Object, Integer> entryLowLevel : entryHighLevel.getValue().entrySet()) {
                    // Add 1 to the number of words with Dist Feature if it is found in the Word
                    if (entryLowLevel.getValue() != 0) {
                        Integer numOfWordsWithFeature = numOfWordsWithFeatures.get(entryHighLevel.getKey()).get(entryLowLevel.getKey());
                        numOfWordsWithFeatures.get(entryHighLevel.getKey()).put(entryLowLevel.getKey(), numOfWordsWithFeature + 1);
                    }
                    // Sum for "current value in the result map + value for current word"
                    Integer i = entryLowLevel.getValue() + resultMap.get(entryHighLevel.getKey()).get(entryLowLevel.getKey());
                    // Put the new sum to result map
                    resultMap.get(entryHighLevel.getKey()).put(entryLowLevel.getKey(), i);
                }
            }
        }
        userLogger.info("finishing calculating Wordlist " + this.getMeaning() + "features stats");
        return resultMap;
    }

    @Deprecated
    public HashMap<Object, PhTypeStats> getPhonotypeStats() {
        if (this.phTypeStatsMap != null) {
            return this.phTypeStatsMap;
        } else {
            // Заполняем статсМапу парами "фонотип : пустой объект статов"
            HashMap<String, HashMap<Object, Integer>> allPhTypes = DistinctiveFeatures.getFeaturesStructureDraft("all");
            for (Map.Entry<String, HashMap<Object, Integer>> outerMap : allPhTypes.entrySet()) {
                for (Map.Entry<Object, Integer> phT : outerMap.getValue().entrySet()) {
                    PhTypeStats stats = new PhTypeStats(phT.getKey());
                    phTypeStatsMap.put(phT.getKey(), stats);
                }
            }

            // рассчитываем, в скольки языках из представленных присутствует каждый фонотип
            calculatePotentialWordsWithPhType();

            // рассчитываем, сколько в WL: 1. экземпляров каждого фонотипа, 2. слов с наличием экземпляра фонотипа
            countAllPhonotypesInstances();

            // рассчитываем 3 базовых параметра для оценки результатов
            calculateBasicStats();

            return this.phTypeStatsMap;
        }
    }


    // Counts all ph-types for further statistics and write result to the Statistics object
    @Deprecated
    public void countAllPhonotypesInstances() {
        // Все фонотипы каждой фонемы из каждого слова в вордлисте суммируем в хашмапе
        for (Map.Entry<Object, PhTypeStats> entry : phTypeStatsMap.entrySet()) {
            Object phType = entry.getKey();
            int counterPh = 0;
            int counterW = 0;
            for (Word w : this.getList()) {
                // TODO раскомментить и переделать int incr = w.countPhonotype(phType);
                int incr = 1;
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


    // TODO метод явно не из этого класса
    @Deprecated
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
        HashMap<String, HashMap<Object, Integer>> fullMap = DistinctiveFeatures.getFeaturesStructureDraft("all");

        for (Map.Entry<String, HashMap<Object, Integer>> outerMap : fullMap.entrySet()) {
            for (Map.Entry<Object, Integer> entry : outerMap.getValue().entrySet()) {
                int count = 0;

                for (Word w : this.getList()) {
                    Language language = LanguageService.getLanguage(w.getLanguage());
                    HashMap<String, HashMap<Object, Integer>> phTypeCov = language.getPhTypeCoverage();

                    if (phTypeCov.get(outerMap.getKey()).get(entry.getKey()) > 0) {
                        count++;
                    }
                }
                // записываем полученное значение в параметры WL
                this.getPhTypeStatsMap().get(entry.getKey()).potentialWordsWithPhType = count;
                //TODO: userLogger.debug(entry.getKey() + " " + count);
            }
        }
        userLogger.debug("calculating potential words with PhType for wordlist " + this.meaning + " is finished");
    }

    /**
     * RETURNS ALL THE WORDS OF CERTAIN LANGUAGE
     * @param language
     * @return
     */
    public ArrayList<Word> getWords(Language language) {
        ArrayList<Word> list = new ArrayList<>();
        for (Word word : this.list) {
            if (word.getLanguage().toLowerCase().equals(language.getTitle().toLowerCase())) {
                list.add(word);
            }
        }
        return list;
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

    public int getNumOfWords() {
        return numOfWords;
    }

    public int getNumOfPhonemes() {
        return numOfPhonemes;
    }

    public HashMap<String, PhonemeInTable.PhonemeStats> getPhonemeStats() {
        return phonemeStats;
    }

    public HashMap<String, HashMap<Object, PhonemeInTable.DistFeatureStats>> getDistFeatureStats() {
        return distFeatureStats;
    }

    public HashMap<String, HashMap<Object, Integer>> getNumOfWordsWithFeatures() {
        return numOfWordsWithFeatures;
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
