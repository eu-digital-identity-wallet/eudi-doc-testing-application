package eu.europa.eudi.api;

import eu.europa.eudi.stepdefs.AutomatedStepDefs;
import eu.europa.eudi.utils.TestSetup;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class EventsApiVerifier {
    private final TestSetup test = AutomatedStepDefs.getTest();

    public void getPresentationEvents(String transactionId) {
        // Set base URI (adjust as needed)
        RestAssured.baseURI = "https://verifier-backend.eudiw.dev"; // replace with actual host

        // Make the GET request with the transactionId in the path
        Response response = RestAssured
                .given()
                .when()
                .get("/ui/presentations/" + transactionId + "/events")
                .then().statusCode(200).log().all().extract().response();
        String fullPath = test.getScenario().getUri().getPath();

        // Extract the directory of the feature file
        String featureDirPath = fullPath.substring(0, fullPath.lastIndexOf('/'));
        File featureDir = new File( featureDirPath + "/logs/api");

        // Extract the feature name from the path
        String featureName = fullPath.substring(fullPath.lastIndexOf('/') + 1).replace(".feature", "");
        File newFile = new File(featureDir, featureName + ".txt");

        try (FileWriter File = new FileWriter(newFile)) {
            File.write(response.getBody().prettyPrint());
            System.out.println("JSON api response written to api logs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
