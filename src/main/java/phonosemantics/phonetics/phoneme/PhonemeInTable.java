package phonosemantics.phonetics.phoneme;

public class PhonemeInTable {
    private String value;
    private int row;
    private int column;
    private boolean isRecognized = false;
    private DistinctiveFeatures distinctiveFeatures;

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
}
