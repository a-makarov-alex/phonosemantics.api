package phonosemantics.input.test;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static final String TEST_FILENAME = "InputTest.xlsx";
    public static final String INPUT_TEST_DIRECTORY = "./src/main/java/phonosemantics/input/test/";
    public static final String TEST_FILE_PATH = INPUT_TEST_DIRECTORY + TEST_FILENAME;

    public static final int NUM_OF_LANGUAGES = 10;
    public static final int NUM_OF_MEANINGS = 2;
    private static List<String> testMeanings;

    static {
        testMeanings = new ArrayList<>();
        testMeanings.add("stone");
        testMeanings.add("leaf");
    }

    public static List<String> getTestMeanings() {
        return testMeanings;
    }
}
