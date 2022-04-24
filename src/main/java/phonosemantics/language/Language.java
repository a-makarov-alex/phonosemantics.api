package phonosemantics.language;


import lombok.Data;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.Word2022;
import phonosemantics.word.wordlist.WordList2022;
import phonosemantics.word.wordlist.WordListService;
import static phonosemantics.phonetics.phoneme.DistinctiveFeatures.Type.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

// TODO: maybe id should be implemented cause 2 languages may have similar name
@Data
public class Language {
    private static final Logger userLogger = Logger.getLogger(Language.class);

    private String title;
    private String family;
    private String group;  // typology etc.
    private Set<PhonemeInTable> phonology;
    // maps save the verdict "if the phoneme/phType were found in the Language words on practice"
    // следующие две коллекции хранят информацию об обнаруженных фонемах и фонемных признаках
    private Set<PhonemeInTable> phCoverage;
    private Map<String, Map<String, Integer>> phTypeCoverage;
    // фонемы, обнаруженные в словах, но не описанные в языке
    // TODO фича - показывать в логах ближайшие похожие из имеющихся в фонологии языка
    private Set<PhonemeInTable> phNotDescribed;

    public Language(String title) {
        this.title = title;
        phonology = readLangPhonologyFromFile();
        if (phonology != null) {
            userLogger.info("phonology is set for " + this.getTitle() + " language");
        }
        //this.phTypeCoverage = calculatePhTypeCoverage(); если раскомментить, зацикливается, т.к. там подтягиваются все вордлисты
        //phNotDescribed = new HashSet<>();
    }

    public Language(String title, Set<PhonemeInTable> phonology) {
        this.title = title;
        this.phonology = phonology;
        this.phTypeCoverage = calculatePhTypeCoverage();
        phNotDescribed = new HashSet<>();
    }


    public Set<PhonemeInTable> readLangPhonologyFromFile() {
        Set<PhonemeInTable> allPhonemes = new HashSet<>();

        try (InputStream inputStream = new FileInputStream(LanguageService.INPUT_LANGUAGES_PATH);
             Workbook wb = WorkbookFactory.create(inputStream)
        ){
            Sheet sheet = wb.getSheetAt(0);
            String[] allPhArr = null;

            int targetRow = this.getRowNumForLanguageInLanguagePhonologyFile();

            if (targetRow != 0) {
                userLogger.info("--- Фонемный состав языка " + this.getTitle() + ": ---");

                // Получение графической формы фонем из файла с фонологичей языка
                StringBuilder strb = new StringBuilder(" ");
                Row r = sheet.getRow(targetRow);

                for (int i = 1; i <= LanguageService.NUM_OF_PHONOLOGY_COLUMNS; i++) {
                    if (r.getCell(i) != null) {
                        strb.append(r.getCell(i).getStringCellValue());
                        strb.append(" ");
                    } else {
                        userLogger.info("Отсутствуют фонемы раздела " + sheet.getRow(0).getCell(i).getStringCellValue() + " для языка " + this.getTitle());
                    }
                }
                allPhArr = strb.toString().split(" ");

                // Поиск фонем в справочнике и их сохранение в виде объектов Phoneme в списке фонем языка
                for (String symbol: allPhArr) {
                    PhonemeInTable phoneme = PhonemesBank.getInstance().find(symbol);
                    if (phoneme != null) {
                        //userLogger.info(phoneme.getValue() + " ");
                        allPhonemes.add(phoneme);
                    } else {
                        // костыль
                        if (symbol.length() > 0) {
                            userLogger.error("Неизвестная фонема: " + symbol + " в составе языка " + this.getTitle());
                        }
                    }
                }
            } else {
                userLogger.error("язык " + this.getTitle() + " не обнаружен в справочнике");
                return null;
            }
        } catch (IOException e) {
            userLogger.error(e.toString());
        }
        return allPhonemes;
    }

    // Поиск строки с названием заданного языка
    private int getRowNumForLanguageInLanguagePhonologyFile() {
        try (InputStream inputStream = new FileInputStream(LanguageService.INPUT_LANGUAGES_PATH);
             Workbook wb = WorkbookFactory.create(inputStream)
        ){
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = 1;
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(0);

            // LOOKING FOR THE LANGUAGE
            while (cell.getCellType() != CellType.BLANK) {
                String cellValue = cell.getStringCellValue();

                if (this.title.equalsIgnoreCase(cellValue)) {
                    return rowNum;
                }
                rowNum++;
                Row r = sheet.getRow(rowNum);
                if (r == null) {
                    break;
                }
                cell = r.getCell(0);
                if (cell == null) {
                    break;
                }

            }
        } catch (IOException e) {
            userLogger.error(e.toString());
            return 0;
        }
        return 0;
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
    public Map<String, Map<String, Integer>> calculatePhTypeCoverage() {
        userLogger.info("calculating PhType coverage");
        DistinctiveFeatures.Type type = ALL;
        Map<String, Map<String, Integer>> fullMap = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(type);

        List<WordList2022> allWordlists = WordListService.getAllWordLists2022();
        for (WordList2022 wl : allWordlists) {
            userLogger.info("Calculating coverage for <" + wl.getMeaning() + "> wordlist");
            for (Word2022  word : wl.getWords(this)) {
                userLogger.info("Word: " + word.getGraphicForm());

                // Put all the distFeatures counters of every word into a full distFeature map
                for (Map.Entry<String, Map<String, Integer>> phTypeHigherLevel : fullMap.entrySet()) {

                    for (Map.Entry<String, Integer> phTypeEntity : phTypeHigherLevel.getValue().entrySet()) {
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
}
