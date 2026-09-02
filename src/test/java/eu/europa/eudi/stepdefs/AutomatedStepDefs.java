package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
        test.mobile().wallet().dashboardPageIsDisplayed();
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

    @Given("the user is accessing the issuer service")
    public void theUserIsAccessingTheIssuerService() {
        test.mobile().issuer().issuerService();
        test.mobile().issuer().clickissuerService();
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
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
//        test.mobile().issuer().transactionCodeIsDisplayed();
       String code = test.mobile().issuer().getTransactionCode();
       test.setTransactionCode(code); // <-- store it for later steps

        System.out.println("Stored transaction code: " + code);
    }

    @Given("the transaction code has been generated")
    public void theTransactionCodeHasBeenGenerated() throws InterruptedException {
        //nothing for automation
        theUserIsAccessingTheIssuerService();
        theUserSelectsToIssueADocumentUsingPreAuthorization();
        theUserProvidesTheirPersonalInformation();
        aTransactionCodeIsGenerated();
    }

    @When("the user chooses to register through the EUDI wallet app")
    public void theUserChoosesToRegisterThroughTheEUDIWalletApp() {
        test.mobile().issuer().clickUseEudiwPid();
    }

    @Then("the user is navigated to the EUDI wallet application")
    public void theUserIsNavigatedToTheEUDIWalletApplication() {
        test.mobile().wallet().checkIfPageIsTrue();
    }

    @And("the user provides the PIN")
    public void theUserProvidesThePIN() {
        test.mobile().wallet().createAPin();
        test.mobile().wallet().renterThePin();
        test.mobile().wallet().successMessageOfSetUpPin();
        test.mobile().wallet().clickAddMyDigitalID();
    }

    @Given("the user has provided the PIN")
    public void theUserHasProvidedThePIN() throws InterruptedException {
        //nothing for automation
        theTransactionCodeHasBeenGenerated();
        theUserChoosesToRegisterThroughTheEUDIWalletApp();
        theUserIsNavigatedToTheEUDIWalletApplication();
        theUserProvidesThePIN();
    }

    @When("the issuer request is shown in the wallet app")
    public void theIssuerRequestIsShownInTheWalletApp() throws InterruptedException {
        test.mobile().issuer().viewDataPage();
    }

    @Then("the user selects the ISSUE button")
    public void theUserSelectsTheISSUEButton() throws InterruptedException {
        test.mobile().wallet().clickAddButton();
    }

    @Given("the user has selected the ISSUE button")
    public void theUserHasSelectedTheISSUEButton() throws InterruptedException {
        //nothing for automation
        theUserHasProvidedThePIN();
        theIssuerRequestIsShownInTheWalletApp();
        theUserSelectsTheISSUEButton();
    }

    @When("the Wallet app prompts for the transaction code")
    public void theWalletAppPromptsForTheTransactionCode() {
        test.mobile().wallet().requestTransactionCode();
    }

    @Then("the user enters the transaction code received from the Issuer")
    public void theUserEntersTheTransactionCodeReceivedFromTheIssuer() {
        test.mobile().wallet().insertTransactionCode();
    }

    @Given("the user has entered the transaction code received from the Issuer")
    public void theUserHasEnteredTheTransactionCodeReceivedFromTheIssuer() throws InterruptedException {
        //nothing for automation
        theUserHasSelectedTheISSUEButton();
        theWalletAppPromptsForTheTransactionCode();
        theUserEntersTheTransactionCodeReceivedFromTheIssuer();
    }

    @When("the Wallet application shows a successful issuance message")
    public void theWalletApplicationShowsASuccessfulIssuanceMessage() {
        test.mobile().wallet().successMessageIsDisplayedForIssuer();
    }

    @Then("the user selects the CONTINUE button")
    public void theUserSelectsTheCONTINUEButton() {
        test.mobile().wallet().clickClose();
    }

    @And("the document appears on the documents screen")
    public void theDocumentAppearsOnTheDocumentsScreen() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().pidMdocIsDisplayed();
    }
}
