package phonosemantics.language;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import phonosemantics.input.test.TestData;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class LanguageTest {
    private static final Logger userLogger = Logger.getLogger(LanguageTest.class);

    @Test
    public void languageConstructor_1arg_phonologySimpleTest() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "language/language_1arg_phonologySimple.properties");
        Properties p = new Properties();
        p.load(reader);
        String langName = p.getProperty("language");

        Language lang = new Language(langName);
        Set<String> expectedSet = new HashSet<>(Arrays.asList(p.getProperty("expected.phonology").split(" ")));
        Set<String> actualSet = lang.getPhonology().stream()
                .map(PhonemeInTable::getValue)
                .collect(Collectors.toSet());

        Assert.assertTrue(actualSet.containsAll(expectedSet));
        Assert.assertEquals(p.getProperty("expected.number.phonemes"), String.valueOf(actualSet.size() - 1)); // дополнительный элемент - пустой символ ""
    }


    // TODO: либо вычищать вручную диакритику и сохранять исходник отдельно,
    //  либо выпиливать диакритику динамически и указывать в логах какие фонемы каким образом изменены (читай: считаны с корректировками под ближайшие похожие)
    @Test
    public void languageConstructor_1arg_phonologyRarePhonemesTest() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "language/language_1arg_phonologyRarePhonemes.properties");
        Properties p = new Properties();
        p.load(reader);
        String langName = p.getProperty("language");

        Language lang = new Language(langName);
        Set<String> expectedSet = new HashSet<>(Arrays.asList(p.getProperty("expected.phonology").split(" ")));
        Set<String> actualSet = lang.getPhonology().stream()
                .map(PhonemeInTable::getValue)
                .collect(Collectors.toSet());
        userLogger.info(lang.getTitle());
        userLogger.info(actualSet);
        userLogger.info(expectedSet);

        Assert.assertTrue(actualSet.containsAll(expectedSet));
        Assert.assertEquals(p.getProperty("expected.number.phonemes"), String.valueOf(actualSet.size() - 1)); // дополнительный элемент - пустой символ ""
    }


    // TODO в конструкторе Language(1 arg) закомментированы строки с coverage, надо придумать откуда их запускать
}
