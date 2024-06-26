package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GeneralStepDefs{

    TestSetup test;

    @Before
    public void setup(Scenario scenario) {
        boolean noReset = scenario.getSourceTagNames().contains("@NoReset");
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        if (android){
            test = new TestSetup(noReset, Literals.General.ANDROID.label, scenario);
            test.startAndroidDriverSession();
        }
        if (ios){
            test = new TestSetup(noReset, Literals.General.IOS.label, scenario);
            test.startIosDriverSession();
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
        test.mobile().verifier().selectShareAttributes();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().chooseData();
        test.mobile().verifier().clickNext();
    }


    @And("user selects to be identified using EUDI Wallet")
    public void userSelectsToBeIdentifiedUsingEUDIWallet() {
        test.mobile().verifier().AuthenticationPageIsDisplayed();
        test.mobile().verifier().chooseWallet();
    }


    @And("user views the data and can unselect any of them")
    public void userViewsTheDataAndCanUnselectAnyOfThem() {
        test.mobile().verifier().viewDataPage();
    }


    @And("user presses the share button")
    public void userPressesTheShareButton() {
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
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @And("user clicks load sample data")
    public void userClicksLoadSampleData() {
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
        //manual
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
}

