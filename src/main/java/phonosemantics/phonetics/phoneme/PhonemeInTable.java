package phonosemantics.phonetics.phoneme;

public class PhonemeInTable {
    private String value;
    private int row;
    private int column;

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
}
