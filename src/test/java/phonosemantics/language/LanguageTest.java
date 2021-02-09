package phonosemantics.language;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import phonosemantics.output.report.OutputFile;

import java.util.Map;

public class LanguageTest {
    private static final Logger userLogger = Logger.getLogger(LanguageTest.class);

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void calculatePhTypeCoverage() {
        String testLangName = "Ket";
        Language language = new Language(testLangName);
        Map<String, Map<Object, Integer>> phTypeCov = language.calculatePhTypeCoverage();
        Assert.assertNotNull(phTypeCov);
        Assert.assertTrue(phTypeCov.get("vocoid").get("true") > 0);
    }

    @Test
    public void wireMockTest() {
        String testUrl = "/getwiremock";
        String fullUrl = "http://localhost:8089/getwiremock";
        stubFor(get(testUrl).willReturn(okJson("success")));
        Response response = RestAssured.given()
                .when()
                .get(fullUrl);
        userLogger.info("RESPONSE CODE " + response.statusCode());
        userLogger.info("RESPONSE " + response.getBody().asString());
        Assert.assertTrue(response.statusCode() == 200);
    }


}