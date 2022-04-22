package phonosemantics.word.wordlist;

import lombok.Data;
import org.apache.log4j.Logger;
import phonosemantics.LoggerConfig;
import phonosemantics.language.Language;
import phonosemantics.language.LanguageService;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Statistics;
import phonosemantics.statistics.WLStats2022;
import phonosemantics.word.Word;
import phonosemantics.word.Word2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
@Data
public class WordList2022 {
    private static final Logger userLogger = Logger.getLogger(WordList2022.class);

    private String meaning;
    private List<Word2022> list;
    private WLStats2022 stats;


    public WordList2022(List<Word2022> list) {
        this.meaning = list.get(0).getMeaning();
        //check that all the words in input list have the same meaning
        for (Word2022 w : list) {
            if (!w.getMeaning().equals(this.meaning)) {
                userLogger.error("words in wordlist have different meanings: " + this.meaning + " != " + w.getMeaning());
                break;
            }
        }
        this.list = list;
        stats = new WLStats2022(list);
    }



    /*@Deprecated
    public Map<Object, PhTypeStats> getPhonotypeStats() {
        if (this.phTypeStatsMap != null) {
            return this.phTypeStatsMap;
        } else {
            phTypeStatsMap = new HashMap<>();
            // Заполняем статсМапу парами "фонотип : пустой объект статов"
            Map<String, Map<String, Integer>> allPhTypes = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(DistinctiveFeatures.Type.ALL);
            for (Map.Entry<String, Map<String, Integer>> outerMap : allPhTypes.entrySet()) {
                for (Map.Entry<String, Integer> phT : outerMap.getValue().entrySet()) {
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
    }*/


    // Counts all ph-types for further statistics and write result to the Statistics object
    /*@Deprecated
    public void countAllPhonotypesInstances() {
        // Все фонотипы каждой фонемы из каждого слова в вордлисте суммируем в хашмапе
        for (Map.Entry<Object, PhTypeStats> entry : phTypeStatsMap.entrySet()) {
            Object phType = entry.getKey();
            int counterPh = 0;
            int counterW = 0;
            for (Word2022 w : this.getList()) {
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
    }*/


    // TODO метод явно не из этого класса
    /*@Deprecated
    public void calculateBasicStats() {
        Map<Object, PhTypeStats> inputMap = phTypeStatsMap;

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
    }*/


    /**
     * Рассчитываем делитель для каждого фонотипа
     * Т.е. количество языков, в которых существуют экземпляры данного фонотипа
     * Применить ко всем листам сразу нельзя, т.к. некоторые значения в отдельных языках могут быть не зафиксированы
     */
    // TODO: метод надо преобразовать и сохранить в версии 2022
    /*public void calculatePotentialWordsWithPhType() {
        Map<String, Map<String, Integer>> fullMap = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(DistinctiveFeatures.Type.ALL);

        for (Map.Entry<String, Map<String, Integer>> outerMap : fullMap.entrySet()) {
            for (Map.Entry<String, Integer> entry : outerMap.getValue().entrySet()) {
                int count = 0;

                for (Word2022 w : this.getList()) {
                    Language language = LanguageService.getLanguage(w.getLanguage());
                    Map<String, Map<String, Integer>> phTypeCov = language.getPhTypeCoverage();

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
    }*/

    /**
     * RETURNS ALL THE WORDS OF CERTAIN LANGUAGE
     * @param language
     * @return
     */
    public List<Word2022> getWords(Language language) {
        List<Word2022> list = new ArrayList<>();
        for (Word2022 word : this.list) {
            if (word.getLanguage().getTitle().equalsIgnoreCase(language.getTitle().toLowerCase())) {
                list.add(word);
            }
        }
        return list;
    }

    @Data
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
    }

    @Override
    public String toString() {
        List<String> words = list.stream()
                .map(Word2022::getGraphicForm)
                .collect(Collectors.toList());

        return "WordList{" +
                "meaning='" + meaning + '\'' +
                ", list=" + words +
                '}';
    }
}