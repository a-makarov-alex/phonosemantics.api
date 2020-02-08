package phonosemantics.phonetics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.data.SoundsBank;
import phonosemantics.phonetics.consonant.Consonant;
import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.phonetics.vowel.Vowel;

import java.util.ArrayList;
import java.util.HashMap;


public class PhonemesBank {
    private static final Logger userLogger = LogManager.getLogger(PhonemesBank.class);

    //private HashMap<String, DistinctiveFeatures> allPhonemes = new HashMap<>();
    private HashMap<String, Object> allPhonemes = new HashMap<>();

    private void addConsonants() {
        // consonants
        // STOPS

        allPhonemes.put("p", new Consonant("p", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("b", new Consonant("b", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.STOP, true));
        allPhonemes.put("p̪", new Consonant("p̪", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("b̪", new Consonant("b̪", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.STOP, true));
        //allPhonemes.put("t̼̪", new Consonant("t̼", PlacePrecise.LABIODENTAL, MannerPricise.STOP));
        //allPhonemes.put("d̼̪", new Consonant("d̼̪", PlacePrecise.LABIODENTAL, MannerPricise.STOP, true));
        allPhonemes.put("t", new Consonant("t", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("d", new Consonant("d", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.STOP, true));
        allPhonemes.put("ʈ", new Consonant("ʈ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("ɖ", new Consonant("ɖ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.STOP, true));
        allPhonemes.put("c", new Consonant("c", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("ɟ", new Consonant("ɟ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.STOP, true));
        allPhonemes.put("k", new Consonant("k", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("g", new Consonant("g", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.STOP, true));
        allPhonemes.put("q", new Consonant("q", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("ɢ", new Consonant("ɢ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.STOP, true));
        allPhonemes.put("ʡ", new Consonant("ʡ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.STOP));
        allPhonemes.put("ʔ", new Consonant("ʔ", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.STOP));

        // FRICATIVES
        // SIBILANTS
        allPhonemes.put("s", new Consonant("s", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT));
        allPhonemes.put("z", new Consonant("z", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT, true));
        allPhonemes.put("ʃ", new Consonant("ʃ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT));
        // TODO подтвердить характериситки š
        allPhonemes.put("š", new Consonant("š", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT));
        allPhonemes.put("ʒ", new Consonant("ʒ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT, true));
        allPhonemes.put("ʂ", new Consonant("ʂ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT));
        allPhonemes.put("ʐ", new Consonant("ʐ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT, true));
        allPhonemes.put("ɕ", new Consonant("ɕ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT));
        allPhonemes.put("ʑ", new Consonant("ʑ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT, true));

        // NON_SIBILANTS
        allPhonemes.put("ɸ", new Consonant("ɸ", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("β", new Consonant("β", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("f", new Consonant("f", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("v", new Consonant("v", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("θ", new Consonant("θ", SoundsBank.PlacePrecise.DENTAL, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("ð", new Consonant("ð", SoundsBank.PlacePrecise.DENTAL, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("ç", new Consonant("ç", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("ʝ", new Consonant("ʝ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("x", new Consonant("x", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("ɣ", new Consonant("ɣ", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("χ", new Consonant("χ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("ʁ", new Consonant("ʁ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("ħ", new Consonant("ħ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("ʕ", new Consonant("ʕ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.FRICATIVE, true));
        allPhonemes.put("h", new Consonant("h", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.FRICATIVE));
        allPhonemes.put("ɦ", new Consonant("ɦ", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.FRICATIVE, true));

        // SONORANT
        // NASAL
        allPhonemes.put("m", new Consonant("m", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.NASAL, true));
        allPhonemes.put("n", new Consonant("n", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.NASAL, true));
        allPhonemes.put("ɳ", new Consonant("ɳ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.NASAL, true));
        allPhonemes.put("ɲ", new Consonant("ɲ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.NASAL, true));
        allPhonemes.put("ŋ", new Consonant("ŋ", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.NASAL, true));
        allPhonemes.put("ng", new Consonant("ng", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.NASAL, true));
        allPhonemes.put("ɴ", new Consonant("ɴ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.NASAL, true));

        // TRILL
        allPhonemes.put("r", new Consonant("r", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.TRILL, true));
        allPhonemes.put("ʀ", new Consonant("ʀ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.TRILL, true));
        allPhonemes.put("ʢ", new Consonant("ʢ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.TRILL, true));

        // APPROXIMANT
        allPhonemes.put("l", new Consonant("l", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.LATERAL, true));
        allPhonemes.put("ɭ", new Consonant("ɭ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.LATERAL, true));
        allPhonemes.put("w", new Consonant("w", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.APPROXIMANT, true));
        allPhonemes.put("j", new Consonant("j", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.APPROXIMANT, true));


        // AFFRICATES
        // TODO
        allPhonemes.put("ts", new Consonant("ts", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT, false));
        allPhonemes.put("tʃ", new Consonant("tʃ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT, false));
        allPhonemes.put("d̠ʒ", new Consonant("d̠ʒ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT, true));

        userLogger.info("consonants map is filled up");
    }

    private void addVowels() {
        // HOW TO GET UNICODE IF NEEDED
        /*String e = "ẽ";
        for (int i = 0; i < e.length(); i++) {
            System.out.print( "\\u" + Integer.toHexString(e.charAt(i) | 0x10000).substring(1));
            System.out.print(" ");
        }*/


        //vowels
        //front
        allPhonemes.put("i", new Vowel("i", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ĩ", new Vowel("ĩ", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL)); // \u0129
        allPhonemes.put("ĩ", new Vowel("ĩ", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL)); // vow + \u0303

        allPhonemes.put("y", new Vowel("y", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("e", new Vowel("e", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ẽ", new Vowel("ẽ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL));

        allPhonemes.put("ø", new Vowel("ø", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɛ", new Vowel("ɛ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("œ", new Vowel("œ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("æ", new Vowel("æ", SoundsBank.Height.NEAR_OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("a", new Vowel("a", SoundsBank.Height.OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ã", new Vowel("ã", SoundsBank.Height.OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL));
        allPhonemes.put("ɶ", new Vowel("ɶ", SoundsBank.Height.OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));

        // central
        allPhonemes.put("ɨ", new Vowel("ɨ", SoundsBank.Height.CLOSE, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ʉ", new Vowel("ʉ", SoundsBank.Height.CLOSE, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɘ", new Vowel("ɘ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɵ", new Vowel("ɵ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ə", new Vowel("ə", SoundsBank.Height.MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL)); // \u0259
        allPhonemes.put("ǝ", new Vowel("ǝ", SoundsBank.Height.MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL)); // \u01DD

        allPhonemes.put("ɜ", new Vowel("ɜ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɞ", new Vowel("ɞ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ä", new Vowel("ä", SoundsBank.Height.OPEN, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));

        // back
        allPhonemes.put("ɯ", new Vowel("ɯ", SoundsBank.Height.CLOSE, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("u", new Vowel("u", SoundsBank.Height.CLOSE, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ʊ", new Vowel("ʊ", SoundsBank.Height.NEAR_CLOSE, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɤ", new Vowel("ɤ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("o", new Vowel("o", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("õ", new Vowel("õ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NASAL));
        allPhonemes.put("ʌ", new Vowel("ʌ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɔ", new Vowel("ɔ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɑ", new Vowel("ɑ", SoundsBank.Height.OPEN, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        allPhonemes.put("ɒ", new Vowel("ɒ", SoundsBank.Height.OPEN, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));

        userLogger.info("vowels map is filled up");
    }

    private void addAffricates() {

        allPhonemes.put("ts", new Consonant("ts", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
        allPhonemes.put("dz", new Consonant("dz", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));
        allPhonemes.put("ʈʂ", new Consonant("ʈʂ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
        allPhonemes.put("ɖʐ", new Consonant("ɖʐ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));
        allPhonemes.put("tɕ", new Consonant("tɕ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
        allPhonemes.put("dʑ", new Consonant("dʑ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));

        userLogger.info("affricates map is filled up");
    }
}
