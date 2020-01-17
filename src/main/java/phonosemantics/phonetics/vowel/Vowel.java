package phonosemantics.phonetics.vowel;

import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.data.SoundsBank;

public class Vowel extends Phoneme {

    private SoundsBank.Height height;
    private SoundsBank.Backness backness;
    private SoundsBank.Roundness roundedness;
    private SoundsBank.Nasalization nasalization;


    /**
     * CONSTRUCTORS
     * **/
    public Vowel(String symbol, SoundsBank.Height height,
                 SoundsBank.Backness backness, SoundsBank.Roundness roundedness, SoundsBank.Nasalization nasalization) {
        super(symbol, SoundClass.VOWEL);
        this.height = height;
        this.backness = backness;
        this.roundedness = roundedness;
        this.nasalization = nasalization;
    }

    /**
     *  GETTERS AND SETTERS
     *  **/
    public SoundsBank.Height getHeight() {
        return height;
    }
    public SoundsBank.Backness getBackness() {
        return backness;
    }
    public SoundsBank.Roundness isRoundedness() {
        return roundedness;
    }
    public SoundsBank.Nasalization isNasalization() {
        return nasalization;
    }
}
