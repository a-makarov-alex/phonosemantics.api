package phonosemantics.phonetics.phoneme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.language.Language;
import phonosemantics.word.wordlist.WordList;

import java.util.HashMap;

//TODO: можно сделать синглтон и менять PhonemeStats в контроллере
public class PhonemeInTable {
    private static final Logger userLogger = LogManager.getLogger(PhonemeInTable.class);

    private String value;
    private int row;
    private int column;
    private boolean isRecognized = false;
    private DistinctiveFeatures distinctiveFeatures;
    private PhonemeStats phonemeStats;   //optional

    public PhonemeInTable(String value, int r, int c) {
        this.value = value;
        this.row = r;
        this.column = c;
    }

    public HashMap<String, HashMap<Object, Integer>> phonemeDistinctiveFeatureStats() {
        HashMap<String, HashMap<Object, Integer>> distFeatureStats = DistinctiveFeatures.getFeaturesStats("all");

        Integer i = 0;
        String key = "";

        if (this.distinctiveFeatures != null) {
            i = this.distinctiveFeatures.getMajorClass().isVocoid() ? 1 : 0;
            key = i == 1 ? "true" : "false";
            distFeatureStats.get("vocoid").put(key, 1);
        } else {
            //userLogger.info("distinctive feature is null: " + this.getValue());
            return null;
        }

        //TODO other fileds

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
        private int numAllPhonemes;
        private int numAllWords;

        public PhonemeStats(int numPh, int numW, int numAllPhonemes, int numAllWords) {
            this.numPh = numPh;
            this.numW = numW;
            this.numAllPhonemes = numAllPhonemes;
            this.numAllWords = numAllWords;
            percentOfAllPhonemes = (double)numPh/(double)numAllPhonemes;
            percentOfWordsWithPhoneme = (double)numW/(double)numAllWords;
            averagePhonemesPerWord = (double) numPh/(double)numAllWords;
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
}
