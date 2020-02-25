package phonosemantics.language;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import phonosemantics.LoggerConfig;
import phonosemantics.data.SoundsBank;
import phonosemantics.phonetics.consonant.Consonant;
import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.phonetics.vowel.Vowel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;

// TODO: maybe id should be implemented cause 2 languages may have similar name
public class Language {
    private static final Logger userLogger = LogManager.getLogger(Language.class);

    private static final String INPUT_LANGUAGES_PATH = "./src/main/java/phonosemantics/input/AllLanguages.xlsx";

    // количество столбцов с фонемами в файле, описывающем языки
    private static int NUM_OF_PHONOLOGY_COLUMNS = 7;
    private static HashMap<String, Language> allLanguages = new HashMap<>();

    private String title;
    private String family;
    private String group;  // typology etc.

    private Set<Phoneme> phonology;

    // maps save the verdict "if the phoneme/phType were found in the Language words on practice"
    private Set<Phoneme> phCoverage;
    private HashMap<Object, Integer> phTypeCoverage;
    private Set<Phoneme> phNotDescribed;

    public Language(String title) {
        this.title = title;
        this.phonology = getLangPhonology();
        this.phTypeCoverage = calculatePhTypeCoverage();
        phCoverage = new HashSet<>();
        phNotDescribed = new HashSet<>();

        allLanguages.put(this.title, this);
    }


    public HashSet<Phoneme> getLangPhonology() {
        // open file for reading
        InputStream inputStream = null;
        HashSet<Phoneme> allPhonemes = new HashSet<>();

        try {
            inputStream = new FileInputStream(INPUT_LANGUAGES_PATH);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = 1;
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(0);
            SoundsBank cBank = SoundsBank.getInstance();
            String[] allPhArr = null;

            // LOOKING FOR LANGUAGE
            while (cell.getCellType() != CellType.BLANK) {
                String s = cell.getStringCellValue();

                if (this.title.toLowerCase().equals(s.toLowerCase())) {
                    if (LoggerConfig.CONSOLE_LANG_PHONOLOGY) {
                        System.out.print("PHONOLOGY for LANG " + this.getTitle() + ": ");
                    }

                    // CREATING A PHONEMES BANK FOR THE LANGUAGE
                    for (Map.Entry<String, Phoneme> entry : cBank.getAllPhonemesTable().entrySet()) {
                        if (allPhArr == null) {
                            String allPh = " ";
                            Row r = sheet.getRow(rowNum);

                            for (int i = 1; i <= NUM_OF_PHONOLOGY_COLUMNS; i++) {
                                if (r.getCell(i) != null) {
                                    allPh += r.getCell(i).getStringCellValue() + " ";
                                }
                            }
                            allPhArr = allPh.split(" ");
                        }

                        for (String ph: allPhArr) {
                            if (ph.equals(entry.getKey())) {
                                if (LoggerConfig.CONSOLE_LANG_PHONOLOGY) {
                                    System.out.print(entry.getKey() + " "); //check all phonemes in console
                                }
                                allPhonemes.add(entry.getValue());
                            }
                        }
                    }
                    if (LoggerConfig.CONSOLE_LANG_PHONOLOGY) {System.out.println();}
                    break;
                }

                rowNum++;
                cell = sheet.getRow(rowNum).getCell(0);
            }

            inputStream.close();
            return allPhonemes;

        } catch (IOException e) {
            userLogger.error(e.toString());
            return null;
        }
    }

    public void categorizePh(Phoneme ph) {
        if (phonology.contains(ph)) {
            if (!phCoverage.contains(ph)) {
                phCoverage.add(ph);
            }
        } else {
            phNotDescribed.add(ph);
        }
    }

    // Count all the phonotypes present in a specific language
    public HashMap<Object, Integer> calculatePhTypeCoverage() {
        //userLogger.debug("PhType coverage calculating is started");
        HashMap<Object, Integer> mapPhType = SoundsBank.getAllPhonotypes();

        if (LoggerConfig.CONSOLE_LANG_PHONOTYPES) {
            userLogger.debug(this.title);
        }

        for (Map.Entry<Object, Integer> entry : mapPhType.entrySet()) {
            if (LoggerConfig.CONSOLE_LANG_PHONOTYPES) {
                userLogger.debug(entry.getKey() + " : ");
            }
            entry.setValue(entry.getValue() + findPhType(entry.getKey()));
            if (LoggerConfig.CONSOLE_LANG_PHONOTYPES) {
                userLogger.debug("TOTAL : " + entry.getValue());
            }
        }
        return mapPhType;
    }


    // Буферный метод, который маппит классы для лямбды
    public Integer findPhType(Object phType) {
        int i = 0;
        Class phTypeClass = phType.getClass();

        // *************************** VOWELS
        if (phTypeClass.equals(SoundsBank.Height.class)) {
            return findVowByPredicate(vow -> vow.getHeight().equals((SoundsBank.Height)phType));
        }

        else if (phTypeClass.equals(SoundsBank.Backness.class)) {
            return findVowByPredicate(vow -> vow.getBackness().equals((SoundsBank.Backness)phType));
        }

        else if (phTypeClass.equals(SoundsBank.Roundness.class)) {
            return findVowByPredicate(vow -> vow.isRoundedness().equals((SoundsBank.Roundness)phType));
        }

        else if (phTypeClass.equals(SoundsBank.Nasalization.class)) {
            return findVowByPredicate(vow -> vow.isNasalization().equals((SoundsBank.Nasalization)phType));
        }

        // **************************CONSONANTS
        if (phTypeClass.equals(SoundsBank.Phonation.class)) {
            return findConsByPredicate(cons -> cons.isVoiced().equals((SoundsBank.Phonation)phType));
        }

        if (phTypeClass.equals(SoundsBank.MannerPricise.class)) {
            return findConsByPredicate(cons -> cons.getMannerPricise().equals((SoundsBank.MannerPricise)phType));
        }

        if (phTypeClass.equals(SoundsBank.MannerApproximate.class)) {
            return findConsByPredicate(cons -> cons.getMannerApproximate().equals((SoundsBank.MannerApproximate)phType));
        }

        else {
            return i;
        }
    }

    // Проверяем каждую фонему языка на соответствие заданному фонотипу и возвращаем число таких фонем в языке.
    private Integer findVowByPredicate(Predicate<Vowel> p) {
        int count = 0;

        for (Phoneme ph : phonology) {
            if (ph != null) {
                if (ph.getClass().equals(Vowel.class)) {
                    Vowel vow = (Vowel) ph;
                    if (p.test(vow)) {
                        count++;
                        if (LoggerConfig.CONSOLE_LANG_PHONOTYPES) {
                            System.out.println(vow.getSymbol());
                        }
                    }
                }
            }
        }
        return count;
    }


    private int findConsByPredicate(Predicate<Consonant> p) {
        int count = 0;

        for (Phoneme ph : phonology) {
            if (ph != null) {
                if (ph.getClass().equals(Consonant.class)) {
                    Consonant cons = (Consonant) ph;
                    if (p.test(cons)) {
                        count++;
                        if (LoggerConfig.CONSOLE_LANG_PHONOTYPES) {
                            System.out.println(cons.getSymbol());
                        }
                    }
                }
            }
        }
        return count;
    }


    /** GETTERS AND SETTERS **/
    public String getTitle() {
        return title;
    }

    public String getFamily() {
        return family;
    }

    public String getGroup() {
        return group;
    }

    public Set<Phoneme> getPhonology() {
        return phonology;
    }

    public static HashMap<String, Language> getAllLanguages() {
        return allLanguages;
    }

    public Set<Phoneme> getPhCoverage() {
        return phCoverage;
    }

    public HashMap<Object, Integer> getPhTypeCoverage() {
        return phTypeCoverage;
    }

    public Set<Phoneme> getPhNotDescribed() {
        return phNotDescribed;
    }

    public static Language getLanguage(String title) {
        if (allLanguages.containsKey(title)) {
            return allLanguages.get(title);
        } else {
            userLogger.debug("language " + title + " is not found and will be added now");
            Language l = new Language(title);
            return l;
        }
    }
}
