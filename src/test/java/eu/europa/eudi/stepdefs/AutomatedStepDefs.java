package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;

public class AutomatedStepDefs {

    private final TestSetup test = TestHooks.getTest();

    public String selectiveDisclosure;
    public String issuerType;
    public String credential;
    public String issuanceMethod;


    @Given("user opens Verifier App")
    public void userOpensVerifierApp(){
        test.mobile().wallet().userOpensVerifier();
        test.mobile().verifier().launchSafari();
        test.mobile().verifier().appOpensSuccessfully();
    }

    @When("the user enters their PIN")
    public void theUserEntersTheirPIN() {
        test.mobile().wallet().createAPin();
    }

    @When("the user enters the correct PIN")
    public void theUserEntersTheCorrectPIN(){
        test.mobile().wallet().createAPin();
    }

    @Given("the user is on the Login screen")
    public void theUserIsOnTheLoginScreen() throws InterruptedException {
        test.mobile().wallet().restartApp();
    }

    @Given("the test is being ignored")
    public void theTestIsBeingIgnored() {
        test.mobile().wallet().skippedTest();
    }

    @Then("the user should see the home screen")
    public void theUserShouldSeeTheHomeScreen() {
        test.mobile().wallet().homePageIsDisplayed();
    }

    @Given("the user is on the home screen")
    public void theUserIsOnTheHomeScreen() throws InterruptedException {
        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheHomeScreen();
    }

    @When("the user navigates to the Documents screen")
    public void theUserNavigatesToTheDocumentsScreen() {
        test.mobile().wallet().clickOnDocuments();
    }

    @Then("the Documents screen is displayed")
    public void theDocumentsScreenIsDisplayed() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @Given("the user is on the Documents screen")
    public void theUserIsOnTheDocumentsScreen() throws InterruptedException {
        theUserIsOnTheHomeScreen();
        theUserNavigatesToTheDocumentsScreen();
        theDocumentsScreenIsDisplayed();
    }

    @When("the user selects to add a new document")
    public void theUserSelectsToAddANewDocument() {
        test.mobile().wallet().addDocButton();
    }

    @And("the user selects to add a new document From list")
    public void theUserSelectsToAddANewDocumentFromList() {
        test.mobile().wallet().clickFromList();
    }


    @Given("the user is viewing the predefined list of attestations")
    public void theUserIsViewingThePredefinedListOfAttestations() throws InterruptedException {
        theUserIsOnTheDocumentsScreen();
        theUserSelectsToAddANewDocument();
        theUserSelectsToAddANewDocumentFromList();
    }

    @When("the user selects one attestation to be issued")
    public void theUserSelectsOneAttestationToBeIssued() throws InterruptedException {
        test.mobile().wallet().scrollUntilPID();
        test.mobile().wallet().clickPID();
        test.mobile().issuer().issuePID("PID (MSO Mdoc)");
    }

    @Then("the wallet displays a success screen")
    public void theWalletDisplaysASuccessScreen() {
        test.mobile().issuer().successfullySharedMessage();
    }

    @When("the user closes the success screen")
    public void theUserClosesTheSuccessScreen() {
        test.mobile().wallet().clickDone();
    }

    @Then("the user navigates back to the Home screen")
    public void theUserShouldBeNavigatedBackToTheHomeScreen() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @When("the user chooses to issue a doc with pre-authorization")
    public void theUserChoosesToIssueADocWithPreAuthorization() throws InterruptedException {
        test.mobile().issuer().launchSafari();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickPersonalIdentificationData();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user registers their personal data")
    public void theUserRegistersTheirPersonalData() throws InterruptedException {
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterCountry();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().authorizeIsDisplayed();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @And("a transaction code has been created")
    public void aTransactionCodeHasBeenCreated() {
        test.mobile().issuer().transactionCodeIsDisplayed();
    }

    @When("the user selects to register with the EUDI wallet app")
    public void theUserSelectsToRegisterWithTheEUDIWalletApp() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @When("the request from the issuer is displayed on the wallet app")
    public void theRequestFromTheIssuerIsDisplayedOnTheWalletApp() {
        test.mobile().wallet().detailsArePresented();
    }

    @Then("the user clicks on the ISSUE button")
    public void theUserClicksOnTheISSUEButton() {
        test.mobile().wallet().clickIssue();
    }

    @When("the Wallet application displays a success message")
    public void theWalletApplicationDisplaysASuccessMessage() {
        test.mobile().issuer().successfullySharedMessage();
    }

    @Then("the user clicks on the CONTINUE button")
    public void theUserClicksOnTheCONTINUEButton() {
        test.mobile().wallet().clickDone();
    }

    @And("the doc is displayed in the dashboard screen")
    public void theDocIsDisplayedInTheDashboardScreen() {
        test.mobile().wallet().homePageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @Given("the user visits the Issuer service")
    public void theUserVisitsTheIssuerService() {
        test.mobile().issuer().issuerService();
    }

    @When("the user selects to issue credential")
    public void theUserSelectsToIssueCredential() throws InterruptedException {
        test.mobile().issuer().launchSafari();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user is presented with a URL to initiate the EUDI Wallet on the same device")
    public void theUserIsPresentedWithAURLToInitiateTheEUDIWalletOnTheSameDevice() {
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
    }

    @Given("the user is presented with a URL to initiate the EUDI Wallet")
    public void theUserIsPresentedWithAURLToInitiateTheEUDIWallet() throws InterruptedException {
        theUserVisitsTheIssuerService();
        theUserSelectsToIssueCredential();
        theUserIsPresentedWithAURLToInitiateTheEUDIWalletOnTheSameDevice();
    }

    @When("the user selects the URL")
    public void theUserSelectsTheURL() throws InterruptedException {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @Then("the user is redirected to the Issuer service to present their PID")
    public void theUserIsRedirectedToTheIssuerServiceToPresentTheirPID() {
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @Given("the user is on the issuer service page")
    public void theUserIsOnTheIssuerServicePage() {
        test.mobile().issuer().issuerService();
    }

    @When("the user chooses to issue a credential to the wallet app")
    public void theUserChoosesToIssueACredentialToTheWalletApp() throws InterruptedException {
        test.mobile().issuer().launchSafari();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user is redirected to the wallet app")
    public void theUserIsRedirectedToTheWalletApp() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @Then("the user is redirected to the issuer for authentication and consent")
    public void theUserIsRedirectedToTheIssuerForAuthenticationAndConsent() {
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @Given("the user is on the Home page")
    public void theUserIsOnTheHomePage() {
        test.mobile().wallet().dashboardPageIsDisplayedDeferred();
    }

    @When("the user decides not to proceed")
    public void theUserDecidesNotToProceed() {
        test.mobile().verifier().insertPIN2();
    }

    @And("EUDI Wallet should return the user to the main page")
    public void eudiWalletShouldReturnTheUserToTheMainPage() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @Then("the EUDI Wallet enables the user to share the document or close the process")
    public void theEUDIWalletEnablesTheUserToShareTheDocumentOrCloseTheProcess() {
        test.mobile().wallet().clickDone();
    }

    @When("the EUDI Wallet displays the presentation request for PID")
    public void theEUDIWalletDisplaysThePresentationRequestForPID() {
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().qrCodeIsDisplayed();
    }

    @Then("the user is prompted to consent by selecting the Share button")
    public void theUserIsPromptedToConsentBySelectingTheShareButton() {
        test.mobile().verifier().viewDataPage();
    }

    @When("the user selects the Share button")
    public void theUserSelectsTheShareButton() {
        test.mobile().wallet().clickShareButton();
    }

    @When("the user enters their six-digit PIN correctly")
    public void theUserEntersTheirSixDigitPINCorrectly() {
        test.mobile().wallet().createAPin();
    }

    @Then("a success message is displayed for the successful presentation of the PID")
    public void aSuccessMessageIsDisplayedForTheSuccessfulPresentationOfThePID() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @When("the user clicks the Continue button")
    public void theUserClicksTheContinueButton() throws InterruptedException {
        test.mobile().wallet().clickDone();
        test.mobile().wallet().clickSubmit();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("the user views a success message for issuing the document")
    public void theUserViewsASuccessMessageForIssuingTheDocument() {
        test.mobile().wallet().successMessageForDrivingIsDisplayed();
        test.mobile().wallet().clickDone();
    }

    @And("the user views the document on the dashboard which issued based on the PID")
    public void theUserViewsTheDocumentOnTheDashboardWhichIssuedBasedOnThePID() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
    }

    @When("the user authenticates and consents the issuance")
    public void theUserAuthenticatesAndConsentsTheIssuance() throws InterruptedException {
        test.mobile().issuer().clickCountrySelection();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("the dashboard appears with the document grayed out and in a pending state")
    public void theDashboardAppearsWithTheDocumentGrayedOutAndInAPendingState() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
    }


    @Given("the user visits the issuer service on the same device")
    public void theUserVisitsTheIssuerServiceOnTheSameDevice() {
        test.mobile().issuer().issuerService();
    }

    @When("the user requests the issuance of an attestation type")
    public void theUserRequestsTheIssuanceOfAnAttestationType() throws InterruptedException {
        test.mobile().issuer().launchSafari();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickPersonalIdentificationData();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the issuer service redirects the user to the Wallet")
    public void theIssuerServiceRedirectsTheUserToTheWallet() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @Given("the EUDI Wallet opens")
    public void theEUDIWalletOpens() throws InterruptedException {
        theUserVisitsTheIssuerServiceOnTheSameDevice();
        theUserRequestsTheIssuanceOfAnAttestationType();
        theIssuerServiceRedirectsTheUserToTheWallet();
    }

    @And("the user authenticates using a six-digit PIN or Biometrics")
    public void theUserAuthenticatesUsingASixDigitPINOrBiometrics() throws InterruptedException {
        test.mobile().verifier().viewDataPage();
        test.mobile().wallet().clickShareButton();
        test.mobile().wallet().createAPin();
    }


    @Given("the user visits the Relying Party service on their mobile device")
    public void theUserVisitsTheRelyingPartyServiceOnTheirMobileDevice() throws MalformedURLException {
        userOpensVerifierApp();
    }

    @Then("the Relying Party service redirects the user to the EUDI Wallet")
    public void theRelyingPartyServiceRedirectsTheUserToTheEUDIWallet() {
        test.mobile().verifier().chooseWalletPageIsDisplayed();
        test.mobile().verifier().chooseWallet();
        test.mobile().verifier().insertPIN2();
    }

    @Then("the authentication is successful")
    public void theAuthenticationIsSuccessful() {
        test.mobile().wallet().authenticationSuccessfully();
        test.mobile().wallet().clickDone();
    }


    @Then("the EUDI Wallet informs the user that the Relying Party requests an attestation")
    public void theEUDIWalletInformsTheUserThatTheRelyingPartyRequestsAnAttestation() {
        test.mobile().verifier().viewDataPage();
    }

    @Then("the EUDI Wallet displays a confirmation message indicating the outcome")
    public void theEUDIWalletDisplaysAConfirmationMessageIndicatingTheOutcome() {
        test.mobile().wallet().authenticationSuccessfully();
        test.mobile().wallet().clickDone();
    }

    @Then("the Relying Party service receives the attestation")
    public void theRelyingPartyServiceReceivesTheAttestation() {
        test.mobile().verifier().walletResponded();
    }

    @Then("the Wallet uses an attestation not previously presented to any Relying Party")
    public void theWalletUsesAnAttestationNotPreviouslyPresentedToAnyRelyingParty() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
    }

    @When("the Wallet receives the attestation from the issuer service")
    public void theWalletReceivesTheAttestationFromTheIssuerService() throws InterruptedException {
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterCountry();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
        test.mobile().issuer().authorizeIsDisplayed();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
        test.mobile().wallet().clickClose();
    }

    @Then("issuer service issues multiple attestations")
    public void issuerServiceIssuesMultipleAttestations() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().nationalIdIsDisplayed();
    }

    @When("the user clicks on the X button")
    public void theUserClicksOnTheXButton() {
        test.mobile().wallet().clickBackButton();
    }

    @Then("the document appears on the dashboard screen")
    public void theDocumentAppearsOnTheDashboardScreen() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @Then("verifier verifies the credential successfully with {}")
    public void theVerifierVerifiesTheCredentialSuccessfullyWith(String status) {
       test.mobile().verifier().verifyCredential(status);
    }

    @Given("the user initiates a {} issuance using the {}")
    public void theUserInitiatesACredentialIssuanceUsingThe(String credential, String issuerType) {
        this.issuerType = issuerType;
        this.credential = credential;
      test.mobile().wallet().initiateCredential(credential, issuerType);
    }

    @And("the issuance method is {}")
    public void theIssuanceMethodIs(String issuanceMethod) throws InterruptedException {
        test.mobile().issuer().issuanceMethodIs(issuanceMethod, this.credential, this.issuerType);
    }

    @And("the issuance is performed on a {} for the {} and {}")
    public void theIssuanceIsPerformedOnA(String issueScenario, String credential, String issuanceMethod) throws InterruptedException {
       test.mobile().issuer().performIssuance(issueScenario, credential, issuanceMethod, this.issuerType);
    }

    @When("the issuance flow is completed")
    public void theIssuanceFlowIsCompleted() {
      test.mobile().issuer().completedIsuuanceFlow(this.issuerType, this.credential, this.issuanceMethod);
    }

    @Then("the credential is stored in the Wallet")
    public void theCredentialIsStoredInTheWallet() {
      test.mobile().wallet().credentialStoredInWallet(this.credential, this.issuerType);
    }

    @When("the user presents the credential to the {}")
    public void theUserPresentsTheCredentialToThe(String verifierType) {
       test.mobile().wallet().presentCredential(verifierType);
    }

    @And("the presentation is performed on a {} for the {}")
    public void thePresentationIsPerformedOnA(String presentationScenario, String credential) throws InterruptedException {
        test.mobile().wallet().performPresentation(presentationScenario, credential, this.selectiveDisclosure, this.issuerType);
    }


    @And("the user shares {}")
    public void theUserShares(String selectiveDisclosure) {
        this.selectiveDisclosure = selectiveDisclosure;
    }

    @Then("the verifier verifies the credential successfully with {} for {}")
    public void theVerifierVerifiesTheCredentialSuccessfully(String presentationScenario, String selectiveDisclosure) {
      test.mobile().verifier().verifierVerifyCredential(presentationScenario, selectiveDisclosure, this.issuerType, this.credential);
    }

    @When("the user selects to issue a document using pre-authorization")
    public void theUserSelectsToIssueADocumentUsingPreAuthorization() throws InterruptedException {
        test.mobile().issuer().scrollUntilPidIssuer();
        test.mobile().issuer().selectPidPythonIssuer();
        test.mobile().issuer().scrollUntilFindSubmitIssuer();
        test.mobile().issuer().clickPreAuthorizationCode();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user provides their personal information")
    public void theUserProvidesTheirPersonalInformation() throws InterruptedException {
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().chooseBirthDate();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().scrollUntilCountryCodePid();
        test.mobile().issuer().enterCountryCode();
        test.mobile().issuer().scrollUntilCountry();
        test.mobile().issuer().enterCountry();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickConfirm();
        test.mobile().issuer().authorizeIsDisplayed();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @And("a transaction code is generated")
    public void aTransactionCodeIsGenerated() {
        test.mobile().issuer().qrCodeIsDisplayed();
//        test.mobile().issuer().transactionCodeIsDisplayed();
       String code = test.mobile().issuer().getTransactionCode();
       test.setTransactionCode(code); // <-- store it for later steps

        System.out.println("Stored transaction code: " + code);
    }

    @When("the user chooses to register through the EUDI wallet app")
    public void theUserChoosesToRegisterThroughTheEUDIWalletApp() {
        test.mobile().issuer().clickUseEudiwPid();
    }

    @Then("the user is navigated to the EUDI wallet application")
    public void theUserIsNavigatedToTheEUDIWalletApplication() {
        //nothing on automation
    }

    @And("the user provides the PIN")
    public void theUserProvidesThePIN() {
        test.mobile().wallet().createAPin();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickAddMyDigitalID();
    }

    @When("the issuer request is shown in the wallet app")
    public void theIssuerRequestIsShownInTheWalletApp() throws InterruptedException {
        test.mobile().issuer().viewDataPage();
    }

    @Then("the user selects the ISSUE button for {}")
    public void theUserSelectsTheISSUEButton(String issuerType) throws InterruptedException {
        this.issuerType = issuerType;
        if ("Python".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().clickAddButton();
        }
    }

    @Then("the user selects ISSUE button")
    public void theUserSelectsISSUEButton() throws InterruptedException {
            test.mobile().wallet().clickAddButton();
    }

    @When("the Wallet app prompts for the transaction code")
    public void theWalletAppPromptsForTheTransactionCode() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobile().issuer().selectCountryOfOrigin();
            test.mobile().issuer().clickFormEu();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().formIsDisplayed();
            test.mobile().issuer().chooseBirthDate();
            test.mobile().issuer().enterFamilyName();
            test.mobile().issuer().enterGivenName();
            test.mobile().issuer().scrollUntilCountryCodePid();
            test.mobile().issuer().enterCountryCode();
            test.mobile().issuer().scrollUntilCountry();
            test.mobile().issuer().enterCountry();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickConfirm();
            test.mobile().issuer().authorizeIsDisplayed();
            test.mobile().issuer().scrollUntilAuthorize();
            test.mobile().issuer().clickAuthorize();
        }else {
            test.mobile().wallet().requestTransactionCode();
        }
    }

    @Then("the user enters the transaction code received from the Issuer")
    public void theUserEntersTheTransactionCodeReceivedFromTheIssuer() {
        if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
            test.mobile().wallet().insertTransactionCode();
        }
    }

    @When("the Wallet application shows a successful issuance message")
    public void theWalletApplicationShowsASuccessfulIssuanceMessage() {
        test.mobile().wallet().successMessageIsDisplayedForIssuer();
    }

    @Then("the user selects the CONTINUE button")
    public void theUserSelectsTheCONTINUEButton() {
        test.mobile().wallet().clickClose();
    }

    @And("the document appears on the documents screen for {}")
    public void theDocumentAppearsOnTheDocumentsScreen(String issuerType) {
        test.mobile().wallet().pidDeferredIsDisplayed(issuerType);
    }

    @When("the user chooses to deliver a credential to the wallet")
    public void theUserChoosesToDeliverACredentialToTheWallet() throws InterruptedException {
        test.mobile().issuer().scrollUntilPidIssuer();
        test.mobile().issuer().selectPidPythonIssuer();
        test.mobile().issuer().scrollUntilFindSubmitIssuer();
        test.mobile().issuer().clickSubmitButton();
        test.mobile().issuer().clickUseEudiwPid();
    }

    @Then("the EUDI Wallet application is opened")
    public void theEUDIWalletApplicationIsOpened() throws InterruptedException {
        test.mobile().wallet().createAPin();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickAddMyDigitalID();
        test.mobile().issuer().viewDataPage();
    }

    @When("the user chooses to deliver a deferred credential to the wallet")
    public void theUserChoosesToDeliverADeferredCredentialToTheWallet() throws InterruptedException {
        test.mobile().issuer().scrollUntilPidIssuer();
        test.mobile().issuer().selectPIDDeferred();
        test.mobile().issuer().scrollUntilFindSubmitIssuer();
        test.mobile().issuer().clickSubmitButton();
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiwPidDeferred();
    }

    @And("the user is redirected to the issuer service for authentication and authorization for {}")
    public void theUserIsRedirectedToTheIssuerServiceForAuthenticationAndAuthorization(String issuerType) throws InterruptedException {
        this.issuerType = issuerType;
        if ("Python".equalsIgnoreCase(this.issuerType)) {
            test.mobile().issuer().selectCountryOfOrigin();
            test.mobile().issuer().clickFormEu();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickSubmit();
            test.mobile().issuer().formIsDisplayed();
            test.mobile().issuer().chooseBirthDate();
            test.mobile().issuer().enterFamilyName();
            test.mobile().issuer().enterGivenName();
            test.mobile().issuer().scrollUntilCountryCodePid();
            test.mobile().issuer().enterCountryCode();
            test.mobile().issuer().scrollUntilCountry();
            test.mobile().issuer().enterCountry();
            test.mobile().issuer().scrollUntilFindSubmit();
            test.mobile().issuer().clickConfirm();
        }
    }

    @When("the user completes authentication and confirms the issuance for {}")
    public void theUserCompletesAuthenticationAndConfirmsTheIssuance(String issuerType) throws InterruptedException {
        this.issuerType = issuerType;
        if ("Python".equalsIgnoreCase(this.issuerType)) {
            test.mobile().issuer().authorizeIsDisplayed();
            test.mobile().issuer().scrollUntilAuthorize();
            test.mobile().issuer().clickAuthorize();
        }
    }

    @And("a notification indicates that the credential request is being processed")
    public void aNotificationIndicatesThatTheCredentialRequestIsBeingProcessed() {
        test.mobile().wallet().notificationOnWalletForDeferred();
    }

    @When("the user dismisses the notification by pressing OK")
    public void theUserDismissesTheNotificationByPressingOK() {
        test.mobile().wallet().clickingOkButton();
    }

    @And("the document is displayed as unavailable with a pending status")
    public void theDocumentIsDisplayedAsUnavailableWithAPendingStatus() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().pendingStatus();
    }

    @When("the issuer provides the requested credential to the wallet")
    public void theIssuerProvidesTheRequestedCredentialToTheWallet() {
        //nothing for automation
    }

    @Then("the user receives a confirmation message indicating that the document has been issued")
    public void theUserReceivesAConfirmationMessageIndicatingThatTheDocumentHasBeenIssued() {
        test.mobile().wallet().popUpConfirmation();
    }

    @When("the user chooses to inspect the document details")
    public void theUserChoosesToInspectTheDocumentDetails() {
        test.mobile().wallet().inspectDocument();
    }

    @Then("the issued credential information is displayed for {}")
    public void theIssuedCredentialInformationIsDisplayed(String issuerType) {
        test.mobile().wallet().pidDeferredIsDisplayed(issuerType);
    }

    @And("the user presses the X button to close the document")
    public void theUserPressesTheXButtonToCloseTheDocument() {
        test.mobile().wallet().clickBackButton();
    }

    @Then("the document details are no longer displayed")
    public void theDocumentDetailsAreNoLongerDisplayed() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @Given("the user is accessing {} service")
    public void theUserIsAccessingService(String issuerType ) {
        test.mobile().issuer().accessingIssuerType(issuerType);
    }

    @When("the user chooses to deliver a deferred credential to the wallet with {}")
    public void theUserChoosesToDeliverADeferredCredentialToTheWalletWith(String issuerType) throws InterruptedException {
        test.mobile().issuer().deliverDeferredToWallet(issuerType);
    }

    @And("the issuance information is displayed to the user for {}")
    public void theIssuanceInformationIsDisplayedToTheUserFor(String issuerType) throws InterruptedException {
        test.mobile().issuer().issuanceInformation(issuerType);
    }

    @Given("the user open the issuer service")
    public void theUserOpenTheIssuerService() {
        test.mobile().issuer().issuerService();
        test.mobile().issuer().clickissuerService();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
    }

    @Then("the document appears on the documents screen")
    public void theDocumentAppearsOnTheDocumentsScreen() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().pidIsDisplayed();
    }

    @Then("the issuer service generates multiple attestations")
    public void theIssuerServiceGeneratesMultipleAttestations() {
        //nothing for automation
    }

    @And("the Wallet shows a counter indicating the total number of attestations issued")
    public void theWalletShowsACounterIndicatingTheTotalNumberOfAttestationsIssued() {
        test.mobile().wallet().counterIsDisplayed();
    }

    @Then("the issuance process continues with the issuer's maximum batch size")
    public void theIssuanceProcessContinuesWithTheIssuerSMaximumBatchSize() {
        //nothing for automation
    }

    @And("the Wallet saves the attestations based on the issuer-defined batch size")
    public void theWalletSavesTheAttestationsBasedOnTheIssuerDefinedBatchSize() {
        //nothing for automation
    }

    @When("the issuer advertised maximum batch size is below the Wallet internal minimum threshold")
    public void theIssuerAdvertisedMaximumBatchSizeIsBelowTheWalletInternalMinimumThreshold() {
        //nothing for automation
    }

    @And("the user accesses the Relying Party service through their mobile device using {}")
    public void theUserAccessesTheRelyingPartyServiceThroughTheirMobileDevice(String verifierType) {
        test.mobile().wallet().presentCredential(verifierType);
    }

    @When("the user chooses to present an attestation type")
    public void theUserChoosesToPresentAnAttestationType() {
        test.mobile().wallet().presentAttestation();
    }

    @Then("the Relying Party service navigates the user to the EUDI Wallet")
    public void theRelyingPartyServiceNavigatesTheUserToTheEUDIWallet() {
        test.mobile().wallet().navigateUserToWallet();
    }

    @When("the user verifies their identity using a six-digit PIN or Biometrics")
    public void theUserVerifiesTheirIdentityUsingASixDigitPINOrBiometrics() {
        test.mobile().wallet().createAPin();
    }

    @Given("the user has been successfully authenticated in the EUDI Wallet")
    public void theUserHasBeenSuccessfullyAuthenticatedInTheEUDIWallet() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @Then("the authentication is completed successfully")
    public void theAuthenticationIsCompletedSuccessfully() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @And("the user is allowed to attempt the authentication again")
    public void theUserIsAllowedToAttemptTheAuthenticationAgain() {
        //NOTHING FOR AUTOMATION
    }

    @When("the user is unable to authenticate using a six-digit PIN or Biometrics")
    public void theUserIsUnableToAuthenticateUsingASixDigitPINOrBiometrics() {
        if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
            test.mobile().wallet().pinFieldIsDisplayed();
            test.mobile().verifier().insertPIN();
        }
        test.mobile().verifier().viewDataPage();

        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {
                test.mobile().wallet().clickPIDFromKotlin();
            }
        } else {
            test.mobile().wallet().clickToViewDetails();
        }

        if ("Python".equalsIgnoreCase(this.issuerType)) {
            if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {
                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                        "testdata/PID/pre_final_shared_data_on_wallet.yml");

            }
        }
        test.mobile().wallet().clickShareButton();
        test.mobile().wallet().pinFieldIsDisplayed();
        test.mobile().wallet().createAFalsePin();

    }

    @Then("the Wallet displays an authentication error")
    public void theWalletDisplaysAnAuthenticationError() {
        test.mobile().wallet().authenticationError();
    }

    @Then("the EUDI Wallet notifies the user that the Relying Party is requesting an attestation with {}")
    public void theEUDIWalletNotifiesTheUserThatTheRelyingPartyIsRequestingAnAttestation(String presentationScenario) {
        test.mobile().verifier().verifierVerifyCredential(presentationScenario, selectiveDisclosure, this.issuerType, this.credential);
    }
}
