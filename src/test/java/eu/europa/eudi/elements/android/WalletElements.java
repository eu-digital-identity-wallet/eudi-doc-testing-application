package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class WalletElements {
    public static final By welcomeScreen = By.xpath("//android.widget.TextView[@text=\"Welcome to your Wallet\"]");
 public static By pinTexfield1 = By.xpath("//android.widget.ScrollView/android.widget.EditText[1]");
    public static By pinTexfield2 = By.xpath("//android.widget.ScrollView/android.widget.EditText[2]");
    public static By pinTexfield3 = By.xpath("//android.widget.ScrollView/android.widget.EditText[3]");
    public static By pinTexfield4 = By.xpath("//android.widget.ScrollView/android.widget.EditText[4]");
    public static By pinTexfield5 = By.xpath("//android.widget.ScrollView/android.widget.EditText[5]");
    public static By pinTexfield6 = By.xpath("//android.widget.ScrollView/android.widget.EditText[6]");
    public static By nextButton = By.xpath("//android.widget.TextView[@text=\"NEXT\"]");
    public static By clickConfirm = By.xpath("//android.widget.TextView[@text=\"CONFIRM\"]");
    public static By successMessage = By.xpath("//android.widget.TextView[@text=\"Your wallet is secured!\"]");
    public static By clickShare = By.xpath("//android.widget.TextView[@text=\"Share\"]");
    public static By authenticationSuccess = By.xpath("//android.widget.TextView[@text=\"You successfully shared the following information with\"]");
    public static By loginPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Welcome back\"]");
    public static By clickPID = By.xpath("//android.widget.TextView[@text='eu.europa.ec.eudi.pid.1' or " + "@text='eu.europa.ec.eudi.pid_mdoc' or " + "@text='PID' or " + "@text='PID (MSO Mdoc)' or " + "@text=' PID (MSO Mdoc)' or " + "@text='PID (MSO Mdoc)']");
   public static By PIDIsDisplayed = By.xpath("//android.widget.TextView[@text=\"PID\" or @text=\"eu.europa.ec.eudi.pid.1\" or @text=\"PID (MSO Mdoc)\"]");
    public static By clickMdlDemo = By.xpath("//android.widget.TextView[@text=\"mDL (MSO Mdoc)\"]");
    public static By mdlIsDisplayed = By.xpath("//android.widget.TextView[@text=\"mDL (MSO Mdoc)\"]");
    public static By confirmsDeletion = By.xpath("//android.view.ViewGroup/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View[2]/android.widget.Button");
    public static By dashboardPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Welcome back, Foteini\"]");
    public static By unselectData = By.xpath("//android.widget.TextView[@text=\"Birth Date\"]");
    public static By correspondingMessageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Choosing not to share certain data may result in failure to issue the requested document.\"]");
    public static By addDoc = By.xpath("//android.view.View[@content-desc=\"Add\"]");
    public static By addDocumentPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Add document\"]");
    public static By clickSubmit = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By successMessageForDrivingIsDisplayed = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");
    public static By issuanceDetails = By.xpath("//android.widget.TextView[@text=\"PID (MSO Mdoc)\"]");
    public static By successMessageIsDisplayedForIssuer = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");

    public static By authorize = By.xpath("//android.widget.Button[@text=\"Authorize\"]");
    public static By detailsOfMdlIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Date of birth\"]");
    public static By detailsOfDocument = By.xpath("//android.widget.TextView[@text=\"Family Name(s)\"]");;
    public static By clickSecondPID = By.xpath("(//android.widget.TextView[@text=\"PID (MSO Mdoc)\"])[2]");
    public static By clickEyeIcon = By.xpath("//android.view.View[@content-desc=\"Hide\"]");
    public static By clickExpandVerification = By.xpath("//android.view.View[@content-desc=\"Arrow down\"]");
    public static By verificationDetails = By.xpath("//android.widget.TextView[@text=\"Birth Date\"]");
    public static By pinFieldIsDisplayed = By.xpath("//android.widget.TextView[@text=\"PIN\"]");
    public static By clickAddMyDigitalID = By.xpath("//android.widget.Button");
    public static By clickDocuments = By.xpath("//android.widget.TextView[@text=\"Documents\"]");
    public static By clickClose = By.xpath("//android.widget.TextView[@text=\"Close\"]");
    public static By documentsPageIsDisplayed = By.xpath("(//android.widget.TextView[@text=\"Documents\"])[1]");
    public static By eyeIcon = By.xpath("//android.view.View[@content-desc=\"Hide\"]"); //androidx.compose.ui.platform.h1/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[4]/android.widget.Button
    public static By clickToAddDocument = By.xpath("//android.view.View[@content-desc=\"Add\"]");
    public static By clickFromList = By.xpath("//android.widget.TextView[@text=\"From list\"]");
    public static By clickBackButton = By.xpath("//android.view.View[@content-desc=\"Go Back\"]");
    public static By clickHomeButton = By.xpath("//android.widget.TextView[@text=\"Home\"]");
    public static By secondPidIsDisplayed = By.xpath("(//android.widget.TextView[@text=\"PID (MSO Mdoc)\"])[2]");
    public static By clickDeleteDocument = By.xpath("//android.widget.ScrollView/android.view.View[3]/android.widget.Button");
    public static By homePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Welcome back, Foteini\"]");
    public static By detailsAreBlurred = By.xpath("//android.view.View[@content-desc=\"Hide\"]");
    public static By clickDownArrow = By.xpath("//android.view.View[@content-desc=\"Arrow down\"]");
    public static By closeCorrespondingMessage = By.xpath("//android.view.View[@content-desc=\"Close sheet\"]");
    public static By clickAdd = By.xpath("//android.widget.TextView[@text=\"Add\"]");
    public static By addPIDPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Choose a digital document from the list below to add to your wallet.\"]");
    public static By successMessageForVerifier = By.xpath("//android.widget.TextView[@text=\"You successfully shared the following information with\"]");
    public static By clickPIDOnDocuments = By.xpath("//android.widget.TextView[@text=\"PID (MSO Mdoc)\"]");
    public static By clickDrivingLicenceButtonOnDocuments = By.xpath("//android.widget.TextView[@text=\"mDL (MSO Mdoc)\"]/ancestor::*[@clickable='true'][1]\n");
    public static By clickPIDOnDocumentsSecond = By.xpath("//android.widget.TextView[@text=\"PID (SD-JWT VC)\"]");
    public static By findConfirm = By.xpath("//android.widget.Button[@text=\"Confirm\"]");
}
