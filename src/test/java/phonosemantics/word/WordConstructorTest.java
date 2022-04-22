package phonosemantics.word;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import phonosemantics.input.test.TestData;

import java.io.FileReader;
import java.util.Properties;

public class WordConstructorTest {
    private static final Logger userLogger = Logger.getLogger(WordConstructorTest.class);

    @Test
    public void createWordWith1ArgsConstructor_1_SymbolGraphemes() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word/word_1_args_1_graph_frequent.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));
    }


    @Test
    public void createWordWith1ArgsConstructor_1_SymbolRareGraphemes_FrontVow_StopCons() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word/word_1_args_1_graph_front_vowels_stop_consonants.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));
    }


    @Test
    public void createWordWith1ArgsConstructor_1_SymbolRareGraphemes_CentralVow_SibilantCons() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word/word_1_args_1_graph_central_vowels_sibilant_consonants.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));
    }


    @Test
    public void createWordWith1ArgsConstructor_1_SymbolRareGraphemes_BackVow_FricativeCons() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word/word_1_args_1_graph_back_vowels_fricative_consonants.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));
    }


    @Test
    public void createWordWith1ArgsConstructor_2_SymbolGraphemes() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word/word_1_args_2_graphs.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));

    }


    @Test
    public void createWordWith1ArgsConstructor_2_SymbolGraphemes_NasalVow_SonorantCons() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word/word_1_args_2_graph_nazal_vowels_sonorant_consonants.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        userLogger.info(word.getPhonemesPool().getTranscription().toString());
        userLogger.info(p.getProperty("expected.transcription"));
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));
    }

    // до сюда проверяется в тестах 85 разных фонем
}
