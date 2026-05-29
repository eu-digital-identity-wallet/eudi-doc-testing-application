package eu.europa.eudi.stepdefs;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.data.yml.FormYml;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.YmlLoader;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.fail;

public class AutomatedStepDefs {

    static TestSetup test;
    EnvDataConfig envDataConfig;
    private String issuerType;
    private String credential;
    private String issuanceMethod;
    private String selectiveDisclosure;

    @Before
    public void setup(Scenario scenario) throws InterruptedException, MalformedURLException {
        envDataConfig = new EnvDataConfig();
        String env = envDataConfig.getExecutionEnvironment();
        boolean noReset = scenario.getSourceTagNames().contains("@noreset");
        boolean data = scenario.getSourceTagNames().contains("@before_01");
        boolean data_for_scan = scenario.getSourceTagNames().contains("@before_04");
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
            if (env.equalsIgnoreCase("browserstack")) {
                waitForBrowserStackToBeReadyAndroid(test.mobileWebDriverFactory().getDriverAndroid());
            }
        }
        if (ios) {
            test = new TestSetup(noReset, Literals.General.IOS.label, scenario);
            test.startIosDriverSession();
            test.setScenario(scenario);
            test.startLogging();
            if (env.equalsIgnoreCase("browserstack")) {
                waitForBrowserStackToBeReadyIos(test.mobileWebDriverFactory().getDriverIos());
            }
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
            test.mobile().wallet().scrollUntilPIDFirst();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().issuer().sleepMethod();
            test.mobile().issuer().successfullySharedMessage();
            test.mobile().wallet().clickExpandVerification();
            test.mobile().wallet().clickExpandVerificationDown();
            test.mobile().wallet().scrollUntilNationality();
            test.mobile().wallet().clickExpandVerificationDown();
            test.mobile().wallet().scrollUp();
            test.mobile().issuer().ckeckFieldsOnWalletFromPyIssuer();
            test.mobile().wallet().clickDone();
            theUserIsOnTheLoginScreen();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().dashboardPageIsDisplayed();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().documentsPageIsDisplayed();


        }

        if (data_for_scan) {
            test.webWebDriverFactory().startWebDriverSession();
            try {
                test.webWebDriverFactory().getDriverWeb().get("https://verifier.eudiw.dev/home");

                test.web().verifier().appOpensSuccessfullyOnWeb();
                test.web().verifier().selectAllAttributesOnWeb();
                test.web().verifier().scrollUntilNextOnWeb();
                test.web().verifier().pidIsDisplayedOnWeb();
                test.web().verifier().scrollUntilNextOnWeb();
                test.web().verifier().uriMethodIsDisplayed();
                test.web().verifier().scrollUntilNextOnWeb();
                test.web().verifier().assertQrCodeIsVisible();
                test.web().verifier().captureScreenOnWeb();
            } finally {
                test.webWebDriverFactory().quitDriverWeb();
            }

            test.mobile().wallet().checkIfPageIsTrue();
            test.mobile().wallet().createAPin();
            test.mobile().wallet().clickNextButton();
            test.mobile().wallet().renterThePin();
            test.mobile().wallet().clickConfirm();
            test.mobile().wallet().successMessageOfSetUpPin();
            test.mobile().wallet().clickAddMyDigitalID();
            test.mobile().wallet().addPIDPageIsDisplayed();
            test.mobile().wallet().scrollUntilPIDFirst();
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
            test.mobile().wallet().addPIDPageIsDisplayed();
            test.mobile().wallet().scrollUntilPIDFirst();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().scrollUntilPIDTwoPid();
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
            test.mobile().wallet().addPIDPageIsDisplayed();
            test.mobile().wallet().scrollUntilPIDFirst();
            test.mobile().wallet().clickPID();
            test.mobile().issuer().issuePID();
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().scrollUntilmDLOnDocuments();
            test.mobile().wallet().clickMdl();
            test.mobile().issuer().issueMDL();
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickHome();
        }
        if (ignored) {
            test.mobile().wallet().skippedTest();
            throw new AssumptionViolatedException("Test is ignored due to @manual:Ignored tag");
        }
    }

    private void waitForBrowserStackToBeReadyAndroid(WebDriver driverAndroid) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            try {
                driverAndroid.getPageSource();
                return;
            } catch (Exception e) {
                Thread.sleep(1500);
            }
        }
    }

    private void waitForBrowserStackToBeReadyIos(WebDriver driverIos) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            try {
                driverIos.getPageSource();
                return;
            } catch (Exception e) {
                Thread.sleep(1500);
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) throws InterruptedException {
        String featureName = test.getScenario().getUri().getPath()
                .substring(test.getScenario().getUri().getPath().lastIndexOf('/') + 1)
                .replace(".feature", "")
                .replace(" ", "_");
        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");
        try (FileWriter fw = new FileWriter("session_map.txt", true)) {

            if (android) {
                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
                String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
                fw.write(featureName + "_Android=" + sessionId + "\n");
                test.stopAndroidDriverSession();
            }
            if (ios) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
                fw.write(featureName + "_IOS=" + sessionId + "\n");
                test.stopIosDriverSession();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cleanupScreenshotsFolder();

        test.stopLogging();
    }

    private void cleanupScreenshotsFolder() {
        try {
            File screenshotsDir = new File("screenshots");
            if (screenshotsDir.exists() && screenshotsDir.isDirectory()) {
                File[] files = screenshotsDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore cleanup errors
        }
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
            test.mobile().verifier().scrollUntilSumbit();
            test.mobile().verifier().clickSubmit();
        } else {
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

    @And("a corresponding message is displayed")
    public void aCorrespondingMessageIsDisplayed() {
        test.mobile().wallet().correspondingMessageIsDisplayed();
    }

    @When("the user enters their PIN")
    public void theUserEntersTheirPIN() throws InterruptedException {
        test.mobile().wallet().createAPin();
    }

    @Then("the user should see the dashboard screen")
    public void theUserShouldSeeTheDashboardScreen() {
        test.mobile().wallet().dashboardPageIsDisplayed();
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

    @When("the user clicks on the mDL doc")
    public void theUserClicksOnTheMDLDoc() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickMdl();
    }

    @Then("the mDL should open")
    public void theMDLShouldOpen() {
        test.mobile().wallet().mdlIsDisplayed();
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

    @Given("the user has successfully entered the PIN")
    public void theUserHasSuccessfullyEnteredThePIN() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.terminateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            driver.activateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            test.mobile().wallet().loginPageIsDisplayed();
            test.mobile().wallet().createAPin();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.terminateApp(test.envDataConfig().getAppiumIosBundleId());
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

    @When("the user presses the delete button")
    public void theUserPressesTheDeleteButton() {
        test.mobile().wallet().scrollUntilYouFindDelete();
        test.mobile().wallet().clickDeleteDocument();
        test.mobile().wallet().confirmsDeletion();
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

    @Given("the user has opened the first PID that was issued")
    public void theUserHasOpenedTheFirstPIDThatWasIssued() {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().openIssuedPID();
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
    public void theUserShouldEnterThePIN() throws InterruptedException {
        test.mobile().wallet().createAPin();
    }

    @And("the user clicks the driving license button")
    public void theUserClicksTheDrivingLicenseButton() throws InterruptedException {
        test.mobile().wallet().scrollUntilmDL();
        test.mobile().wallet().clickDrivingLicenceButton();
    }

    @Then("the user is redirected to the issuer service to issue mDL")
    public void theUserIsRedirectedToTheIssuerServiceToIssueMDL() {
        test.mobile().issuer().selectCountryOfOrigin();
    }

    @And("the user clicks on Credential Provider FormEU and submits")
    public void theUserClicksOnCredentialProviderFormEUAndSubmits() throws InterruptedException {
        test.mobile().issuer().clickFormEu();
        test.mobile().issuer().clickSubmit();
    }

    @Then("the provider form is displayed for the user to register personal data")
    public void theProviderFormIsDisplayedForTheUserToRegisterPersonalData() throws InterruptedException {
        test.mobile().issuer().formIsDisplayed();
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
            test.mobile().verifier().scrollUntilSumbit();
            test.mobile().verifier().clickSubmit();
        } else {
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
        test.mobile().verifier().insertPIN2();
    }

    @Then("the PIN field is displayed to authorize sharing")
    public void thePINFieldIsDisplayedToAuthorizeSharing() {
        test.mobile().wallet().pinFieldIsDisplayed();
    }

    @When("the user enters the correct PIN")
    public void theUserEntersTheCorrectPIN() throws InterruptedException {
        test.mobile().wallet().createAPin();
    }

    @Given("the user is on the Login screen")
    public void theUserIsOnTheLoginScreen() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            Thread.sleep(500);
            driver.terminateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            driver.activateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            test.mobile().wallet().loginPageIsDisplayed();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            Thread.sleep(500);
            driver.terminateApp(test.envDataConfig().getAppiumIosBundleId());
            driver.activateApp(test.envDataConfig().getAppiumIosBundleId());
            test.mobile().wallet().loginPageIsDisplayed();
        }
    }

    @When("the user clicks on Documents")
    public void theUserClicksOnDocuments() {
        test.mobile().wallet().clickOnDocuments();
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

    @Given("the test is being ignored")
    public void theTestIsBeingIgnored() {
        test.mobile().wallet().skippedTest();
    }

    @Given("the user is on Home page")
    public void theUserIsOnHomePage() {
        test.mobile().wallet().homePageIsDisplayed();
    }

    @And("the details should be blurred by default auto")
    public void theDetailsShouldBeBlurredByDefault() {
        test.mobile().wallet().detailsAreBlurred();
    }

    @Given("the user is viewing the details of an attestation auto")
    public void theUserIsViewingTheDetailsOfAnAttestation() {
        theUserIsOnHomePage();
        theUserClicksOnDocuments();
        theUserClicksOnThePIDDoc();
        thePIDShouldOpen();
        theDetailsShouldBeBlurredByDefault();
    }

    @When("the user selects eye icon auto")
    public void theUserSelectsEyeIcon() {
        test.mobile().wallet().clickEyeIcon();
    }

    @Then("the attestation details should no longer be blurred auto")
    public void theAttestationDetailsShouldNoLongerBeBlurred() {
        test.mobile().wallet().detailsAreNotBlurred();
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

    @Given("the user is viewing the details of the mDL")
    public void theUserIsViewingTheDetailsOfTheMDL() throws InterruptedException {
        theUserIsOnTheHomeScreen();
        theUserClicksOnTheMDLDoc();
        theMDLShouldOpen();
        theDetailsShouldBeBlurredByDefault();
    }

    @Given("the home page is displayed on wallet")
    public void theHomePageIsDisplayedOnWallet() throws InterruptedException {
        theUserIsOnTheLoginScreen();
        theUserEntersTheirPIN();
        theUserShouldSeeTheHomeScreen();
    }

    @And("the user clicks the PID button")
    public void theUserClicksThePidButton() throws InterruptedException {
        test.mobile().wallet().scrollUntilPIDOnDocuments();
        test.mobile().wallet().clickPIDOnDocuments();
    }

    @Then("the credentials provider is displayed")
    public void theCredentialsProviderIsDisplayed() {
        test.mobile().issuer().selectCountryOfOriginDev();
    }

    @Given("the credentials provider is displayed on screen")
    public void theCredentialsProviderIsDisplayedOnScreen() throws InterruptedException {
        theHomePageIsDisplayedOnWallet();
        theUserClicksTheAddDocButton();
        theAddDocumentPageIsDisplayed();
        theUserClicksThePidButton();
        theCredentialsProviderIsDisplayed();
    }

    @Given("the expanded verification details are seen")
    public void theExpandedVerificationDetailsAreSeen() throws MalformedURLException, InterruptedException {
        theUserViewsTheDocumentThatIsRequested();
        theUserInsertsThePIN();
        theUserClicksToViewTheDocumentsDetails();
        theExpandedVerificationDetailsAreDisplayed();
    }

    @When("the user clicks done")
    public void theUserClicksDone() {
        test.mobile().wallet().clickDone();
    }

    @Then("the user gets redirected to verifier and views the respond")
    public void theUserGetsRedirectedToVerifierAndViewsTheRespond() {
        test.mobile().verifier().walletResponded();
        test.mobile().verifier().clickTransactionsLogs();
        test.mobile().verifier().clickTransactionInitialized();
        test.mobile().verifier().getTransactionId();

    }

    @When("the user inserts the PIN")
    public void theUserInsertsThePIN() throws InterruptedException {
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
      //donothing
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
         //donothing
    }

    @And("the screen displays a Close button to return to the Home screen")
    public void theScreenShouldDisplayACloseButtonToReturnToTheHomeScreen() {
         //donothing
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
    public void theUserSelectsToRegisterWithTheEUDIWalletApp() throws InterruptedException {
        test.mobile().issuer().qrCodeIsDisplayed();
        test.mobile().issuer().clickUseEudiw();
    }

    @Then("the user is redirected to the EUDI wallet application")
    public void theUserIsRedirectedToTheEUDIWalletApplication() {
        //manual
    }

    @And("the user enters the PIN")
    public void theUserEntersThePIN() {
        //manual
    }


    @When("the request from the issuer is displayed on the wallet app")
    public void theRequestFromTheIssuerIsDisplayedOnTheWalletApp() {
        test.mobile().wallet().detailsArePresented();
    }

    @Then("the user clicks on the ISSUE button")
    public void theUserClicksOnTheISSUEButton() {
        test.mobile().wallet().clickIssue();
    }

    @Then("the Wallet app requests the transaction code")
    public void theWalletAppRequestsTheTransactionCode() {
       //donothing
    }

    @And("the user enters the transaction code provided by the Issuer")
    public void theUserEntersTheTransactionCodeProvidedByTheIssuer() {
       //donothing
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

    @Then("the Wallet is initiated and the user is presented with details of the credentials to be issued \\(type of credential, issuer name, image)")
    public void theWalletIsInitiatedAndTheUserIsPresentedWithDetailsOfTheCredentialsToBeIssuedTypeOfCredentialIssuerNameImage() {
        //donothing
    }

    @Given("the user is presented with details of the credentials to be issued")
    public void theUserIsPresentedWithDetailsOfTheCredentialsToBeIssued() throws InterruptedException {
        theUserIsPresentedWithAURLToInitiateTheEUDIWallet();
        theUserSelectsTheURL();
        theWalletIsInitiatedAndTheUserIsPresentedWithDetailsOfTheCredentialsToBeIssuedTypeOfCredentialIssuerNameImage();
    }

    @When("the user selects to proceed with the issuance process")
    public void theUserSelectsToProceedWithTheIssuanceProcess() {
       //donothing
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

    @And("the user sees the details regarding the issuance")
    public void theUserSeesTheDetailsRegardingTheIssuance() {
        //donothing
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
      //donothing
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
        //donothing
    }

    @When("the user decides not to proceed")
    public void theUserDecidesNotToProceed() {
        test.mobile().verifier().insertPIN2();
    }

    @Then("the user can select the Abort operation option")
    public void theUserCanSelectTheAbortOperationOption() {
        //donothing
    }

    @And("EUDI Wallet should return the user to the main page")
    public void eudiWalletShouldReturnTheUserToTheMainPage() {
        test.mobile().wallet().dashboardPageIsDisplayed();
    }

    @When("the EUDI Wallet receives the signed document")
    public void theEUDIWalletReceivesTheSignedDocument() {
        //donothing
    }

    @Then("the EUDI Wallet enables the user to share the document or close the process")
    public void theEUDIWalletEnablesTheUserToShareTheDocumentOrCloseTheProcess() {
        test.mobile().wallet().clickDone();
    }

    @Given("the user has been redirected to the Issuer service to present their PID")
    public void theUserHasBeenRedirectedToTheIssuerServiceToPresentTheirPID() throws InterruptedException {
        theUserIsPresentedWithDetailsOfTheCredentialsToBeIssued();
        theUserSelectsToProceedWithTheIssuanceProcess();
        theUserIsRedirectedToTheIssuerServiceToPresentTheirPID();
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
      //donothing
    }

    @Given("the user has been prompted to enter their six-digit PIN")
    public void theUserHasBeenPromptedToEnterTheirSixDigitPIN() throws InterruptedException {
        theUserHasBeenPromptedToConsentBySelectingTheShareButton();
        theUserSelectsTheShareButton();
        theUserIsPromptedToEnterTheirSixDigitPIN();
    }

    @When("the user enters their six-digit PIN correctly")
    public void theUserEntersTheirSixDigitPINCorrectly() throws InterruptedException {
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
        test.mobile().issuer().scrollUntilAuthorize();
        test.mobile().issuer().clickAuthorize();
    }

    @Then("the user is redirected to the wallet app after issuance")
    public void theUserIsRedirectedToTheWalletAppAfterIssuance() {
       //donothing
    }

    @And("a message appears stating that the request is in progress")
    public void aMessageAppearsStatingThatTheRequestIsInProgress() {
     //donothing
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
      //donothing
    }

    @Then("the dashboard appears with the document grayed out and in a pending state")
    public void theDashboardAppearsWithTheDocumentGrayedOutAndInAPendingState() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
    }

    @Given("the wallet app is polling the issuer for the credential")
    public void theWalletAppIsPollingTheIssuerForTheCredential() throws InterruptedException {
        theUserSeesTheIssuanceInProgressMessage();
        theUserClicksOK();
        theDashboardAppearsWithTheDocumentGrayedOutAndInAPendingState();
    }

    @Then("the user views a modal informing them that the document has been issued")
    public void theUserViewsAModalInformingThemThatTheDocumentHasBeenIssued() {
        //donothing
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

    @When("the EUDI Wallet selects an available matching attestation")
    public void theEUDIWalletSelectsAnAvailableMatchingAttestation() {
         //donothing
    }

    @Then("the Wallet uses an attestation not previously presented to any Relying Party")
    public void theWalletUsesAnAttestationNotPreviouslyPresentedToAnyRelyingParty() {
        test.mobile().wallet().dashboardPageIsDisplayed();
        test.mobile().wallet().clickOnDocuments();
    }

    @And("the EUDI Wallet reduces the internal counter of unused attestations")
    public void theEUDIWalletReducesTheInternalCounterOfUnusedAttestations() {
          //donothing
    }

    @Given("the authentication is successful and continue")
    public void theAuthenticationIsSuccessfulAndContinue() throws InterruptedException {
        theUserVisitsTheIssuerServiceOnTheSameDevice();
        theUserRequestsTheIssuanceOfAnAttestationType();
        theIssuerServiceRedirectsTheUserToTheWallet();
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

    @Then("the Wallet displays a counter showing the number of attestations issued")
    public void theWalletDisplaysACounterShowingTheNumberOfAttestationsIssued() {
        //donothing
    }

    @Given("the user views the issuance confirmation modal")
    public void theUserViewsTheIssuanceConfirmationModal() throws InterruptedException {
        theWalletAppIsPollingTheIssuerForTheCredential();
        theUserViewsAModalInformingThemThatTheDocumentHasBeenIssued();
    }

    @When("the user clicks to view the document information")
    public void theUserClicksToViewTheDocumentInformation() {
       //donothing
    }

    @Then("the document is open")
    public void theDocumentIsOpen() {
        //nothing
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
                case "kotlin":
                    test.mobile().issuer().kotlinIssuerService();
                    break;
                case "python":

                    break;
            }
        } else {
            switch (issuerType.toLowerCase()) {
                case "kotlin":
                    test.mobile().wallet().launchApp();
                    test.mobile().wallet().checkIfPageIsTrue();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickNextButton();
                    test.mobile().wallet().renterThePin();
                    test.mobile().wallet().clickConfirm();
                    test.mobile().wallet().successMessageOfSetUpPin();
                    test.mobile().wallet().clickAddMyDigitalID();
                    test.mobile().issuer().kotlinIssuerService();
                    break;
                case "python":
                    test.mobile().wallet().launchApp();
                    test.mobile().wallet().checkIfPageIsTrue();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickNextButton();
                    test.mobile().wallet().renterThePin();
                    test.mobile().wallet().clickConfirm();
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
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().insertPidFromList();
                } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().insertMdlFromList();
                }
                break;
            case "credential offer":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
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
                    test.mobile().issuer().issueCredentialsPageIsDisplayed();
                    test.mobile().issuer().clickWalletLink();
                    test.mobile().wallet().viewDataPage();
                    test.mobile().wallet().clickAddButton();
                    test.mobile().issuer().signInUsser();
                    test.mobile().issuer().fillLoginForm();
                    break;
                case "cross device":
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
                    test.mobile().issuer().signInUsser();
                    test.mobile().issuer().fillLoginForm();
                    break;
            }
        } else {
            switch (issueScenario.toLowerCase()) {
                case "same device":
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                    } else {
                        if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                            test.mobile().issuer().issuerService();
                            test.mobile().issuer().sleepMethod();
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
                    } else {
                        test.mobile().issuer().issuerService();
                        test.mobile().issuer().sleepMethod();
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
                        }                        test.mobile().wallet().theQRScannerIsActivatedForIssuance();
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
            test.mobile().wallet().clickExpandVerification();
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_wallet.yml");
            } else {
                verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/ios_kotlin_data_on_wallet.yml");
            }
        }
    }

    @Then("the credential is stored in the Wallet")
    public void theCredentialIsStoredInTheWallet() {
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickExpandVerification();
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
            test.mobile().wallet().clickToViewDetails();
            test.mobile().wallet().clickToViewDetails();
            test.mobile().wallet().clickClose();
        }
    }

    private void verifyMandatoryInfoLabelsPresentInAuthorizePage(String yamlPath) {

        FormYml yml = YmlLoader.load(yamlPath, FormYml.class);

        boolean isAndroid = test.getSystemOperation().equals(Literals.General.ANDROID.label);

        AppiumDriver driver = isAndroid
                ? (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid()
                : (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

        Set<String> screenTexts = collectAllTexts(driver, isAndroid, 8);

        yml.fields.forEach((fieldKey, cfg) -> {

            if (!cfg.required) return;

            if (!screenTexts.contains(fieldKey)) {
                throw new AssertionError("Missing label: " + fieldKey);
            }

            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                if (!screenTexts.contains(cfg.value.trim())) {
                    throw new AssertionError("Missing value: " + cfg.value);
                }
            }
        });
    }

    private Set<String> collectAllTexts(AppiumDriver driver, boolean isAndroid, int maxScrolls) {

        Set<String> allTexts = new HashSet<>();

        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.65);
        int endY = (int) (size.height * 0.35);

        String lastPageSource = "";
        int noChangeCounter = 0;

        for (int i = 0; i < maxScrolls; i++) {

            String pageSource = driver.getPageSource();

            if (pageSource.equals(lastPageSource)) {
                noChangeCounter++;
            } else {
                noChangeCounter = 0;
            }

            lastPageSource = pageSource;

            extractTexts(pageSource, allTexts, isAndroid);

            if (noChangeCounter >= 2) break;

            scrollFast(driver, startX, startY, endY);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return allTexts;
    }

    private void extractTexts(String pageSource, Set<String> allTexts, boolean isAndroid) {

        Pattern pattern;

        if (isAndroid) {
            pattern = Pattern.compile("text=\"(.*?)\"");
        } else {
            pattern = Pattern.compile("label=\"(.*?)\"");
        }

        Matcher matcher = pattern.matcher(pageSource);

        while (matcher.find()) {
            String txt = matcher.group(1).trim();
            if (!txt.isEmpty()) {
                allTexts.add(txt);
            }
        }
    }

    private void scrollFast(AppiumDriver driver, int startX, int startY, int endY) {

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));

        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(180),
                PointerInput.Origin.viewport(), startX, endY));

        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    @When("the user presents the credential to the {}")
    public void theUserPresentsTheCredentialToThe(String verifierType) throws MalformedURLException {
        switch (verifierType.toLowerCase()) {
            case "web verifier":
                test.mobile().wallet().userOpensVerifier();
                break;
        }
    }

    private void slowScroll() {

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            String originalContext = driver.getContext();

            try {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                }

                Dimension size = driver.manage().window().getSize();

                int startX = size.width / 2;
                int startY = (int) (size.height * 0.75);
                int endY = (int) (size.height * 0.30);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 0);

                swipe.addAction(finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                ));

                swipe.addAction(finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));

                swipe.addAction(finger.createPointerMove(
                        Duration.ofMillis(800),
                        PointerInput.Origin.viewport(),
                        startX,
                        endY
                ));

                swipe.addAction(finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                driver.perform(Collections.singletonList(swipe));

            } finally {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context(originalContext);
                }
            }
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            String originalContext = driver.getContext();

            try {

                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                }

                Dimension size = driver.manage().window().getSize();

                int startX = size.width / 2;
                int startY = (int) (size.height * 0.75);
                int endY = (int) (size.height * 0.30);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 0);

                swipe.addAction(finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                ));

                swipe.addAction(finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));

                swipe.addAction(finger.createPointerMove(
                        Duration.ofMillis(800),
                        PointerInput.Origin.viewport(),
                        startX,
                        endY
                ));

                swipe.addAction(finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                driver.perform(Collections.singletonList(swipe));

            } finally {

                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context(originalContext);
                }
            }
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
                            test.mobile().verifier().selectSpecificAttributesOnVerifier(credential);
                            test.mobile().verifier().scrollUntilNext();

                            if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra")
                                    || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {

                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().selectAttributes();
                                test.mobile().verifier().clickSpecificAttributes();
                                test.mobile().verifier().clickSelect();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().scrollUntilSumbit();
                                test.mobile().verifier().clickSubmit();

                            } else {

                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().clickNextForAndroid();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().assertAndClickNext();
                            }

                            break;

                        case "all attributes":

                            test.mobile().verifier().launchSafari();
                            test.mobile().verifier().appOpensSuccessfully();
                            test.mobile().verifier().selectAllAttributes();
                            test.mobile().verifier().scrollUntilNext();

                            if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra")
                                    || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {

                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().scrollUntilSumbit();
                                test.mobile().verifier().clickSubmit();

                            } else {

                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().clickNextForAndroid();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().assertAndClickNext();
                            }

                            break;
                    }

                    test.mobile().verifier().chooseWallet();
                    test.mobile().verifier().viewDataPage();
                    test.mobile().wallet().clickPIDFromKotlin();
                    test.mobile().wallet().unselectData();
                    test.mobile().wallet().closeCorrespondingMessage();
                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().pinFieldIsDisplayed();
                    test.mobile().verifier().insertPIN();
                    test.mobile().wallet().authenticationSuccessfully();

                    break;

                case "cross device":

                    test.webWebDriverFactory().startWebDriverSession();

                    try {

                        test.webWebDriverFactory().getDriverWeb().get("https://verifier.eudiw.dev/home");
                        test.web().verifier().appOpensSuccessfullyOnWeb();
                        test.web().verifier().selectAllAttributesOnWeb();
                        test.web().verifier().scrollUntilNextOnWeb();
                        test.web().verifier().pidIsDisplayedOnWeb();
                        test.web().verifier().scrollUntilNextOnWeb();
                        test.web().verifier().uriMethodIsDisplayed();
                        test.web().verifier().scrollUntilSubmitOnWeb();
                        test.web().verifier().assertQrCodeIsVisible();
                        test.web().verifier().captureScreenOnWeb();

                    } catch (org.openqa.selenium.WebDriverException e) {

                        e.printStackTrace();
                    }

                    theUserIsOnTheLoginScreen();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickAuthenticate();
                    test.mobile().wallet().clickOnlinePresentation();
                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }                    test.mobile().wallet().theQRScannerIsActivated();
                    test.mobile().wallet().mockQRInject(test.web().verifier().getCapturedScreenFile());
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

                            if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra")
                                    || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {

                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().selectAttributes();
                                test.mobile().verifier().clickSpecificAttributes();
                                test.mobile().verifier().clickSelect();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().scrollUntilSumbit();
                                test.mobile().verifier().clickSubmit();

                            } else {

                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().clickNextForAndroid();
                                test.mobile().verifier().clickNext();
                                test.mobile().verifier().assertAndClickNext();
                            }

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


                            if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra")
                                        || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {


                                    test.mobile().verifier().clickNext();
                                    test.mobile().verifier().scrollUntilSumbit();
                                    test.mobile().verifier().clickSubmit();

                                } else {

                                    test.mobile().verifier().clickNextForAndroid();
                                    test.mobile().verifier().clickNext();
                                    test.mobile().verifier().assertAndClickNext();
                                }

                            break;
                    }

                    test.mobile().verifier().chooseWallet();
                    if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
                        test.mobile().wallet().createAPin();
                    }
                    test.mobile().verifier().viewDataPage();

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {

                        test.mobile().wallet().clickMDLFromKotlin();

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        } else {
                            test.mobile().wallet().unselectDataForMdlKotlinAllAttributes();
                        }

                    } else {

                        test.mobile().wallet().clickToViewDetails();
                        test.mobile().wallet().unselectDataForMdlPython();
                    }

                    test.mobile().wallet().closeCorrespondingMessage();
                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        }
                    } else {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlPython();
                        }
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet.yml");

                        } else {

                            test.mobile().wallet().clickExpandVerification();
                            test.mobile().wallet().clickToViewDetails();

                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/pre_final_shared_data_on_wallet_all_attributes_ios.yml");
                            }
                        }

                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {

                            verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/kotlin_pre_final_shared_data_on_wallet.yml");

                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/kotlin_pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                verifyMandatoryInfoLabelsPresentInAuthorizePage(
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

                                test.webWebDriverFactory().getDriverWeb().get("https://verifier.eudiw.dev/home");
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

                                test.webWebDriverFactory().getDriverWeb().get("https://verifier.eudiw.dev/home");
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

                    } else {

                        test.mobile().wallet().clickToViewDetails();
                        test.mobile().wallet().unselectDataForMdlPython();
                    }

                    test.mobile().wallet().closeCorrespondingMessage();
                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlKotlin();
                        }
                    } else {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().unselectDataForMdlPython();
                        }
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet.yml");

                        } else {

                            test.mobile().wallet().clickExpandVerification();
                            test.mobile().wallet().clickToViewDetails();

                            verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet_all_attributes.yml");
                        }

                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/kotlin_pre_final_shared_data_on_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/mDL/kotlin_pre_final_shared_data_on_wallet_all_attributes.yml");
                            } else {
                                verifyMandatoryInfoLabelsPresentInAuthorizePage(
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
                    test.mobile().issuer().sleepMethod();
//        test.mobile().wallet().checkDataOnVerifierFromWallet();
                    test.mobile().verifier().clickCloseOnVerifier();
                    break;
                case "cross device":
                    test.mobile().wallet().clickClose();
//                    test.web().verifier().walletRespondedOnWeb();
                    test.web().verifier().clickViewContentOnWeb();
//                test.web().verifier().sleepMethod();
//        test.mobile().wallet().checkDataOnVerifierFromWallet();
                    test.web().verifier().clickCloseOnVerifier();
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
                            verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/data_on_verifier_from_wallet.yml");
                        }else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/mDL/data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
//                                verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/mDL/data_on_verifier_from_wallet_all_attributes_ios.yml");
                            }
                        }
                    } else {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_verifier_from_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
                                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                    verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes_ios.yml");
                                } else {
                                    //to do
                                }
                            }
                        }
                    }
                    test.mobile().issuer().sleepMethod();
                    break;
                case "cross device":
                    test.mobile().wallet().clickClose();
                    test.web().verifier().checkTheResponse();
//                        test.web().verifier().walletRespondedOnWebforMdlKotlin();
//                        test.web().verifier().clickViewContentOnWeb();
                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/data_on_verifier_from_wallet.yml");
                        }else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
                                verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/data_on_verifier_from_wallet_all_attributes_ios.yml");
                            }
                        }
                    } else {
                        if ("specific attributes".equalsIgnoreCase(this.selectiveDisclosure)) {
                            verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/kotlin_data_on_verifier_from_wallet.yml");
                        } else {
                            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                                verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes.yml");
                            } else {
//                                verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb("testdata/mDL/kotlin_data_on_verifier_from_wallet_all_attributes_ios.yml");
                            }
                        }
                    }
                    test.web().verifier().clickCloseOnVerifierWeb();
                    test.webWebDriverFactory().quitDriverWeb();
                    break;
            }
        }
    }

    private void verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb(String yamlPath) {
        FormYml yml = YmlLoader.load(yamlPath, FormYml.class);
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();

        // Wait for first required field to appear before reading page text
        String firstRequiredKey = yml.fields.entrySet().stream()
                .filter(e -> e.getValue().required)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if (firstRequiredKey != null) {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(d -> d.findElement(By.tagName("body")).getText().contains(firstRequiredKey));
        }

        String pageText = driver.findElement(By.tagName("body")).getText();

        yml.fields.forEach((fieldKey, cfg) -> {

            if (!cfg.required) return;

            if (!pageText.contains(fieldKey)) {
                throw new AssertionError("Label not found: " + fieldKey);
            }

            if (cfg.value != null && !cfg.value.trim().isEmpty()) {

                if (!pageText.contains(cfg.value.trim())) {
                    throw new AssertionError(
                            "Wrong value for " + fieldKey +
                                    " expected: " + cfg.value
                    );
                }
            }
        });
    }


    private void verifyMandatoryInfoLabelsPresentInAuthorizePageAllAttributes(String yamlPath) throws InterruptedException {

        FormYml yml = YmlLoader.load(yamlPath, FormYml.class);

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            switchToWebView(driver);

            String pageText = getPageText(driver);

            yml.fields.forEach((fieldKey, cfg) -> {

                if (!cfg.required) return;

                if (!pageText.contains(fieldKey)) {
                    throw new AssertionError("Label not found: " + fieldKey);
                }

                if (cfg.value != null && !cfg.value.trim().isEmpty()) {

                    if (!pageText.contains(cfg.value.trim())) {
                        throw new AssertionError(
                                "Wrong value for " + fieldKey +
                                        " expected: " + cfg.value
                        );
                    }
                }
            });

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

            wait.until(d -> {
                List<WebElement> els = driver.findElements(
                        AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText'")
                );

                return els.size() > 5;
            });

            List<WebElement> els = driver.findElements(
                    AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText'")
            );

            for (WebElement el : els) {
                System.out.println("TEXT FOUND: " + el.getAttribute("name"));
            }

            String pageText = collectAllTextsIOS(driver);

            System.out.println("PAGE TEXT: " + pageText);

            yml.fields.forEach((fieldKey, cfg) -> {

                if (!cfg.required) return;

                String normalizedKey = fieldKey.toLowerCase();

                if (!pageText.contains(normalizedKey)) {
                    throw new AssertionError("Missing label: " + fieldKey);
                }

                if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                    String value = cfg.value.trim().toLowerCase();

                    if (!pageText.contains(value)) {
                        throw new AssertionError("Missing value: " + cfg.value);
                    }
                }
            });
        }
    }

    private String collectAllTextsIOS(IOSDriver driver) {

        List<WebElement> elements = driver.findElements(
                AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText'")
        );

        StringBuilder allText = new StringBuilder();

        for (WebElement el : elements) {
            String text = el.getAttribute("name");

            if (text != null && !text.trim().isEmpty()) {
                allText.append(text.toLowerCase()).append(" ");
            }
        }

        return allText.toString();
    }

    private String getPageText(AndroidDriver driver) {
        return driver.findElement(By.tagName("body")).getText();
    }

    private void switchToWebView(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(d -> {
            for (String context : driver.getContextHandles()) {

                System.out.println("Found context: " + context);

                if (context.contains("WEBVIEW")) {
                    try {
                        driver.context(context);

                        if (driver.getPageSource().length() > 0) {
                            System.out.println("Switched to WebView: " + context);
                            return true;
                        }

                    } catch (Exception e) {
                        System.out.println("WebView not ready yet...");
                    }
                }
            }
            return false;
        });
    }
}
