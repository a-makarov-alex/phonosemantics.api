package phonosemantics.phonetics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.poi.ss.usermodel.*;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.MannerPrecise;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.*;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Backness;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Height;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Roundness;
import phonosemantics.word.wordlist.WordList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PhonemesBank {
    private static final Logger userLogger = LogManager.getLogger(PhonemesBank.class);

    //путь к шаблону всех согласных фонем
    public static final String INPUT_FILE_PATH = "./src/main/java/phonosemantics/input/PhonemesCoverageExample.xlsx";

    private static final CoverageSheet VOWELS_SHEET = new CoverageSheet(createWorkbook(INPUT_FILE_PATH).getSheetAt(0), 2, 8, 1, 6);
    private static final CoverageSheet CONSONANTS_SHEET = new CoverageSheet(createWorkbook(INPUT_FILE_PATH).getSheetAt(1), 2, 14, 1, 24);

    private HashMap<String, PhonemeInTable> allPhonemes;

    private ArrayList<PhonemeInTable> allPhonemesForTableUI; // use this field only through getPhonemesListForTableUI() method
    private ArrayList<PhonemeInTable> vowelsForTable;   //вынужденная мера
    private ArrayList<PhonemeInTable> consonantsForTable; //вынужденная мера


    /***************************  SINGLETON ************************/
    private static PhonemesBank instance;

    public static PhonemesBank getInstance() {
        if (instance == null) {
            instance = new PhonemesBank();
            userLogger.debug("sounds bank instance initialized");
        }
        return instance;
    }

    /*************************** CONSTRUCTOR ************************/
    public PhonemesBank() {
        // TODO - заполнить final map
        // 1. найти фонемы в файле и записать их в ArrayList для UI таблицы (там не подходит HashMap из-за уникальности ключей)
        // 2. скопировать все реальные фонемы из ArrayList в HashMap<String, PhonemeInTable>
        // 3. добавить к фонемам в HashMap значения DistFeatures
        // 4. добавить ендпойнт для проверки, где остались пустые DistFeatures

        allPhonemesForTableUI = createAllPhonemesList();
        allPhonemes = copyAllPhonemesToHashMap();
        addDistinctiveFeaturesForAllPhonemes();

        // TODO all the affricates
        // TODO all the diacritics
    }

    /**
     * Получение объекта Workbook по указанному пути расположения файла Excel
     * **/
    private static Workbook createWorkbook(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(inputStream);
            inputStream.close();
            return wb;
        } catch (IOException e) {
            userLogger.error("PhonemesCoverageFile can not be opened: " + e.toString());
            return null;
        }
    }

    private HashMap<String, DistinctiveFeatures> getAllConsonantsFeatures() {
        HashMap<String, DistinctiveFeatures> allPhonemes = new HashMap<>();

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
        return allPhonemes;
    }


    private HashMap<String, DistinctiveFeatures> getAllVowelsFeatures() {
        // HOW TO GET UNICODE IF NEEDED
        /*String e = "ẽ";
        for (int i = 0; i < e.length(); i++) {
            System.out.print( "\\u" + Integer.toHexString(e.charAt(i) | 0x10000).substring(1));
            System.out.print(" ");
        }*/
        HashMap<String, DistinctiveFeatures> allPhonemes = new HashMap<>();

        //vowels
        //front
        allPhonemes.put("i", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("ĩ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED, true)); // \u0129
        allPhonemes.put("ĩ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.UNROUNDED, true)); // vow + \u0303

        allPhonemes.put("y", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.FRONT, Roundness.ROUNDED));
        allPhonemes.put("e", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("ẽ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.FRONT, Roundness.UNROUNDED, true));
        allPhonemes.put("ø", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.FRONT, Roundness.ROUNDED));
        allPhonemes.put("ɛ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN_MID, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("œ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN_MID, Backness.FRONT, Roundness.ROUNDED));
        allPhonemes.put("æ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.NEAR_OPEN, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("a", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN, Backness.FRONT, Roundness.UNROUNDED));
        allPhonemes.put("ã", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN, Backness.FRONT, Roundness.UNROUNDED, true));
        allPhonemes.put("ɶ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN, Backness.FRONT, Roundness.ROUNDED));

        // central
        allPhonemes.put("ɨ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.CENTRAL, Roundness.UNROUNDED));
        allPhonemes.put("ʉ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.CENTRAL, Roundness.ROUNDED));
        allPhonemes.put("ɘ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.CENTRAL, Roundness.UNROUNDED));
        allPhonemes.put("ɵ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.CENTRAL, Roundness.ROUNDED));
        allPhonemes.put("ə", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.MID, Backness.CENTRAL, Roundness.UNROUNDED)); // \u0259
        allPhonemes.put("ǝ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.MID, Backness.CENTRAL, Roundness.UNROUNDED)); // \u01DD
        allPhonemes.put("ɜ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN_MID, Backness.CENTRAL, Roundness.UNROUNDED));
        allPhonemes.put("ɞ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN_MID, Backness.CENTRAL, Roundness.ROUNDED));
        allPhonemes.put("ä", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN, Backness.CENTRAL, Roundness.UNROUNDED));

        // back
        allPhonemes.put("ɯ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.BACK, Roundness.UNROUNDED));
        allPhonemes.put("u", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE, Backness.BACK, Roundness.ROUNDED));
        allPhonemes.put("ʊ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.NEAR_CLOSE, Backness.BACK, Roundness.ROUNDED));
        allPhonemes.put("ɤ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.BACK, Roundness.UNROUNDED));
        allPhonemes.put("o", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.BACK, Roundness.ROUNDED));
        allPhonemes.put("õ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.CLOSE_MID, Backness.BACK, Roundness.ROUNDED, true));
        allPhonemes.put("ʌ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN_MID, Backness.BACK, Roundness.UNROUNDED));
        allPhonemes.put("ɔ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN_MID, Backness.BACK, Roundness.ROUNDED));
        allPhonemes.put("ɑ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN, Backness.BACK, Roundness.UNROUNDED));
        allPhonemes.put("ɒ", new DistinctiveFeatures(MannerPrecise.VOWEL, Height.OPEN, Backness.BACK, Roundness.ROUNDED));

        userLogger.info("vowels map is filled up");
        return allPhonemes;
    }

    private void addAffricates() {

//        allPhonemes.put("ts", new Consonant("ts", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
//        allPhonemes.put("dz", new Consonant("dz", SoundsBank.PlacePrecise.ALVEOLAR, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));
//        allPhonemes.put("ʈʂ", new Consonant("ʈʂ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
//        allPhonemes.put("ɖʐ", new Consonant("ɖʐ", SoundsBank.PlacePrecise.RETROFLEX, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));
//        allPhonemes.put("tɕ", new Consonant("tɕ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, false));
//        allPhonemes.put("dʑ", new Consonant("dʑ", SoundsBank.PlacePrecise.PALATAL, SoundsBank.MannerPricise.SIBILANT_AFFRICATE, true));

        userLogger.info("affricates map is filled up");
    }

    /**
     *  FIND PHONEME IN THE HASHMAP WITH ALL PHONEMES
     * **/
    public PhonemeInTable find(String requestedSymbol) {
        requestedSymbol = requestedSymbol.toLowerCase();
        PhonemeInTable ph = allPhonemes.get(requestedSymbol);
        if (ph != null) {
            return ph;
        }
        return null;
    }

    /**
     * CHECK IF REQUESTED SYMBOL IS A SPECIAL SYMBOL BUT NOT A PHONEME
     * **/
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

    /**
     * ПОЛУЧЕНИЕ СПИСКА ФОНЕМ ДЛЯ ФОРМИРОВАНИЯ ТАБЛИЦЫ НА UI
     * ВКЛЮЧАЕТ В СЕБЯ РАСПОЗНАННЫЕ ФОНЕМЫ, НЕРАСПОЗНАННЫЕ ФОНЕМЫ, КООРДИНАТЫ ПУСТЫХ ЯЧЕЕК
     * **/
    public ArrayList<PhonemeInTable> getPhonemesForTableUI(String type) {
        if (vowelsForTable != null && type.toLowerCase().equals("vowel")) {
            return vowelsForTable;
        } else {
            if (consonantsForTable != null && type.toLowerCase().equals("consonant")) {
                return consonantsForTable;
            }
        }
        CoverageSheet sheet = null;
        switch (type.toLowerCase()) {
            case "vowel" : {
                sheet = VOWELS_SHEET;
                userLogger.info("extracting vowels for table");
                break;
            }
            case "consonant" : {
                sheet = CONSONANTS_SHEET;
                userLogger.info("extracting consonants for table");
                break;
            }
            default : {
                userLogger.info("request parameter has an invalid value");
                return null;
            }
        }
        ArrayList<PhonemeInTable> resultList = new ArrayList<>();
        return extractAllPhonemesFromFile(sheet, resultList);
    }


    /**
     *   ДИНАМИЧЕСКИЕ ДАННЫЕ (статистика по Wordlist)
     */
    public ArrayList<PhonemeInTable> getPhonemesListForTableUI(WordList wl) {
        ArrayList<PhonemeInTable> phList = this.getPhonemesListForTableUI();
        for (PhonemeInTable ph : phList) {
            ph.setPhonemeStats(wl.getPhonemeStats().get(ph.getValue()));
        }
        userLogger.info("phonemes list for <<" + wl.getMeaning() + ">> wordlist is composed successfully");
        return phList;
    }


    /**
     *  СТАТИЧЕСКИЕ ДАННЫЕ
     *  Condition:  vowel / consonant
     * **/
    public ArrayList<PhonemeInTable> getPhonemesListForTableUI() {
        return getPhonemesListForTableUI("all");
    }

    public ArrayList<PhonemeInTable> getPhonemesListForTableUI(String condition) {
        if (condition.equals("all")) {
            return allPhonemesForTableUI;
        }

        ArrayList<PhonemeInTable> list = new ArrayList<>();

        switch (condition.toLowerCase()) {
            case "vowel" : {
                for (PhonemeInTable ph : getPhonemesForTableUI("vowel")) {
                    if (!ph.getValue().equals("")) {
                        list.add(ph);
                    }
                }
                userLogger.info("returning vowel phonemes");
                return list;
            }
            case "consonant" : {
                for (PhonemeInTable ph : getPhonemesForTableUI("consonant")) {
                    if (!ph.getValue().equals("")) {
                        list.add(ph);
                    }
                }
                userLogger.info("returning consonant phonemes");
                return list;
            }
            default : {
                userLogger.info("request parameter has an invalid value");
                return null;
            }
        }
    }

    private ArrayList<PhonemeInTable> createAllPhonemesList() {
        // Check constructor for more info
        userLogger.info("extracting all phonemes list from input file");
        ArrayList<PhonemeInTable> resultList = new ArrayList<>();

        userLogger.info("extracting vowels");
        extractAllPhonemesFromFile(VOWELS_SHEET, resultList);

        userLogger.info("extracting consonants");
        extractAllPhonemesFromFile(CONSONANTS_SHEET, resultList);
        return resultList;
    }


    /**
     * ДЛЯ ТАБЛИЦЫ НА UI НЕЛЬЗЯ ИСПОЛЬЗОВАТЬ HASHMAP, ТАК КАК ТАМ УНИКАЛЬНЫЕ КЛЮЧИ, А НАМ НУЖНЫ ВСЕ ДАННЫЕ О ПУСТЫХ ЯЧЕЙКАХ
     * **/
    private ArrayList<PhonemeInTable> extractAllPhonemesFromFile(CoverageSheet sheet, ArrayList<PhonemeInTable> resultList) {

        for (int i = sheet.firstRow; i <= sheet.lastRow; i++) {
            Row r = sheet.sheet.getRow(i);
            for (int j = sheet.firstCol; j <= sheet.lastCol; j++) {
                Cell c = r.getCell(j);

                /* *********** FOR BLANK CELLS **************/
                if (c == null) {
                    resultList.add(new PhonemeInTable("", i, j));
                }
                else {
                    if (c.getStringCellValue().equals("")) {
                        resultList.add(new PhonemeInTable("", i, j));

                        /* ************ FOR REAL PHONEMES *************/
                    } else {
                        PhonemeInTable ph = new PhonemeInTable(c.getStringCellValue(), i, j);
                        resultList.add(ph);
                    }
                }
            }
        }
        userLogger.info("extracting is finished successfully");
        return resultList;
    }


    private HashMap<String, PhonemeInTable> copyAllPhonemesToHashMap() {
        // Check constructor for more information
        HashMap<String, PhonemeInTable> resultMap = new HashMap<>();
        userLogger.info("copying phonemes to HashMap started");

        for (PhonemeInTable ph : allPhonemesForTableUI) {
            if (ph.getValue() != "") {
                resultMap.put(ph.getValue(), ph);
            }
        }
        userLogger.info("copying phonemes to HashMap finished successfully");
        return resultMap;
    }


    /**
     * ДОБАВЛЯЕТ ВАЛИДНОЕ ЗНАЧЕНИЕ ПОЛЯ DISTINCTIVE FEATURES КАК ДЛЯ ARRAYLIST, ТАК И ДЛЯ HASHMAP
     */
    private void addDistinctiveFeaturesForAllPhonemes() {
        HashMap<String, DistinctiveFeatures> buffer;

        buffer = getAllConsonantsFeatures();
        for (Map.Entry<String, DistinctiveFeatures> entry : buffer.entrySet()) {
            PhonemeInTable ph = allPhonemes.get(entry.getKey());
            if (ph == null) {
                userLogger.info("consonant " + entry.getKey() + " is not found in PhonemesCoverageTable");

            } else {
                ph.setDistinctiveFeatures(entry.getValue());
                ph.setRecognized(true);
            }
        }
        userLogger.info("Distinctive features added for consonants");


        buffer = getAllVowelsFeatures();
        for (Map.Entry<String, DistinctiveFeatures> entry : buffer.entrySet()) {
            PhonemeInTable ph = allPhonemes.get(entry.getKey());
            if (ph == null) {
                userLogger.info("vowel " + entry.getKey() + " is not found in PhonemesCoverageTable");
            } else {
                ph.setDistinctiveFeatures(entry.getValue());
            }
        }
        userLogger.info("Distinctive features added for vowels");
    }
}

class CoverageSheet {
    Sheet sheet;
    // Границы таблицы звуков для осмотра
    int firstRow;
    int lastRow;
    int firstCol;
    int lastCol;

    public CoverageSheet(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        this.sheet = sheet;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }
}
