package phonosemantics.phonetics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.data.SoundsBank;
import phonosemantics.phonetics.consonant.Consonant;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.MannerPrecise;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.*;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Backness;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Height;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Roundness;
import phonosemantics.phonetics.vowel.Vowel;

import java.util.HashMap;


public class PhonemesBank {
    private static final Logger userLogger = LogManager.getLogger(PhonemesBank.class);

    private HashMap<String, DistinctiveFeatures> allPhonemes = new HashMap<>();

    private void addConsonants() {
        // consonants
        // STOPS
        allPhonemes.put("p", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.BILABIAL));
        allPhonemes.put("b", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.BILABIAL));
        allPhonemes.put("p̪", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.LABIODENTAL));
        allPhonemes.put("b̪", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.LABIODENTAL));
        //allPhonemes.put("t̼̪", new Consonant("t̼", PlacePrecise.LABIODENTAL, MannerPricise.STOP));
        //allPhonemes.put("d̼̪", new Consonant("d̼̪", PlacePrecise.LABIODENTAL, MannerPricise.STOP, true));
        allPhonemes.put("t", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.ALVEOLAR));
        allPhonemes.put("d", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.ALVEOLAR));
        allPhonemes.put("ʈ", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.RETROFLEX));
        allPhonemes.put("ɖ", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.RETROFLEX));
        allPhonemes.put("c", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.PALATAL));
        allPhonemes.put("ɟ", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.PALATAL));
        allPhonemes.put("k", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.VELAR));
        allPhonemes.put("g", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.VELAR));
        allPhonemes.put("q", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.UVULAR));
        allPhonemes.put("ɢ", new DistinctiveFeatures(MannerPrecise.PLOSIVE, true, PlacePrecise.UVULAR));
        allPhonemes.put("ʡ", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.EPIGLOTTAL));
        allPhonemes.put("ʔ", new DistinctiveFeatures(MannerPrecise.PLOSIVE, false, PlacePrecise.GLOTTAL));

        // FRICATIVES
        // SIBILANTS
        allPhonemes.put("s", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.ALVEOLAR, Sibilant.SIBILANT));
        allPhonemes.put("z", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.ALVEOLAR, Sibilant.SIBILANT));
        allPhonemes.put("ʃ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.POSTALVEOLAR, Sibilant.SIBILANT));
        // TODO подтвердить характериситки š
        allPhonemes.put("š", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.POSTALVEOLAR, Sibilant.SIBILANT));
        allPhonemes.put("ʒ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.POSTALVEOLAR, Sibilant.SIBILANT));
        allPhonemes.put("ʂ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.RETROFLEX, Sibilant.SIBILANT));
        allPhonemes.put("ʐ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.RETROFLEX, Sibilant.SIBILANT));
        allPhonemes.put("ɕ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.PALATAL, Sibilant.SIBILANT));
        allPhonemes.put("ʑ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.PALATAL, Sibilant.SIBILANT));

        // NON_SIBILANTS
        allPhonemes.put("ɸ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.BILABIAL));
        allPhonemes.put("β", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.BILABIAL));
        allPhonemes.put("f", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.LABIODENTAL, Sibilant.NOT_SIBILANT));
        allPhonemes.put("v", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.LABIODENTAL, Sibilant.NOT_SIBILANT));
        allPhonemes.put("θ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.DENTAL));
        allPhonemes.put("ð", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.DENTAL));
        allPhonemes.put("ç", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.PALATAL));
        allPhonemes.put("ʝ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.PALATAL));
        allPhonemes.put("x", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.VELAR));
        allPhonemes.put("ɣ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.VELAR));
        allPhonemes.put("χ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, false, PlacePrecise.UVULAR, Sibilant.NOT_SIBILANT));

        allPhonemes.put("ʁ", new DistinctiveFeatures(MannerPrecise.FRICATIVE, true, PlacePrecise.UVULAR, Lateral.NOT_LATERAL, Rhotics.RHOTICS));

//TODO        allPhonemes.put("ħ", new Consonant("ħ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.FRICATIVE));
//        allPhonemes.put("ʕ", new Consonant("ʕ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.FRICATIVE, true));
//        allPhonemes.put("h", new Consonant("h", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.FRICATIVE));
//        allPhonemes.put("ɦ", new Consonant("ɦ", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.FRICATIVE, true));

        // SONORANT
        // NASAL
        allPhonemes.put("m", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.BILABIAL));
        allPhonemes.put("n", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.ALVEOLAR));
        allPhonemes.put("ɳ", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.RETROFLEX));
        allPhonemes.put("ɲ", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.PALATAL));
        allPhonemes.put("ŋ", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.VELAR));
        allPhonemes.put("ng", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.VELAR));
        allPhonemes.put("ɴ", new DistinctiveFeatures(MannerPrecise.NASAL, true, PlacePrecise.UVULAR));

        // TRILL
        allPhonemes.put("r", new DistinctiveFeatures(MannerPrecise.TRILL, true, PlacePrecise.ALVEOLAR));
        allPhonemes.put("ʀ", new DistinctiveFeatures(MannerPrecise.TRILL, true, PlacePrecise.UVULAR));

//TODO        allPhonemes.put("ʢ", new Consonant("ʢ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.TRILL, true));

        // APPROXIMANT
        allPhonemes.put("l", new DistinctiveFeatures(MannerPrecise.APPROXIMANT, true, PlacePrecise.ALVEOLAR, Lateral.LATERAL, Rhotics.NOT_RHOTICS));
        allPhonemes.put("ɭ", new DistinctiveFeatures(MannerPrecise.APPROXIMANT, true, PlacePrecise.RETROFLEX, Lateral.LATERAL, Rhotics.NOT_RHOTICS));
        allPhonemes.put("w", new DistinctiveFeatures(MannerPrecise.APPROXIMANT, PlacePrecise.BILABIAL, Semivowel.SEMIVOWEL));
        allPhonemes.put("j", new DistinctiveFeatures(MannerPrecise.APPROXIMANT, PlacePrecise.PALATAL, Semivowel.SEMIVOWEL));

        // AFFRICATES
        // TODO
        allPhonemes.put("ts", new DistinctiveFeatures(MannerPrecise.AFFRICATE, false, PlacePrecise.ALVEOLAR, Sibilant.SIBILANT));
        allPhonemes.put("tʃ", new DistinctiveFeatures(MannerPrecise.AFFRICATE, false, PlacePrecise.POSTALVEOLAR, Sibilant.SIBILANT));
        allPhonemes.put("d̠ʒ", new DistinctiveFeatures(MannerPrecise.AFFRICATE, true, PlacePrecise.POSTALVEOLAR, Sibilant.SIBILANT));

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
        allPhonemes.put("i", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("ĩ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED, true)); // \u0129
        allPhonemes.put("ĩ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED, true)); // vow + \u0303

        allPhonemes.put("y", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.ROUNDED));
        allPhonemes.put("e", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("ẽ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.FRONT, Roundness.UNROUNDED, true));

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
