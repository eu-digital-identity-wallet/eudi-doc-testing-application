package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.logging.LogEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import eu.europa.eudi.utils.factory.ReadmeManager;

import static eu.europa.eudi.utils.factory.ReadmeManager.FEATURE_FILES_DIR;

public class GeneralStepDefs{

    TestSetup test;
    @Before
    public void setup(Scenario scenario) {

        try {
            ReadmeManager.createBackupDirIfNotExists();
            ReadmeManager.removeReadmeFiles(Paths.get(FEATURE_FILES_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean noReset = scenario.getSourceTagNames().contains("@noreset");
        boolean data = scenario.getSourceTagNames().contains("@before_01");
        boolean without_data = scenario.getSourceTagNames().contains("@before_02");
        boolean two_pid_data = scenario.getSourceTagNames().contains("@before_03");
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
            test.mobile().wallet().clickContinue();
            test.mobile().wallet().loadSampleDocuments();
            test.mobile().wallet().dashboardPageIsDisplayed();
        }

        if (without_data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickContinue();
        }

        if (two_pid_data) {
            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickContinue();
            test.mobile().wallet().loadSampleDocuments();
            test.mobile().wallet().dashboardPageIsDisplayed();
            test.mobile().wallet().addDocButton();
            test.mobile().wallet().clickNationalId();
            test.mobile().issuer().authenticationMethodSelection();
            test.mobile().issuer().clickCountrySelection();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().clickFormEu();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().enterGivenName();
            test.mobile().issuer().enterFamilyName();
            test.mobile().issuer().chooseBirthDate();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().scrollUntilAuthorize();
            test.mobile().issuer().clickAuthorize();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().clickXButton();
            test.mobile().wallet().dashboardPageIsDisplayed();
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        if (android){
            test.stopAndroidDriverSession();
        }
        if (ios){
            test.stopIosDriverSession();
        }
        test.stopLogging();
        try {
            // Call ReadmeManager.restoreReadmeFiles() after the test teardown
            ReadmeManager.restoreReadmeFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void userOpensVerifierApp() {
        test.mobile().wallet().userOpensVerifier();
    }

    @And("user selects specific data to share")
    public void userSelectSpecificDataToShare() {
        test.mobile().verifier().appOpensSuccefully();
//        test.mobile().verifier().selectShareAttributes();
//        test.mobile().verifier().clickNext();
//        test.mobile().verifier().chooseData();
//        test.mobile().verifier().clickNext();
        theVerifierRequestsADocFromTheWalletUser();
    }


    @And("user selects to be identified using EUDI Wallet")
    public void userSelectsToBeIdentifiedUsingEUDIWallet() {
        test.mobile().verifier().chooseWallet();
    }


    @And("user views the data and can unselect any of them")
    public void userViewsTheDataAndCanUnselectAnyOfThem() {
        test.mobile().verifier().viewDataPage();
    }


    @And("user presses the share button")
    public void userPressesTheShareButton() {
        test.mobile().wallet().checkIfPageIsTrue();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
        test.mobile().wallet().loadSampleDocuments();
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().userOpensVerifier();
        theVerifierRequestsADocFromTheWalletUser();
        test.mobile().verifier().chooseWallet();
        test.mobile().verifier().viewDataPage();
        test.mobile().wallet().clickShareButton();
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
        test.mobile().wallet().startAndStopDriver();
        test.mobile().wallet().loginPageIsDisplayed();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @And("user clicks load sample data")
    public void userClicksLoadSampleData() {
//        theUserIsOnTheLoginScreen();
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
    public void theUserSelectsToIssueACredential() {
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickPersonalIdentificationData();
        test.mobile().issuer().clickSubmitButton();

    }

    @Then("the user is redirected to the EUDI Wallet")
    public void theUserIsRedirectedToTheEUDIWallet() {
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().welcomePage();
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().createAPin();
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().clickNextButton();
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().renterThePin();
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().clickConfirm();
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().successMessageOfSetUpPin();
//        preAuthorizationCodeSameDevice.feature.mobile().wallet().clickContinue();
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @And("the details of the credential to be issued are presented")
    public void theDetailsOfTheCredentialToBeIssuedArePresented() {
        test.mobile().wallet().detailsArePresented();
    }

    @Given("the user is presented with the credential details on the EUDI Wallet")
    public void theUserIsPresentedWithTheCredentialDetailsOnTheEUDIWallet() {
        theUserIsOnTheIssuerService();
        theUserSelectsToIssueACredential();
        theUserIsRedirectedToTheEUDIWallet();
        test.mobile().wallet().detailsArePresented();
    }

    @When("the user presses the ISSUE button")
    public void theUserPressesTheISSUEButton() {
        test.mobile().wallet().clickIssue();
    }

    @Then("the user is redirected back to the issuer service")
    public void theUserIsRedirectedBackToTheIssuerService() {
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @And("the user is prompted to authenticate and consent to the issuance")
    public void theUserIsPromptedToAuthenticateAndConsentToTheIssuance() {
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @And("the user is asked to authenticate and consent on the issuer service")
    public void theUserIsAskedToAuthenticateAndConsentOnTheIssuerService() {
        theUserIsPresentedWithTheCredentialDetailsOnTheEUDIWallet();
        test.mobile().wallet().clickIssue();
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @When("the user authenticates and consents to the issuance")
    public void theUserAuthenticatesAndConsentsToTheIssuance() {
        test.mobile().issuer().clickCountrySelection();
        test.mobile().wallet().clickSubmit();

    }

    @And("inserts the required credential details")
    public void insertsTheRequiredCredentialDetails() {
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().dataPageIsDisplayed();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
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
        test.mobile().wallet().clickContinue();
    }

    @Then("the new document is presented in the EUDI Wallet dashboard screen")
    public void theNewDocumentIsPresentedInTheEUDIWalletDashboardScreen() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @Given("the user sees a success message in the EUDI Wallet app")
    public void theUserSeesASuccessMessageInTheEUDIWalletApp() {
        theUserIsAskedToAuthenticateAndConsentOnTheIssuerService();
        theUserAuthenticatesAndConsentsToTheIssuance();
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

    @Given("the user is on the Add document screen")
    public void theUserIsOnTheAddDocumentScreen() {
        theUserLaunchesTheEUDIWalletForTheFirstTime();
        theUserSetsUpTheirPIN();
        theAddDocumentScreenIsAppeared();
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

    @Given("the user is on the issuance service page")
    public void theUserIsOnTheIssuanceServicePage() {
        theUserIsOnTheAddDocumentScreen();
        theUserHasOnlyTheNationalIDAndSCANQROptionsAvailable();
        theUserClicksOnTheNationalIDOption();
        theUserIsRedirectedToTheIssuanceService();
    }

    @When("the user follows the process to issue a new PID")
    public void theUserFollowsTheProcessToIssueANewPID() {
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
//        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheDashboardScreen();
    }

    @When("the user clicks on the PID doc")
    public void theUserClicksOnThePIDDoc() {
        test.mobile().wallet().clickNationalId();
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
//        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheDashboardScreen();
        theUserClicksOnThePIDDoc();
        thePIDShouldOpen();
        theUserShouldSeeTheDetailsOfThePID();
    }

    @When("the user clicks the X button")
    public void theUserClicksTheXButton() {
        test.mobile().wallet().clickXButton();
    }

    @Then("the PID should close")
    public void thePIDShouldClose() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @And("the user should see the dashboard screen again")
    public void theUserShouldSeeTheDashboardScreenAgain() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Then("the user should see the documents they have issued so far")
    public void theUserShouldSeeTheDocumentsTheyHaveIssuedSoFar() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @When("the user clicks on the mDL doc")
    public void theUserClicksOnTheMDLDoc() {
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
    public void theMDLIsOpen() {
//        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheDashboardScreen();
        theUserClicksOnTheMDLDoc();
        theMDLShouldOpen();
        theUserShouldSeeTheDetailsOfTheMDL();
    }

    @Then("the mDL should close")
    public void theMDLShouldClose() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @When("the user clicks the add doc button")
    public void theUserClicksTheAddDocButton() {
        test.mobile().wallet().addDocButton();
    }

    @And("the add document page is displayed automated")
    public void theAddDocumentPageIsDisplayedAutomated() {
       test.mobile().wallet().addDocumentPageIsDisplayed();
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
        test.mobile().wallet().startAndStopDriver();
        test.mobile().wallet().loginPageIsDisplayed();
        test.mobile().wallet().createAPin();
    }

    @When("the user opens a mDL")
    public void theUserOpensAMDL() {
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
        test.mobile().wallet().clickDeleteButton();
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

    @Then("the user should see the dashboard")
    public void theUserShouldSeeTheDashboard() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @When("the user opens a PID \\(not the first one issued)")
    public void theUserOpensAPIDNotTheFirstOneIssued() {
        test.mobile().wallet().clickSecondNationalId();
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
        theUserHasSuccessfullyEnteredThePIN();
        test.mobile().wallet().clickNationalId();
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
        theUserHasOpenedTheFirstPIDThatWasIssued();
        theUserPressesTheDeleteButton();
        theApplicationShouldReboot();
        theUserShouldEnterThePIN();
    }

    @When("the user is prompted to enter a PID")
    public void theUserIsPromptedToEnterAPID() {
        test.mobile().wallet().addDocumentPageIsDisplayed();
    }

    @Then("the user should be able to enter a PID again")
    public void theUserShouldBeAbleToEnterAPIDAgain() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @And("the user clicks the driving license button")
    public void theUserClicksTheDrivingLicenseButton() {
        test.mobile().wallet().clickDrivingLicenceButton();
    }

    @Then("the user is redirected to the issuer service to issue mDL")
    public void theUserIsRedirectedToTheIssuerServiceToIssueMDL() {
//preAuthorizationCodeSameDevice.feature
//preAuthorizationCodeSameDevice.feature.startAndroidDriverSession();
//        AndroidDriver driver = (AndroidDriver) preAuthorizationCodeSameDevice.feature.mobileWebDriverFactory().getDriverAndroid();
//        driver.runAppInBackground(Duration.ofSeconds(10));
//        driver.activateApp("com.android.chrome");
    }

    @Given("the issuer service -authentication method selection screen- is displayed")
    public void theIssuerServiceAuthenticationMethodSelectionScreenIsDisplayed() {
        theDashboardPageIsDisplayedOnWallet();
        theUserClicksTheAddDocButton();
        theAddDocumentPageIsDisplayedAutomated();
        theUserClicksTheDrivingLicenseButton();
        theAuthenticationMethodSelectionIsDisplayed();

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
    public void aProviderFormIsDisplayed() {
        theAuthenticationMethodSelectionIsDisplayedOnScreen();
        theUserClicksOnCountrySelectionAndSubmits();
        theUserClicksOnCredentialProviderFormEUAndSubmits();
        theProviderFormIsDisplayedForTheUserToRegisterPersonalData();
    }

    @When("the user registers personal data")
    public void theUserRegistersPersonalData() {
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterDocumentNumber();
        test.mobile().issuer().clickScreen();
        test.mobile().issuer().scrollUntilFindDate();
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
        test.mobile().wallet().clickNextButton();
    }

    @And("the driving license is displayed in the wallet")
    public void theDrivingLicenseIsDisplayedInTheWallet() {
        test.mobile().wallet().mdlIsDisplayed();
        test.mobile().wallet().clickXButton();
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @When("the user fills in the form")
    public void theUserFillsInTheForm() {
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("a success message for pid is displayed")
    public void aSuccessMessageForPidIsDisplayed() {
        test.mobile().wallet().successMessageIsDisplayed();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().nationalIdIsDisplayed();
        test.mobile().wallet().clickXButton();
    }

    @And("the national id is displayed in the dashboard")
    public void theNationalIdIsDisplayedInTheDashboard() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Then("the user should see the add document page")
    public void theUserShouldSeeTheAddDocumentPage() {
        test.mobile().wallet().addDocumentPageIsDisplayed();
    }

    @Given("user opens Verifier Application")
    public void userOpensVerifierApplication() {
        test.mobile().wallet().checkIfPageIsTrue();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
        test.mobile().wallet().loadSampleDocuments();
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().userOpensVerifier();
    }

    @Given("the user is in the verifier app")
    public void theUserIsInTheVerifierApp() {
        userOpensVerifierApp();
    }

    @When("the verifier requests a doc from the wallet user")
    public void theVerifierRequestsADocFromTheWalletUser() {
        test.mobile().verifier().appOpensSuccefully();
        test.mobile().verifier().selectShareAttributes();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().selectAttributes();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().clickNext();
//        test.mobile().verifier().chooseData();
//        test.mobile().verifier().chooseData2();
//        test.mobile().verifier().scrollUntilFindIssuanceDate();
//        test.mobile().verifier().clickIssuanceDate();
//        test.mobile().verifier().clickNext();
    }

    @Then("the requestor of the data is displayed in the wallet")
    public void theRequestorOfTheDataIsDisplayedInTheWallet() {
        test.mobile().verifier().chooseWallet();
        test.mobile().verifier().viewDataPage();
    }

    @And("the document from which the data are requested is displayed")
    public void theDocumentFromWhichTheDataAreRequestedIsDisplayed() {
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @Given("the user is viewing the optional data")
    public void theUserIsViewingTheOptionalData() {
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

    @Given("the user is viewing the data request details")
    public void theUserIsViewingTheDataRequestDetails() {
        theUserIsViewingTheOptionalData();
        theUserClicksTheEyeIcon();
        theActualValuesOfTheDataAreDisplayed();
    }

    @When("the user clicks to expand the verification section")
    public void theUserClicksToExpandTheVerificationSection() {
        test.mobile().wallet().clickExpandVerification();
    }

    @Then("the expanded verification details are displayed")
    public void theExpandedVerificationDetailsAreDisplayed() {
        test.mobile().wallet().verificationDetailsAreDisplayed();
    }

    @Given("the user has selected some data")
    public void theUserHasSelectedSomeData() {
        theUserIsInTheVerifierApp();
        theVerifierRequestsADocFromTheWalletUser();
        theRequestorOfTheDataIsDisplayedInTheWallet();
        theDocumentFromWhichTheDataAreRequestedIsDisplayed();
        test.mobile().wallet().optionalDataIsDisplayed();
    }

    @When("the user unselects some of this data")
    public void theUserUnselectsSomeOfThisData() {
        test.mobile().wallet().unselectData();
    }

    @Given("the user has finalized data selection")
    public void theUserHasFinalizedDataSelection() {
        theUserIsInTheVerifierApp();
        theVerifierRequestsADocFromTheWalletUser();
        theRequestorOfTheDataIsDisplayedInTheWallet();
        theDocumentFromWhichTheDataAreRequestedIsDisplayed();
        test.mobile().wallet().optionalDataIsDisplayed();
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
    public void theUserIsPromptedToEnterAPINForSharing() {
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
    public void userSelectsToBeIdentifiedUsingTheEUDIWallet() {
        test.mobile().wallet().checkIfPageIsTrue();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
        test.mobile().wallet().loadSampleDocuments();
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().userOpensVerifier();
        test.mobile().verifier().appOpensSuccefully();
        theVerifierRequestsADocFromTheWalletUser();
        test.mobile().verifier().chooseWallet();
    }

    @When("user authorizes the disclosure of the data")
    public void userAuthorizesTheDisclosureOfTheData() {
        test.mobile().wallet().pinFieldIsDisplayed();
        test.mobile().wallet().createAPin();
    }

    @Then("user presses the share button on wallet")
    public void userPressesTheShareButtonOnWallet() {
        test.mobile().wallet().clickShareButton();
    }

    @Given("the authentication method selection is displayed on screen")
    public void theAuthenticationMethodSelectionIsDisplayedOnScreen() {
        theDashboardPageIsDisplayedOnWallet();
        theUserClicksTheAddDocButton();
        theAddDocumentPageIsDisplayedAutomated();
        theUserClicksTheNationalIdButton();
        theAuthenticationMethodSelectionIsDisplayed();
    }

    @Given("a provider form is displayed for mdl")
    public void aProviderFormIsDisplayedForMdl() {
        theIssuerServiceAuthenticationMethodSelectionScreenIsDisplayed();
        theUserClicksOnCountrySelectionAndSubmits();
        theUserClicksOnCredentialProviderFormEUAndSubmits();
        theProviderFormIsDisplayedForTheUserToRegisterPersonalData();
    }

    @Given("the user is on the Login screen")
    public void theUserIsOnTheLoginScreen() {
        test.mobile().wallet().startAndStopDriver();
        test.mobile().wallet().loginPageIsDisplayed();
    }
}

