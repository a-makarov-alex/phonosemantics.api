package phonosemantics.language;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpRequest.request;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.RequestDefinition;
import org.mockserver.verify.VerificationTimes;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public class LanguageTest {
    private static final Logger userLogger = Logger.getLogger(LanguageTest.class);

    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("mockserver/mockserver");

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Rule
    public MockServerContainer mockServer = new MockServerContainer(DEFAULT_IMAGE_NAME);

    @Test
    public void calculatePhTypeCoverage() {
        String testLangName = "Ket";
        Language language = new Language(testLangName);
        Map<String, Map<String, Integer>> phTypeCov = language.calculatePhTypeCoverage();
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
        //userLogger.info("RESPONSE CODE " + response.statusCode());
        //userLogger.info("RESPONSE " + response.getBody().asString());
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void testMockserverResponse() throws Exception {
        MockServerClient mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort());
        String path = "/person";
        mockServerClient.when(request()
                            .withMethod("GET")
                            .withPath(path)
                            .withQueryStringParameter("name", "peter"))
                        .respond(response()
                            .withBody("Peter the person!"));

        /*.withStatusCode(302)
                .withCookie("sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW")
                .withHeader("Location", "https://www.mock-server.com") */
        // Ещё примеры
        //https://www.mock-server.com/mock_server/creating_expectations.html

        Response response = RestAssured.given()
                .when()
                .get(mockServer.getEndpoint() + "/person?name=peter");
        //userLogger.info("RESPONSE CODE MOCKSERVER: " + response.statusCode());
        //userLogger.info("RESPONSE BODY MOCKSERVER: " + response.getBody().asString());
        Assert.assertEquals(200, response.statusCode());

        mockServerClient.verify(
                HttpRequest.request().withPath(path),
                VerificationTimes.once());

        //HttpRequest[] requests = mockServerClient.retrieveRecordedRequests(HttpRequest.request().withPath(path));
        RequestDefinition[] requests = mockServerClient.retrieveRecordedRequests(HttpRequest.request().withPath(path));
        userLogger.info("requests num: " + requests.length);
        userLogger.info("requests: " + requests[0]);
    }
}