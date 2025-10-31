package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileWriter;
import java.net.MalformedURLException;

public class GeneralStepDefs{

    static TestSetup test;
    @Before
    public void setup(Scenario scenario) throws InterruptedException, MalformedURLException {
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
            test.mobile().issuer().issueMDL();
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
    public void tearDown(Scenario scenario) throws InterruptedException {
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        String env = test.envDataConfig().getExecutionEnvironment();
        String outputPath = "C:/Users/ftheofil/Projects/eu-digital-identity-walleteudi-doc-testing-application-internal/src/test/resources/features/android/regressionTests/logs/ui-browserstack";
        if (android) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
            String featureName = test.getScenario().getUri().getPath()
                    .substring(test.getScenario().getUri().getPath().lastIndexOf('/') + 1)
                    .replace(".feature", "")
                    .replace(" ", "_");

            try (FileWriter fw = new FileWriter("session_map.txt", true)) {
                fw.write(featureName + "=" + sessionId + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            test.stopAndroidDriverSession();
        }
        if (ios)
        { test.stopIosDriverSession();
        }
        test.stopLogging();
    }


    public static TestSetup getTest() {
        return test;
    }


    @Given("user opens Verifier App")
    public void userOpensVerifierApp() throws MalformedURLException {
        test.mobile().wallet().userOpensVerifier();
        test.mobile().verifier().launchSafari();
        test.mobile().verifier().appOpensSuccessfully();
    }

    @When("user selects specific data to share")
    public void userSelectSpecificDataToShare() {
        test.mobile().verifier().launchSafari();
        test.mobile().verifier().appOpensSuccessfully();
        test.mobile().verifier().selectAllAttributes();
        test.mobile().verifier().scrollUntilNext();
        if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra") || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {
            test.mobile().verifier().clickNext();
            test.mobile().verifier().clickNext();
            test.mobile().verifier().clickNext();
        }      else{
            test.mobile().verifier().clickNext();
            test.mobile().verifier().clickNextForAndroid();
            test.mobile().verifier().clickNext();
            test.mobile().verifier().assertAndClickNext();
        }
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

    @And("user is authenticated successfully")
    public void userIsAuthenticatedSuccessfully() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @And("a corresponding message is displayed")
    public void aCorrespondingMessageIsDisplayed() {
        test.mobile().wallet().correspondingMessageIsDisplayed();
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
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
//        test.mobile().issuer().scrollUntilCountry();
        test.mobile().issuer().enterCountry();
//        test.mobile().issuer().clickNationality();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
//        test.mobile().issuer().clickNationality();
//        test.mobile().issuer().addOptionalAttributes();
//        test.mobile().issuer().clickAgeOver18OnIssuer();
//        test.mobile().issuer().clickAddAttributes();
//        test.mobile().issuer().enableAgeOver18();
//        test.mobile().issuer().clickConfirm();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().authorizeIsDisplayed();
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
    }


    @Then("the new document is presented in the EUDI Wallet dashboard screen")
    public void theNewDocumentIsPresentedInTheEUDIWalletDashboardScreen() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().secondPIDIsDisplayed();
    }

    @Given("the user sees a success message in the EUDI Wallet app")
    public void theUserSeesASuccessMessageInTheEUDIWalletApp() throws InterruptedException {
        theUserIsAskedToAuthenticateAndConsentOnTheIssuerService();
        insertsTheRequiredCredentialDetails();
        test.mobile().wallet().successMessageIsDisplayedForIssuer();
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
    public void theUserClicksTheDrivingLicenseButton() throws InterruptedException {
        test.mobile().wallet().scrollUntilmDL();
        test.mobile().wallet().clickDrivingLicenceButton();
    }

    @Then("the user is redirected to the issuer service to issue mDL")
    public void theUserIsRedirectedToTheIssuerServiceToIssueMDL() {
        test.mobile().issuer().requestCredentialsPageIsDisplayed();
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
//        test.mobile().issuer().enterCodeFieldIssuer();
//        test.mobile().issuer().enterSignFieldIssuer();
//        test.mobile().issuer().enterValueFieldIssuer();
        test.mobile().issuer().clickScreen();
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
//        test.mobile().issuer().scrollUntilCountry();
        test.mobile().issuer().enterCountry();
//        test.mobile().issuer().clickNationality();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
//        test.mobile().issuer().clickNationality();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
//        test.mobile().issuer().clickConfirm();
        test.mobile().issuer().authorizeIsDisplayed();
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

        if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra") || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {
            test.mobile().verifier().clickNext();
            test.mobile().verifier().clickNext();
            test.mobile().verifier().clickNext();
        }      else{
            test.mobile().verifier().clickNext();
            test.mobile().verifier().clickNextForAndroid();
            test.mobile().verifier().clickNext();
            test.mobile().verifier().assertAndClickNext();
        }
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

    @Given("the user views the document that is requested")
    public void theUserViewsTheDocumentThatIsRequested() throws MalformedURLException {
        theUserHasFinalizedDataSelection();
        theUserClicksTheSHAREButton();
        thePINFieldIsDisplayedToAuthorizeSharing();
    }

    @Then("the user clicks to view the document's details")
    public void theUserClicksToViewTheDocumentsDetails() {
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

    @When("the user enters the correct PIN")
    public void theUserEntersTheCorrectPIN() {
        test.mobile().wallet().createAPin();
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

    @Given("a provider form is displayed for mdl")
    public void aProviderFormIsDisplayedForMdl() throws InterruptedException {
        theIssuerServiceTestCredentialProviderScreenIsDisplayed();
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

    @Given("the issuer service -test credential provider screen- is displayed")
    public void theIssuerServiceTestCredentialProviderScreenIsDisplayed() throws InterruptedException {
        theHomePageIsDisplayedOnWallet();
        theUserClicksTheAddDocButton();
        theAddDocumentPageIsDisplayed();
        theUserClicksTheDrivingLicenseButton();
        theUserIsRedirectedToTheIssuerServiceToIssueMDL();
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
    public void theUserClicksThePidButton() throws InterruptedException {
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

    @Then("the wallet displays a predefined list of attestations that the user can issue and add to their EUDI Wallet")
    public void theWalletShouldDisplayAPredefinedListOfAttestationsThatTheUserCanIssueAndAddToTheirEUDIWallet() {
//        test.mobile().wallet().predefinedListIsDisplayed();
    }

    @Given("the user is viewing the predefined list of attestations")
    public void theUserIsViewingThePredefinedListOfAttestations() throws InterruptedException {
        theUserIsOnTheDocumentsScreen();
        theUserSelectsToAddANewDocument();
        theUserSelectsToAddANewDocumentFromList();
        theWalletShouldDisplayAPredefinedListOfAttestationsThatTheUserCanIssueAndAddToTheirEUDIWallet();
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

    @And("the screen informs the user about the attestation issued and the issuer who issued it")
    public void theScreenShouldInformTheUserAboutTheAttestationIssuedAndTheIssuerWhoIssuedIt() {
//        test.mobile().wallet().informUserAboutAttestation();
    }

    @And("the screen displays a Close button to return to the Home screen")
    public void theScreenShouldDisplayACloseButtonToReturnToTheHomeScreen() {
//        test.mobile().wallet().clickButtonIsDisplayed();
    }

    @Given("the user is viewing the success screen after an attestation is issued")
    public void theUserIsViewingTheSuccessScreenAfterAnAttestationIsIssued() throws InterruptedException {
        theUserIsViewingThePredefinedListOfAttestations();
        theUserSelectsOneAttestationToBeIssued();
        theWalletDisplaysASuccessScreen();
        theScreenShouldInformTheUserAboutTheAttestationIssuedAndTheIssuerWhoIssuedIt();
        theScreenShouldDisplayACloseButtonToReturnToTheHomeScreen();
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
//        test.mobile().issuer().clickPreAuthorizationCode();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user registers their personal data")
    public void theUserRegistersTheirPersonalData() throws InterruptedException {
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
//        test.mobile().issuer().scrollUntilCountry();
        test.mobile().issuer().enterCountry();
//        test.mobile().issuer().enterRegion();
//        test.mobile().issuer().enterLocality();
//        test.mobile().issuer().clickNationality();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
//        test.mobile().issuer().clickNationality();
//        test.mobile().issuer().addOptionalAttributes();
//        test.mobile().issuer().clickAgeOver18OnIssuer();
//        test.mobile().issuer().clickAddAttributes();
//        test.mobile().issuer().enableAgeOver18();
//        test.mobile().issuer().clickConfirm();
        test.mobile().issuer().scrollUntilFindSubmit();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().authorizeIsDisplayed();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @And("a transaction code has been created")
    public void aTransactionCodeHasBeenCreated() {
        test.mobile().issuer().transactionCodeIsDisplayed();
//       test.mobile().issuer().getTransactionCode();
//       String code = test.mobile().issuer().getTransactionCode();
//       test.setTransactionCode(code); // <-- store it for later steps

//        System.out.println("Stored transaction code: " + code);
    }

    @Given("the transaction code has been created")
    public void theTransactionCodeHasBeenCreated() throws InterruptedException {
        theUserIsOnTheIssuerService();
        theUserChoosesToIssueADocWithPreAuthorization();
        theUserRegistersTheirPersonalData();
        aTransactionCodeHasBeenCreated();
    }

    @When("the user selects to register with the EUDI wallet app")
    public void theUserSelectsToRegisterWithTheEUDIWalletApp() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();    }

    @Then("the user is redirected to the EUDI wallet application")
    public void theUserIsRedirectedToTheEUDIWalletApplication() {
        //manual
    }

    @And("the user enters the PIN")
    public void theUserEntersThePIN() {
        //manual
    }

    @Given("the user has entered the PIN")
    public void theUserHasEnteredThePIN() throws InterruptedException {
        theTransactionCodeHasBeenCreated();
        theUserSelectsToRegisterWithTheEUDIWalletApp();
        theUserIsRedirectedToTheEUDIWalletApplication();
        theUserEntersThePIN();
    }

    @When("the request from the issuer is displayed on the wallet app")
    public void theRequestFromTheIssuerIsDisplayedOnTheWalletApp() {
        test.mobile().wallet().detailsArePresented();
    }

    @Then("the user clicks on the ISSUE button")
    public void theUserClicksOnTheISSUEButton() {
        test.mobile().wallet().clickIssue();
    }

    @Given("the user has clicked on the ISSUE button")
    public void theUserHasClickedOnTheISSUEButton() throws InterruptedException {
        theUserHasEnteredThePIN();
        theRequestFromTheIssuerIsDisplayedOnTheWalletApp();
        theUserClicksOnTheISSUEButton();
    }

    @Then("the Wallet app requests the transaction code")
    public void theWalletAppRequestsTheTransactionCode() {
//        test.mobile().wallet().verfiricationIsDisplayed();
    }

    @And("the user enters the transaction code provided by the Issuer")
    public void theUserEntersTheTransactionCodeProvidedByTheIssuer() {
//        test.mobile().wallet().sendTrasactionCode();
    }

    @Given("the user entered the transaction code provided by the Issuer")
    public void theUserEnteredTheTransactionCodeProvidedByTheIssuer() throws InterruptedException {
        theUserHasClickedOnTheISSUEButton();
        theWalletAppRequestsTheTransactionCode();
        theUserEntersTheTransactionCodeProvidedByTheIssuer();
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
//        test.mobile().issuer().clickAgeOver18();
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
    public void theUserSelectsTheURL() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @Then("the Wallet is initiated and the user is presented with details of the credentials to be issued \\(type of credential, issuer name, image)")
    public void theWalletIsInitiatedAndTheUserIsPresentedWithDetailsOfTheCredentialsToBeIssuedTypeOfCredentialIssuerNameImage() {
//        test.mobile().wallet().detailsOfAgeOver18IsDisplayed();
    }

    @Given("the user is presented with details of the credentials to be issued")
    public void theUserIsPresentedWithDetailsOfTheCredentialsToBeIssued() throws InterruptedException {
        theUserIsPresentedWithAURLToInitiateTheEUDIWallet();
        theUserSelectsTheURL();
        theWalletIsInitiatedAndTheUserIsPresentedWithDetailsOfTheCredentialsToBeIssuedTypeOfCredentialIssuerNameImage();
    }

    @When("the user selects to proceed with the issuance process")
    public void theUserSelectsToProceedWithTheIssuanceProcess() {
//        test.mobile().wallet().clickAdd();
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
//        test.mobile().issuer().clickPseudonymDeferred();
        test.mobile().issuer().clickSubmitButton();
    }

    @Then("the user is redirected to the wallet app")
    public void theUserIsRedirectedToTheWalletApp() {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @And("the user sees the details regarding the issuance")
    public void theUserSeesTheDetailsRegardingTheIssuance() {
//        test.mobile().wallet().detailsArePresentedForDeferred();
    }

    @Given("the user is on the wallet app with issuance details")
    public void theUserIsOnTheWalletAppWithIssuanceDetails() throws InterruptedException {
        theUserIsOnTheIssuerServicePage();
        theUserChoosesToIssueACredentialToTheWalletApp();
        theUserIsRedirectedToTheWalletApp();
        theUserSeesTheDetailsRegardingTheIssuance();
    }

    @When("the user clicks the Issue button")
    public void theUserClicksTheIssueButton() {
//        test.mobile().wallet().clickAdd();
    }

    @Then("the user is redirected to the issuer for authentication and consent")
    public void theUserIsRedirectedToTheIssuerForAuthenticationAndConsent() {
        test.mobile().issuer().authenticationPageIsDisplayed();
    }

    @Given("the user is on the Home page")
    public void theUserIsOnTheHomePage() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @When("the user selects the Online option in the Authenticate section")
    public void theUserSelectsTheOnlineOptionInTheAuthenticateSection() {
//         test.mobile().wallet().clickAuthentication();
    }

    @When("the user decides not to proceed")
    public void theUserDecidesNotToProceed() {
        test.mobile().verifier().insertPIN2();
//        test.mobile().wallet().selectSigningCertificateIsDisplayed();
//        test.mobile().wallet().clickAbortToSigning();
    }

    @Then("the user can select the Abort operation option")
    public void theUserCanSelectTheAbortOperationOption() {
//        test.mobile().wallet().cancelSigningIsDisplayed();
//        test.mobile().wallet().clickCancelSigning();
    }

    @And("EUDI Wallet should return the user to the main page")
    public void eudiWalletShouldReturnTheUserToTheMainPage() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @When("the EUDI Wallet receives the signed document")
    public void theEUDIWalletReceivesTheSignedDocument() {
//        test.mobile().wallet().clickToViewSignDocument();
//        test.mobile().wallet().clickBackWallet();
    }

    @Then("the EUDI Wallet enables the user to share the document or close the process")
    public void theEUDIWalletEnablesTheUserToShareTheDocumentOrCloseTheProcess() {
        test.mobile().wallet().clickDone();
//       test.mobile().wallet().closeIsDisplayed();
//       test.mobile().wallet().shareIsDisplayed();
    }

    @Given("the user has been redirected to the Issuer service to present their PID")
    public void theUserHasBeenRedirectedToTheIssuerServiceToPresentTheirPID() throws InterruptedException {
        theUserIsPresentedWithDetailsOfTheCredentialsToBeIssued();
        theUserSelectsToProceedWithTheIssuanceProcess();
        theUserIsRedirectedToTheIssuerServiceToPresentTheirPID();
    }

    @When("the EUDI Wallet displays the presentation request for PID")
    public void theEUDIWalletDisplaysThePresentationRequestForPID() {
//        test.mobile().issuer().clickPidAuthentication();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().qrCodeIsDisplayed();
//        test.mobile().issuer().clickRequestButton();
    }

    @Then("the user is prompted to consent by selecting the Share button")
    public void theUserIsPromptedToConsentBySelectingTheShareButton() {
        test.mobile().verifier().viewDataPage();
    }

    @Given("the user has been prompted to consent by selecting the Share button")
    public void theUserHasBeenPromptedToConsentBySelectingTheShareButton() throws InterruptedException {
        theUserHasBeenRedirectedToTheIssuerServiceToPresentTheirPID();
        theEUDIWalletDisplaysThePresentationRequestForPID();
        theUserIsPromptedToConsentBySelectingTheShareButton();
    }

    @When("the user selects the Share button")
    public void theUserSelectsTheShareButton() {
        test.mobile().wallet().clickShareButton();
    }

    @Then("the user is prompted to enter their six-digit PIN")
    public void theUserIsPromptedToEnterTheirSixDigitPIN() {
//        test.mobile().wallet().createAPinIsDisplayed();
    }

    @Given("the user has been prompted to enter their six-digit PIN")
    public void theUserHasBeenPromptedToEnterTheirSixDigitPIN() throws InterruptedException {
        theUserHasBeenPromptedToConsentBySelectingTheShareButton();
        theUserSelectsTheShareButton();
        theUserIsPromptedToEnterTheirSixDigitPIN();
    }

    @When("the user enters their six-digit PIN correctly")
    public void theUserEntersTheirSixDigitPINCorrectly() {
        test.mobile().wallet().createAPin();
    }

    @Then("a success message is displayed for the successful presentation of the PID")
    public void aSuccessMessageIsDisplayedForTheSuccessfulPresentationOfThePID() {
        test.mobile().wallet().authenticationSuccessfully();
    }

    @Given("a success message is displayed for the successful presentation of PID")
    public void aSuccessMessageIsDisplayedForTheSuccessfulPresentationOfPID() throws InterruptedException {
        theUserHasBeenPromptedToEnterTheirSixDigitPIN();
        theUserEntersTheirSixDigitPINCorrectly();
        aSuccessMessageIsDisplayedForTheSuccessfulPresentationOfThePID();
    }

    @When("the user clicks the Continue button")
    public void theUserClicksTheContinueButton() throws InterruptedException {
        test.mobile().wallet().clickDone();
//        test.mobile().wallet().TestProviderFormIsDisplayed();
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
//       test.mobile().wallet().ageOver18IsDisplayed();
    }

    @Given("the user is on the issuer page for authentication and consent")
    public void theUserIsOnTheIssuerPageForAuthenticationAndConsent() throws InterruptedException {
        theUserIsOnTheWalletAppWithIssuanceDetails();
        theUserClicksTheIssueButton();
        theUserIsRedirectedToTheIssuerForAuthenticationAndConsent();
    }

    @When("the user authenticates and consents the issuance")
    public void theUserAuthenticatesAndConsentsTheIssuance() throws InterruptedException {
        test.mobile().issuer().clickCountrySelection();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().formIsDisplayed();
//        test.mobile().issuer().enableAgeOver18OnDeffered();
//        test.mobile().issuer().clickConfirm();
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("the user is redirected to the wallet app after issuance")
    public void theUserIsRedirectedToTheWalletAppAfterIssuance() {
//        test.mobile().wallet().isOnWallet();
    }

    @And("a message appears stating that the request is in progress")
    public void aMessageAppearsStatingThatTheRequestIsInProgress() {
//        test.mobile().wallet().requestInProgressIsDisplayed();
    }

    @Given("the user sees the issuance in progress message")
    public void theUserSeesTheIssuanceInProgressMessage() throws InterruptedException {
        theUserIsOnTheIssuerPageForAuthenticationAndConsent();
        theUserAuthenticatesAndConsentsTheIssuance();
        theUserIsRedirectedToTheWalletAppAfterIssuance();
        aMessageAppearsStatingThatTheRequestIsInProgress();
    }

    @When("the user clicks OK")
    public void theUserClicksOK() {
//       test.mobile().wallet().clickOk();
    }

    @Then("the dashboard appears with the document grayed out and in a pending state")
    public void theDashboardAppearsWithTheDocumentGrayedOutAndInAPendingState() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
//        test.mobile().wallet().documentInPendingState();
    }

    @Given("the wallet app is polling the issuer for the credential")
    public void theWalletAppIsPollingTheIssuerForTheCredential() throws InterruptedException {
        theUserSeesTheIssuanceInProgressMessage();
        theUserClicksOK();
        theDashboardAppearsWithTheDocumentGrayedOutAndInAPendingState();
    }

    @Then("the user views a modal informing them that the document has been issued")
    public void theUserViewsAModalInformingThemThatTheDocumentHasBeenIssued() {
//        test.mobile().wallet().documentIsIssued();
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
    public void theUserAuthenticatesUsingASixDigitPINOrBiometrics() {
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
    public void theUserIsSuccessfullyAuthenticatedInTheEUDIWallet() throws MalformedURLException {
        theEUDIWalletIsOpened();
        theUserAuthenticatesUsingASixDigitPINOrBiometrics();
        theAuthenticationIsSuccessful();
    }

    @Then("the EUDI Wallet informs the user that the Relying Party requests an attestation")
    public void theEUDIWalletInformsTheUserThatTheRelyingPartyRequestsAnAttestation() {
        test.mobile().verifier().viewDataPage();
    }

    @Given("the EUDI Wallet requests the user to consent")
    public void theEUDIWalletRequestsTheUserToConsent() throws MalformedURLException {
        theEUDIWalletIsOpened();
        theUserAuthenticatesUsingASixDigitPINOrBiometrics();
        theAuthenticationIsSuccessful();
    }

    @Given("the user consents to the attestation presentation")
    public void theUserConsentsToTheAttestationPresentation() throws MalformedURLException {
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
    public void methodAIsConfiguredForTheAttestationType() throws MalformedURLException {
        theUserConsentsToTheAttestationPresentation();
        theEUDIWalletDisplaysAConfirmationMessageIndicatingTheOutcome();
        theRelyingPartyServiceReceivesTheAttestation();
    }

    @When("the EUDI Wallet selects an available matching attestation")
    public void theEUDIWalletSelectsAnAvailableMatchingAttestation() {
//        test.mobile().wallet().openWallet();
    }

    @Then("the Wallet uses an attestation not previously presented to any Relying Party")
    public void theWalletUsesAnAttestationNotPreviouslyPresentedToAnyRelyingParty() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
    }

    @And("the EUDI Wallet reduces the internal counter of unused attestations")
    public void theEUDIWalletReducesTheInternalCounterOfUnusedAttestations() {
//        test.mobile().wallet().instanceHasReduced();
    }

    @Given("the authentication is successful and continue")
    public void theAuthenticationIsSuccessfulAndContinue() throws InterruptedException {
        theUserVisitsTheIssuerServiceOnTheSameDevice();
        theUserRequestsTheIssuanceOfAnAttestationType();
        theIssuerServiceRedirectsTheUserToTheWallet();
    }

    @When("the Wallet receives the attestation from the issuer service")
    public void theWalletReceivesTheAttestationFromTheIssuerService() throws InterruptedException {
//        test.mobile().wallet().informUserAboutAttestation();
//        test.mobile().wallet().clickAdd();
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
        test.mobile().issuer().formIsDisplayed();
        test.mobile().issuer().enterFamilyName();
        test.mobile().issuer().enterGivenName();
        test.mobile().issuer().chooseBirthDate();
//        test.mobile().issuer().scrollUntilCountry();
        test.mobile().issuer().enterCountry();
//        test.mobile().issuer().enterRegion();
//        test.mobile().issuer().enterLocality();
//        test.mobile().issuer().clickNationality();
        test.mobile().issuer().scrollUntilCountryCode();
        test.mobile().issuer().enterCountryCode();
//        test.mobile().issuer().clickNationality();
//        test.mobile().issuer().addOptionalAttributes();
//        test.mobile().issuer().clickAgeOver18OnIssuer();
//        test.mobile().issuer().clickAddAttributes();
//        test.mobile().issuer().enableAgeOver18();
//        test.mobile().issuer().clickConfirm();
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

    @Then("the Wallet displays a counter showing the number of attestations issued")
    public void theWalletDisplaysACounterShowingTheNumberOfAttestationsIssued() {
//        test.mobile().wallet().counterOfIssuedAttestation();
    }

    @Given("the user views the issuance confirmation modal")
    public void theUserViewsTheIssuanceConfirmationModal() throws InterruptedException {
        theWalletAppIsPollingTheIssuerForTheCredential();
        theUserViewsAModalInformingThemThatTheDocumentHasBeenIssued();
    }

    @When("the user clicks to view the document information")
    public void theUserClicksToViewTheDocumentInformation() {
//        test.mobile().wallet().clickToSeeDocument();
    }

    @Then("the document is open")
    public void theDocumentIsOpen() {
//        test.mobile().wallet().documentOpened();
    }

    @When("the user clicks on the X button")
    public void theUserClicksOnTheXButton() {
        test.mobile().wallet().clickBackButton();
    }

    @Then("the document should close")
    public void theDocumentShouldClose() {
        //manual
    }

    @Then("the document appears on the dashboard screen")
    public void theDocumentAppearsOnTheDashboardScreen() {
        test.mobile().wallet().documentsPageIsDisplayed();
    }

    @When("the issuer sends the credential to the wallet app")
    public void theIssuerSendsTheCredentialToTheWalletApp() {
        //donothing
    }

    @And("the user clicks on the PID doc on documents")
    public void theUserClicksOnThePIDDocOnDocuments() {
        test.mobile().wallet().clickPID();
    }

    @Given("the user is viewing the details of attestation auto")
    public void theUserIsViewingTheDetailsOfAttestationAuto() {
        theUserIsOnHomePage();
        theUserClicksOnDocuments();
        theUserClicksOnThePIDDocument();
        thePIDShouldOpen();
        theDetailsShouldBeBlurredByDefault();
    }

    private void theUserClicksOnThePIDDocument() {
        test.mobile().wallet().clickPID();
    }

    @Given("the PID is now open")
    public void thePIDIsNowOpen() {
        theUserIsViewingTheDetailsOfAttestationAuto();
        theUserSelectsEyeIcon();
    }
}

