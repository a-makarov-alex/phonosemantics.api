package phonosemantics.statistics;

import lombok.Data;
import org.apache.log4j.Logger;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.Word2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class WLStats2022 {
    private static final Logger userLogger = Logger.getLogger(WLStats2022.class);

    private Map<String, PhonemeInTable.PhonemeStats> phonemeStatsMap;
    private Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> distFeatureStats = new HashMap<>();
    private int numOfWords;
    private int numOfPhonemes;
    // TODO Эта структура уже включена в distFeatureStats
    private Map<String, Map<String, Integer>> numOfWordsWithFeatures;


    public WLStats2022(List<Word2022> list) {
        this.numOfWords = list.size();
        this.numOfPhonemes = 0;
        for (Word2022 w : list) {
            numOfPhonemes += w.getPhonemesPool().getTranscription().size();
        }

        //userLogger.info("--- подсчёт статистики по фонемам для вордлиста " + list.get(0).getMeaning() + " ---");
        this.phonemeStatsMap = fillPhonemeStatsMap(list);

        //userLogger.info("--- подсчёт статистики по фонемным признакам для вордлиста " + list.get(0).getMeaning() + " ---");
        Map<String, Map<String, Integer>> rawStats = this.calculateFeaturesStats(list);
        distFeatureStats = this.fillDistinctiveFeatureStatsMap(rawStats);
    }


    // Заполняем статистику по отдельным фонемам
    private Map<String, PhonemeInTable.PhonemeStats> fillPhonemeStatsMap(List<Word2022> list) {
        Map<String, PhonemeInTable.PhonemeStats> resultMap = new HashMap<>();

        for (PhonemeInTable phoneme : PhonemesBank.getInstance().getAllPhonemesList()) {
            String currentPh = phoneme.getValue();
            int counterPh = 0;
            int counterW = 0;

            for (Word2022 w : list) {
                boolean wordIsCounted = false;
                for (String ph : w.getPhonemesPool().getTranscription()) {
                    if (ph.equals(currentPh)) {
                        counterPh++;
                        wordIsCounted = true;
                    }
                }
                if (wordIsCounted) {
                    counterW++;
                }
            }
            // Рассчитываем статистические данные по каждой фонеме и сохраняем
            resultMap.put(phoneme.getValue(), new PhonemeInTable.PhonemeStats(
                    counterPh,
                    counterW,
                    numOfPhonemes,
                    numOfWords));
        }
        return resultMap;
    }


    // Заполняем данные статистики по фонотипам исходя из сырых данных по фонемам
    public Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> fillDistinctiveFeatureStatsMap(Map<String, Map<String, Integer>> rawStats) {
        Map<String, Map<Object, PhonemeInTable.DistFeatureStats>> resultMap = new HashMap<>();

        for (Map.Entry<String, Map<String, Integer>> highLevelEntry : rawStats.entrySet()) {
            resultMap.put(highLevelEntry.getKey(), new HashMap<>());

            for (Map.Entry<String, Integer> lowLevelEntry : highLevelEntry.getValue().entrySet()) {
                int numOfWordsWithFeature = this.numOfWordsWithFeatures.get(highLevelEntry.getKey()).get(lowLevelEntry.getKey());

                // Рассчитываем статистические данные по каждому фонотипу и сохраняем
                resultMap.get(highLevelEntry.getKey()).put(
                        lowLevelEntry.getKey(),
                        new PhonemeInTable.DistFeatureStats(
                                lowLevelEntry.getValue(),
                                numOfWordsWithFeature,
                                numOfPhonemes,
                                numOfWords
                        ));
            }
        }
        return resultMap;
    }


    private Map<String, Map<String, Integer>> calculateFeaturesStats(List<Word2022> list) {
        DistinctiveFeatures.Type type = DistinctiveFeatures.Type.ALL;

        Map<String, Map<String, Integer>> resultMap = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(type);
        Map<String, Map<String, Integer>> bufferForWordStats;
        numOfWordsWithFeatures = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(type);

        // TODO подумать: можно создать ещё одну вложенную мапу, в которой сохранять количество признаков в КАЖДОМ слове, а не только слов с признаком
        // TODO более продвинутый вариант - с сохранением структуры, чтобы знать где признак встречается в начале, где следует за другими признаками и т.д.
        // TODO ещё надо сделать так, чтобы данные каждый раз не пересчитывались впустую заново

        for (Word2022 w : list) {
            bufferForWordStats = w.countWordDistinctiveFeaturesStats(type);

            for (Map.Entry<String, Map<String, Integer>> entryHighLevel : bufferForWordStats.entrySet()) {
                for (Map.Entry<String, Integer> entryLowLevel : entryHighLevel.getValue().entrySet()) {
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
        return resultMap;
    }
}
