package phonosemantics.word.wordlist;

import org.junit.Test;
import phonosemantics.data.InputConfig;
import phonosemantics.input.test.TestData;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WordListServiceTest {

    @Test
    public void getAllWordlistsSimpleTest() {
        ArrayList<WordList> allWls = WordListService.getAllWordLists(TestData.TEST_FILE_PATH);
        assert allWls != null;
        assert allWls.size() == TestData.NUM_OF_MEANINGS;
        // TODO: check if the num of WLs equals the num of meanings in the input file
    }

    @Test
    public void getWordlistSimpleTest() {
        String meaning = "stone";
        WordList wl = WordListService.getWordlist(meaning, TestData.TEST_FILE_PATH);
        assert wl != null;
        assert wl.getList().size() == TestData.NUM_OF_LANGUAGES;
    }

    @Test
    public void getWordlistWithEmptyRowTest() {
        String meaning = "leaf";
        WordList wl = WordListService.getWordlist(meaning, TestData.TEST_FILE_PATH);
        assert wl != null;
        assert wl.getList().size() == TestData.NUM_OF_LANGUAGES - 1;
    }


}