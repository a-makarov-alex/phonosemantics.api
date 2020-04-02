package phonosemantics.language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import phonosemantics.language.Language;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LanguageService {
    private static final Logger userLogger = LogManager.getLogger(Language.class);
    public static final String INPUT_LANGUAGES_PATH = "./src/main/java/phonosemantics/input/AllLanguages.xlsx";

    // количество столбцов с фонемами в файле, описывающем языки
    public static int NUM_OF_PHONOLOGY_COLUMNS = 7;
    private static HashMap<String, Language> allLanguages;


    /**
     * READING ALL THE LANGUAGES WITH AVAILABLE PHONOLOGY
     */
    public static void readAllLanguagesFromFile() {
        if (allLanguages == null) {
            allLanguages = new HashMap<>();
            userLogger.info("reading languages from file");

            try {
                InputStream inputStream = new FileInputStream(INPUT_LANGUAGES_PATH);
                Workbook wb = WorkbookFactory.create(inputStream);
                inputStream.close();
                Sheet sheet = wb.getSheetAt(0);

                int rowNum = 1;
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(0);
                String[] allPhArr = null;

                // LOOKING FOR LANGUAGE
                while (cell.getCellType() != CellType.BLANK) {

                    String languageName = cell.getStringCellValue();
                    if (languageName == null) {
                        break;
                    }

                    Set<PhonemeInTable> languagePhonology = new HashSet<>();
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

                        for (String symbol : allPhArr) {
                            if (symbol.equals(ph.getValue())) {
                                userLogger.debug(ph.getValue() + " "); //check all phonemes in console
                                languagePhonology.add(ph);
                            }
                        }
                    }

                    Language language = new Language(languageName, languagePhonology);
                    userLogger.info("new language: " + language.getTitle());
                    allLanguages.put(languageName, language);

                    rowNum++;
                    cell = sheet.getRow(rowNum).getCell(0);
                }

            } catch (IOException e) {
                userLogger.error(e.toString());
            }
        }
    }


    public static void readAllLanguageNamesFromFile() {
        if (allLanguages == null) {
            allLanguages = new HashMap<>();
        }

        try {
            InputStream inputStream = new FileInputStream(INPUT_LANGUAGES_PATH);
            Workbook wb = WorkbookFactory.create(inputStream);
            inputStream.close();
            Sheet sheet = wb.getSheetAt(0);

            int rowNum = 1;
            Row row = sheet.getRow(rowNum);
            Cell cell = row.getCell(0);

            // LOOKING FOR LANGUAGE
            while (cell.getCellType() != CellType.BLANK) {

                String languageName = cell.getStringCellValue();
                if (languageName == null) {
                    break;
                }

                if (!allLanguages.containsKey(languageName)) {
                    Language language = new Language(languageName);
                    userLogger.info("new language without phonology: " + language.getTitle());
                    allLanguages.put(languageName, language);
                }

                rowNum++;
                if (sheet.getRow(rowNum) == null) {
                    break;
                }
                cell = sheet.getRow(rowNum).getCell(0);
                if (cell == null) {
                    break;
                }
            }

        } catch (IOException e) {
            userLogger.error(e.toString());
        }
    }

    /**
     * GET LANGUAGE BY TITLE
     * Принцип ленивой загрузки. Первоначально из файла берутся только названия языков,
     * а если данные по языку будут затребованы, тогда уже из файла подгрузится его фонемный состав (phonology).
     */
    public static Language getLanguage(String title) {
        // after the first request all language names are read from language file
        if (allLanguages == null) {
            readAllLanguageNamesFromFile();
        }

        // if the language was not previously requested, but found in file, its phonology is read from language file
        if (allLanguages.containsKey(title)) {
            Language lang = allLanguages.get(title);
            if (lang.getPhonology() == null) {
                allLanguages.get(title).readLangPhonologyFromFile();
            }
            return allLanguages.get(title);
        } else {
            //if language is not described in input file, it is added to allLanguages map without phonology
            userLogger.debug("language " + title + " phonology is not described");
            Language l = new Language(title);
            return l;
        }
    }

    /**
     * GET ALL LANGUAGES
     */
    public static HashMap<String, Language> getAllLanguages() {
        readAllLanguagesFromFile();
        for (Map.Entry<String, Language> languageEntry : allLanguages.entrySet()) {
            languageEntry.getValue().readLangPhonologyFromFile();
        }
        return allLanguages;
    }

    /**
     * GET ALL LANGUAGE NAMES
     */
    public static ArrayList<String> getAllLanguageNames() {
        userLogger.info("getting names of all available languages");
        readAllLanguageNamesFromFile();

        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Language> lang : allLanguages.entrySet()) {
            result.add(lang.getKey());
        }
        return result;
    }
}
