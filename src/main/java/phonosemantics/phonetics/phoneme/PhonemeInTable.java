package phonosemantics.phonetics.phoneme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.statistics.Math;

import java.util.HashMap;

//TODO: можно сделать синглтон и менять PhonemeStats в контроллере
public class PhonemeInTable {
    private static final Logger userLogger = LogManager.getLogger(PhonemeInTable.class);

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

    public HashMap<String, HashMap<Object, Integer>> countPhonemeDistinctiveFeaturesInstances() {
        HashMap<String, HashMap<Object, Integer>> distFeatureStats = DistinctiveFeatures.getFeaturesStructureDraft("all");
        DistinctiveFeatures df = this.distinctiveFeatures;

        // TODO это можно как-то автоматизировать через перебор полей класса, но пока нет смысла
        if (df == null) {
            //userLogger.info("distinctive feature is null: " + this.getValue());
            return null;
        }

        // MAJOR CLASS
        String key = df.getMajorClass().isVocoid() ? "true" : "false";
        distFeatureStats.get("vocoid").put(key, 1);

        key = df.getMajorClass().isApproximant() ? "true" : "false";
        distFeatureStats.get("approximant").put(key, 1);

        // MANNER
        key = df.getManner().isSonorant() ? "true" : "false";
        distFeatureStats.get("sonorant").put(key, 1);

        key = df.getManner().isContinuant() ? "true" : "false";
        distFeatureStats.get("continuant").put(key, 1);

        key = df.getManner().isNasal() ? "true" : "false";
        distFeatureStats.get("nasal").put(key, 1);

        key = df.getManner().isVoiced() ? "true" : "false";
        distFeatureStats.get("voiced").put(key, 1);

        distFeatureStats.get("stricture").put(df.getManner().getStricture(), 1);
        distFeatureStats.get("rhotics").put(df.getManner().getRhotics(), 1);
        distFeatureStats.get("lateral").put(df.getManner().getLateral(), 1);
        distFeatureStats.get("sibilant").put(df.getManner().getSibilant(), 1);
        distFeatureStats.get("semivowel").put(df.getManner().getSemivowel(), 1);
        distFeatureStats.get("strident").put(df.getManner().getStrident(), 1);
        distFeatureStats.get("mannerPrecise").put(df.getManner().getMannerPrecise(), 1);

        // PLACE
        distFeatureStats.get("placeApproximate").put(df.getPlace().getPlaceApproximate(), 1);
        distFeatureStats.get("placePrecise").put(df.getPlace().getPlacePrecise(), 1);

        // VOWEL SPACE
        distFeatureStats.get("height").put(df.getVowelSpace().getHeight(), 1);
        distFeatureStats.get("backness").put(df.getVowelSpace().getBackness(), 1);
        distFeatureStats.get("roundness").put(df.getVowelSpace().getRoundness(), 1);

        //TODO check if there are any other fileds

        return distFeatureStats;
    }

    public String getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isRecognized() {
        return isRecognized;
    }

    public void setRecognized(boolean recognized) {
        isRecognized = recognized;
    }

    public DistinctiveFeatures getDistinctiveFeatures() {
        return distinctiveFeatures;
    }

    public void setDistinctiveFeatures(DistinctiveFeatures distinctiveFeatures) {
        this.distinctiveFeatures = distinctiveFeatures;
    }

    public PhonemeStats getPhonemeStats() {
        return phonemeStats;
    }

    public void setPhonemeStats(PhonemeStats phonemeStats) {
        this.phonemeStats = phonemeStats;
    }

    public static class PhonemeStats {
        private Double percentOfAllPhonemes;
        private Double percentOfWordsWithPhoneme;
        private Double averagePhonemesPerWord;
        private int numPh;
        private int numW;
        private int numAllPhonemes; //optional. already present in WordList
        private int numAllWords; //optional. already present in WordList

        public PhonemeStats(int numPh, int numW, int numAllPhonemes, int numAllWords) {
            this.numPh = numPh;
            this.numW = numW;
            this.numAllPhonemes = numAllPhonemes;
            this.numAllWords = numAllWords;
            percentOfAllPhonemes = Math.round((double)numPh/(double)numAllPhonemes, 3);
            percentOfWordsWithPhoneme = Math.round((double)numW/(double)numAllWords, 3);
            averagePhonemesPerWord = Math.round((double) numPh/(double)numAllWords, 3);
        }

        public Double getPercentOfAllPhonemes() {
            return percentOfAllPhonemes;
        }

        public Double getPercentOfWordsWithPhoneme() {
            return percentOfWordsWithPhoneme;
        }

        public Double getAveragePhonemesPerWord() {
            return averagePhonemesPerWord;
        }

        public int getNumPh() {
            return numPh;
        }

        public int getNumW() {
            return numW;
        }

        public int getNumAllPhonemes() {
            return numAllPhonemes;
        }

        public int getNumAllWords() {
            return numAllWords;
        }
    }

    public static class DistFeatureStats {
        private Double percentOfAllPhonemes;
        private Double percentOfWordsWithFeature;
        private Double averageFeatureInstancesPerWord;
        private int numPhWithFeature;
        private int numWordsWithFeature;
        private int numAllPhonemes; //optional. already present in WordList
        private int numAllWords; //optional. already present in WordList

        public DistFeatureStats(int numFeat, int numW, int numAllPhonemes, int numAllWords) {
            this.numPhWithFeature = numFeat;
            this.numWordsWithFeature = numW;
            this.numAllPhonemes = numAllPhonemes;
            this.numAllWords = numAllWords;
            percentOfAllPhonemes = Math.round((double)numFeat/(double)numAllPhonemes,3);
            percentOfWordsWithFeature = Math.round((double)numW/(double)numAllWords, 3);
            averageFeatureInstancesPerWord = Math.round((double) numFeat/(double)numAllWords, 3);
        }

        public Double getPercentOfAllPhonemes() {
            return percentOfAllPhonemes;
        }

        public Double getPercentOfWordsWithFeature() {
            return percentOfWordsWithFeature;
        }

        public Double getAverageFeatureInstancesPerWord() {
            return averageFeatureInstancesPerWord;
        }

        public int getNumPhWithFeature() {
            return numPhWithFeature;
        }

        public int getNumWordsWithFeature() {
            return numWordsWithFeature;
        }

        public int getNumAllPhonemes() {
            return numAllPhonemes;
        }

        public int getNumAllWords() {
            return numAllWords;
        }
    }
}
