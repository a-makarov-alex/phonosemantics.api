package phonosemantics.phonetics.phoneme;

public class Phoneme {
    private String symbol;
    private SoundClass soundClass;

    public enum SoundClass {
        VOWEL, CONSONANT
    }


    public Phoneme(String symbol, SoundClass soundClass) {
        this.symbol = symbol;
        this.soundClass = soundClass;
    }

    public Phoneme(String symbol) {
        this.symbol = symbol;
    }

    public Phoneme() {
    }

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}