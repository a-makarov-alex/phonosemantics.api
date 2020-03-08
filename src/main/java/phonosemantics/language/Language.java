package phonosemantics.language;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import phonosemantics.App;
import phonosemantics.LoggerConfig;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.Word;
import phonosemantics.word.wordlist.WordList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    private Set<PhonemeInTable> phonology;

    // maps save the verdict "if the phoneme/phType were found in the Language words on practice"
    private Set<PhonemeInTable> phCoverage;
    private HashMap<String, HashMap<Object, Integer>> phTypeCoverage;
    private Set<PhonemeInTable> phNotDescribed;

    public Language(String title) {
        this.title = title;
        this.phonology = getLangPhonology();
        this.phTypeCoverage = calculatePhTypeCoverage();
        phCoverage = new HashSet<>();
        phNotDescribed = new HashSet<>();

        allLanguages.put(this.title, this);
    }


    public HashSet<PhonemeInTable> getLangPhonology() {
        // open file for reading
        InputStream inputStream = null;
        HashSet<PhonemeInTable> allPhonemes = new HashSet<>();

        try {
            inputStream = new FileInputStream(INPUT_LANGUAGES_PATH);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = 1;
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(0);
            //SoundsBank cBank = SoundsBank.getInstance();
            String[] allPhArr = null;

            // LOOKING FOR LANGUAGE
            while (cell.getCellType() != CellType.BLANK) {
                String s = cell.getStringCellValue();

                if (this.title.toLowerCase().equals(s.toLowerCase())) {
                    userLogger.debug("PHONOLOGY for LANG " + this.getTitle() + ": ");

                    // CREATING A PHONEMES BANK FOR THE LANGUAGE
                    for (PhonemeInTable ph : PhonemesBank.getInstance().getAllPhonemesList()) {
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

                        for (String symbol: allPhArr) {
                            if (symbol.equals(ph.getValue())) {
                                userLogger.debug(ph.getValue() + " "); //check all phonemes in console
                                allPhonemes.add(ph);
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

    public void categorizePh(PhonemeInTable ph) {
        if (phonology.contains(ph)) {
            if (!phCoverage.contains(ph)) {
                phCoverage.add(ph);
            }
        } else {
            phNotDescribed.add(ph);
        }
    }

    // Count all the phonotypes present in a specific language
    public HashMap<String, HashMap<Object, Integer>> calculatePhTypeCoverage() {
        userLogger.info("calculating PhType coverage");
        HashMap<String, HashMap<Object, Integer>> fullMap = DistinctiveFeatures.getFeaturesStats("all");


        ArrayList<WordList> allWordlists = App.getAllWordLists();
        for (WordList wl : allWordlists) {
            userLogger.info("Calculating coverage for <" + wl.getMeaning() + "> wordlist");
            for (Word word : wl.getWordsByLanguage(this)) {
                userLogger.info("Word: " + word.getGraphicForm());

                // Put all the distFeatures counters of every word into a full distFeature map
                for (Map.Entry<String, HashMap<Object, Integer>> phTypeHigherLevel : fullMap.entrySet()) {

                    for (Map.Entry<Object, Integer> phTypeEntity : phTypeHigherLevel.getValue().entrySet()) {
                        // Entity example: {true: 0} or {HIGH_MID: 0}

                        Integer i = word.wordDistinctiveFeatures().get(phTypeHigherLevel.getKey()).get(phTypeEntity.getKey());
                        //userLogger.info("PH: " + phTypeHigherLevel.getKey() + " --- " + phTypeEntity.getKey() + " " + i);
                        phTypeEntity.setValue(phTypeEntity.getValue() + i);
                    }
                }
            }
        }
        return fullMap;
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

    public Set<PhonemeInTable> getPhonology() {
        return phonology;
    }

    public static HashMap<String, Language> getAllLanguages() {
        return allLanguages;
    }

    public Set<PhonemeInTable> getPhCoverage() {
        return phCoverage;
    }

    public HashMap<String, HashMap<Object, Integer>> getPhTypeCoverage() {
        return phTypeCoverage;
    }

    public Set<PhonemeInTable> getPhNotDescribed() {
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
