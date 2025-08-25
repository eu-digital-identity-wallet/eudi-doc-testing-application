package eu.europa.eudi.stepdefs;

import eu.europa.eudi.api.EventsApiVerifier;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class GeneralStepDefs{

    static TestSetup test;
    @Before
    public void setup(Scenario scenario) throws InterruptedException {
      
        boolean noReset = scenario.getSourceTagNames().contains("@noreset");
        boolean data = scenario.getSourceTagNames().contains("@before_01");
        boolean two_pid_data = scenario.getSourceTagNames().contains("@before_02");
        boolean pid_and_mdl_data = scenario.getSourceTagNames().contains("@before_03");
        boolean ignored = scenario.getSourceTagNames().contains("@Ignored");
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        if (android) {
            test = new TestSetup(noReset, Literals.General.ANDROID.label, scenario);
            test.startAndroidDriverSession();
            test.setScenario(scenario);
            test.startLogging();
        }
        if (ios) {
            test = new TestSetup(noReset, Literals.General.IOS.label, scenario);
            test.startIosDriverSession();
            test.setScenario(scenario);
            test.startLogging();
        }
        if (data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().addPIDPageIsDisplayed();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().issuer().sleepMethod();
            test.mobile().issuer().successfullySharedMessage();
            test.mobile().wallet().clickDone();
        }

        if (two_pid_data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().scrollUntilPID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickDone();
        }

        if (pid_and_mdl_data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().scrollUntilmDL();
            test.mobile().wallet().clickMdl();
            test.mobile().issuer().clickFormEu();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().enterFamilyName();
            test.mobile().issuer().enterGivenName();
            test.mobile().issuer().chooseBirthDate();
            test.mobile().issuer().enterDocumentNumber();
            test.mobile().issuer().scrollUntilFindDate();
            test.mobile().issuer().clickScreen();
            test.mobile().issuer().chooseIssueDate();
            test.mobile().issuer().chooseExpiryDate();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().authorizeIsDisplayed();
            test.mobile().issuer().scrollUntilAuthorize();
            test.mobile().issuer().clickAuthorize();
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickHome();
        }
        if (ignored) {
            // Logic to skip the test
            test.mobile().wallet().skippedTest();
            throw new AssumptionViolatedException("Test is ignored due to @manual:Ignored tag");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        boolean web = scenario.getSourceTagNames().contains("@WEB");
        
        if (test != null) {
            if (android){
                test.stopAndroidDriverSession();
            }
            if (ios) { 
                test.stopIosDriverSession();
            }
            test.stopLogging();
        }
    }


    public static TestSetup getTest() {
        return test;
    }


    @Given("user sets up wallet")
    public void userSetsUpWallet() {
        test.mobile().wallet().checkIfPageIsTrue();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
        test.mobile().wallet().loadSampleDocuments();
        test.mobile().wallet().userProfilIsDisplayed();
    }


    @Given("user opens Verifier App")
    public void userOpensVerifierApp() throws MalformedURLException {
        test.mobile().wallet().userOpensVerifier();
        test.mobile().verifier().appOpensSuccessfully();
    }

    @When("user selects specific data to share")
    public void userSelectSpecificDataToShare() {
        test.mobile().verifier().launchSafari();
        test.mobile().verifier().appOpensSuccessfully();
        test.mobile().verifier().selectAllAttributes();
        test.mobile().verifier().scrollUntilNext();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().assertAndClickNext();
    }

    @And("user selects to be identified using EUDI Wallet")
    public void userSelectsToBeIdentifiedUsingEUDIWallet() {
        test.mobile().verifier().chooseWallet();
    }

    @And("user views the data and can unselect any of them")
    public void userViewsTheDataAndCanUnselectAnyOfThem() {
        test.mobile().verifier().viewDataPage();
        test.mobile().wallet().clickExpandVerification();
        test.mobile().wallet().unselectData();
    }

    @And("user presses the share button")
    public void userPressesTheShareButton() throws MalformedURLException {
        userSelectsToBeIdentifiedUsingTheEUDIWallet();
        userViewsTheDataAndCanUnselectAnyOfThem();
        userPressesTheShareButtonOnWallet();
    }

    @And("user authorizes the discolsure of the data")
    public void userAuthorizesTheDiscolsureOfTheData() {
        test.mobile().verifier().authorizeData();
    }

    @And("user is authenticated successfully")
    public void userIsAuthenticatedSuccessfully() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @Given("welcome to EUDI page is displayed")
    public void welcomeToEUDIPageIsDisplayed() {
        test.mobile().wallet().welcomePage();
    }

    @And("user sets up a quick pin")
    public void userSetsUpAQuickPin() {
        test.mobile().wallet().createAPin();
    }

    @And("user clicks next button")
    public void userClicksNextButton() {
        test.mobile().wallet().clickNextButton();
    }

    @And("user re-enters pin")
    public void userReEntersPin() {
        test.mobile().wallet().renterThePin();
    }

    @And("user clicks confirm button")
    public void userClicksConfirmButton() {
        test.mobile().wallet().clickConfirm();
    }

    @Then("a successfull message is appeared")
    public void aSuccsfullMessageIsAppeared() {
        test.mobile().wallet().successMessageOfSetUpPin();
    }

    @And("user clicks national id")
    public void userClicksNationalId() {
        test.mobile().wallet().clickNationalId();
    }

    @Then("national id is displayed")
    public void nationalIdIsDisplayed() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @And("user clicks mdl")
    public void userClicksMdl() {
        test.mobile().wallet().clickMdl();
    }

    @Then("mdl is displayed")
    public void mdlIsDisplayed() {
        test.mobile().wallet().mdlIsDisplayed();
    }

    @And("user clicks delete button")
    public void userClicksDeleteButton() {
        test.mobile().wallet().clickDeleteButton();
    }

    @And("user confirms deletion")
    public void userConfirmsDeletion() {
        test.mobile().wallet().confirmsDeletion();
    }

    @Then("the login page is displayed")
    public void theLoginPageIsDisplayed() {
        test.mobile().wallet().loginPageIsDisplayed();
    }

    @Then("the dashboard page is displayed")
    public void theDashboardPageIsDisplayed() {
        test.mobile().wallet().checkIfPageIsTrue();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
        test.mobile().wallet().loadSampleDocuments();
        test.mobile().wallet().dashboardPageIsDisplayed();
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.terminateApp("eu.europa.ec.euidi.dev");
// Re-launches the app from scratch
        driver.activateApp("eu.europa.ec.euidi.dev");
//        test.mobile().wallet().startAndStopDriver();
        test.mobile().wallet().loginPageIsDisplayed();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @And("user clicks load sample data")
    public void userClicksLoadSampleData() {
        theUserEntersTheirPIN();
        theUserShouldSeeTheAddDocumentPage();
        test.mobile().wallet().loadSampleDocuments();
    }

    @And("user clicks continue button")
    public void userClicksContinueButton() {
        test.mobile().wallet().clickContinue();
    }

    @And("user views the data and unselect any of them")
    public void userViewsTheDataAndUnselectAnyOfThem() {
        test.mobile().wallet().unselectData();
    }

    @And("a corresponding message is displayed")
    public void aCorrespondingMessageIsDisplayed() {
        test.mobile().wallet().correspondingMessageIsDisplayed();
    }

    @And("user clicks again the data")
    public void userClicksAgainTheData() {
        test.mobile().wallet().clickAgainData();
    }

    @And("user clicks add doc button")
    public void userClicksAddDocButton() {
        test.mobile().wallet().addDocButton();
    }

    @And("add document page is displayed")
    public void addDocumentPageIsDisplayed() {
        test.mobile().wallet().addDocumentPageIsDisplayed();
    }

    @And("user clicks national id button")
    public void userClicksNationalIdButton() {
        test.mobile().wallet().clickNationalIdButton();
    }

    @And("authentication method selection is displayed")
    public void authenticationMethodSelectionIsDisplayed() {
        test.mobile().issuer().authenticationMethodSelection();
    }

    @And("user clicks country selection")
    public void userClicksCountrySelection() {
        test.mobile().issuer().clickCountrySelection();
        test.mobile().issuer().clickSubmit();
    }

    @And("user clicks FormEU")
    public void userClicksFormEU() {
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
    }

    @And("data page is displayed")
    public void dataPageIsDisplayed() {
        test.mobile().issuer().dataPageIsDisplayed();
    }

    @And("user enters data")
    public void userEntersData() {
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().clickSubmit();
    }

    @And("a success message is displayed")
    public void aSuccessMessageIsDisplayed() {
        test.mobile().wallet().successMessageIsDisplayed();
        test.mobile().wallet().clickNextButton();
    }

    @And("user clicks driving licence button")
    public void userClicksDrivingLicenceButton() {
        test.mobile().wallet().clickDrivingLicenceButton();
    }

    @And("user enters data for drivring licence")
    public void userEntersDataForDrivringLicence() {
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterDocumentNumber();
        test.mobile().issuer().clickScreen();
        test.mobile().issuer().scrollUntilFindDate();
        test.mobile().issuer().chooseIssueDate();
        test.mobile().issuer().chooseExpiryDate();
        test.mobile().issuer().clickSubmit();
    }

    @Then("driving licence is displayed")
    public void drivingLicenceIsDisplayed() {
        test.mobile().wallet().drivingLicenceIsDisplayed();
        test.mobile().wallet().clickXButton();
    }

    @And("a success message for driving licence is displayed")
    public void aSuccessMessageForDrivingLicenceIsDisplayed() {
        test.mobile().wallet().successMessageForDrivingIsDisplayed();
        test.mobile().wallet().clickNextButton();
    }

    @Then("driving licence is displayed in the EUDI Wallet dashboard")
    public void drivingLicenceIsDisplayedInTheEUDIWalletDashboard() {
        test.mobile().wallet().drivingLicenceIsDisplayedInDashboard();
    }

    @And("user enters data for drivring licence for ios")
    public void userEntersDataForDrivringLicenceForIos() {
        test.mobile().issuer().chooseIssueDate();
        test.mobile().issuer().chooseExpiryDate();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterDocumentNumber();
        test.mobile().issuer().clickScreen();
        test.mobile().issuer().clickSubmit();
    }

    @Given("the user is on the issuer service")
    public void theUserIsOnTheIssuerService() {
        test.mobile().issuer().issuerService();
    }

    @When("the user selects to issue a credential")
    public void theUserSelectsToIssueACredential() throws InterruptedException {
        test.mobile().issuer().launchSafari();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickPersonalIdentificationData();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user is redirected to the EUDI Wallet")
    public void theUserIsRedirectedToTheEUDIWallet() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @And("the details of the credential to be issued are presented")
    public void theDetailsOfTheCredentialToBeIssuedArePresented() {
        test.mobile().verifier().insertPIN2();
        test.mobile().wallet().detailsArePresented();
    }

    @Given("the user is presented with the credential details on the EUDI Wallet")
    public void theUserIsPresentedWithTheCredentialDetailsOnTheEUDIWallet() throws InterruptedException {
        theUserIsOnTheIssuerService();
        theUserSelectsToIssueACredential();
        theUserIsRedirectedToTheEUDIWallet();
        theDetailsOfTheCredentialToBeIssuedArePresented();
    }

    @When("the user presses the Issue button")
    public void theUserPressesTheIssueButton() {
        test.mobile().wallet().clickIssue();
    }

    @Then("the user is redirected back to the issuer service")
    public void theUserIsRedirectedBackToTheIssuerService() {
     //auto accept pop up
    }

    @And("the user is prompted to authenticate and consent to the issuance")
    public void theUserIsPromptedToAuthenticateAndConsentToTheIssuance() {
        //auto accept pop up
    }

    @And("the user is asked to authenticate and consent on the issuer service")
    public void theUserIsAskedToAuthenticateAndConsentOnTheIssuerService() throws InterruptedException {
        theUserIsPresentedWithTheCredentialDetailsOnTheEUDIWallet();
        test.mobile().wallet().clickIssue();
    }

    @When("the user authenticates and consents to the issuance")
    public void theUserAuthenticatesAndConsentsToTheIssuance() {
        //auto accept pop up
    }

    @And("inserts the required credential details")
    public void insertsTheRequiredCredentialDetails() throws InterruptedException {
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().dataPageIsDisplayed();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterCountry();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("the user is redirected to the EUDI Wallet app")
    public void theUserIsRedirectedToTheEUDIWalletApp() {
        test.mobile().wallet().successMessageIsDisplayedForIssuer();
    }

    @And("a success message is displayed on the EUDI Wallet app")
    public void aSuccessMessageIsDisplayedOnTheEUDIWalletApp() {
        test.mobile().wallet().successMessageIsDisplayedForIssuer();
    }

    @When("the user presses the CONTINUE button")
    public void theUserPressesTheCONTINUEButton() {
        test.mobile().wallet().clickDone();
//        test.mobile().wallet().clickContinue();
    }

    @When("user presses the done button")
    public void theUserPressesTheDoneButton() {
        test.mobile().wallet().clickDone();
    }

    @Then("the new document is presented in the EUDI Wallet dashboard screen")
    public void theNewDocumentIsPresentedInTheEUDIWalletDashboardScreen() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().secondPIDIsDisplayed();
    }

    @Given("the user sees a success message in the EUDI Wallet app")
    public void theUserSeesASuccessMessageInTheEUDIWalletApp() throws InterruptedException {
        theUserIsAskedToAuthenticateAndConsentOnTheIssuerService();
//        theUserAuthenticatesAndConsentsToTheIssuance();
        insertsTheRequiredCredentialDetails();
        test.mobile().wallet().successMessageIsDisplayedForIssuer();
    }

    @Given("the user launches the EUDI Wallet for the first time")
    public void theUserLaunchesTheEUDIWalletForTheFirstTime() {
        test.mobile().wallet().welcomePage();
    }

    @When("the user sets up their PIN")
    public void theUserSetsUpTheirPIN() {
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
    }

    @Then("the 'Add document' screen is appeared")
    public void theAddDocumentScreenIsAppeared() {
        test.mobile().wallet().addDocumentPageIsDisplayed();
    }

    @When("the user has only the ‘National ID’ and ‘SCAN QR’ options available")
    public void theUserHasOnlyTheNationalIDAndSCANQROptionsAvailable() {
        test.mobile().wallet().nationalIdIsDisplayed();
        test.mobile().wallet().scanQrIsDisplayed();
    }

    @And("the user clicks on the 'National ID' option")
    public void theUserClicksOnTheNationalIDOption() {
        test.mobile().wallet().clickNationalId();
    }

    @Then("the user is redirected to the issuance service")
    public void theUserIsRedirectedToTheIssuanceService() {
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @When("the user follows the process to issue a new PID")
    public void theUserFollowsTheProcessToIssueANewPID() throws InterruptedException {
        theUserAuthenticatesAndConsentsToTheIssuance();
        insertsTheRequiredCredentialDetails();
        test.mobile().wallet().successMessageIsDisplayed();
        test.mobile().wallet().clickNextButton();
    }

    @Then("the user should be able to preview the PID")
    public void theUserShouldBeAbleToPreviewThePID() {
        test.mobile().wallet().previewPid();
    }

    @When("the user enters their PIN")
    public void theUserEntersTheirPIN() {
        test.mobile().wallet().createAPin();
    }

    @Then("the user should see the dashboard screen")
    public void theUserShouldSeeTheDashboardScreen() {
      test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Given("the user is on the dashboard screen")
    public void theUserIsOnTheDashboardScreen() {
        theUserShouldSeeTheDashboardScreen();
    }

    @When("the user clicks on the PID doc")
    public void theUserClicksOnThePIDDoc() {
        test.mobile().wallet().clickPID();
    }

    @Then("the PID should open")
    public void thePIDShouldOpen() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @And("the user should see the details of the PID")
    public void theUserShouldSeeTheDetailsOfThePID() {
        test.mobile().wallet().detailsOfPidIsDisplayed();
    }

    @Given("the PID is open")
    public void thePIDIsOpen() {
        theUserIsViewingTheDetailsOfAnAttestation();
        theUserSelectsEyeIcon();
    }

    @When("the user clicks the back button")
    public void theUserClicksTheBackButton() {
        test.mobile().wallet().clickBackButton();
    }

    @Then("the PID should close")
    public void thePIDShouldClose() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @And("the user should see the dashboard screen again")
    public void theUserShouldSeeTheDashboardScreenAgain() {
        test.mobile().wallet().clickHome();
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Then("the user should see the documents they have issued so far")
    public void theUserShouldSeeTheDocumentsTheyHaveIssuedSoFar() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @When("the user clicks on the mDL doc")
    public void theUserClicksOnTheMDLDoc() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickMdl();
    }

    @Then("the mDL should open")
    public void theMDLShouldOpen() {
        test.mobile().wallet().mdlIsDisplayed();
    }

    @And("the user should see the details of the mDL")
    public void theUserShouldSeeTheDetailsOfTheMDL() {
        test.mobile().wallet().mdlDetailsAreDisplayed();
    }

    @Given("the mDL is open")
    public void theMDLIsOpen() throws InterruptedException {
        theUserIsViewingTheDetailsOfTheMDL();
    }

    @Then("the mDL should close")
    public void theMDLShouldClose() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @When("the user clicks the add doc button")
    public void theUserClicksTheAddDocButton() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickToAddDocument();
    }

    @And("the add document page is displayed")
    public void theAddDocumentPageIsDisplayed() {
       test.mobile().wallet().addDocumentPageIsDisplayed();
       test.mobile().wallet().clickFromList();
    }

    @And("the user clicks the national id button")
    public void theUserClicksTheNationalIdButton() {
        test.mobile().wallet().clickNationalId();
    }

    @Then("the authentication method selection is displayed")
    public void theAuthenticationMethodSelectionIsDisplayed() {
        test.mobile().issuer().authenticationMethodSelection();
    }

    @When("the dashboard page is displayed on wallet")
    public void theDashboardPageIsDisplayedOnWallet() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Given("the user has successfully entered the PIN")
    public void theUserHasSuccessfullyEnteredThePIN() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
//        test.mobile().wallet().startAndStopDriver();
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.terminateApp(test.envDataConfig().getAppiumAndroidAppPackage());
// Re-launches the app from scratch
            driver.activateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            test.mobile().wallet().loginPageIsDisplayed();
            test.mobile().wallet().createAPin();
        }else{
            //        test.mobile().wallet().startAndStopDriver();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.terminateApp(test.envDataConfig().getAppiumIosBundleId());
// Re-launches the app from scratch
            driver.activateApp(test.envDataConfig().getAppiumIosBundleId());
            test.mobile().wallet().loginPageIsDisplayed();
            test.mobile().wallet().createAPin();
        }
    }

    @When("the user opens a mDL")
    public void theUserOpensAMDL() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickMdl();
    }

    @Then("the user should see the document contents")
    public void theUserShouldSeeTheDocumentContents() {
        test.mobile().wallet().mdlDetailsAreDisplayed();
    }

    @Given("the user has opened the selected mDL")
    public void theUserHasOpenedTheSelectedMDL() {
        theUserHasSuccessfullyEnteredThePIN();
        theUserOpensAMDL();
        theUserShouldSeeTheDocumentContents();
    }

    @When("the user presses the delete button")
    public void theUserPressesTheDeleteButton() {
        test.mobile().wallet().scrollUntilYouFindDelete();
        test.mobile().wallet().clickDeleteDocument();
        test.mobile().wallet().confirmsDeletion();
    }

    @When ("the user presses the delete button for mDL")
    public void theUserPressesTheDeleteButtonForMdl() {
        test.mobile().wallet().scrollUntilYouFindDelete();
        test.mobile().wallet().clickDeleteDocument();
        test.mobile().wallet().confirmsDeletion();
    }

    @Then("the document should be deleted")
    public void theDocumentShouldBeDeleted() {
        test.mobile().wallet().confirmsDeletion();
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Given("the document has been deleted")
    public void theDocumentHasBeenDeleted() {
        theUserHasSuccessfullyEnteredThePIN();
        theUserOpensAMDL();
        theUserShouldSeeTheDocumentContents();
        theUserPressesTheDeleteButton();
        theDocumentShouldBeDeleted();
    }

    @When("the user opens a PID \\(not the first one issued)")
    public void theUserOpensAPIDNotTheFirstOneIssued() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickSecondPID();
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @Then("the user should see the pid document contents")
    public void theUserShouldSeeThePidDocumentContents() {
        test.mobile().wallet().detailsOfDocumentIsDisplayed();
    }

    @Given("the user has opened the selected PID")
    public void theUserHasOpenedTheSelectedPID() {
        theUserHasSuccessfullyEnteredThePIN();
        theUserOpensAPIDNotTheFirstOneIssued();
        theUserShouldSeeThePidDocumentContents();
    }

    @Given("the user has opened the first PID that was issued")
    public void theUserHasOpenedTheFirstPIDThatWasIssued() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickPID();
        test.mobile().wallet().detailsOfDocumentIsDisplayed();
    }

    @And("the application should reboot")
    public void theApplicationShouldReboot() {
        test.mobile().wallet().loginPageIsDisplayed();
    }

    @Given("the application has rebooted")
    public void theApplicationHasRebooted() {
        theUserHasOpenedTheFirstPIDThatWasIssued();
        theUserPressesTheDeleteButton();
    }

    @When("the login screen appears")
    public void theLoginScreenAppears() {
        theApplicationShouldReboot();
    }

    @Then("the user should enter the PIN")
    public void theUserShouldEnterThePIN() {
        test.mobile().wallet().createAPin();
    }

    @Given("the user has successfully entered the PIN after reboot")
    public void theUserHasSuccessfullyEnteredThePINAfterReboot() {
        theApplicationHasRebooted();
        theLoginScreenAppears();
        theUserShouldEnterThePIN();
    }

    @When("the user is prompted to enter a PID")
    public void theUserIsPromptedToEnterAPID() {
        test.mobile().wallet().clickPID();
    }

    @Then("the user should be able to enter a PID again")
    public void theUserShouldBeAbleToEnterAPIDAgain() throws InterruptedException {
        test.mobile().issuer().issuePID();
        test.mobile().issuer().successfullySharedMessage();
        test.mobile().wallet().clickDone();
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @And("the user clicks the driving license button")
    public void theUserClicksTheDrivingLicenseButton() {
        test.mobile().wallet().scrollUntilmDL();
        test.mobile().wallet().clickDrivingLicenceButton();
    }

    @Then("the user is redirected to the issuer service to issue mDL")
    public void theUserIsRedirectedToTheIssuerServiceToIssueMDL() {
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
    }

    @When("the user clicks on country selection and submits")
    public void theUserClicksOnCountrySelectionAndSubmits() {
        test.mobile().issuer().clickCountrySelection();
        test.mobile().issuer().clickSubmit();
    }

    @And("the user clicks on Credential Provider FormEU and submits")
    public void theUserClicksOnCredentialProviderFormEUAndSubmits() {
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
    }

    @Then("the provider form is displayed for the user to register personal data")
    public void theProviderFormIsDisplayedForTheUserToRegisterPersonalData() {
        test.mobile().issuer().formIsDisplayed();
    }

    @Given("a provider form is displayed")
    public void aProviderFormIsDisplayed() throws InterruptedException {
    theCredentialsProviderIsDisplayedOnScreen();
    theUserClicksOnCredentialProviderFormEUAndSubmits();
    theProviderFormIsDisplayedForTheUserToRegisterPersonalData();
    }

    @When("the user registers personal data")
    public void theUserRegistersPersonalData() throws InterruptedException {
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterDocumentNumber();
        test.mobile().issuer().scrollUntilFindDate();
        test.mobile().issuer().clickScreen();
        test.mobile().issuer().chooseIssueDate();
        test.mobile().issuer().chooseExpiryDate();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("a success message for mdl is displayed")
    public void aSuccessMessageForMdlIsDisplayed() {
        test.mobile().wallet().successMessageForDrivingIsDisplayed();
        test.mobile().wallet().clickDone();
    }

    @And("the driving license is displayed in the wallet")
    public void theDrivingLicenseIsDisplayedInTheWallet() {
        test.mobile().wallet().mdlIsDisplayed();
    }

    @When("the user fills in the form")
    public void theUserFillsInTheForm() throws InterruptedException {
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterCountry();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
//        test.mobile().issuer().scrollUntilFindSubmit();
//        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("a success message for pid is displayed")
    public void aSuccessMessageForPidIsDisplayed() {
        test.mobile().wallet().successMessageForDrivingIsDisplayed();
        test.mobile().wallet().clickDone();
    }

    @And("the national id is displayed in the dashboard")
    public void theNationalIdIsDisplayedInTheDashboard() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().secondPIDIsDisplayed();
    }

    @Then("the user should see the add document page")
    public void theUserShouldSeeTheAddDocumentPage() {
        test.mobile().wallet().addDocumentPageIsDisplayed();
    }

    @Given("user opens Verifier Application")
    public void userOpensVerifierApplication() throws MalformedURLException {
        userOpensVerifierApp();
    }

    @Given("the user is in the verifier app")
    public void theUserIsInTheVerifierApp() throws MalformedURLException {
        userOpensVerifierApp();
    }

    @When("the verifier requests a doc from the wallet user")
    public void theVerifierRequestsADocFromTheWalletUser() {
        test.mobile().verifier().launchSafari();
        test.mobile().verifier().appOpensSuccessfully();
        test.mobile().verifier().selectAllAttributes();
        test.mobile().verifier().scrollUntilNext();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().assertAndClickNext();
    }

    @Then("the requestor of the data is displayed in the wallet")
    public void theRequestorOfTheDataIsDisplayedInTheWallet() {
        test.mobile().verifier().chooseWalletPageIsDisplayed();
        test.mobile().verifier().chooseWallet();
        test.mobile().verifier().insertPIN2();
    }

    @And("the document from which the data are requested is displayed")
    public void theDocumentFromWhichTheDataAreRequestedIsDisplayed() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @Given("the user is viewing the optional data")
    public void theUserIsViewingTheOptionalData() throws MalformedURLException {
        theUserIsInTheVerifierApp();
        theVerifierRequestsADocFromTheWalletUser();
        theRequestorOfTheDataIsDisplayedInTheWallet();
        theDocumentFromWhichTheDataAreRequestedIsDisplayed();
        test.mobile().wallet().optionalDataIsDisplayed();
    }

    @When("the user clicks the eye icon")
    public void theUserClicksTheEyeIcon() {
        test.mobile().wallet().clickEyeIcon();
    }

    @Then("the actual values of the data are displayed")
    public void theActualValuesOfTheDataAreDisplayed() {
        test.mobile().wallet().actualDataAreDisplayed();
    }

    @Given("the user views the document that is requested")
    public void theUserViewsTheDocumentThatIsRequested() throws MalformedURLException {
        theUserHasFinalizedDataSelection();
        theUserClicksTheSHAREButton();
        thePINFieldIsDisplayedToAuthorizeSharing();
        test.mobile().wallet().createAPin();
    }

    @Then("the user clicks to view the document's details")
    public void theUserClicksToViewTheDocumentsDetails() {
        test.mobile().wallet().createAPin();
        test.mobile().wallet().successMessageIsDisplayedForVerifier();
        test.mobile().wallet().clickToViewDetails();
    }

    @Then("the expanded verification details are displayed")
    public void theExpandedVerificationDetailsAreDisplayed() {
        test.mobile().wallet().verificationDetailsAreDisplayed();
    }

    @Given("the user has selected some data")
    public void theUserHasSelectedSomeData() throws MalformedURLException {
        theUserIsInTheVerifierApp();
        theVerifierRequestsADocFromTheWalletUser();
        theRequestorOfTheDataIsDisplayedInTheWallet();
        theDocumentFromWhichTheDataAreRequestedIsDisplayed();
    }

    @When("the user unselects some of this data")
    public void theUserUnselectsSomeOfThisData() {
        test.mobile().wallet().clickToViewDetails();
        test.mobile().wallet().detailsOfDocumentIsDisplayed();
        test.mobile().wallet().unselectData();
    }

    @Given("the user has finalized data selection")
    public void theUserHasFinalizedDataSelection() throws MalformedURLException {
        theUserHasSelectedSomeData();
        theUserUnselectsSomeOfThisData();
        aCorrespondingMessageIsDisplayed();
        test.mobile().wallet().closeCorrespondingMessage();
    }

    @When("the user clicks the share button")
    public void theUserClicksTheSHAREButton() {
        test.mobile().wallet().clickShareButton();
    }

    @Then("the PIN field is displayed to authorize sharing")
    public void thePINFieldIsDisplayedToAuthorizeSharing() {
        test.mobile().wallet().pinFieldIsDisplayed();
    }

    @Given("the user is prompted to enter a PIN for sharing")
    public void theUserIsPromptedToEnterAPINForSharing() throws MalformedURLException {
        theUserIsInTheVerifierApp();
        theVerifierRequestsADocFromTheWalletUser();
        theRequestorOfTheDataIsDisplayedInTheWallet();
        theDocumentFromWhichTheDataAreRequestedIsDisplayed();
        test.mobile().wallet().optionalDataIsDisplayed();
        test.mobile().wallet().clickShareButton();
        test.mobile().wallet().pinFieldIsDisplayed();
    }

    @When("the user enters the correct PIN")
    public void theUserEntersTheCorrectPIN() {
        test.mobile().wallet().createAPin();
    }

    @Then("a successful message is displayed indicating the data has been authorized for sharing")
    public void aSuccessfulMessageIsDisplayedIndicatingTheDataHasBeenAuthorizedForSharing() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @Given("user selects to be identified using the EUDI Wallet")
    public void userSelectsToBeIdentifiedUsingTheEUDIWallet() throws MalformedURLException {
        userOpensVerifierApplication();
        userSelectSpecificDataToShare();
        userSelectsToBeIdentifiedUsingEUDIWallet();
        test.mobile().verifier().insertPIN2();
    }

    @When("user authorizes the disclosure of the data")
    public void userAuthorizesTheDisclosureOfTheData() {
        test.mobile().wallet().pinFieldIsDisplayed();
        test.mobile().verifier().insertPIN();
    }

    @Then("user presses the share button on wallet")
    public void userPressesTheShareButtonOnWallet() {
        test.mobile().wallet().closeCorrespondingMessage();
        test.mobile().wallet().clickShareButton();
    }

    @Given("the authentication method selection is displayed on screen")
    public void theAuthenticationMethodSelectionIsDisplayedOnScreen() {
        theDashboardPageIsDisplayedOnWallet();
        theUserClicksOnDocuments();
        theDocumentsPageIsDisplayed();
        theUserClicksOnThePIDButton();
        theAuthenticationMethodSelectionIsDisplayed();
    }

    @Given("a provider form is displayed for mdl")
    public void aProviderFormIsDisplayedForMdl() throws InterruptedException {
        theIssuerServiceTestCredentialProviderScreenIsDisplayed();
//        theUserClicksOnCountrySelectionAndSubmits();
        theUserClicksOnCredentialProviderFormEUAndSubmits();
        theProviderFormIsDisplayedForTheUserToRegisterPersonalData();
    }

    @Given("the user is on the Login screen")
    public void theUserIsOnTheLoginScreen() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.terminateApp(test.envDataConfig().getAppiumAndroidAppPackage());
// Re-launches the app from scratch
            driver.activateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            test.mobile().wallet().loginPageIsDisplayed();
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.terminateApp(test.envDataConfig().getAppiumIosBundleId());
// Re-launches the app from scratch
            driver.activateApp(test.envDataConfig().getAppiumIosBundleId());
            test.mobile().wallet().loginPageIsDisplayed();
            }
    }

    @When("the user clicks on Documents")
    public void theUserClicksOnDocuments() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().documentsPageIsDisplayed();
    }

//    @Given("the user is on the Home screen of the EUDI Wallet")
//    public void theUserIsOnTheHomeScreenOfTheEUDIWallet() {
//        test.mobile().wallet().dashboardPageIsDisplayed();
//    }

//    @When("the user navigates to the Documents screen")
//    public void theUserNavigatesToTheDocumentsScreen() {
//        test.mobile().wallet().clickOnDocuments();
//    }

//    @Then("the Documents screen should be displayed showing a list of issued attestations")
//    public void theDocumentsScreenShouldBeDisplayedShowingAListOfIssuedAttestations() {
//        test.mobile().wallet().documentsPageIsDisplayed();
//    }

//    @Given("the user is on the Documents screen")
//    public void theUserIsOnTheDocumentsScreen() {
//        theUserShouldSeeTheDashboard();
//        theUserNavigatesToTheDocumentsScreen();
//        theDocumentsScreenShouldBeDisplayedShowingAListOfIssuedAttestations();
//    }

//    @When("the user selects an attestation from the list")
//    public void theUserSelectsAnAttestationFromTheList() {
//        test.mobile().wallet().clickPID();
//    }

//    @Then("the details of the selected attestation should be displayed")
//    public void theDetailsOfTheSelectedAttestationShouldBeDisplayed() {
//        test.mobile().wallet().documentsDetailsAreDisplayed();
//    }

//    @And("the details should be blurred by default")
//    public void theDetailsShouldBeBlurredByDefault() {
//        test.mobile().wallet().detailsAreDisplayedBlurred();
//    }

//    @And("the user should see the eye icon to view the details of the attestation")
//    public void theUserShouldSeeTheEyeIconToViewTheDetailsOfTheAttestation() {
//        test.mobile().wallet().eyeIconIsDisplayed();
//    }

//    @Given("the user is viewing the details of an attestation")
//    public void theUserIsViewingTheDetailsOfAnAttestation() {
//        theUserIsOnTheDashboardScreen();
//        theUserSelectsAnAttestationFromTheList();
//        theDetailsOfTheSelectedAttestationShouldBeDisplayed();
//        theUserShouldSeeTheEyeIconToViewTheDetailsOfTheAttestation();
//    }

//    @When("the user selects eye icon")
//    public void theUserSelectsEyeIcon() {
//        test.mobile().wallet().clickEyeIcon();
//    }

//    @Then("the attestation details should no longer be blurred")
//    public void theAttestationDetailsShouldNoLongerBeBlurred() {
//        //waiting for an attribute to check
//    }

//    @And("the user should be able to view the full details of the attestation")
//    public void theUserShouldBeAbleToViewTheFullDetailsOfTheAttestation() {
//        //waiting for an attribute to check
//    }

    @And("the Documents page is displayed")
    public void theDocumentsPageIsDisplayed() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @Given("the issuer service -test credential provider screen- is displayed")
    public void theIssuerServiceTestCredentialProviderScreenIsDisplayed() throws InterruptedException {
        theHomePageIsDisplayedOnWallet();
        theUserClicksTheAddDocButton();
        theAddDocumentPageIsDisplayed();
        theUserClicksTheDrivingLicenseButton();
        theUserIsRedirectedToTheIssuerServiceToIssueMDL();
    }

    @And("the user clicks on the PID button")
    public void theUserClicksOnThePIDButton() {
        test.mobile().wallet().clickToAddDocument();
        test.mobile().wallet().clickFromList();
        test.mobile().wallet().scrollUntilPID();
        test.mobile().wallet().clickPID();
    }

    @When("the user goes in Documents dashboard")
    public void theUserGoesInDocumentsDashboard() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @Then("Then the user should not see the second PID")
    public void thenTheUserShouldNotSeeTheSecondPID() {
        test.mobile().wallet().secondPIDIsNotDisplayed();
    }

    @Then("the user should see the Documents dashboard")
    public void theUserShouldSeeTheDocumentsDashboard() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @Given("the test is being ignored")
    public void theTestIsBeingIgnored() {
        test.mobile().wallet().skippedTest();
    }

    @Given("the user is on Home page")
    public void theUserIsOnHomePage(){
        test.mobile().wallet().homePageIsDisplayed();
    }

    @And ("the details should be blurred by default auto")
    public void theDetailsShouldBeBlurredByDefault(){
        test.mobile().wallet().detailsAreBlurred();
    }

    @And ("the user should see the eye icon to view the details of the attestation auto")
    public void theUserShouldSeeTheEyeIconToViewTheDetailsOfTheAttestation(){
        test.mobile().wallet().eyeIconIsDisplayed();
    }

    @Given ("the user is viewing the details of an attestation auto")
    public void theUserIsViewingTheDetailsOfAnAttestation(){
        theUserIsOnHomePage();
        theUserClicksOnDocuments();
        theUserClicksOnThePIDDoc();
        thePIDShouldOpen();
        theDetailsShouldBeBlurredByDefault();
    }

    @When ("the user selects eye icon auto")
    public void theUserSelectsEyeIcon(){
        test.mobile().wallet().clickEyeIcon();
    }

    @Then ("the attestation details should no longer be blurred auto")
    public void theAttestationDetailsShouldNoLongerBeBlurred(){
        test.mobile().wallet().detailsAreNotBlurred();
    }

    @Then ("the user should see the home screen")
    public void theUserShouldSeeTheHomeScreen(){
        test.mobile().wallet().homePageIsDisplayed();
    }

    @Given ("the user is on the home screen")
    public void theUserIsOnTheHomeScreen() throws InterruptedException {
        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheHomeScreen();
    }

    @Given ("the user is viewing the details of the mDL")
    public void theUserIsViewingTheDetailsOfTheMDL() throws InterruptedException {
        theUserIsOnTheHomeScreen();
        theUserClicksOnTheMDLDoc();
        theMDLShouldOpen();
        theDetailsShouldBeBlurredByDefault();
    }

    @Given ("the home page is displayed on wallet")
    public void theHomePageIsDisplayedOnWallet() throws InterruptedException {
        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheHomeScreen();
    }

    @And ("the user clicks the PID button")
    public void theUserClicksThePidButton(){
        test.mobile().wallet().scrollUntilPID();
        test.mobile().wallet().clickPID();
    }

    @Then ("the credentials provider is displayed")
    public void theCredentialsProviderIsDisplayed(){
        test.mobile().wallet().credentialsProviderIsDisplayed();
    }

    @Given ("the credentials provider is displayed on screen")
    public void theCredentialsProviderIsDisplayedOnScreen() throws InterruptedException {
        theHomePageIsDisplayedOnWallet();
        theUserClicksTheAddDocButton();
        theAddDocumentPageIsDisplayed();
        theUserClicksThePidButton();
        theCredentialsProviderIsDisplayed();
    }

    @Given ("the expanded verification details are seen")
    public void theExpandedVerificationDetailsAreSeen() throws MalformedURLException {
        theUserViewsTheDocumentThatIsRequested();
        theUserInsertsThePIN();
        theUserClicksToViewTheDocumentsDetails();
        theExpandedVerificationDetailsAreDisplayed();
    }

    @When ("the user clicks done")
    public void theUserClicksDone(){
        test.mobile().wallet().clickDone();
    }

    @Then ("the user gets redirected to verifier and views the respond")
    public void theUserGetsRedirectedToVerifierAndViewsTheRespond(){
        test.mobile().verifier().walletResponded();
        test.mobile().verifier().clickTransactionsLogs();
        test.mobile().verifier().clickTransactionInitialized();
        test.mobile().verifier().getTransactionId();

    }

    @When ("the user inserts the PIN")
    public void theUserInsertsThePIN() {
        test.mobile().wallet().createAPin();
    }

    @Given("the user before")
    public void the_user_before() throws InterruptedException {
        test.mobile().wallet().checkIfPageIsTrue();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();

    }
}

