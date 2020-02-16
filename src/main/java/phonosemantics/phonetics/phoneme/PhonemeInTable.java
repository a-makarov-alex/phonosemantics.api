package phonosemantics.phonetics.phoneme;

import phonosemantics.word.wordlist.WordList;

//TODO: можно сделать синглтон и менять PhonemeStats в контроллере
public class PhonemeInTable {
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
