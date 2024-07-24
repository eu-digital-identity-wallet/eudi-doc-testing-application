package eu.europa.eudi.stepdefs;

import eu.europa.eudi.utils.TestSetup;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ManuallyStepDefs {
    TestSetup test;
    @Given("the user is on the Login screen")
    public void theUserIsOnTheLoginScreen() {
        test.mobile().wallet().startAndStopDriver();
        test.mobile().wallet().loginPageIsDisplayed();
    }

    @Given("the user is on Login screen")
    public void theUserIsOnLoginScreen() {
        //manual
    }

    @When("the user enters PIN")
    public void theUserEntersPIN() {
        //manual
    }

    @Then("the user see the dashboard screen")
    public void theUserSeeTheDashboardScreen() {
        //manual
    }

    @Given("the user is on dashboard screen")
    public void theUserIsOnDashboardScreen() {
        //manual
    }

    @Given("the user has successfully entered their PIN")
    public void theUserHasSuccessfullyEnteredTheirPIN() {
        //manual
    }

    @Then("the user see the document contents")
    public void theUserSeeTheDocumentContents() {
        //manual
    }

    @Then("their document should be deleted")
    public void theirDocumentShouldBeDeleted() {
        //manual
    }

    @Given("the dashboard page is displayed on screen")
    public void theDashboardPageIsDisplayedOnScreen() {
        //manual
    }

    @When("the user clicks add doc button")
    public void theUserClicksAddDocButton() {
        //manual
    }

    @Then("on screen is displayed the authentication method selection")
    public void onScreenIsDisplayedTheAuthenticationMethodSelection() {
        //manual
    }

    @When("the user clicks on the X button")
    public void theUserClicksOnTheXButton() {
        //manual
    }

    @When("the user presses on the ISSUE button")
    public void theUserPressesOnTheISSUEButton() {
        //manual
    }

    @Given("the user sees the success message in the EUDI Wallet app")
    public void theUserSeesTheSuccessMessageInTheEUDIWalletApp() {
        //manual
    }

    @When("the user presses on the CONTINUE button")
    public void theUserPressesOnTheCONTINUEButton() {
        //manual
    }

    @When("the user authenticates and consents the issuance")
    public void theUserAuthenticatesAndConsentsTheIssuance() {
        //manual
    }

    @When("the user presses on the Issue button")
    public void theUserPressesOnTheIssueButton() {
        //manual
    }

    @Given("the user is on issuer service")
    public void theUserIsOnIssuerService() {
        //manual
    }

    @Then("the user registers their personal data")
    public void theUserRegistersTheirPersonalData() {
        //manual
    }
}
