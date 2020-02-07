package phonosemantics.phonetics;

import phonosemantics.data.SoundsBank;
import phonosemantics.phonetics.consonant.Consonant;
import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.phonetics.vowel.Vowel;

import java.util.HashMap;

public class Phonemes {


    private void addConsonants() {
        HashMap<String, Phoneme> table = this.allPhonemesTable;

        // consonants
        // STOPS
        table.put("p", new Consonant("p", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.STOP));
        table.put("b", new Consonant("b", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.STOP, true));
        table.put("p̪", new Consonant("p̪", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.STOP));
        table.put("b̪", new Consonant("b̪", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.STOP, true));
        //table.put("t̼̪", new Consonant("t̼", PlacePrecise.LABIODENTAL, MannerPricise.STOP));
        //table.put("d̼̪", new Consonant("d̼̪", PlacePrecise.LABIODENTAL, MannerPricise.STOP, true));
        table.put("t", new Consonant("t", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.STOP));
        table.put("d", new Consonant("d", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.STOP, true));
        table.put("ʈ", new Consonant("ʈ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.STOP));
        table.put("ɖ", new Consonant("ɖ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.STOP, true));
        table.put("c", new Consonant("c", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.STOP));
        table.put("ɟ", new Consonant("ɟ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.STOP, true));
        table.put("k", new Consonant("k", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.STOP));
        table.put("g", new Consonant("g", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.STOP, true));
        table.put("q", new Consonant("q", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.STOP));
        table.put("ɢ", new Consonant("ɢ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.STOP, true));
        table.put("ʡ", new Consonant("ʡ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.STOP));
        table.put("ʔ", new Consonant("ʔ", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.STOP));

        // FRICATIVES
        // SIBILANTS
        table.put("s", new Consonant("s", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT));
        table.put("z", new Consonant("z", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT, true));
        table.put("ʃ", new Consonant("ʃ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT));
        // TODO подтвердить характериситки š
        table.put("š", new Consonant("š", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT));
        table.put("ʒ", new Consonant("ʒ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT, true));
        table.put("ʂ", new Consonant("ʂ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT));
        table.put("ʐ", new Consonant("ʐ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT, true));
        table.put("ɕ", new Consonant("ɕ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT));
        table.put("ʑ", new Consonant("ʑ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT, true));

        // NON_SIBILANTS
        table.put("ɸ", new Consonant("ɸ", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.FRICATIVE));
        table.put("β", new Consonant("β", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("f", new Consonant("f", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.FRICATIVE));
        table.put("v", new Consonant("v", SoundsBank.PlacePrecise.LABIODENTAL, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("θ", new Consonant("θ", SoundsBank.PlacePrecise.DENTAL, SoundsBank.MannerPricise.FRICATIVE));
        table.put("ð", new Consonant("ð", SoundsBank.PlacePrecise.DENTAL, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("ç", new Consonant("ç", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.FRICATIVE));
        table.put("ʝ", new Consonant("ʝ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("x", new Consonant("x", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.FRICATIVE));
        table.put("ɣ", new Consonant("ɣ", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("χ", new Consonant("χ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.FRICATIVE));
        table.put("ʁ", new Consonant("ʁ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("ħ", new Consonant("ħ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.FRICATIVE));
        table.put("ʕ", new Consonant("ʕ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.FRICATIVE, true));
        table.put("h", new Consonant("h", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.FRICATIVE));
        table.put("ɦ", new Consonant("ɦ", SoundsBank.PlacePrecise.GLOTTAL, SoundsBank.MannerPricise.FRICATIVE, true));

        // SONORANT
        // NASAL
        table.put("m", new Consonant("m", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.NASAL, true));
        table.put("n", new Consonant("n", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.NASAL, true));
        table.put("ɳ", new Consonant("ɳ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.NASAL, true));
        table.put("ɲ", new Consonant("ɲ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.NASAL, true));
        table.put("ŋ", new Consonant("ŋ", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.NASAL, true));
        table.put("ng", new Consonant("ng", SoundsBank.PlacePrecise.VELAR, SoundsBank.MannerPricise.NASAL, true));
        table.put("ɴ", new Consonant("ɴ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.NASAL, true));

        // TRILL
        table.put("r", new Consonant("r", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.TRILL, true));
        table.put("ʀ", new Consonant("ʀ", SoundsBank.PlacePrecise.UVULAR, SoundsBank.MannerPricise.TRILL, true));
        table.put("ʢ", new Consonant("ʢ", SoundsBank.PlacePrecise.EPIGLOTTAL, SoundsBank.MannerPricise.TRILL, true));

        // APPROXIMANT
        table.put("l", new Consonant("l", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.LATERAL, true));
        table.put("ɭ", new Consonant("ɭ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.LATERAL, true));
        table.put("w", new Consonant("w", SoundsBank.PlacePrecise.BILABIAL, SoundsBank.MannerPricise.APPROXIMANT, true));
        table.put("j", new Consonant("j", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.APPROXIMANT, true));


        // AFFRICATES
        // TODO
        table.put("ts", new Consonant("ts", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT, false));
        table.put("tʃ", new Consonant("tʃ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT, false));
        table.put("d̠ʒ", new Consonant("d̠ʒ", SoundsBank.PlacePrecise.POSTALVEOLAR, SoundsBank.MannerPricise.SIBILANT, true));

        userLogger.info("consonants map is filled up");
    }

    private void addVowels() {
        // HOW TO GET UNICODE IF NEEDED
        /*String e = "ẽ";
        for (int i = 0; i < e.length(); i++) {
            System.out.print( "\\u" + Integer.toHexString(e.charAt(i) | 0x10000).substring(1));
            System.out.print(" ");
        }*/

        HashMap<String, Phoneme> table = this.allPhonemesTable;

        //vowels
        //front
        table.put("i", new Vowel("i", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ĩ", new Vowel("ĩ", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL)); // \u0129
        table.put("ĩ", new Vowel("ĩ", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL)); // vow + \u0303

        table.put("y", new Vowel("y", SoundsBank.Height.CLOSE, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("e", new Vowel("e", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ẽ", new Vowel("ẽ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL));

        table.put("ø", new Vowel("ø", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɛ", new Vowel("ɛ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("œ", new Vowel("œ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("æ", new Vowel("æ", SoundsBank.Height.NEAR_OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("a", new Vowel("a", SoundsBank.Height.OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ã", new Vowel("ã", SoundsBank.Height.OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NASAL));
        table.put("ɶ", new Vowel("ɶ", SoundsBank.Height.OPEN, SoundsBank.Backness.FRONT, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));

        // central
        table.put("ɨ", new Vowel("ɨ", SoundsBank.Height.CLOSE, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ʉ", new Vowel("ʉ", SoundsBank.Height.CLOSE, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɘ", new Vowel("ɘ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɵ", new Vowel("ɵ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ə", new Vowel("ə", SoundsBank.Height.MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL)); // \u0259
        table.put("ǝ", new Vowel("ǝ", SoundsBank.Height.MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL)); // \u01DD

        table.put("ɜ", new Vowel("ɜ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɞ", new Vowel("ɞ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ä", new Vowel("ä", SoundsBank.Height.OPEN, SoundsBank.Backness.CENTRAL, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));

        // back
        table.put("ɯ", new Vowel("ɯ", SoundsBank.Height.CLOSE, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("u", new Vowel("u", SoundsBank.Height.CLOSE, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ʊ", new Vowel("ʊ", SoundsBank.Height.NEAR_CLOSE, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɤ", new Vowel("ɤ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("o", new Vowel("o", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("õ", new Vowel("õ", SoundsBank.Height.CLOSE_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NASAL));
        table.put("ʌ", new Vowel("ʌ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɔ", new Vowel("ɔ", SoundsBank.Height.OPEN_MID, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɑ", new Vowel("ɑ", SoundsBank.Height.OPEN, SoundsBank.Backness.BACK, SoundsBank.Roundness.UNROUNDED, SoundsBank.Nasalization.NON_NASAL));
        table.put("ɒ", new Vowel("ɒ", SoundsBank.Height.OPEN, SoundsBank.Backness.BACK, SoundsBank.Roundness.ROUNDED, SoundsBank.Nasalization.NON_NASAL));

        userLogger.info("vowels map is filled up");
    }

    private void addAffricates() {
        HashMap<String, Phoneme> table = this.allPhonemesTable;

        //vowels
        //front
        table.put("ts", new Consonant("ts", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
        table.put("dz", new Consonant("dz", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));
        table.put("ʈʂ", new Consonant("ʈʂ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
        table.put("ɖʐ", new Consonant("ɖʐ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));
        table.put("tɕ", new Consonant("tɕ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
        table.put("dʑ", new Consonant("dʑ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));

        userLogger.info("affricates map is filled up");
    }
}
