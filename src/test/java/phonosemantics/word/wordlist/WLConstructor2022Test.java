package phonosemantics.word.wordlist;

import org.apache.log4j.Logger;
import org.junit.Test;
import phonosemantics.input.test.TestData;

public class WLConstructor2022Test {
    private static final Logger userLogger = Logger.getLogger(WLConstructor2022Test.class);

    @Test
    public void createWL_basicConstructorTest() {
        WordListService.getAllWordLists2022(TestData.INPUT_WL_CONSTRUCTOR_PATH);
        WordList2022 wl = WordListService.getWordlist2022("leaf");

        userLogger.info(" getNumOfPhonemes: \n" + wl.getStats().getNumOfPhonemes());
        userLogger.info("\n getNumOfWords: \n" + wl.getStats().getNumOfWords());
        userLogger.info("\n getNumOfWordsWithFeatures: \n" + wl.getStats().getNumOfWordsWithFeatures());
        userLogger.info("\n getPhonemeStatsMap: \n" + wl.getStats().getPhonemeStatsMap());
        userLogger.info("\n getDistFeatureStats: \n" + wl.getStats().getDistFeatureStats());
    }
}
