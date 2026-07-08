package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;
import static org.junit.Assert.fail;

public class AutomatedStepDefs {

    private final TestSetup test = TestHooks.getTest();

    private String issuerType;
    private String credential;
    private String issuanceMethod;
    private String selectiveDisclosure;

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
        test.mobile().issuer().issuePID();
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
    public void theUserIsRedirectedToTheWalletApp() throws InterruptedException {
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
    public void theEUDIWalletDisplaysThePresentationRequestForPID() throws InterruptedException {
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
    public void theUserEntersTheirSixDigitPINCorrectly() throws InterruptedException {
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
    public void theIssuerServiceRedirectsTheUserToTheWallet() throws InterruptedException {
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

    @When("the user selects the option to present an attestation type")
    public void theUserSelectsTheOptionToPresentAnAttestationType() {
        test.mobile().verifier().launchSafari();
        test.mobile().verifier().appOpensSuccessfully();
        test.mobile().verifier().selectAllAttributes();
        test.mobile().verifier().scrollUntilNext();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().clickNext();
        test.mobile().verifier().assertAndClickNext();
    }

    @Then("the Relying Party service redirects the user to the EUDI Wallet")
    public void theRelyingPartyServiceRedirectsTheUserToTheEUDIWallet() {
        test.mobile().verifier().chooseWalletPageIsDisplayed();
        test.mobile().verifier().chooseWallet();
        test.mobile().verifier().insertPIN2();
    }

    @Given("the EUDI Wallet is opened")
    public void theEUDIWalletIsOpened() throws MalformedURLException {
        theUserVisitsTheRelyingPartyServiceOnTheirMobileDevice();
        theUserSelectsTheOptionToPresentAnAttestationType();
        theRelyingPartyServiceRedirectsTheUserToTheEUDIWallet();
    }

    @Then("the authentication is successful")
    public void theAuthenticationIsSuccessful() {
        test.mobile().wallet().authenticationSuccessfully();
        test.mobile().wallet().clickDone();
    }

    @Given("the user is successfully authenticated in the EUDI Wallet")
    public void theUserIsSuccessfullyAuthenticatedInTheEUDIWallet() throws MalformedURLException, InterruptedException {
        theEUDIWalletIsOpened();
        theUserAuthenticatesUsingASixDigitPINOrBiometrics();
        theAuthenticationIsSuccessful();
    }

    @Then("the EUDI Wallet informs the user that the Relying Party requests an attestation")
    public void theEUDIWalletInformsTheUserThatTheRelyingPartyRequestsAnAttestation() {
        test.mobile().verifier().viewDataPage();
    }

    @Given("the EUDI Wallet requests the user to consent")
    public void theEUDIWalletRequestsTheUserToConsent() throws MalformedURLException, InterruptedException {
        theEUDIWalletIsOpened();
        theUserAuthenticatesUsingASixDigitPINOrBiometrics();
        theAuthenticationIsSuccessful();
    }

    @Given("the user consents to the attestation presentation")
    public void theUserConsentsToTheAttestationPresentation() throws MalformedURLException, InterruptedException {
        theEUDIWalletIsOpened();
        theUserAuthenticatesUsingASixDigitPINOrBiometrics();
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

    @Given("Method A is configured for the attestation type")
    public void methodAIsConfiguredForTheAttestationType() throws MalformedURLException, InterruptedException {
        theUserConsentsToTheAttestationPresentation();
        theEUDIWalletDisplaysAConfirmationMessageIndicatingTheOutcome();
        theRelyingPartyServiceReceivesTheAttestation();
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
        if ("failed".equalsIgnoreCase(status)) {
            fail("Credential verification failed as per test input.");
        }
        if ("passed".equalsIgnoreCase(status)) {
            System.out.println("Credential verification passed as per test input.");
        }
    }

    @Given("the user initiates a {} issuance using the {}")
    public void theUserInitiatesACredentialIssuanceUsingThe(String credential, String issuerType) throws InterruptedException, MalformedURLException {
        this.issuerType = issuerType;
        this.credential = credential;
        test.mobile().wallet().launchApp();
        if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {
            switch (issuerType.toLowerCase()) {
                case "kotlin", "python":
                    test.mobile().wallet().launchApp();
                    test.mobile().wallet().checkIfPageIsTrue();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().renterThePin();
                    test.mobile().wallet().successMessageOfSetUpPin();
                    test.mobile().wallet().clickAddMyDigitalID();
                    break;
            }
        } else {
            switch (issuerType.toLowerCase()) {
                case "kotlin":
                    test.mobile().wallet().launchApp();
                    test.mobile().wallet().checkIfPageIsTrue();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().renterThePin();
                    test.mobile().wallet().successMessageOfSetUpPin();
                    test.mobile().wallet().clickAddMyDigitalID();
                    test.mobile().issuer().kotlinIssuerService();
                    break;
                case "python":
                    test.mobile().wallet().launchApp();
                    test.mobile().wallet().checkIfPageIsTrue();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().renterThePin();
                    test.mobile().wallet().successMessageOfSetUpPin();
                    test.mobile().wallet().clickAddMyDigitalID();
            }
        }
    }

    @And("the issuance method is {}")
    public void theIssuanceMethodIs(String issuanceMethod) throws InterruptedException {
        this.issuanceMethod = issuanceMethod;
        switch (issuanceMethod.toLowerCase()) {
            case "from list":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertPidFromListKotlin();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertMdlFromList();
                    }
                } else {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertPidFromList();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertMdlFromList();
                    }
                }
                break;
            case "credential offer":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                    test.mobile().issuer().kotlinIssuerService();
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().selectPIDKotlin();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().selectMDLKotlin();
                    }
                    test.mobile().issuer().scrollUntilGenerate();
                    test.mobile().issuer().clickGenerate();
                } else {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                    }
                }
                break;
        }
    }

    @And("the issuance is performed on a {} for the {}")
    public void theIssuanceIsPerformedOnA(String issueScenario, String credential) throws InterruptedException {
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            switch (issueScenario.toLowerCase()) {
                case "same device":
                    if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                        test.mobile().issuer().issueCredentialsPageIsDisplayed();
                        test.mobile().issuer().clickWalletLink();
                        test.mobile().wallet().viewDataPage();
                        test.mobile().wallet().clickAddButton();
                        test.mobile().issuer().signInUser();
                        test.mobile().issuer().fillLoginForm();
                    }
                    break;
                case "cross device":
                    test.mobile().issuer().qrCodeIsDisplayedKotlin();
                    test.mobile().verifier().captureScreen();
                    theUserIsOnTheLoginScreen();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickOnDocuments();
                    test.mobile().wallet().clickToAddDocument();
                    test.mobile().wallet().clickQROption();
                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }
                    test.mobile().wallet().theQRScannerIsActivatedForIssuance();
                    test.mobile().wallet().mockQRInject(test.mobile().verifier().getCapturedScreenFile());
                    test.mobile().wallet().viewDataPage();
                    test.mobile().wallet().clickAddButton();
                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
                    }
                    test.mobile().issuer().signInUser();
                    test.mobile().issuer().fillLoginForm();
                    break;
            }
        } else {
            switch (issueScenario.toLowerCase()) {
                case "same device":
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                            test.mobile().issuer().issuerService();
                            test.mobile().issuer().requestCredentialsPageIsDisplayed();
                            test.mobile().issuer().scrollUntilPidIssuer();
                            test.mobile().issuer().selectPidPythonIssuer();
                            test.mobile().issuer().scrollUntilFindSubmitIssuer();
                            test.mobile().issuer().clickSubmitButton();
                            test.mobile().issuer().clickUseEudiwPid();
                            test.mobile().wallet().clickAddButton();
                            test.mobile().issuer().issuePID();
                        }
                    } else {
                        if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                            test.mobile().issuer().issuerService();
                            test.mobile().issuer().requestCredentialsPageIsDisplayed();
                            test.mobile().issuer().scrollUntilMdlIssuer();
                            test.mobile().issuer().selectMdlPythonIssuer();
                            test.mobile().issuer().scrollUntilFindSubmitIssuer();
                            test.mobile().issuer().clickSubmitButton();
                            test.mobile().issuer().clickUseEudiw();
                            test.mobile().wallet().clickAddButton();
                            test.mobile().issuer().issueMDL();

                        }
                    }
                    break;
                case "cross device":
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                        test.mobile().issuer().requestCredentialsPageIsDisplayed();
                        test.mobile().issuer().scrollUntilPidIssuer();
                        test.mobile().issuer().selectPidPythonIssuer();
                        test.mobile().issuer().scrollUntilFindSubmitIssuer();
                        test.mobile().issuer().clickSubmitButton();
                        test.mobile().issuer().qrCodeIsDisplayed();
                        test.mobile().verifier().captureScreen();
                        theUserIsOnTheLoginScreen();
                        test.mobile().wallet().createAPin();
                        test.mobile().wallet().clickOnDocuments();
                        test.mobile().wallet().clickToAddDocument();
                        test.mobile().wallet().clickQROption();
                        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                            if (test.mobile().wallet().isQrVisible()) {
                                test.mobile().wallet().onlyThisTimeQR();
                            }
                        }
                        test.mobile().wallet().theQRScannerIsActivatedForIssuance();
                        test.mobile().wallet().mockQRInject(test.mobile().verifier().getCapturedScreenFile());
                        test.mobile().wallet().viewDataPage();
                        test.mobile().wallet().clickAddButton();
                        test.mobile().issuer().issuePID();
                    } else {
                        test.mobile().issuer().issuerService();
                        test.mobile().issuer().requestCredentialsPageIsDisplayed();
                        test.mobile().issuer().scrollUntilMdlIssuer();
                        test.mobile().issuer().selectMdlPythonIssuer();
                        test.mobile().issuer().scrollUntilFindSubmitIssuer();
                        test.mobile().issuer().clickSubmitButton();
                        test.mobile().issuer().qrCodeIsDisplayed();
                        test.mobile().verifier().captureScreen();
                        theUserIsOnTheLoginScreen();
                        test.mobile().wallet().createAPin();
                        test.mobile().wallet().clickOnDocuments();
                        test.mobile().wallet().clickToAddDocument();
                        test.mobile().wallet().clickQROption();
                        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                            if (test.mobile().wallet().isQrVisible()) {
                                test.mobile().wallet().onlyThisTimeQR();
                            }
                        }
                        test.mobile().wallet().theQRScannerIsActivatedForIssuance();
                        test.mobile().wallet().mockQRInject(test.mobile().verifier().getCapturedScreenFile());
                        test.mobile().wallet().viewDataPage();
                        test.mobile().wallet().clickAddButton();
                        test.mobile().issuer().issueMDL();
                        break;
                    }
            }
        }
    }

    @When("the issuance flow is completed")
    public void theIssuanceFlowIsCompleted() throws InterruptedException {
        if ("Python".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().successMessageIsDisplayedForIssuer();
        }
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().successMessageIsDisplayedForIssuer();
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                test.mobile().wallet().clickExpandVerification();
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUntilPlaceOfBirth();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_wallet.yml");
                } else {
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_wallet.yml");
                }
            } else {
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().clickExpandVerificationMSODocIOS();
                    test.mobile().wallet().clickExpandNationalityIOS();
                    test.mobile().wallet().clickExpandPlaceOfBirthIOS();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/ios_kotlin_data_on_wallet.yml");
                } else {
                    test.mobile().wallet().clickExpandVerification();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/ios_kotlin_data_on_wallet.yml");
                }
            }
        }
    }

    @Then("the credential is stored in the Wallet")
    public void theCredentialIsStoredInTheWallet() {
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickClose();
                test.mobile().wallet().clickOnDocuments();
                test.mobile().wallet().secondPIDKotlinIsDisplayed();
            } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickClose();
                test.mobile().wallet().clickOnDocuments();
                test.mobile().wallet().mdlIsDisplayedKotlin();
            }
        } else {
            test.mobile().wallet().clickExpandVerification();
            if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickToViewDetails();
            }else{
                test.mobile().wallet().clickToViewDetails();
                test.mobile().wallet().clickToViewDetails();
            }
            test.mobile().wallet().clickClose();
        }
    }

    @When("the user presents the credential to the {}")
    public void theUserPresentsTheCredentialToThe(String verifierType) throws MalformedURLException {
        switch (verifierType.toLowerCase()) {
            case "web verifier":
                test.mobile().wallet().userOpensVerifier();
                break;
        }
    }

    @And("the presentation is performed on a {} for the {}")
    public void thePresentationIsPerformedOnA(String presentationScenario, String credential) throws InterruptedException {

        if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {

            switch (presentationScenario.toLowerCase()) {

                case "same device":

                    switch (this.selectiveDisclosure.toLowerCase()) {

                        case "specific attributes":

                            test.mobile().verifier().launchSafari();
                            test.mobile().wallet().rotateScreen();
                            test.mobile().verifier().appOpensSuccessfully();
                            test.mobile().verifier().selectSpecificAttributesOnVerifier();
                            test.mobile().verifier().scrollUntilNext();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().selectAttributes();
                            test.mobile().verifier().clickSpecificAttributes();
                            test.mobile().verifier().clickSelect();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().scrollUntilSumbit();
                            test.mobile().verifier().clickSubmit();
                            break;

                        case "all attributes":
                            test.mobile().verifier().launchSafari();
                            test.mobile().verifier().appOpensSuccessfully();
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().verifier().appOpensSuccessfully();
                                test.mobile().verifier().selectAllAttributes();
                                test.mobile().verifier().scrollUntilNext();
                                test.mobile().verifier().clickNext();
                            } else {
                                test.mobile().verifier().selectSpecificAttributesOnVerifier();
                                test.mobile().verifier().scrollUntilNext();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().selectAttributes();
                                test.mobile().verifier().selectTheMandatoryAttributes();
                                test.mobile().verifier().clickSelect();
                            }

                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().scrollUntilSumbit();
                            test.mobile().verifier().clickSubmit();
                            break;

                    }

                    test.mobile().verifier().chooseWallet();
                    test.mobile().verifier().viewDataPage();

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {

                        test.mobile().wallet().clickPIDFromKotlin();
                        test.mobile().wallet().unselectData();
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectData();
                        }

                    } else {

                        test.mobile().wallet().clickToViewDetails();
                        test.mobile().wallet().unselectDataPIDPython();
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataPIDPython();
                        }
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/PID/pre_final_shared_data_on_wallet.yml");

                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().clickExpandVerification();
                            }
                                test.mobile().wallet().scrollUntilNationality();
                                test.mobile().wallet().clickExpandVerification();
                                test.mobile().wallet().scrollUpForBirthDateOnPID();
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }

                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/PID/kotlin_pre_final_shared_data_on_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().scrollUntilNationality();
                                test.mobile().wallet().clickExpandVerificationDown();
                                test.mobile().wallet().scrollUntilPlaceOfBirth();
                                test.mobile().wallet().clickExpandVerificationDown();
                                test.mobile().wallet().scrollUpForBirthDateOnPID();
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/kotlin_pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/kotlin_pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }
                    }

                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().pinFieldIsDisplayed();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().authenticationSuccessfully();

                    break;

                case "cross device":

                    switch (this.selectiveDisclosure.toLowerCase()) {

                        case "specific attributes":

                            test.webWebDriverFactory().startWebDriverSession();

                            try {
                                test.envDataConfig();
                                String url = test.envDataConfig().getVerifierUrl();
                                test.webWebDriverFactory().getDriverWeb().get(url);
                                test.web().verifier().appOpensSuccessfullyOnWeb();
                                test.web().verifier().selectSpecificAttributesOnWebForPID();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().pidIsDisplayedOnWeb();
                                test.web().verifier().clickSpecificAttributesButtonForPID();
                                test.web().verifier().selectSpecificAttributesOnWeb();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().uriMethodIsDisplayed();
                                test.web().verifier().scrollUntilSubmitOnWeb();
                                test.web().verifier().assertQrCodeIsVisible();
                                test.web().verifier().captureScreenOnWeb();

                            } catch (org.openqa.selenium.WebDriverException e) {

                                e.printStackTrace();
                            }

                            break;

                        case "all attributes":

                            test.webWebDriverFactory().startWebDriverSession();

                            try {
                                test.envDataConfig();
                                String url = test.envDataConfig().getVerifierUrl();
                                test.webWebDriverFactory().getDriverWeb().get(url);
                                test.web().verifier().appOpensSuccessfullyOnWeb();

                                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

                                    test.web().verifier().selectAllAttributesOnWebForPID();
                                    test.web().verifier().scrollUntilNextOnWeb();
                                    test.web().verifier().pidIsDisplayedOnWeb();
                                    test.web().verifier().scrollUntilNextOnWeb();

                                } else {

                                    test.web().verifier().selectSpecificAttributesOnWebForPID();
                                    test.web().verifier().scrollUntilNextOnWeb();
                                    test.web().verifier().pidIsDisplayedOnWeb();
                                    test.web().verifier().clickSpecificAttributesButtonForPID();
                                    test.web().verifier().selectMandatoryAttributesOnWeb();
                                    test.web().verifier().scrollUntilNextOnWeb();
                                }

                                test.web().verifier().uriMethodIsDisplayed();
                                test.web().verifier().scrollUntilSubmitOnWeb();
                                test.web().verifier().assertQrCodeIsVisible();
                                test.web().verifier().captureScreenOnWeb();

                            } catch (org.openqa.selenium.WebDriverException e) {

                                e.printStackTrace();
                            }

                            break;
                    }

                    theUserIsOnTheLoginScreen();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickAuthenticate();
                    test.mobile().wallet().clickOnlinePresentation();


                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }
                    test.mobile().wallet().mockQRInject(test.web().verifier().getCapturedScreenFile());

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {

                        test.mobile().wallet().clickPIDFromKotlin();
                        test.mobile().wallet().unselectData();
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectData();
                        }

                    } else {

                        test.mobile().wallet().clickToViewDetails();
                        test.mobile().wallet().unselectDataPIDPython();
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataPIDPython();
                        }
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/PID/pre_final_shared_data_on_wallet.yml");

                        } else {

                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().clickExpandVerification();
                            }
                            test.mobile().wallet().scrollUntilNationality();
                            test.mobile().wallet().clickExpandVerification();
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }

                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_pre_final_shared_data_on_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().scrollUntilNationality();
                                test.mobile().wallet().clickExpandVerificationDown();
                                test.mobile().wallet().scrollUntilPlaceOfBirth();
                                test.mobile().wallet().clickExpandVerificationDown();
                                test.mobile().wallet().scrollUpForBirthDateOnPID();
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }
                    }


                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().authenticationSuccessfully();

                    break;
            }

        } else {

            switch (presentationScenario.toLowerCase()) {

                case "same device":

                    switch (this.selectiveDisclosure.toLowerCase()) {

                        case "specific attributes":

                            test.mobile().verifier().launchSafari();
                            test.mobile().verifier().appOpensSuccessfully();
                            test.mobile().verifier().selectSpecificAttributesForMdl();
                            test.mobile().verifier().scrollUntilNext();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().selectAttributes();
                            test.mobile().verifier().clickSpecificAttributes();
                            test.mobile().verifier().clickSelect();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().scrollUntilSumbit();
                            test.mobile().verifier().clickSubmit();
                            break;

                        case "all attributes":

                            test.mobile().verifier().launchSafari();
                            test.mobile().verifier().appOpensSuccessfully();
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().verifier().selectAllAttributesForMdl();
                                test.mobile().verifier().scrollUntilNext();
                                test.mobile().verifier().clickNext();
                            } else {
                                test.mobile().verifier().selectSpecificAttributesForMdl();
                                test.mobile().verifier().scrollUntilNext();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().selectAttributes();
                                test.mobile().verifier().selectTheMandatoryAttributes();
                                test.mobile().verifier().clickSelect();
                            }

                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().scrollUntilSumbit();
                            test.mobile().verifier().clickSubmit();
                            break;
                    }

                    test.mobile().verifier().chooseWallet();
                    test.mobile().verifier().viewDataPage();

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {

                        test.mobile().wallet().clickMDLFromKotlin();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        } else {
                            test.mobile().wallet().unselectDataForMdlKotlinAllAttributes();
                        }
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        }

                    } else {

                        test.mobile().wallet().clickToViewDetails();
                        test.mobile().wallet().unselectDataPython();
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataPython();
                        }
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet.yml");

                        } else {

                            test.mobile().wallet().clickExpandVerification();
                            test.mobile().wallet().clickToViewDetails();

                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }

                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {

                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/kotlin_pre_final_shared_data_on_wallet.yml");

                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/kotlin_pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/kotlin_pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }
                    }

                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().pinFieldIsDisplayed();
                    test.mobile().verifier().insertPIN();
                    test.mobile().wallet().authenticationSuccessfully();

                    break;

                case "cross device":

                    switch (this.selectiveDisclosure.toLowerCase()) {

                        case "specific attributes":

                            test.webWebDriverFactory().startWebDriverSession();

                            try {
                                test.envDataConfig();
                                String url = test.envDataConfig().getVerifierUrl();
                                test.webWebDriverFactory().getDriverWeb().get(url);
                                test.web().verifier().appOpensSuccessfullyOnWeb();
                                test.web().verifier().selectSpecificAttributesOnWebForMdl();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().mdlIsDisplayedOnWeb();
                                test.web().verifier().clickSpecificAttributesButtonForMdl();
                                test.web().verifier().selectSpecificAttributesOnWeb();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().uriMethodIsDisplayed();
                                test.web().verifier().scrollUntilSubmitOnWeb();
                                test.web().verifier().assertQrCodeIsVisible();
                                test.web().verifier().captureScreenOnWeb();

                            } catch (org.openqa.selenium.WebDriverException e) {

                                e.printStackTrace();
                            }

                            break;

                        case "all attributes":

                            test.webWebDriverFactory().startWebDriverSession();

                            try {
                                test.envDataConfig();
                                String url = test.envDataConfig().getVerifierUrl();
                                test.webWebDriverFactory().getDriverWeb().get(url);
                                test.web().verifier().appOpensSuccessfullyOnWeb();

                                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

                                    test.web().verifier().selectAllAttributesOnWebForMdl();
                                    test.web().verifier().scrollUntilNextOnWeb();
                                    test.web().verifier().mdlIsDisplayedOnWeb();
                                    test.web().verifier().scrollUntilNextOnWeb();

                                } else {

                                    test.web().verifier().selectSpecificAttributesOnWebForMdl();
                                    test.web().verifier().scrollUntilNextOnWeb();
                                    test.web().verifier().mdlIsDisplayedOnWeb();
                                    test.web().verifier().clickSpecificAttributesButtonForMdl();
                                    test.web().verifier().selectMandatoryAttributesOnWeb();
                                    test.web().verifier().scrollUntilNextOnWeb();
                                }

                                test.web().verifier().uriMethodIsDisplayed();
                                test.web().verifier().scrollUntilSubmitOnWeb();
                                test.web().verifier().assertQrCodeIsVisible();
                                test.web().verifier().captureScreenOnWeb();

                            } catch (org.openqa.selenium.WebDriverException e) {

                                e.printStackTrace();
                            }

                            break;
                    }

                    theUserIsOnTheLoginScreen();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickAuthenticate();
                    test.mobile().wallet().clickOnlinePresentation();


                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }
                    test.mobile().wallet().mockQRInject(test.web().verifier().getCapturedScreenFile());

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {

                        test.mobile().wallet().clickMDLFromKotlin();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        } else {
                            test.mobile().wallet().unselectDataForMdlKotlinAllAttributes();
                        }
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        }

                    } else {

                        test.mobile().wallet().clickToViewDetails();
                        test.mobile().wallet().unselectDataPython();
                        test.mobile().wallet().closeCorrespondingMessage();
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataPython();
                        }
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet.yml");

                        } else {

                            test.mobile().wallet().clickExpandVerification();
                            test.mobile().wallet().clickToViewDetails();

                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet_all_attributes.yml");
                        }

                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/kotlin_pre_final_shared_data_on_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/kotlin_pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/kotlin_pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }
                    }


                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().authenticationSuccessfully();

                    break;
            }
        }
    }


    @And("the user shares {}")
    public void theUserShares(String selectiveDisclosure) {
        this.selectiveDisclosure = selectiveDisclosure;
    }

    @Then("the verifier verifies the credential successfully with {} for {}")
    public void theVerifierVerifiesTheCredentialSuccessfully(String presentationScenario, String selectiveDisclosure) throws InterruptedException {
        if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {
            switch (presentationScenario.toLowerCase()) {
                case "same device":
                    test.mobile().wallet().clickClose();
                    test.mobile().verifier().walletResponded();
                    test.mobile().verifier().clickViewContent();
                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/data_on_verifier_from_wallet.yml");
                        }else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/PID/data_on_verifier_from_wallet_all_attributes.yml");
                            }
                        }
                    } else {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_verifier_from_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/PID/kotlin_data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/PID/kotlin_data_on_verifier_from_wallet_all_attributes_ios.yml");
                            }
                        }
                    }
                    break;
                case "cross device":
                    test.mobile().wallet().clickClose();
                    test.web().verifier().clickViewContentOnWeb();
                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/PID/data_on_verifier_from_wallet.yml");
                        }else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/PID/data_on_verifier_from_wallet_all_attributes.yml");
                            }
                        }
                    } else {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/PID/kotlin_data_on_verifier_from_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/PID/kotlin_data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/PID/kotlin_data_on_verifier_from_wallet_all_attributes_ios.yml");
                            }
                        }
                    }
                    test.web().verifier().clickCloseOnVerifierWeb();
                    test.webWebDriverFactory().quitDriverWeb();
                    break;
            }
        } else {
            switch (presentationScenario.toLowerCase()) {
                case "same device":
                    test.mobile().wallet().clickClose();
                    test.mobile().verifier().walletRespondedMdlKotlin();
                    test.mobile().verifier().clickViewContent();
                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/data_on_verifier_from_wallet.yml");
                        }else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/mDL/data_on_verifier_from_wallet_all_attributes.yml");
                            }
                        }
                    } else {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_verifier_from_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
                                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes_ios.yml");
                                }
                            }
                        }
                    }
                    break;
                case "cross device":
                    test.mobile().wallet().clickClose();
                    test.web().verifier().checkTheResponse();
                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/data_on_verifier_from_wallet.yml");
                        }else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/data_on_verifier_from_wallet_all_attributes_ios.yml");
                            }
                        }
                    } else {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/kotlin_data_on_verifier_from_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes.yml");
                            }
                        }
                    }
                    test.web().verifier().clickCloseOnVerifierWeb();
                    test.webWebDriverFactory().quitDriverWeb();
                    break;
            }
        }
    }
}
