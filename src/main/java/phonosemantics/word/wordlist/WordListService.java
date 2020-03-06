package phonosemantics.word.wordlist;

import org.apache.poi.ss.usermodel.*;
import phonosemantics.App;
import phonosemantics.LoggerConfig;
import phonosemantics.word.Word;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//TODO: be careful, remove hardcode from this class
public class WordListService {
    private static final String FILENAME = "Input.xlsx";
    private static final String INPUT_DIRECTORY = "./src/main/java/phonosemantics/input/";
    static final Logger userLogger = LogManager.getLogger(WordListService.class);

    /**
     * Reads all the words from inputFile and write them to one list of Word entities
     */
    public static ArrayList<WordList> readAllWordListsFromInputFile() {

        userLogger.info("wordlist extracting from file starting...");

        // open file for reading
        InputStream inputStream = null;
        ArrayList<WordList> allWordlists = new ArrayList<>();

        try {
            inputStream = new FileInputStream(INPUT_DIRECTORY + FILENAME);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);

            int rowNum = 0;
            int colNum = 1;

            // read all the headers with words' Meanings
            while (sheet.getRow(rowNum).getCell(colNum) != null) {
                if (LoggerConfig.CONSOLE_SHOW_FOUND_MEANINGS_IN_INPUT_FILE) {
                    userLogger.debug("--- meaning found: " + sheet.getRow(rowNum).getCell(colNum).getStringCellValue());
                }
                WordList wl = composeWordList(sheet.getRow(rowNum).getCell(colNum).getStringCellValue());
                allWordlists.add(wl);

                colNum++;
            }
            inputStream.close();
            userLogger.info(colNum + " wordlists are composed from input file");
            return allWordlists;

        } catch (IOException e) {
            userLogger.error(e.toString());
            return null;
        }
    }

    /**
     * Reads a list of words from inputFile by meaning
     */
    public static WordList composeWordList(String meaning) {

        // open file for reading
        InputStream inputStream = null;
        ArrayList<Word> list = new ArrayList<Word>();

        try {
            inputStream = new FileInputStream(INPUT_DIRECTORY + FILENAME);
            userLogger.info("--- wordlist composing for " + meaning);
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet;
            sheet = wb.getSheetAt(0);
            Row nullRow = sheet.getRow(0);
            Cell cell;
            int count = 0;

            // Find meaning in the headers
            for (int col = 1; col <= sheet.getLastRowNum(); col++) {
                cell = nullRow.getCell(col);
                // stop on the first empty column
                if (cell == null) {
                    userLogger.info("PROBLEM: There is no words for " + meaning + " in the input file");
                    break;
                } else {
                    // Meaning is found successfully
                    if (cell.getStringCellValue().toLowerCase().equals(meaning.toLowerCase())) {
                        if (LoggerConfig.CONSOLE_SHOW_FOUND_MEANINGS_IN_INPUT_FILE) {
                            userLogger.debug("SUCCESS: Words for " + meaning +
                                    " found in the " + col + " column of the input file");
                        }
                        int lastRow = sheet.getLastRowNum();
                        for (int i = 1; i <=  lastRow + 1; i++) {
                            // Stop on the first empty row
                            if (sheet.getRow(i) == null) {
                                if (LoggerConfig.CONSOLE_SHOW_FOUND_MEANINGS_IN_INPUT_FILE) {
                                    userLogger.debug("Number of words for " + meaning + " : " + (count));
                                }
                                break;
                            }
                            cell = sheet.getRow(i).getCell(col);
                            if (cell != null && cell.getCellType() != CellType.BLANK) {
                                Word word = new Word(
                                        cell.getStringCellValue(),
                                        sheet.getRow(i).getCell(0).getStringCellValue());
                                word.setMeaning(nullRow.getCell(col).getStringCellValue());
                                list.add(word);
                                count++;
                            } else {
                                userLogger.info("No value for word \"" + nullRow.getCell(col).getStringCellValue() +
                                        "\" of language " + sheet.getRow(i).getCell(0).getStringCellValue());
                            }
                        }
                        // Break after wordlist is created
                        break;
                    }
                }
            }
            if (LoggerConfig.CONSOLE_SHOW_FOUND_MEANINGS_IN_INPUT_FILE) {
                System.out.println();
            }
            inputStream.close();
            return new WordList(list);

        } catch (IOException e) {
            userLogger.error(e.toString());
            return null;
        }
    }

    public static WordList getWordlist(String meaning) {
        ArrayList<WordList> allWordlists = App.getAllWordLists();
        for (WordList wl : allWordlists) {
            if (wl.getMeaning().equals(meaning)) {
                return wl;
            }
        }
        return null;
    }

//    public void prepareLanguagesMap() {
//        try {
//            userLogger.debug(INPUT_DIRECTORY + this.filePath);
//            FileInputStream inputStream = new FileInputStream(this.filePath);
//
//            Workbook wb = WorkbookFactory.create(inputStream);
//            Sheet sheet = wb.getSheetAt(0);
//            int rowNum = 1;
//
//            // read all languages names one by one
//            // and create their objects
//            while (sheet.getRow(rowNum) != null) {
//                Language l = new Language(sheet.getRow(rowNum).getCell(0).getStringCellValue());
//            }
//            inputStream.close();
//
//        } catch (IOException e) {
//            userLogger.error("file not found by path: " + this.filePath + ". " + e.toString());
//        }
//    }
}
