package phonosemantics.word.wordlist;

import org.junit.Assert;
import org.junit.Test;
import phonosemantics.input.test.TestData;

import java.util.List;

public class WordListServiceTest {

    @Test
    public void getAllWordlistsSimpleTest() {
        List<WordList2022> allWls = WordListService.getAllWordLists2022(TestData.TEST_FILE_PATH);
        Assert.assertNotNull(allWls);
        Assert.assertEquals(allWls.size(), TestData.NUM_OF_MEANINGS);
        // TODO: check if the num of WLs equals the num of meanings in the input file
    }

    @Test
    public void getWordlistSimpleTest() {
        String meaning = "stone";
        WordList2022 wl = WordListService.getWordlist2022(meaning, TestData.TEST_FILE_PATH);
        Assert.assertNotNull(wl);
        Assert.assertEquals(wl.getList().size(), TestData.NUM_OF_LANGUAGES);
    }

    @Test
    public void getWordlistWithEmptyRowTest() {
        String meaning = "leaf";
        WordList2022 wl = WordListService.getWordlist2022(meaning, TestData.TEST_FILE_PATH);
        Assert.assertNotNull(wl);
        Assert.assertEquals(wl.getList().size(), TestData.NUM_OF_LANGUAGES - 1);
    }
}