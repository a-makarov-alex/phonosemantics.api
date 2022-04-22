package phonosemantics.phonetics.phoneme;

import lombok.Data;
import org.apache.log4j.Logger;
import phonosemantics.statistics.Math;

import java.util.Map;

//TODO: можно сделать синглтон и менять PhonemeStats в контроллере
@Data
public class PhonemeInTable {
    private static final Logger userLogger = Logger.getLogger(PhonemeInTable.class);

    private String value;
    private int row;
    private int column;
    private boolean isRecognized = false;
    private DistinctiveFeatures distinctiveFeatures;
    // TODO это не должно быть полем! вынести исключительно в метод
    private PhonemeStats phonemeStats;   //optional

    public PhonemeInTable(String value, int r, int c) {
        this.value = value;
        this.row = r;
        this.column = c;
    }

    // Метод преобразует для фонемы DistinctiveFeatures в Map<String, Map<String, Integer>> с единицами для найденных признаков
    public Map<String, Map<String, Integer>> countPhonemeDistinctiveFeaturesInstances() {
        Map<String, Map<String, Integer>> distFeatureStats = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(DistinctiveFeatures.Type.ALL);
        DistinctiveFeatures df = this.distinctiveFeatures;
        final String TRUE = "true";
        final String FALSE = "false";

        // TODO это можно как-то автоматизировать через перебор полей класса, но пока нет смысла
        if (df == null) {
            //userLogger.info("distinctive feature is null: " + this.getValue());
            return null;
        }

        // MAJOR CLASS
        String key = df.getMajorClass().isVocoid() ? TRUE : FALSE;
        distFeatureStats.get("vocoid").put(key, 1);

        key = df.getMajorClass().isApproximant() ? TRUE : FALSE;
        distFeatureStats.get("approximant").put(key, 1);

        // MANNER
        key = df.getManner().isSonorant() ? TRUE : FALSE;
        distFeatureStats.get("sonorant").put(key, 1);

        key = df.getManner().isContinuant() ? TRUE : FALSE;
        distFeatureStats.get("continuant").put(key, 1);

        key = df.getManner().isNasal() ? TRUE : FALSE;
        distFeatureStats.get("nasal").put(key, 1);

        key = df.getManner().isVoiced() ? TRUE : FALSE;
        distFeatureStats.get("voiced").put(key, 1);

        distFeatureStats.get("stricture").put(String.valueOf(df.getManner().getStricture()), 1);
        distFeatureStats.get("rhotics").put(String.valueOf(df.getManner().getRhotics()), 1);
        distFeatureStats.get("lateral").put(String.valueOf(df.getManner().getLateral()), 1);
        distFeatureStats.get("sibilant").put(String.valueOf(df.getManner().getSibilant()), 1);
        distFeatureStats.get("semivowel").put(String.valueOf(df.getManner().getSemivowel()), 1);
        distFeatureStats.get("strident").put(String.valueOf(df.getManner().getStrident()), 1);
        distFeatureStats.get("mannerPrecise").put(String.valueOf(df.getManner().getMannerPrecise()), 1);

        // PLACE
        distFeatureStats.get("placeApproximate").put(String.valueOf(df.getPlace().getPlaceApproximate()), 1);
        distFeatureStats.get("placePrecise").put(String.valueOf(df.getPlace().getPlacePrecise()), 1);

        // VOWEL SPACE
        distFeatureStats.get("height").put(String.valueOf(df.getVowelSpace().getHeight()), 1);
        distFeatureStats.get("backness").put(String.valueOf(df.getVowelSpace().getBackness()), 1);
        distFeatureStats.get("roundness").put(String.valueOf(df.getVowelSpace().getRoundness()), 1);

        //TODO check if there are any other fileds

        return distFeatureStats;
    }

    @Data
    public static class PhonemeStats {
        private Double percentOfAllPhonemes;
        private Double percentOfWordsWithPhoneme;
        private Double averagePhonemesPerWord;
        private int numPh;
        private int numW;

        public PhonemeStats(int numPh, int numW, int numAllPhonemes, int numAllWords) {
            this.numPh = numPh;
            this.numW = numW;
            percentOfAllPhonemes = Math.round((double)numPh/(double)numAllPhonemes, 3);
            percentOfWordsWithPhoneme = Math.round((double)numW/(double)numAllWords, 3);
            averagePhonemesPerWord = Math.round((double) numPh/(double)numAllWords, 3);
        }
    }

    @Data
    public static class DistFeatureStats {
        private Double percentOfAllPhonemes;
        private Double percentOfWordsWithFeature;
        private Double averageFeatureInstancesPerWord;
        private int numPhWithFeature;
        private int numWordsWithFeature;

        public DistFeatureStats(int numFeat, int numW, int numAllPhonemes, int numAllWords) {
            this.numPhWithFeature = numFeat;
            this.numWordsWithFeature = numW;
            percentOfAllPhonemes = Math.round((double)numFeat/(double)numAllPhonemes,3);
            percentOfWordsWithFeature = Math.round((double)numW/(double)numAllWords, 3);
            averageFeatureInstancesPerWord = Math.round((double) numFeat/(double)numAllWords, 3);
        }
    }
}