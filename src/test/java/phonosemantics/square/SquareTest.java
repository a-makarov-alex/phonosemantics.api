package phonosemantics.square;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import phonosemantics.output.report.PatternSquareFile;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.PlacePrecise;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Backness;
import phonosemantics.word.Word;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

public class SquareTest {
    private static final Logger userLogger = Logger.getLogger(SquareTest.class);

    @Test
    public void analyzePlosive() {
        String testPair = "up";
        List<String> testData = new ArrayList<String>(){{
            add(testPair);
        }};
        PatternSquareFile patternSquareFile = new PatternSquareFile(testData);
        boolean result = patternSquareFile.checkPatternFoundInFile(
                Backness.BACK,
                PlacePrecise.BILABIAL
        );
        Assert.assertTrue(result);
    }

    @Test
    public void analyzeWithJson() {
        String testPair = "ip";

        PatternSquareFile patternSquareFile = new PatternSquareFile(testPair);
        Assert.assertTrue(patternSquareFile.assertValidResult(testPair));
    }
}
