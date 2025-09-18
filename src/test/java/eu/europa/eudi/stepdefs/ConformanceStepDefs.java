package eu.europa.eudi.stepdefs;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.GoogleAuthHelper;
import eu.europa.eudi.utils.TestSetup;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

public class ConformanceStepDefs {
    TestSetup test;

    @Before
    public void setup(Scenario scenario) {
        boolean web = scenario.getSourceTagNames().contains("@WEB");
        if (web) {
            test = new TestSetup(false, Literals.General.WEB.label, scenario);
            test.startWebDriverSession();
            test.setScenario(scenario);
            test.startLogging();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        boolean web = scenario.getSourceTagNames().contains("@WEB");
        if (web) {
            test.stopWebDriverSession();
        }
        if (test != null) {
            test.stopLogging();
        }
    }

    @Given("the user opens the conformance suite")
    public void theUserOpensTheConformanceSuite() {
        test.web().conformance().openConformanceSuite();
    }

    @When("the page loads")
    public void thePageLoads() {
        test.web().conformance().waitForPageToLoad();
    }

    @Then("the login page should be displayed")
    public void theLoginPageShouldBeDisplayed() {
        test.web().conformance().verifyLoginPageDisplayed();
    }

    @Given("the conformance suite login page is displayed")
    public void theConformanceSuiteLoginPageIsDisplayed() throws Exception {
        theUserOpensTheConformanceSuite();
        thePageLoads();
        theLoginPageShouldBeDisplayed();


    }

    @When("the user proceeds with Google")
    public void theUserProceedsWithGoogle() throws IOException, ClassNotFoundException {
        test.web().conformance().clickProceedWithGoogle();
    }

    @When("the user enters email")
    public void theUserEntersEmail() throws Exception {

        NetHttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        Credential credential = GoogleAuthHelper.getCredentials(HTTP_TRANSPORT);

        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
        GenericUrl url = new GenericUrl("https://www.googleapis.com/oauth2/v2/userinfo");

        HttpRequest request = requestFactory.buildGetRequest(url);
        HttpResponse response = request.execute();

        String userInfoJson = response.parseAsString();
        System.out.println("User info: " + userInfoJson);

        test.web().conformance().enterGoogleEmail();
        test.web().conformance().clickGoogleNext();
    }

    @When("the user enters password")
    public void theUserEntersPassword() {
        test.web().conformance().enterGooglePassword();
        test.web().conformance().clickGooglePasswordNext();
        test.web().conformance().waitForPageToLoad();
    }

    @Then("the user should be logged in successfully")
    public void theUserShouldBeLoggedInSuccessfully() {
        test.web().conformance().verifyTheUserIsLoggedIn();
    }

    @Given("the user logged in successfully")
    public void theUserLoggedInSuccessfully() throws Exception {
        theConformanceSuiteLoginPageIsDisplayed();
        theUserProceedsWithGoogle();
        theUserEntersEmail();
        theUserEntersPassword();
        theUserShouldBeLoggedInSuccessfully();
    }

    @When("the user checks the show early version tests box")
    public void theUserChecksTheShowEarlyVersionTestsBox() {
        test.web().conformance().waitForPageToLoad();
        test.web().conformance().checkEarlyVersionTestsCheckbox();
    }

    @Then("the user selects a test plan")
    public void theUserSelectsATestPlan() {
        test.web().conformance().verifyTestPlanPageDisplayed();
        test.web().conformance().selectTestPlan();
    }

    @Given("the user has selected a test plan")
    public void theUserHasSelectedATestPlan() throws Exception {
        theUserLoggedInSuccessfully();
        theUserChecksTheShowEarlyVersionTestsBox();
        theUserSelectsATestPlan();
    }

    @When("the correct fields are displayed")
    public void theCorrectFieldsAreDisplayed() {
        test.web().conformance().verifySenderConstrainingFieldDisplayed();
        test.web().conformance().verifyAuthRequestTypeDisplayed();
        test.web().conformance().verifyGrantTypeDisplayed();
    }

    @Then("the user fills in the required fields")
    public void theUserFillsInTheRequiredFields() {
        test.web().conformance().selectSenderConstraining();
        test.web().conformance().selectClientAuthType();
        test.web().conformance().selectAuthCodeFlow();
        test.web().conformance().selectAuthRequestType();
        test.web().conformance().selectRequestMethod();
        test.web().conformance().selectGrantType();
//        test.web().conformance().selectCredentialOfferParameter();
        test.web().conformance().selectMetadataDiscovery();
        test.web().conformance().selectFAPIProfile();
        test.web().conformance().selectFAPIResponseMode();
    }

    @Then("configure test section appears")
    public void configureTestSectionAppears() {
        test.web().conformance().verifyConfigureTestDisplayed();
    }

    @Given("configure test form is displayed")
    public void configureTestFormIsDisplayed() throws Exception {
        theUserHasSelectedATestPlan();
        theCorrectFieldsAreDisplayed();
        theUserFillsInTheRequiredFields();
        configureTestSectionAppears();
    }

    @When("the user fills the configure test form")
    public void theUserFillsTheConfigureTestForm() {
        test.web().conformance().enterAlias();
        test.web().conformance().selectPublish();
        test.web().conformance().enterCredentialIssuerURL();
        test.web().conformance().enterCredentialConfID();
        test.web().conformance().enterCredentialIssuerMetadataURL();
        test.web().conformance().enterAuthServerURLOverride();
        test.web().conformance().enterClientID();
        test.web().conformance().enterJwks();
        test.web().conformance().selectDPoPSigningAlg();
    }

    @And("the user scrolls until the create test plan button")
    public void theUserScrollsUntilTheCreateTestPlanButton() {
        test.web().conformance().scrollUntilEndOfPage();
    }

    @And("the user executes the test plan")
    public void theUserExecutesTheTestPlan() {
        test.web().conformance().executeTestPlan();
    }

    @Given("the user executed the test plan")
    public void theUserExecutedTheTestPlan() throws Exception {
        configureTestFormIsDisplayed();
        theUserFillsTheConfigureTestForm();
        theUserScrollsUntilTheCreateTestPlanButton();
        theUserExecutesTheTestPlan();
    }

    @When("the test plan results page is displayed")
    public void theTestPlanResultsPageIsDisplayed() {
        test.web().conformance().waitForPageToLoad();
        test.web().conformance().verifyTestPlanResultsPageDisplayed();
    }

    @And("the user runs the first test")
    public void theUserRunsTheFirstTest() {
        test.web().conformance().theUserClicksRunTest();
    }

    @Then("the user verifies test's success")
    public void theUserVerifiesTestSSuccess() {
        test.web().conformance().waitForPageToLoad();
        test.web().conformance().verifyTestIsPassed();
    }
}