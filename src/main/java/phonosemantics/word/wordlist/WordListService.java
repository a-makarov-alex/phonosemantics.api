package phonosemantics.word.wordlist;

import org.apache.poi.ss.usermodel.*;
import phonosemantics.LoggerConfig;
import phonosemantics.data.InputConfig;
import phonosemantics.word.Word;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//TODO: be careful, remove hardcode from this class
public class WordListService {
    static final Logger userLogger = LogManager.getLogger(WordListService.class);


    // TODO: запихнуть в контекст эти данные, если мы не оставим путь как константу
    // TODO: add some kind of context to project to store data
    private static List<WordList> allWordLists;
    // TODO allWordlist можно заполнять в статическом блоке

    public static List<WordList> getAllWordLists(String path) {
        if (allWordLists != null) {
            userLogger.info("allWordlists is NOT null");
            return allWordLists;
        } else {
            userLogger.info("allWordlists is null");
            allWordLists = WordListService.readAllWordListsFromInputFile(path);
            return allWordLists;
        }
    }

    // For default input file
    public static List<WordList> getAllWordLists() {
        return getAllWordLists(InputConfig.INPUT_DIRECTORY + InputConfig.FILENAME);
    }


    /**
     * Reads all the words from inputFile and write them to one list of Word entities
     */
    public static List<WordList> readAllWordListsFromInputFile(String path) {
        userLogger.info("wordlist extracting from file starting...");
        // open file for reading
        List<WordList> allWordlists = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(inputStream);
        ) {
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = 0;
            int colNum = 1;

            // read all the headers with words' Meanings
            while (sheet.getRow(rowNum).getCell(colNum) != null) {
                if (LoggerConfig.CONSOLE_SHOW_FOUND_MEANINGS_IN_INPUT_FILE) {
                    userLogger.debug("--- meaning found: " + sheet.getRow(rowNum).getCell(colNum).getStringCellValue());
                }
                WordList wl = composeWordList(sheet.getRow(rowNum).getCell(colNum).getStringCellValue(), path);
                allWordlists.add(wl);

                colNum++;
            }
            userLogger.info(colNum - 1 + " wordlists are composed from input file");
            return allWordlists;

        } catch (IOException e) {
            userLogger.error(e.toString());
            return null;
        }
    }

    /**
     * Reads a list of words from inputFile by meaning
     */
    public static WordList composeWordList(String meaning, String path) {
        // open file for reading
        List<Word> list = new ArrayList<Word>();

        try (InputStream inputStream = new FileInputStream(path);
             Workbook wb = WorkbookFactory.create(inputStream);
        ) {
            userLogger.info("--- wordlist composing for " + meaning);

            Sheet sheet = wb.getSheetAt(0);
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
            WordList wordList = new WordList(list);
            return wordList;

        } catch (IOException e) {
            userLogger.error(e.toString());
            return null;
        }
    }

    public static WordList getWordlist(String meaning) {
        return getWordlist(
                meaning,
                InputConfig.INPUT_DIRECTORY + InputConfig.FILENAME
        );
    }

    public static WordList getWordlist(String meaning, String filePath) {
        meaning = meaning.toLowerCase();
        List<WordList> allWordlists = getAllWordLists(filePath);
        for (WordList wl : allWordlists) {
            if (wl.getMeaning().equals(meaning)) {
                return wl;
            }
        }
        return null;
    }

//    public void prepareLanguagesMap() {
//        try(FileInputStream inputStream = new FileInputStream(this.filePath)) {
//            userLogger.debug(INPUT_DIRECTORY + this.filePath);
//            Workbook wb = WorkbookFactory.create(inputStream);
//            Sheet sheet = wb.getSheetAt(0);
//            int rowNum = 1;
//
//            // read all languages names one by one
//            // and create their objects
//            while (sheet.getRow(rowNum) != null) {
//                Language l = new Language(sheet.getRow(rowNum).getCell(0).getStringCellValue());
//            }
//
//        } catch (IOException e) {
//            userLogger.error("file not found by path: " + this.filePath + ". " + e.toString());
//        }
//    }
}
