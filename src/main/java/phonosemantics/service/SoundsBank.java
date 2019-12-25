package phonosemantics.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.model.phonetics.Consonant;
import phonosemantics.model.phonetics.Phoneme;
import phonosemantics.model.phonetics.Vowel;

import java.util.HashMap;

/************ Singleton class
 *
 * Provides a table with all the vowels and consonants and its parameters
 * Every vowel is an object of Vowel class
 * Every consonant is an object of Consonant class
 *
 * *********************/
public class SoundsBank {
    static final Logger userLogger = LogManager.getLogger(SoundsBank.class);

    private HashMap<String, Phoneme> allPhonemesTable;
    public HashMap<String, Phoneme> getAllPhonemesTable() {
        return allPhonemesTable;
    }
    private static HashMap<Object, Integer> allPhTypesMap = null;

    /****************************  ENUMS VOWELS ************************/
    public enum Height {
        OPEN, NEAR_OPEN, OPEN_MID, MID, CLOSE_MID, NEAR_CLOSE, CLOSE
    }

    public enum Backness {
        BACK, CENTRAL, FRONT
    }

    public enum Roundness {
        ROUNDED, UNROUNDED
    }

    public enum Nasalization {
        NASAL, NON_NASAL
    }

    /**************************  ENUMS CONSONANTS ************************/
    public enum PlaceApproximate {
        LABIAL, CORONAL, DORSAL, LARYNGEAL
    }

    public enum PlacePrecise {
        BILABIAL, LABIODENTAL,                      // LABIAL
        DENTAL, ALVEOLAR, POSTALVEOLAR, RETROFLEX,  // CORONAL
        PALATAL, VELAR, UVULAR,                     // DORSAL
        EPIGLOTTAL, GLOTTAL
    }

    public enum MannerApproximate {
        OBSTRUENT, SONORANT, LIQUID
    }

    public enum MannerPricise {
        STOP, AFFRICATE, FRICATIVE, SIBILANT, SIBILANT_AFFRICATE,            // OBSTRUENT
        NASAL, APPROXIMANT, TRILL, FLAP,  // SONORANT
        LATERAL                         // LIQUID
    }

    public enum Phonation {
        VOICED, DEVOICED
    }

    /***************************  SINGLETON ************************/
    private static SoundsBank instance;

    public static SoundsBank getInstance() {
        if (instance == null) {
            instance = new SoundsBank();
            userLogger.debug("sounds bank instance initialized");
        }
        return instance;
    }

    /*************************** CONSTRUCTOR ************************/
    public SoundsBank() {
        this.allPhonemesTable = new HashMap<>();
        addConsonants();
        addVowels();
        // TODO all the affricates
        // TODO all the diacritics
    }

    // Создает MAP со всеми фонотипами в качестве ключей. Значения нулевые.
    public static HashMap<Object, Integer> getAllPhonotypes() {
        if (allPhTypesMap != null) {
            return allPhTypesMap;

        } else {
            HashMap<Object, Integer> map = new HashMap<>();

            /* **********************  VOWELS ***************************/
            // Height
            Object[] hArr = Height.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            // Backness
            hArr = Backness.values();
            for (int i = 0; i < hArr.length; i++) {
                map.put(hArr[i], 0);
            }

            // Roundness
            hArr = Roundness.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            // Nasalization
            hArr = Nasalization.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            /* **********************  CONSONANTS ***************************/
            // Cons.MannerApprox
            hArr = MannerApproximate.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            // Cons.MannerPrecise
            hArr = MannerPricise.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            // Cons.PlaceApprox
            hArr = PlaceApproximate.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            // Cons.PlacePrecise
            hArr = PlacePrecise.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            // Phonation
            hArr = Phonation.values();
            for (Object ob : hArr) {
                map.put(ob, 0);
            }

            userLogger.debug("map of phonotypes is created");
            allPhTypesMap = map;
            return map;
        }
    }

    private void addConsonants() {
        HashMap<String, Phoneme> table = this.allPhonemesTable;

        // consonants
        // STOPS
        table.put("p", new Consonant("p", PlacePrecise.BILABIAL, MannerPricise.STOP));
        table.put("b", new Consonant("b", PlacePrecise.BILABIAL, MannerPricise.STOP, true));
        table.put("p̪", new Consonant("p̪", PlacePrecise.LABIODENTAL, MannerPricise.STOP));
        table.put("b̪", new Consonant("b̪", PlacePrecise.LABIODENTAL, MannerPricise.STOP, true));
        //table.put("t̼̪", new Consonant("t̼", PlacePrecise.LABIODENTAL, MannerPricise.STOP));
        //table.put("d̼̪", new Consonant("d̼̪", PlacePrecise.LABIODENTAL, MannerPricise.STOP, true));
        table.put("t", new Consonant("t", PlacePrecise.ALVEOLAR, MannerPricise.STOP));
        table.put("d", new Consonant("d", PlacePrecise.ALVEOLAR, MannerPricise.STOP, true));
        table.put("ʈ", new Consonant("ʈ", PlacePrecise.RETROFLEX, MannerPricise.STOP));
        table.put("ɖ", new Consonant("ɖ", PlacePrecise.RETROFLEX, MannerPricise.STOP, true));
        table.put("c", new Consonant("c", PlacePrecise.PALATAL, MannerPricise.STOP));
        table.put("ɟ", new Consonant("ɟ", PlacePrecise.PALATAL, MannerPricise.STOP, true));
        table.put("k", new Consonant("k", PlacePrecise.VELAR, MannerPricise.STOP));
        table.put("g", new Consonant("g", PlacePrecise.VELAR, MannerPricise.STOP, true));
        table.put("q", new Consonant("q", PlacePrecise.UVULAR, MannerPricise.STOP));
        table.put("ɢ", new Consonant("ɢ", PlacePrecise.UVULAR, MannerPricise.STOP, true));
        table.put("ʡ", new Consonant("ʡ", PlacePrecise.EPIGLOTTAL, MannerPricise.STOP));
        table.put("ʔ", new Consonant("ʔ", PlacePrecise.GLOTTAL, MannerPricise.STOP));

        // FRICATIVES
        // SIBILANTS
        table.put("s", new Consonant("s", PlacePrecise.ALVEOLAR, MannerPricise.SIBILANT));
        table.put("z", new Consonant("z", PlacePrecise.ALVEOLAR, MannerPricise.SIBILANT, true));
        table.put("ʃ", new Consonant("ʃ", PlacePrecise.POSTALVEOLAR, MannerPricise.SIBILANT));
        // TODO подтвердить характериситки š
        table.put("š", new Consonant("š", PlacePrecise.POSTALVEOLAR, MannerPricise.SIBILANT));
        table.put("ʒ", new Consonant("ʒ", PlacePrecise.POSTALVEOLAR, MannerPricise.SIBILANT, true));
        table.put("ʂ", new Consonant("ʂ", PlacePrecise.RETROFLEX, MannerPricise.SIBILANT));
        table.put("ʐ", new Consonant("ʐ", PlacePrecise.RETROFLEX, MannerPricise.SIBILANT, true));
        table.put("ɕ", new Consonant("ɕ", PlacePrecise.PALATAL, MannerPricise.SIBILANT));
        table.put("ʑ", new Consonant("ʑ", PlacePrecise.PALATAL, MannerPricise.SIBILANT, true));

        // NON_SIBILANTS
        table.put("ɸ", new Consonant("ɸ", PlacePrecise.BILABIAL, MannerPricise.FRICATIVE));
        table.put("β", new Consonant("β", PlacePrecise.BILABIAL, MannerPricise.FRICATIVE, true));
        table.put("f", new Consonant("f", PlacePrecise.LABIODENTAL, MannerPricise.FRICATIVE));
        table.put("v", new Consonant("v", PlacePrecise.LABIODENTAL, MannerPricise.FRICATIVE, true));
        table.put("θ", new Consonant("θ", PlacePrecise.DENTAL, MannerPricise.FRICATIVE));
        table.put("ð", new Consonant("ð", PlacePrecise.DENTAL, MannerPricise.FRICATIVE, true));
        table.put("ç", new Consonant("ç", PlacePrecise.PALATAL, MannerPricise.FRICATIVE));
        table.put("ʝ", new Consonant("ʝ", PlacePrecise.PALATAL, MannerPricise.FRICATIVE, true));
        table.put("x", new Consonant("x", PlacePrecise.VELAR, MannerPricise.FRICATIVE));
        table.put("ɣ", new Consonant("ɣ", PlacePrecise.VELAR, MannerPricise.FRICATIVE, true));
        table.put("χ", new Consonant("χ", PlacePrecise.UVULAR, MannerPricise.FRICATIVE));
        table.put("ʁ", new Consonant("ʁ", PlacePrecise.UVULAR, MannerPricise.FRICATIVE, true));
        table.put("ħ", new Consonant("ħ", PlacePrecise.EPIGLOTTAL, MannerPricise.FRICATIVE));
        table.put("ʕ", new Consonant("ʕ", PlacePrecise.EPIGLOTTAL, MannerPricise.FRICATIVE, true));
        table.put("h", new Consonant("h", PlacePrecise.GLOTTAL, MannerPricise.FRICATIVE));
        table.put("ɦ", new Consonant("ɦ", PlacePrecise.GLOTTAL, MannerPricise.FRICATIVE, true));

        // SONORANT
        // NASAL
        table.put("m", new Consonant("m", PlacePrecise.BILABIAL, MannerPricise.NASAL, true));
        table.put("n", new Consonant("n", PlacePrecise.ALVEOLAR, MannerPricise.NASAL, true));
        table.put("ɳ", new Consonant("ɳ", PlacePrecise.RETROFLEX, MannerPricise.NASAL, true));
        table.put("ɲ", new Consonant("ɲ", PlacePrecise.PALATAL, MannerPricise.NASAL, true));
        table.put("ŋ", new Consonant("ŋ", PlacePrecise.VELAR, MannerPricise.NASAL, true));
        table.put("ng", new Consonant("ng", PlacePrecise.VELAR, MannerPricise.NASAL, true));
        table.put("ɴ", new Consonant("ɴ", PlacePrecise.UVULAR, MannerPricise.NASAL, true));

        // TRILL
        table.put("r", new Consonant("r", PlacePrecise.ALVEOLAR, MannerPricise.TRILL, true));
        table.put("ʀ", new Consonant("ʀ", PlacePrecise.UVULAR, MannerPricise.TRILL, true));
        table.put("ʢ", new Consonant("ʢ", PlacePrecise.EPIGLOTTAL, MannerPricise.TRILL, true));

        // APPROXIMANT
        table.put("l", new Consonant("l", PlacePrecise.ALVEOLAR, MannerPricise.LATERAL, true));
        table.put("ɭ", new Consonant("ɭ", PlacePrecise.RETROFLEX, MannerPricise.LATERAL, true));
        table.put("w", new Consonant("w", PlacePrecise.BILABIAL, MannerPricise.APPROXIMANT, true));
        table.put("j", new Consonant("j", PlacePrecise.PALATAL, MannerPricise.APPROXIMANT, true));


        // AFFRICATES
        // TODO
        table.put("ts", new Consonant("ts", PlacePrecise.ALVEOLAR, MannerPricise.SIBILANT, false));
        table.put("tʃ", new Consonant("tʃ", PlacePrecise.POSTALVEOLAR, MannerPricise.SIBILANT, false));
        table.put("d̠ʒ", new Consonant("d̠ʒ", PlacePrecise.POSTALVEOLAR, MannerPricise.SIBILANT, true));

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
        table.put("i", new Vowel("i", Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED,Nasalization.NON_NASAL));
        table.put("ĩ", new Vowel("ĩ", Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED,Nasalization.NASAL)); // \u0129
        table.put("ĩ", new Vowel("ĩ", Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED,Nasalization.NASAL)); // vow + \u0303

        table.put("y", new Vowel("y", Height.CLOSE, Backness.FRONT, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("e", new Vowel("e", Height.CLOSE_MID, Backness.FRONT, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ẽ", new Vowel("ẽ", Height.CLOSE_MID, Backness.FRONT, Roundness.UNROUNDED, Nasalization.NASAL));

        table.put("ø", new Vowel("ø", Height.CLOSE_MID, Backness.FRONT, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ɛ", new Vowel("ɛ", Height.OPEN_MID, Backness.FRONT, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("œ", new Vowel("œ", Height.OPEN_MID, Backness.FRONT, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("æ", new Vowel("æ", Height.NEAR_OPEN, Backness.FRONT, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("a", new Vowel("a", Height.OPEN, Backness.FRONT, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ã", new Vowel("ã", Height.OPEN, Backness.FRONT, Roundness.UNROUNDED, Nasalization.NASAL));
        table.put("ɶ", new Vowel("ɶ", Height.OPEN, Backness.FRONT, Roundness.ROUNDED, Nasalization.NON_NASAL));

        // central
        table.put("ɨ", new Vowel("ɨ", Height.CLOSE, Backness.CENTRAL, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ʉ", new Vowel("ʉ", Height.CLOSE, Backness.CENTRAL, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ɘ", new Vowel("ɘ", Height.CLOSE_MID, Backness.CENTRAL, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ɵ", new Vowel("ɵ", Height.CLOSE_MID, Backness.CENTRAL, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ə", new Vowel("ə", Height.MID, Backness.CENTRAL, Roundness.UNROUNDED, Nasalization.NON_NASAL)); // \u0259
        table.put("ǝ", new Vowel("ǝ", Height.MID, Backness.CENTRAL, Roundness.UNROUNDED, Nasalization.NON_NASAL)); // \u01DD

        table.put("ɜ", new Vowel("ɜ", Height.OPEN_MID, Backness.CENTRAL, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ɞ", new Vowel("ɞ", Height.OPEN_MID, Backness.CENTRAL, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ä", new Vowel("ä", Height.OPEN, Backness.CENTRAL, Roundness.UNROUNDED, Nasalization.NON_NASAL));

        // back
        table.put("ɯ", new Vowel("ɯ", Height.CLOSE, Backness.BACK, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("u", new Vowel("u", Height.CLOSE, Backness.BACK, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ʊ", new Vowel("ʊ", Height.NEAR_CLOSE, Backness.BACK, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ɤ", new Vowel("ɤ", Height.CLOSE_MID, Backness.BACK, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("o", new Vowel("o", Height.CLOSE_MID, Backness.BACK, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("õ", new Vowel("õ", Height.CLOSE_MID, Backness.BACK, Roundness.ROUNDED, Nasalization.NASAL));
        table.put("ʌ", new Vowel("ʌ", Height.OPEN_MID, Backness.BACK, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ɔ", new Vowel("ɔ", Height.OPEN_MID, Backness.BACK, Roundness.ROUNDED, Nasalization.NON_NASAL));
        table.put("ɑ", new Vowel("ɑ", Height.OPEN, Backness.BACK, Roundness.UNROUNDED, Nasalization.NON_NASAL));
        table.put("ɒ", new Vowel("ɒ", Height.OPEN, Backness.BACK, Roundness.ROUNDED, Nasalization.NON_NASAL));

        userLogger.info("vowels map is filled up");
    }

    private void addAffricates() {
        HashMap<String, Phoneme> table = this.allPhonemesTable;

        //vowels
        //front
        table.put("ts", new Consonant("ts", PlacePrecise.ALVEOLAR, MannerPricise.SIBILANT_AFFRICATE, false));
        table.put("dz", new Consonant("dz", PlacePrecise.ALVEOLAR, MannerPricise.SIBILANT_AFFRICATE, true));
        table.put("ʈʂ", new Consonant("ʈʂ", PlacePrecise.RETROFLEX, MannerPricise.SIBILANT_AFFRICATE, false));
        table.put("ɖʐ", new Consonant("ɖʐ", PlacePrecise.RETROFLEX, MannerPricise.SIBILANT_AFFRICATE, true));
        table.put("tɕ", new Consonant("tɕ", PlacePrecise.PALATAL, MannerPricise.SIBILANT_AFFRICATE, false));
        table.put("dʑ", new Consonant("dʑ", PlacePrecise.PALATAL, MannerPricise.SIBILANT_AFFRICATE, true));

        userLogger.info("affricates map is filled up");
    }

    public Phoneme find(String requestedSymbol) {
        return allPhonemesTable.get(requestedSymbol);
    }

    public static boolean isExtraSign(String symbol) {
        String[] symbols = {
                ".", ",", "-", "=", "˗"
        };
        boolean result = false;

        for (String s :symbols) {
            if (s.equals(symbol)) {
                result = true;
                break;
            }
        }
        return result;
    }
}