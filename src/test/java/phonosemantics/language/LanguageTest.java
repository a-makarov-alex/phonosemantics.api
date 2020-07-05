package phonosemantics.language;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class LanguageTest {
    private static final String testLangName = "Ket";

    @Test
    public void calculatePhTypeCoverage() {
        Language language = new Language(testLangName);
        Map<String, Map<Object, Integer>> phTypeCov = language.calculatePhTypeCoverage();
        Assert.assertNotNull(phTypeCov);
        Assert.assertTrue(phTypeCov.get("vocoid").get("true") > 0);
    }

}