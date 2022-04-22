package phonosemantics.phonetics.phoneme;

import lombok.Data;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

@Data
public class FeaturesPool {
    private static final Logger userLogger = Logger.getLogger(FeaturesPool.class);

    private List<DistinctiveFeatures> featuresTranscription; // транскрипция слова в виде последовательности объектов, отражающих набор фонемных признаков.

    // Характеристики первых и последних звуков.
    private DistinctiveFeatures initialConsonantFeatures;
    private DistinctiveFeatures initialVowelFeatures;
    private DistinctiveFeatures finalConsonantFeatures;
    private DistinctiveFeatures finalVowelFeatures;

    private Map<String, Map<String, Integer>> wordDistinctiveFeaturesStats;

    public FeaturesPool(PhonemesPool phonemesPool) {
        countWordDistinctiveFeaturesStats(
                DistinctiveFeatures.Type.ALL,
                phonemesPool.getTranscriptionFull()
        );

        initialConsonantFeatures = phonemesPool.getInitialConsonant().getDistinctiveFeatures();
        initialVowelFeatures = phonemesPool.getInitialVowel().getDistinctiveFeatures();
        //userLogger.info(initialVowelFeatures.toString());
    }


    /**
     * RETURNS SUM OF EVERY DISTINCTIVE FEATURE FOR A CERTAIN WORD
     */
    public Map<String, Map<String, Integer>> countWordDistinctiveFeaturesStats(DistinctiveFeatures.Type type, List<PhonemeInTable> transcriptionFull) {

        if (this.wordDistinctiveFeaturesStats != null) {
            return this.wordDistinctiveFeaturesStats;
        }

        Map<String, Map<String, Integer>> distFeaturesMap = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(type);

        for (Map.Entry<String, Map<String, Integer>> phTypeHigherLevel : distFeaturesMap.entrySet()) {
            for (Map.Entry<String, Integer> phTypeEntity : phTypeHigherLevel.getValue().entrySet()) {
                // Entity example: {true: 0} or {HIGH_MID: 0}
                // Get certain distinctive feature value from every word's phoneme
                Integer sumForWord = 0;

                for (PhonemeInTable ph : transcriptionFull) {
                    // add 1 if phoneme has feature, add 0 if not
                    Map<String, Map<String, Integer>> stats = ph.countPhonemeDistinctiveFeaturesInstances();
                    if (stats != null) {
                        sumForWord += stats.get(phTypeHigherLevel.getKey()).get(String.valueOf(phTypeEntity.getKey()));
                    } else {
                        userLogger.error("--- Фонемные признаки отсутствуют в справочнике для фонемы " + ph.getValue() );
                    }
                }
                phTypeEntity.setValue(sumForWord);
            }
        }
        this.wordDistinctiveFeaturesStats = distFeaturesMap;    // "кешируем" рассчёты для слова
        return distFeaturesMap;
    }
}
