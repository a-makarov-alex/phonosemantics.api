package phonosemantics.word.word;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import phonosemantics.input.test.TestData;
import phonosemantics.word.Word2022;

import java.io.FileReader;
import java.util.Properties;

public class WordConstructorTest {
    private static final Logger userLogger = Logger.getLogger(WordConstructorTest.class);

    @Test
    public void createWordWith1ArgsConstructor_1_SymbolGraphemes() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word_1_args_1_graph.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));

    }


    @Test
    public void createWordWith1ArgsConstructor_1_SymbolRareGraphemes() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word_1_args_1_graph_rare.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        userLogger.info(word.getPhonemesPool().getTranscription().toString());
        userLogger.info(p.getProperty("expected.transcription"));
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));

    }


    @Test
    public void createWordWith1ArgsConstructor_2_SymbolGraphemes() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "word_1_args_2_graphs.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        Assert.assertEquals(p.getProperty("expected.transcription"), word.getPhonemesPool().getTranscription().toString());
        Assert.assertEquals(p.getProperty("expected.length"), String.valueOf(word.getLength()));

    }
}
