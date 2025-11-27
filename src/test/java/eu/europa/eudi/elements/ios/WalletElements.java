package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class WalletElements {
    public static final By welcomeScreen = By.xpath("//XCUIElementTypeStaticText[@name=\"Welcome to your wallet\"]");
    public static By pinTexfield1 = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[1]");
    public static By pinTexfield2 = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[2]");
    public static By pinTexfield3 = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[3]");
    public static By pinTexfield4 = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[4]");
    public static By pinTexfield5 = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[5]");
    public static By pinTexfield6 = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[6]");
    public static By nextButton = By.xpath("//XCUIElementTypeButton[@name=\"Proceed\"]");
    public static By clickConfirm = By.xpath("//XCUIElementTypeButton[@name=\"Confirm\"]");
    public static By successMessage = By.xpath("//XCUIElementTypeStaticText[@name=\"Your wallet is secured!\"]");
    public static By dashboardPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Welcome back, Foteini\"]");
    public static By clickShare = By.xpath("//XCUIElementTypeButton[@label=\"Share\"]");
    public static By authenticationSuccess = By.xpath("//XCUIElementTypeStaticText[@name=\"You successfully shared the following information with\"]");
    public static By clickMdl = By.xpath("//XCUIElementTypeStaticText[@name=\"mDL (MSO Mdoc)\"]");
    public static By mdlIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"mDL (MSO Mdoc)\"]");
    public static By addDoc = By.xpath("//XCUIElementTypeStaticText[@name=\"ADD DOC\"]");
    public static By addDocumentPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Add document\"]");
    public static By clickSubmit = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By loginPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Login\"]");
    public static By successMessageForDrivingIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"You successfully shared the following information with\"]");
    public static By unselectData = By.xpath("(//XCUIElementTypeButton[@name='checkmark.square.fill'])[1] | (//XCUIElementTypeButton[@name='Selected'])[1]");
    public static By clickIssue = By.xpath("//XCUIElementTypeButton[@name=\"Issue\"]");
    public static By successMessageIsDisplayedForIssuer = By.xpath("//XCUIElementTypeStaticText[@name=\"You successfully shared the following information with\"]");
    public static By detailsOfMdlIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Date of birth\"]");
    public static By clickEyeIcon = By.xpath("//XCUIElementTypeButton[@label=\"Hide\"]"); //XCUIElementTypeButton[@name="eye.slash"]

    public static By authorize = By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]");
    public static By pinFieldIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Enter your PIN to share data\"]");
    public static By detailsOfDocument = By.xpath("//XCUIElementTypeStaticText[@label=\"Family Name(s)\"]");
    public static By clickExpandVerification = By.xpath("//XCUIElementTypeImage[@label=\"Go Down\"]"); //"//XCUIElementTypeImage[@name=\"chevron.down\"]"
    public static By verificationDetails = By.xpath("//XCUIElementTypeStaticText[@label=\"Birth Date\"]");
    public static By clickAddMyDigitalID = By.xpath("//XCUIElementTypeButton[@name=\"Add my Digital ID\"]");
    public static By clickPID = By.xpath("//XCUIElementTypeStaticText[@name=\"PID\" or @name=\"eu.europa.ec.eudi.pid_mdoc\" or @name=\"eu_pid_doctype_name\" or @name=\"PID (MSO Mdoc)\" or @name=\" PID (MSO Mdoc)\"]");
    public static By clickDone = By.xpath("//XCUIElementTypeButton[@name=\"Done\"]");
    public static By clickOnDocuments = By.xpath("//XCUIElementTypeButton[@name='doc.fill' or @label='Documents']");
    public static By homePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Welcome back, Foteini\"]");
    public static By documentsPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Documents\"]");
    public static By PIDIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"PID\" or @name=\"eu_pid_doctype_name\" or @name=\"PID (MSO Mdoc)\"]");
    public static By detailsAreBlurred = By.xpath("//XCUIElementTypeOther[@name=\"Hide\"]");
    public static By eyeIcon = By.xpath("//XCUIElementTypeButton[@name=\"Hide\"]"); //XCUIElementTypeButton[@name="eye.slash"]
    public static By detailsAreNotBlurred = By.xpath("//XCUIElementTypeButton[@name=\"Show\"]"); //XCUIElementTypeButton[@name="Hide"]
    public static By clickBackButton = By.xpath("//XCUIElementTypeButton[@label=\"Back\"]"); //XCUIElementTypeButton[@name="chevron.left"]
    public static By clickHomeButton = By.xpath("//XCUIElementTypeButton[@label=\"Home\"]");//"//XCUIElementTypeButton[@name=\"house.fill\"]"
    public static By clickToAddDocument = By.xpath("//XCUIElementTypeOther[@name=\"plus\"]");
    public static By clickFromList = By.xpath("//XCUIElementTypeStaticText[@name=\"Choose from list\"]");
    public static By secondPidIsDisplayed = By.xpath("(//XCUIElementTypeStaticText[@name=\"PID (MSO Mdoc)\"])[2]");
    public static By clickSecondPID = By.xpath("(//XCUIElementTypeStaticText[@name=\"PID\"])[2]");
    public static By clickDeleteDocument = By.xpath("//XCUIElementTypeButton[@name=\"Delete document\"]");
    public static By pinTexfield1Ver = By.xpath("//XCUIElementTypeKey[@name=\"1\"]");
    public static By pinTexfield2Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[3]");
    public static By pinTexfield3Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[4]");
    public static By pinTexfield4Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[5]");
    public static By pinTexfield5Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[6]");
    public static By pinTexfield6Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[7]");
    public static By clickDownArrow = By.xpath("//XCUIElementTypeImage[@name='chevron.down'] | //XCUIElementTypeImage[@name='Go Down']");
    public static By issuanceDetailsNew = By.xpath("//XCUIElementTypeStaticText[@name=\"PID (MSO Mdoc)\"]");
    public static By confirmsDeletion = By.xpath("(//XCUIElementTypeButton[@name=\"Delete document\"])[2]");
    public static By detailsAreBlurredReal = By.xpath("//XCUIElementTypeOther[@name=\"eye.slash\"]");
}