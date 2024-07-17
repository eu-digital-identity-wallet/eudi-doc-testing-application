package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GeneralStepDefs{

    TestSetup test;

    @Before
    public void setup(Scenario scenario) {
        boolean noReset = scenario.getSourceTagNames().contains("@noreset");
        boolean data = scenario.getSourceTagNames().contains("@before_01");
        boolean without_data = scenario.getSourceTagNames().contains("@before_02");
        boolean two_pid_data = scenario.getSourceTagNames().contains("@before_03");
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        if (android) {
            test = new TestSetup(noReset, Literals.General.ANDROID.label, scenario);
            test.startAndroidDriverSession();
        }
        if (ios) {
            test = new TestSetup(noReset, Literals.General.IOS.label, scenario);
            test.startIosDriverSession();
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
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().scrollUntilAuthorize();
            test.mobile().issuer().clickAuthorize();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().clickXButton();
            test.mobile().wallet().dashboardPageIsDisplayed();
        }
    }

//    @After
//    public void tearDown(Scenario scenario) {
//        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
//        boolean ios = scenario.getSourceTagNames().contains("@IOS");
//        if (android){
//            test.stopAndroidDriverSession();
//        }
//        if (ios){
//            test.stopIosDriverSession();
//        }
//    }

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
        test.mobile().verifier().selectShareAttributes();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().chooseData();
        test.mobile().verifier().clickNext();
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
        test.mobile().verifier().appOpensSuccefully();
        test.mobile().verifier().selectShareAttributes();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().chooseData();
        test.mobile().verifier().clickNext();
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
        theUserIsOnTheLoginScreen();
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
        test.mobile().issuer().issuerServicePageIsDisplayed();
    }

    @When("the user selects to issue a credential")
    public void theUserSelectsToIssueACredential() {
        test.mobile().issuer().selectIssueTest();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().clickPersonalIdentificationData();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmitButton();
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @Then("the user is redirected to the EUDI Wallet")
    public void theUserIsRedirectedToTheEUDIWallet() {
        test.mobile().wallet().welcomePage();
        test.mobile().wallet().createAPin();
        test.mobile().wallet().clickNextButton();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().clickConfirm();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickContinue();
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
        test.mobile().issuer().clickSubmit();
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

    @Given("the user has the EUDI Wallet app installed")
    public void theUserHasTheEUDIWalletAppInstalled() {
        //manual
    }

    @When("the user opens the EUDI Wallet app")
    public void theUserOpensTheEUDIWalletApp() {
        //manual
    }

    @And("the user enters their PIN correctly")
    public void theUserEntersTheirPINCorrectly() {
        //manual
    }

    @Then("the dashboard page should be displayed")
    public void theDashboardPageShouldBeDisplayed() {
        //manual
    }

    @Given("the user is on the dashboard page of the EUDI Wallet app")
    public void theUserIsOnTheDashboardPageOfTheEUDIWalletApp() {
        //manual
    }

    @When("the user clicks on the Add doc button")
    public void theUserClicksOnTheAddDocButton() {
        //manual
    }

    @Then("the Add document page should be displayed")
    public void theAddDocumentPageShouldBeDisplayed() {
        //manual
    }

    @Given("the user is on the Add document page")
    public void theUserIsOnTheAddDocumentPage() {
        //manual
    }

    @When("the user clicks on the Scan QR option")
    public void theUserClicksOnTheScanQROption() {
        //manual
    }

    @Then("the QR code scanner should be activated")
    public void theQRCodeScannerShouldBeActivated() {
        //manual
    }

    @Given("the QR code scanner is activated")
    public void theQRCodeScannerIsActivated() {
        //manual
    }

    @When("the user scans a QR code from the issuer service")
    public void theUserScansAQRCodeFromTheIssuerService() {
        //manual
    }

    @Then("the details of the credential to be issued should be displayed including the type of credential and the issuer name")
    public void theDetailsOfTheCredentialToBeIssuedShouldBeDisplayedIncludingTheTypeOfCredentialAndTheIssuerName() {
        //manual
    }

    @Given("the user is presented with the credential details to be issued")
    public void theUserIsPresentedWithTheCredentialDetailsToBeIssued() {
        //manual
    }

    @Then("the user should be redirected to the issuer service for authentication and consent")
    public void theUserShouldBeRedirectedToTheIssuerServiceForAuthenticationAndConsent() {
        //manual
    }

    @Given("the user is on the issuer's service page and has authenticated")
    public void theUserIsOnTheIssuerSServicePageAndHasAuthenticated() {
        //manual
    }

    @When("the user consents to the issuance and inserts the credential")
    public void theUserConsentsToTheIssuanceAndInsertsTheCredential() {
        //manual
    }

    @And("the credential issuance process completes")
    public void theCredentialIssuanceProcessCompletes() {
        //manual
    }

    @Then("the user should be redirected back to the EUDI Wallet app")
    public void theUserShouldBeRedirectedBackToTheEUDIWalletApp() {
        //manual
    }

    @And("a success message should appear")
    public void aSuccessMessageShouldAppear() {
        //manual
    }

    @Then("the new document should be presented in the dashboard screen")
    public void theNewDocumentShouldBePresentedInTheDashboardScreen() {
        //manual
    }

    @When("the user opens the EUDI Wallet for the first time")
    public void theUserOpensTheEUDIWalletForTheFirstTime() {
        //manual
    }

    @Then("the user is prompted to set up a PIN")
    public void theUserIsPromptedToSetUpAPIN() {
        //manual
    }

    @When("the user sets up the PIN successfully")
    public void theUserSetsUpThePINSuccessfully() {
        //manual
    }

    @Then("the Add document page is displayed")
    public void theAddDocumentPageIsDisplayed() {
       test.mobile().wallet().addDocumentPageIsDisplayed();
    }

    @Then("the camera is activated to scan a QR code")
    public void theCameraIsActivatedToScanAQRCode() {
        //manual
    }

    @Given("the user has activated the camera to scan a QR code")
    public void theUserHasActivatedTheCameraToScanAQRCode() {
        //manual
    }

    @When("the user scans the QR code from the issuer service")
    public void theUserScansTheQRCodeFromTheIssuerService() {
        //manual
    }

    @Then("the user is presented with the details of the credential to be issued including the type of credential and issuer name")
    public void theUserIsPresentedWithTheDetailsOfTheCredentialToBeIssuedIncludingTheTypeOfCredentialAndIssuerName() {
        //manual
    }

    @Given("the user is presented with the credential details")
    public void theUserIsPresentedWithTheCredentialDetails() {
        //manual
    }

    @Then("the user is redirected to the issuer service for authentication and consent")
    public void theUserIsRedirectedToTheIssuerServiceForAuthenticationAndConsent() {
        //manual
    }

    @Given("the user is at the issuer service page")
    public void theUserIsAtTheIssuerServicePage() {
        //manual
    }

    @When("the user authenticates and consents to the issuance of the document")
    public void theUserAuthenticatesAndConsentsToTheIssuanceOfTheDocument() {
        //manual
    }

    @Then("the credential is issued to the user")
    public void theCredentialIsIssuedToTheUser() {
        //manual
    }

    @And("the user is redirected back to the EUDI Wallet app")
    public void theUserIsRedirectedBackToTheEUDIWalletApp() {
        //manual
    }

    @Given("the user is redirected back to the EUDI Wallet app after the issuance")
    public void theUserIsRedirectedBackToTheEUDIWalletAppAfterTheIssuance() {
        //manual
    }

    @Then("the new document is displayed in the dashboard screen")
    public void theNewDocumentIsDisplayedInTheDashboardScreen() {
        //manual
    }

    @When("the user opens the issuer service")
    public void theUserOpensTheIssuerService() {
        //manual
    }

    @And("selects to issue a credential")
    public void selectsToIssueACredential() {
        //manual
    }

    @Then("the EUDI Wallet app opens")
    public void theEUDIWalletAppOpens() {
        //manual
    }

    @Given("the EUDI Wallet app is open")
    public void theEUDIWalletAppIsOpen() {
        //manual
    }

    @When("the user sets up a PIN successfully")
    public void theUserSetsUpAPINSuccessfully() {
        //manual
    }

    @Then("the user is presented with details of the credential to be issued")
    public void theUserIsPresentedWithDetailsOfTheCredentialToBeIssued() {
        //manual
    }

    @Given("the user has successfully set up a PIN")
    public void theUserHasSuccessfullySetUpAPIN() {
        //manual
    }

    @When("the wallet app presents the details of the credential")
    public void theWalletAppPresentsTheDetailsOfTheCredential() {
        //manual
    }

    @Then("the details include the type of credential and issuer name")
    public void theDetailsIncludeTheTypeOfCredentialAndIssuerName() {
        //manual
    }

    @Given("the details of the credential are displayed")
    public void theDetailsOfTheCredentialAreDisplayed() {
        //manual
    }

    @Given("the user is redirected to the issuer service")
    public void theUserIsRedirectedToTheIssuerService() {
        //manual
    }

    @When("the user authenticates and consents to the issuance of the credential")
    public void theUserAuthenticatesAndConsentsToTheIssuanceOfTheCredential() {
        //manual
    }

    @Then("the credential is inserted into the user's EUDI Wallet")
    public void theCredentialIsInsertedIntoTheUserSEUDIWallet() {
        //manual
    }

    @Given("the user is redirected back to the EUDI Wallet app after the credential issuance")
    public void theUserIsRedirectedBackToTheEUDIWalletAppAfterTheCredentialIssuance() {
        //manual
    }

    @Then("the new document is displayed in the dashboard screen of the EUDI Wallet app")
    public void theNewDocumentIsDisplayedInTheDashboardScreenOfTheEUDIWalletApp() {
        //manual
    }

    @When("the user clicks on the Scan QR option manually")
    public void theUserClicksOnTheScanQROptionManually() {
        //manual
    }

    @When("the user presses the ISSUE button manually")
    public void theUserPressesTheISSUEButtonManually() {
        //manual
    }

    @And("the user presses the CONTINUE button manually")
    public void theUserPressesTheCONTINUEButtonManually() {
        //manual
    }

    @Given("the user sees a success message in the EUDI Wallet app manually")
    public void theUserSeesASuccessMessageInTheEUDIWalletAppManually() {
        //manual
    }

    @When("a success message is displayed manually")
    public void aSuccessMessageIsDisplayedManually() {
        //manual
    }

    @Given("the user is on the Login screen")
    public void theUserIsOnTheLoginScreen() {
        test.mobile().wallet().startAndStopDriver();
        test.mobile().wallet().loginPageIsDisplayed();
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
        theUserIsOnTheLoginScreen();
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
        theUserIsOnTheLoginScreen();
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
        theUserIsOnTheLoginScreen();
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
//test
//test.startAndroidDriverSession();
//        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
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
        test.mobile().verifier().chooseData();
        test.mobile().verifier().chooseData2();
        test.mobile().verifier().scrollUntilFindIssuanceDate();
        test.mobile().verifier().clickIssuanceDate();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().chooseWallet();
    }

    @Then("the requestor of the data is displayed in the wallet")
    public void theRequestorOfTheDataIsDisplayedInTheWallet() {
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
        test.mobile().verifier().selectShareAttributes();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().chooseData();
        test.mobile().verifier().clickNext();
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

    @When("the user clicks on the Age Verification")
    public void theUserClicksOnTheAgeVerification() {
        //manual
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

    @Given("the user visits the Relying Party service on a different device from the one where the wallet app is installed")
    public void theUserVisitsTheRelyingPartyServiceOnADifferentDeviceFromTheOneWhereTheWalletAppIsInstalled() {
        //manual
    }

    @When("the Relying Party service displays a QR code")
    public void theRelyingPartyServiceDisplaysAQRCode() {
        //manual
    }

    @Then("the user views the QR code")
    public void theUserViewsTheQRCode() {
        //manual
    }

    @When("the user clicks on the options button")
    public void theUserClicksOnTheOptionsButton() {
        //manual
    }

    @And("the user clicks on the scan qr code button")
    public void theUserClicksOnTheScanAQRCodeButton() {
        //manual
    }

    @Then("the camera opens and the user scan a QR code")
    public void theCameraOpensAndTheUserScansTheQRCode() {
        //manual
    }

    @Given("the user scans the QR code")
    public void theUserScansTheQRCode() {
        //manual
    }

    @When("the wallet app displays a screen informing the user about the age verification request")
    public void theWalletAppDisplaysAScreenInformingTheUserAboutTheAgeVerificationRequest() {
        //manual
    }

    @Then("the user reads the age verification request")
    public void theUserReadsTheAgeVerificationRequest() {
        //manual
    }

    @When("the user taps the share button in the wallet app")
    public void theUserTapsTheShareButtonInTheWalletApp() {
        //manual
    }

    @Then("the wallet app prompts the user to enter the PIN")
    public void theWalletAppPromptsTheUserToEnterThePIN() {
        //manual
    }

    @When("the user enters the incorrect PIN")
    public void theUserEntersTheIncorrectPIN() {
        //manual
    }

    @Then("the wallet app displays a corresponding error message")
    public void theWalletAppDisplaysACorrespondingErrorMessage() {
        //manual
    }

    @When("the user re-enters the correct PIN")
    public void theUserReEntersTheCorrectPIN() {
        //manual
    }

    @Then("the wallet app displays a success message")
    public void theWalletAppDisplaysASuccessMessage() {
        //manual
    }

    @Given("the user visits the Relying Party service on their mobile device")
    public void theUserVisitsTheRelyingPartyServiceOnTheirMobileDevice() {
        //manual
    }

    @When("the user selects to verify the age limit with the EUDI Wallet")
    public void theUserSelectsToVerifyTheAgeLimitWithTheEUDIWallet() {
        //manual
    }

    @Then("the Relying Party service redirects the user to the EUDI Wallet")
    public void theRelyingPartyServiceRedirectsTheUserToTheEUDIWallet() {
        //manual
    }

    @When("the user authenticates successfully in the EUDI Wallet")
    public void theUserAuthenticatesSuccessfullyInTheEUDIWallet() {
        //manual
    }

    @Then("the EUDI Wallet presents a screen to inform the user about the Age Verification Attestation request")
    public void theEUDIWalletPresentsAScreenToInformTheUserAboutTheAgeVerificationAttestationRequest() {
        //manual
    }

    @When("the user views the Age Verification Attestation to be presented")
    public void theUserViewsTheAgeVerificationAttestationToBePresented() {
        //manual
    }

    @Given("the EUDI Wallet user clicks on the share button")
    public void theEUDIWalletUserClicksOnTheShareButton() {
        //manual
    }

    @When("a authentication page appears")
    public void aAuthenticationPageAppears() {
        //manual
    }

    @Given("the user has clicked on the {string} button")
    public void theUserHasClickedOnTheContinueButton() {
        //manual
    }

    @When("the user is redirected to the relying party")
    public void theUserIsRedirectedToTheRelyingParty() {
        //manual
    }

    @Then("the Relying Party service presents the data from the EUDI wallet")
    public void theRelyingPartyServicePresentsTheDataFromTheEUDIWallet() {
        //manual
    }

    @When("the user opens an age verification doc")
    public void theUserOpensAnAgeVerificationDoc() {
        //manual
    }

    @Given("the user has an age verification document open")
    public void theUserHasAnAgeVerificationDocumentOpen() {
        //manual
    }

    @When("the user clicks the delete button")
    public void theUserClicksTheDeleteButton() {
        //manual
    }

    @Then("a confirmation modal should appear")
    public void aConfirmationModalShouldAppear() {
        //manual
    }

    @Given("a confirmation modal is displayed")
    public void aConfirmationModalIsDisplayed() {
        //manual
    }

    @When("the user clicks the No button")
    public void theUserClicksTheNoButton() {
        //manual
    }

    @Then("the modal should close")
    public void theModalShouldClose() {
        //manual
    }

    @And("the user should still view the age verification document")
    public void theUserShouldStillViewTheAgeVerificationDocument() {
        //manual
    }

    @When("the user clicks the delete button again")
    public void theUserClicksTheDeleteButtonAgain() {
        //manual
    }

    @When("the user clicks the Yes button")
    public void theUserClicksTheYesButton() {
        //manual
    }

    @And("the user should be redirected to the dashboard")
    public void theUserShouldBeRedirectedToTheDashboard() {
        //manual
    }

    @And("the user clicks the Age Verification button")
    public void theUserClicksTheAgeVerificationButton() {
        //manual
    }

    @When("the user clicks country selection")
    public void theUserClicksCountrySelection() {
        //manual
    }

    @And("the user clicks FormEU")
    public void theUserClicksFormEU() {
        //manual
    }

    @Then("the data page is displayed")
    public void theDataPageIsDisplayed() {
        //manual
    }

    @Given("a form is displayed")
    public void aFormIsDisplayed() {
        //manual
    }

    @And("the age verification is displayed in the dashboard")
    public void theAgeVerificationIsDisplayedInTheDashboard() {
        //manual
    }

    @And("the user should see the details of the Age Verification")
    public void theUserShouldSeeTheDetailsOfTheAgeVerification() {
        //manual
    }

    @Given("the Age Verification is open")
    public void theAgeVerificationIsOpen() {
        //manual
    }

    @Then("the Age Verification should close")
    public void theAgeVerificationShouldClose() {
        //manual
    }

    @Given("the user visits the Issuer service")
    public void theUserVisitsTheIssuerService() {
        //manual
    }

    @When("the user chooses to issue a doc with pre-authorization")
    public void theUserChoosesToIssueADocWithPreAuthorization() {
        //manual
    }

    @Then("the Issuer service creates a QR code and a transaction code")
    public void theIssuerServiceCreatesAQRCodeAndATransactionCode() {
        //manual
    }

    @Given("the issuer has displayed a QR code")
    public void theIssuerHasDisplayedAQRCode() {
        //manual
    }

    @When("the user initiates the wallet app")
    public void theUserInitiatesTheWalletApp() {
        //manual
    }

    @Then("the dashboard screen is displayed")
    public void theDashboardScreenIsDisplayed() {
        //manual
    }

    @Given("the waller app has been initiated")
    public void theWallerAppHasBeenInitiated() {
        //manual
    }

    @When("the user clicks on the {string} button on the wallet app")
    public void theUserClicksOnTheADDDOCButtonOnTheWalletApp() {
        //manual
    }

    @Then("the user views the {string} screen")
    public void theUserViewsTheAddDocumentScreen() {
        //manual
    }

    @Given("the {string} screen is diplayed")
    public void theAddDocumentScreenIsDiplayed() {
        //manual
    }

    @Then("the phone camera opens")
    public void thePhoneCameraOpens() {
        //manual
    }

    @Given("the phone camera has opened")
    public void thePhoneCameraHasOpened() {
        //manual
    }

    @When("the user scans the QR code from the issuer")
    public void theUserScansTheQRCodeFromTheIssuer() {
        //manual
    }

    @Then("the details of the request are displayed on the wallet app")
    public void theDetailsOfTheRequestAreDisplayedOnTheWalletApp() {
        //manual
    }

    @Given("the user is presented with the request details to be issued")
    public void theUserIsPresentedWithTheRequestDetailsToBeIssued() {
        //manual
    }

    @Then("the Wallet app requests the transaction code")
    public void theWalletAppRequestsTheTransactionCode() {
        //manual
    }

    @And("the user enters the transaction code provided by the Issuer")
    public void theUserEntersTheTransactionCodeProvidedByTheIssuer() {
        //manual
    }

    @And("the doc is displayed in the dashboard screen")
    public void theDocIsDisplayedInTheDashboardScreen() {
        //manual
    }

    @And("a transaction code has been created")
    public void aTransactionCodeHasBeenCreated() {
        //manual
    }

    @Given("the transaction code has been created")
    public void theTransactionCodeHasBeenCreated() {
        //manual
    }

    @When("the user selects to register with the EUDI wallet app")
    public void theUserSelectsToRegisterWithTheEUDIWalletApp() {
        //manual
    }

    @And("the user enters the PIN")
    public void theUserEntersThePIN() {
        //manual
    }

    @Given("the user has entered the PIN")
    public void theUserHasEnteredThePIN() {
        //manual
    }

    @When("the request from the issuer is displayed on the wallet app")
    public void theRequestFromTheIssuerIsDisplayedOnTheWalletApp() {
        //manual
    }

    @Given("the user entered the transaction code provided by the Issuer")
    public void theUserEnteredTheTransactionCodeProvidedByTheIssuer() {
        //manual
    }

    @And("the add document page is displayed on screen")
    public void theAddDocumentPageIsDisplayedOnScreen() {
        //manual
    }

    @When("the Wallet app displays a success message on screen")
    public void theWalletAppDisplaysASuccessMessageOnScreen() {
        //manual
    }

    @Then("the user is redirected to the EUDI wallet application")
    public void theUserIsRedirectedToTheEUDIWalletApplication() {
        //manual
    }

    @When("the Wallet application displays a success message")
    public void theWalletApplicationDisplaysASuccessMessage() {
        //manual
    }

    @Then("the Add document page is displayed on wallet")
    public void theAddDocumentPageIsDisplayedOnWallet() {
       // manual
    }

    @Given("the user is on the Login screen manually")
    public void theUserIsOnTheLoginScreenManually() {
        //manual
    }

    @Given("the user has successfully entered the PIN manually")
    public void theUserHasSuccessfullyEnteredThePINManually() {
        //manual
    }

    @Then("the user should see the document contents manually")
    public void theUserShouldSeeTheDocumentContentsManually() {
      //  manual
    }

    @Then("the document should be deleted manually")
    public void theDocumentShouldBeDeletedManually() {
        //manual
    }

    @When("the user enters their PIN manually")
    public void theUserEntersTheirPINManually() {
        //manual
    }

    @Then("the user should see the dashboard screen manually")
    public void theUserShouldSeeTheDashboardScreenManually() {
        //manual
    }

    @Given("the dashboard page is displayed manually")
    public void theDashboardPageIsDisplayedManually() {
        //manual
    }

    @When("the user clicks the add doc button manually")
    public void theUserClicksTheAddDocButtonManually() {
        //manual
    }

    @Then("the authentication method selection is displayed manually")
    public void theAuthenticationMethodSelectionIsDisplayedManually() {
        //manual
    }

    @When("the user fills in the form manually")
    public void theUserFillsInTheFormManually() {
        //manual
    }

    @Given("the user is on the dashboard screen manually")
    public void theUserIsOnTheDashboardScreenManually() {
        //manual
    }

    @Then("the PID should open manually")
    public void thePIDShouldOpenManually() {
        //manual
    }

    @When("the user clicks the X button manually")
    public void theUserClicksTheXButtonManually() {
        //manual
    }

    @And("the user should see the dashboard screen again manually")
    public void theUserShouldSeeTheDashboardScreenAgainManually() {
        //manual
    }

    @Given("the user is on the issuer service manually")
    public void theUserIsOnTheIssuerServiceManually() {
        //manual
    }

    @Then("the user registers personal data manually")
    public void theUserRegistersPersonalDataManually() {
        //manual
    }

    @Then("the Add document page is displayed on wallet manually")
    public void theAddDocumentPageIsDisplayedOnWalletManually() {
        //manual
    }

    @Given("the user is on the issuer service page")
    public void theUserIsOnTheIssuerServicePage() {
        //manual
    }

    @When("the user chooses to issue a credential to the wallet app")
    public void theUserChoosesToIssueACredentialToTheWalletApp() {
        //manual
    }

    @Then("the user is redirected to the wallet app")
    public void theUserIsRedirectedToTheWalletApp() {
        //manual
    }

    @And("the user views the details regarding the issuance")
    public void theUserViewsTheDetailsRegardingTheIssuance() {
        //manual
    }

    @Given("the user is on the wallet app with issuance details")
    public void theUserIsOnTheWalletAppWithIssuanceDetails() {
        //manual
    }

    @When("the user clicks the cancel button")
    public void theUserClicksTheCancelButton() {
        //manual
    }

    @Then("a modal appears asking if they really want to cancel the issuance process")
    public void aModalAppearsAskingIfTheyReallyWantToCancelTheIssuanceProcess() {
        //manual
    }

    @Given("the user views the cancellation confirmation modal")
    public void theUserViewsTheCancellationConfirmationModal() {
        //manual
    }

    @When("the user clicks the cancel button on the modal")
    public void theUserClicksTheCancelButtonOnTheModal() {
        //manual
    }

    @Then("the issuance process is canceled")
    public void theIssuanceProcessIsCanceled() {
        //manual
    }

    @And("the user returns to the {string} screen")
    public void theUserReturnsToTheAddDocumentScreen() {
        //manual
    }

    @And("the user sees the details regarding the issuance")
    public void theUserSeesTheDetailsRegardingTheIssuance() {
        //manual
    }

    @When("the user clicks the {string} button")
    public void theUserClicksTheISSUEButton() {
        //manual
    }

    @Then("the user is redirected to the issuer for authentication and consent")
    public void theUserIsRedirectedToTheIssuerForAuthenticationAndConsent() {
        //manual
    }

    @Given("the user is on the issuer page for authentication and consent")
    public void theUserIsOnTheIssuerPageForAuthenticationAndConsent() {
        //manual
    }

    @When("the user authenticates and consents to the issuance manually")
    public void theUserAuthenticatesAndConsentsToTheIssuanceManually() {
        //manual
    }

    @And("a message appears stating that the request is in progress")
    public void aMessageAppearsStatingThatTheRequestIsInProgress() {
        //manual
    }

    @Given("the user sees the issuance in progress message")
    public void theUserSeesTheIssuanceInProgressMessage() {
        //manual
    }

    @When("the user clicks OK")
    public void theUserClicksOK() {
        //manual
    }

    @Then("the dashboard appears with the document grayed out and in a pending state")
    public void theDashboardAppearsWithTheDocumentGrayedOutAndInAPendingState() {
        //manual
    }

    @Given("the wallet app is polling the issuer for the credential")
    public void theWalletAppIsPollingTheIssuerForTheCredential() {
        //manual
    }

    @When("the issuer sends the credential to the wallet app")
    public void theIssuerSendsTheCredentialToTheWalletApp() {
        //manual
    }

    @Then("the user views a modal informing them that the document has been issued")
    public void theUserViewsAModalInformingThemThatTheDocumentHasBeenIssued() {
        //manual
    }

    @Given("the user views the issuance confirmation modal")
    public void theUserViewsTheIssuanceConfirmationModal() {
        //manual
    }

    @When("the user clicks the view button")
    public void theUserClicksTheViewButton() {
        //manual
    }

    @Then("the user views the document information")
    public void theUserViewsTheDocumentInformation() {
        //manual
    }

    @And("the user clicks the ADD button")
    public void theUserClicksTheADDButton() {
        //manual
    }

    @Then("the document appears on the dashboard screen")
    public void theDocumentAppearsOnTheDashboardScreen() {
        //manual
    }

    @Then("the Add document page is displayed manually")
    public void theAddDocumentPageIsDisplayedManually() {
        //manual
    }

    @And("the user clicks on the scan a qr code button")
    public void theUserClicksOnTheScanAQrCodeButton() {
        //manual
    }

    @And("the user clicks on scan a QR code button")
    public void theUserClicksOnScanAQRCodeButton() {
        //manual
    }

    @Then("camera opens and the user scans the QR code")
    public void cameraOpensAndTheUserScansTheQRCode() {
        //manual
    }

    @Then("the user clicks on the Share button")
    public void theUserClicksOnTheShareButton() {
        //manual
    }

    @And("the user clicks on the Continue button")
    public void theUserClicksOnTheContinueButton() {
        //manual
    }

    @Given("the user has clicked on continue button")
    public void theUserHasClickedOnContinueButton() {
        //manual
    }

    @And("the user returns to the Add Document page screen")
    public void theUserReturnsToTheAddDocumentPageScreen() {
        //manual
    }

    @Then("the user clicks on the CONTINUE button")
    public void theUserClicksOnTheCONTINUEButton() {
        //manual
    }

    @When("the user clicks on the SCAN QR button")
    public void theUserClicksOnTheSCANQRButton() {
        //manual
    }

    @Given("the Add document screen is displayed")
    public void theAddDocumentScreenIsDisplayed() {
        //manual
    }

    @Then("the user views the Add document page screen")
    public void theUserViewsTheAddDocumentPageScreen() {
        //manual
    }

    @When("the user clicks on the ADD DOC button on the wallet application")
    public void theUserClicksOnTheADDDOCButtonOnTheWalletApplication() {
        //manual
    }

    @Then("the user clicks on the ISSUE button")
    public void theUserClicksOnTheISSUEButton() {
        //manual
    }

    @Given("the user has clicked on the ISSUE button")
    public void theUserHasClickedOnTheISSUEButton() {
        //manual
    }

    @When("the user clicks the Issue button")
    public void theUserClicksTheIssueButton() {
        //manual
    }

    @Given("the authentication method selection is displayed on screen manually")
    public void theAuthenticationMethodSelectionIsDisplayedOnScreenManually() {
        //manual
    }

    @When("the user presses the Issue button manually")
    public void theUserPressesTheIssueButtonManually() {
        //manual
    }

    @And("there is an age verification attestation")
    public void thereIsAnAgeVerificationAttestation() {
        //manual
    }

    @Then("the verifier's request appears in the wallet app")
    public void theVerifierSRequestAppearsInTheWalletApp() {
        //manual
    }


    @Then("the QR code appears")
    public void theQRCodeAppears() {
        //manual
    }

    @Given("the QR code is displayed")
    public void theQRCodeIsDisplayed() {
        //manual
    }

    @When("the verifier scans the QR code")
    public void theVerifierScansTheQRCode() {
        //manual
    }

    @When("the user clicks on the SHOW QR button")
    public void theUserClicksOnTheSHOWQRButton() {
        //manual
    }

    @When("the user chooses to proceed with the request")
    public void theUserChoosesToProceedWithTheRequest() {
        //manual
    }

    @And("the user presses the SHARE button")
    public void theUserPressesTheSHAREButton() {
        //manual
    }

    @Then("the user is prompted to enter the PIN")
    public void theUserIsPromptedToEnterThePIN() {
        //manual
    }

    @Given("the user has pressed the {string} button")
    public void theUserHasPressedTheSHAREButton() {
        //manual
    }

    @Then("a success message appears in the wallet app")
    public void aSuccessMessageAppearsInTheWalletApp() {
        //manual
    }

    @And("the verifier views the information shared by the wallet user")
    public void theVerifierViewsTheInformationSharedByTheWalletUser() {
        //manual
    }

    @Given("the user has pressed the SHARE button manually")
    public void theUserHasPressedTheSHAREButtonManually() {
        //manual
    }

    @When("the user presses the SHOW QR/TAP button")
    public void theUserPressesTheSHOWQRTAPButton() {
        //manual
    }
}

