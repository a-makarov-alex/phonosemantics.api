package phonosemantics.square;

import org.junit.Assert;
import org.junit.Test;
import phonosemantics.output.report.PatternSquareFile;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.PlacePrecise;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Backness;

import java.util.ArrayList;
import java.util.List;

public class SquareTest {
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
}
