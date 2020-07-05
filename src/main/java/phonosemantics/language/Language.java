package phonosemantics.language;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.Word;
import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

// TODO: maybe id should be implemented cause 2 languages may have similar name
public class Language {
    private static final Logger userLogger = LogManager.getLogger(Language.class);

    private String title;
    private String family;
    private String group;  // typology etc.
    private Set<PhonemeInTable> phonology;

    // maps save the verdict "if the phoneme/phType were found in the Language words on practice"
    private Set<PhonemeInTable> phCoverage;
    private Map<String, Map<Object, Integer>> phTypeCoverage;
    private Set<PhonemeInTable> phNotDescribed;

    public Language(String title) {
        this.title = title;
        phCoverage = new HashSet<>();
        phNotDescribed = new HashSet<>();
    }

    public Language(String title, Set<PhonemeInTable> phonology) {
        this.title = title;
        this.phonology = phonology;
        this.phTypeCoverage = calculatePhTypeCoverage();
        phCoverage = new HashSet<>();
        phNotDescribed = new HashSet<>();
    }


    public void readLangPhonologyFromFile() {
        Set<PhonemeInTable> allPhonemes = new HashSet<>();

        try (InputStream inputStream = new FileInputStream(LanguageService.INPUT_LANGUAGES_PATH);
             Workbook wb = WorkbookFactory.create(inputStream)
        ){
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = 1;
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(0);
            String[] allPhArr = null;

            // LOOKING FOR THE LANGUAGE
            while (cell.getCellType() != CellType.BLANK) {
                String cellValue = cell.getStringCellValue();

                if (this.title.equalsIgnoreCase(cellValue)) {
                    userLogger.debug("PHONOLOGY for LANG " + this.getTitle() + ": ");

                    // CREATING A PHONEMES BANK FOR THE LANGUAGE
                    for (PhonemeInTable ph : PhonemesBank.getInstance().getAllPhonemesList()) {
                        if (allPhArr == null) {
                            String allPh = " ";
                            Row r = sheet.getRow(rowNum);

                            for (int i = 1; i <= LanguageService.NUM_OF_PHONOLOGY_COLUMNS; i++) {
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
                    break;
                }
                rowNum++;
                cell = sheet.getRow(rowNum).getCell(0);
            }
            this.phonology = allPhonemes;
            userLogger.info("phonology is set for " + this.getTitle() + " language");
            this.phTypeCoverage = calculatePhTypeCoverage();

        } catch (IOException e) {
            userLogger.error(e.toString());
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
    public Map<String, Map<Object, Integer>> calculatePhTypeCoverage() {
        userLogger.info("calculating PhType coverage");
        String type = "all";
        Map<String, Map<Object, Integer>> fullMap = DistinctiveFeatures.getFeaturesStructureDraft(type);


        List<WordList> allWordlists = WordListService.getAllWordLists();
        for (WordList wl : allWordlists) {
            userLogger.info("Calculating coverage for <" + wl.getMeaning() + "> wordlist");
            for (Word word : wl.getWords(this)) {
                userLogger.info("Word: " + word.getGraphicForm());

                // Put all the distFeatures counters of every word into a full distFeature map
                for (Map.Entry<String, Map<Object, Integer>> phTypeHigherLevel : fullMap.entrySet()) {

                    for (Map.Entry<Object, Integer> phTypeEntity : phTypeHigherLevel.getValue().entrySet()) {
                        // Entity example: {true: 0} or {HIGH_MID: 0}

                        Integer i = word.countWordDistinctiveFeaturesStats(type).get(phTypeHigherLevel.getKey()).get(phTypeEntity.getKey());
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

    public Set<PhonemeInTable> getPhCoverage() {
        return phCoverage;
    }

    public Map<String, Map<Object, Integer>> getPhTypeCoverage() {
        return phTypeCoverage;
    }

    public Set<PhonemeInTable> getPhNotDescribed() {
        return phNotDescribed;
    }
}
