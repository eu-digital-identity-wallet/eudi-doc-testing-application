package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class WalletElements {
    public static final By welcomeScreen = By.id("pin_screen_title");
    public static By pinTexfield1 = By.id("pin_text_field_0");
    public static By pinTexfield2 = By.id("pin_text_field_1");
    public static By pinTexfield3 = By.id("pin_text_field_2");
    public static By pinTexfield4 = By.id("pin_text_field_3");
    public static By pinTexfield5 = By.id("pin_text_field_4");
    public static By pinTexfield6 = By.id("pin_text_field_5");
    public static By nextButton = By.id("Proceed");
    public static By clickConfirm = By.id("Confirm");
    public static By successMessage = By.id("success_screen_title");
    public static By dashboardPageIsDisplayed = By.id("home_tab_screen_username_text");
    public static By clickShare = By.id("Share");
    public static By authenticationSuccess = By.xpath("//XCUIElementTypeStaticText[@label=\"You successfully shared the following information with\"]");
    public static By clickMdl = By.id("mDL (MSO Mdoc)");
    public static By addDoc = By.xpath("//XCUIElementTypeStaticText[@label=\"ADD DOC\"]");
    public static By addDocumentPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@label=\"Add document\"]");
    public static By clickSubmit = By.xpath("//XCUIElementTypeButton[@label=\"Submit\"]");
    public static By loginPageIsDisplayed = By.id("biometric_screen_pin_title");
    public static By successMessageForDrivingIsDisplayed = By.id("document_success_screen_content_header_description");
    public static By unselectData = By.xpath("(//XCUIElementTypeButton[@name='checkmark.square.fill'])[1] | (//XCUIElementTypeButton[@name='Selected'])[1]");
    public static By clickIssue = By.id("Issue");
    public static By successMessageIsDisplayedForIssuer = By.id("document_success_screen_content_header_description");
    public static By pinFieldIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@label=\"Enter your PIN to share data\"]");
    public static By clickExpandVerification = By.xpath("//XCUIElementTypeImage[@label=\"Go Down\"]"); //"//XCUIElementTypeImage[@name=\"chevron.down\"]"
    public static By clickAddMyDigitalID = By.id("Go to home");
    public static By clickPID = By.id("PID (MSO Mdoc)");
    public static By clickDone = By.id("Done");
    public static By clickOnDocuments = By.id("documents_tab");
    public static By homePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Welcome back, Foteini\"]");
    public static By documentsPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Documents\"]");
    public static By PIDIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"PID\" or @name=\"eu_pid_doctype_name\" or @name=\"PID (MSO Mdoc)\"]");
    public static By clickBackButton = By.xpath("//XCUIElementTypeButton[@label=\"Back\"]"); //XCUIElementTypeButton[@name="chevron.left"]
    public static By clickHomeButton = By.id("home_tab");
    public static By clickToAddDocument = By.id("plus");
    public static By clickFromList = By.id("Choose from list");
    public static By secondPidIsDisplayed = By.xpath("(//XCUIElementTypeStaticText[@label=\"PID (MSO Mdoc)\"])[2]");
    public static By pinTexfield1Ver = By.xpath("//XCUIElementTypeKey[@name=\"1\"]");
    public static By pinTexfield2Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[3]");
    public static By pinTexfield3Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[4]");
    public static By pinTexfield4Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[5]");
    public static By pinTexfield5Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[6]");
    public static By pinTexfield6Ver = By.xpath("//XCUIElementTypeApplication[@name=\"EUDI Wallet\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeImage[7]");
    public static By clickDownArrow = By.xpath("//XCUIElementTypeImage[@name='chevron.down'] | //XCUIElementTypeImage[@name='Go Down']");
    public static By issuanceDetailsNew = By.xpath("//XCUIElementTypeStaticText[@name=\"PID (MSO Mdoc)\"]");
    public static By addPIDPageIsDisplayed = By.id("add_document_screen_subtitle");
    public static By authenticateButton = By.xpath("//XCUIElementTypeButton[@label=\"Authenticate\"]");
    public static By scanQRIsActivated = By.xpath("//XCUIElementTypeStaticText[@label=\"Scan a QR code provided from an issuer to add a digital document to your wallet.\"]");
    public static By walletLink = By.xpath("//XCUIElementTypeStaticText[@name=\"Link\"]");
    public static By selectMDLKotlin = By.xpath("//XCUIElementTypeOther[@name=\"org.iso.18013.5.1.mDL\"] | //XCUIElementTypeStaticText[@name='mDL (MSO Mdoc)']/preceding-sibling::XCUIElementTypeSwitch[1]");    public static By mdlIsDisplayedKotlin = By.xpath("(//XCUIElementTypeStaticText[@name=\"Mobile Driving Licence (MSO MDoc)\"])");
    public static By clickPidFromKotlin = By.xpath("//XCUIElementTypeImage[@name=\"Go Down\"]");
    public static By unselectDataForMdlKotlin = By.xpath("(//XCUIElementTypeButton[@name=\"Selected\"])[1]");
    public static By scanQR = By.id("Scan QR");
    public static By onlinePresentation = By.id("home_tab_screen_dialog_online_button");
}