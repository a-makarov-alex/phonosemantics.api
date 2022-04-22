package phonosemantics.word;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import phonosemantics.input.test.TestData;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.FeaturesPool;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

public class FeaturesPoolTest {
    private static final Logger userLogger = Logger.getLogger(FeaturesPoolTest.class);

    // Проверяется, что задаваемые из справочника данные по фонеме i в полном объеме
    // подтягиваются в FeaturesPool.initialVowel при создании Word2022 через конструктор с 1 параметром
    @Test
    public void createFeaturesPool() throws Exception {
        FileReader reader = new FileReader(TestData.INPUT_TEST_DIRECTORY_2022 + "features_test.properties");
        Properties p = new Properties();
        p.load(reader);
        String form = p.getProperty("graphic.form");

        Word2022 word = new Word2022(form);
        FeaturesPool fpActual = word.getFeaturesPool();

        DistinctiveFeatures df = null;

        try {
            Gson gson = new Gson();
            // Получаем параметры фонем из json файлов
            String initVow = p.getProperty("initial.vowel");
            String filePath = TestData.JSON_DIRECTORY + initVow + ".json";

            PhonemeInTable ph = gson.fromJson(new FileReader(filePath), PhonemeInTable.class);
            df = ph.getDistinctiveFeatures();

        } catch (FileNotFoundException e) {
            userLogger.error("FILE NOT FOUND: " + e.toString());
        }

        //userLogger.info(df.toString() + "\n");
        //userLogger.info(fpActual.getInitialVowelFeatures().toString());
        Assert.assertEquals(df, fpActual.getInitialVowelFeatures());
    }
}
