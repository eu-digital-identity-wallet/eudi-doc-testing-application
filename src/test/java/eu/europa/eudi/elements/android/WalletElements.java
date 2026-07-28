package eu.europa.eudi.elements.android;

import eu.europa.eudi.utils.config.EnvDataConfig;
import org.openqa.selenium.By;

public class WalletElements {

    private static final String APP_PACKAGE = new EnvDataConfig().getAppiumAndroidAppPackage();

    public static final By welcomeScreen = By.id(APP_PACKAGE + ":id/pin_screen_title");
    public static By clickConfirm = By.id(APP_PACKAGE + ":id/pin_screen_button");
    public static By successMessage = By.xpath("//android.widget.TextView[@text=\"Your wallet is secured!\"]");
    public static By clickShare = By.xpath("//android.widget.TextView[@text=\"Share\"]");
    public static By authenticationSuccess = By.xpath("//android.widget.TextView[@text=\"You successfully shared the following information with\"]");
    public static By loginPageIsDisplayed = By.id(APP_PACKAGE + ":id/biometric_screen_title");
    public static By clickPID = By.xpath("//android.view.View[@resource-id=\"eu.europa.ec.euidi:id/add_document_screen_attestation_https://issuer.eudiw.dev_eu.europa.ec.eudi.pid_mdoc,eu.europa.ec.eudi.pid_mdoc_deferred,eu.europa.ec.eudi.pid_vc_sd_jwt\"]/android.view.View");
    public static By PIDIsDisplayed = By.xpath("//android.widget.TextView[@text=\"PID\" or @text=\"eu.europa.ec.eudi.pid.1\" or @text=\"PID (MSO MDoc)\"]");
    public static By clickMdlPython = By.xpath("//android.view.View[@resource-id=\"eu.europa.ec.euidi:id/add_document_screen_attestation_https://issuer.eudiw.dev_eu.europa.ec.eudi.mdl_mdoc\"]/android.view.View");
    public static By dashboardPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Welcome back, Nikos\"]");
    public static By unselectData = By.xpath("//android.widget.TextView[@text=\"Birth Date\"]");
    public static By unselectDataPython = By.xpath("//android.widget.TextView[@text=\"Date of birth\"]");
    public static By unselectDataPidPython = By.xpath("//android.widget.TextView[@text=\"Birth Date\"]");
    public static By unselectDataForMdlKotlin = By.xpath("//android.widget.TextView[@text=\"Birth Date\"]");
    public static By addDoc = By.xpath("//android.view.View[@content-desc=\"Add\"]");
    public static By addDocumentPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Add document\"]");
    public static By clickSubmit = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By successMessageForDrivingIsDisplayed = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");
    public static By issuanceDetails = By.xpath("//android.widget.TextView[@text=\"PID (MSO Mdoc)\"]");
    public static By successMessageIsDisplayedForIssuer = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");
    public static By clickExpandVerification = By.xpath("//android.view.View[@content-desc=\"Arrow down\"]");
    public static By pinFieldIsDisplayed = By.xpath("//android.widget.TextView[@text=\"PIN\"]");
    public static By clickAddMyDigitalID = By.id(APP_PACKAGE + ":id/success_screen_primary_button");
    public static By clickDocuments = By.id(APP_PACKAGE + ":id/dashboard_screen_bottom_navigation_item_documents");
    public static By clickClose = By.id(APP_PACKAGE + ":id/document_success_screen_button");
    public static By documentsPageIsDisplayed = By.xpath("(//android.widget.TextView[@text=\"Documents\"])[1]");
    public static By clickToAddDocument = By.id(APP_PACKAGE + ":id/documents_screen_plus_button");
    public static By clickFromList = By.xpath("//android.widget.TextView[@text=\"From list\"]");
    public static By clickBackButton = By.xpath("//android.view.View[@content-desc=\"Go Back\"]");
    public static By secondPidIsDisplayed = By.xpath("(//android.widget.TextView[@text=\"PID (MSO MDoc)\"])");
    public static By homePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Welcome back, Foteini\"]");
    public static By clickDownArrow = By.xpath("//android.view.View[@content-desc=\"Arrow down\"]");
    public static By closeCorrespondingMessage = By.xpath("//android.view.View[@content-desc=\"Close sheet\"]");
    public static By clickAdd = By.xpath("//android.widget.TextView[@text=\"Add\"]");
    public static By scanQRButton = By.xpath("//android.view.ViewGroup/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View[2]/android.widget.Button");
    public static By onlyThisTimeQR = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_one_time_button\"]");
    public static By authenticateButton = By.xpath("//android.widget.TextView[@text=\"Authenticate\"]");
    public static By walletLink = By.xpath("//android.widget.TextView[@text=\"Link\"]");
    public static By addButton = By.xpath("//android.widget.TextView[@text=\"Add\"]");
    public static By clickExpandDetails = By.xpath("//android.view.View[@content-desc=\"Arrow down\"]");
    public static By clickPidFromKotlin = By.xpath("//android.widget.TextView[@text=\"PID (MSO MDoc)\"]");;
    public static By scanQRIsActivatedForIssuance = By.xpath("//android.widget.TextView[@text=\"Scan a QR code provided from an issuer to add a digital document to your wallet.\"]");
    public static By selectMDLKotlinCredential = By.xpath("//android.widget.CheckBox[@resource-id=\"credentialId-Eaa-0\"]");
    public static By mdlIsDisplayedKotlin = By.xpath("//android.widget.TextView[@text=\"Mobile Driving Licence (MSO MDoc)\"]");
    public static By unselectDataForMdlKotlinAllAttributes = By.xpath("//android.widget.TextView[@text=\"Administrative Number\"]");;
    public static By selectMDLPythonCredential = By.xpath("//android.widget.TextView[@text=\" mDL (MSO Mdoc)\"]/preceding-sibling::android.widget.CheckBox");
    public static By selectPIDPythonCredential = By.xpath("//android.widget.TextView[@text=' PID (MSO Mdoc)']/../android.widget.CheckBox");
    public static By clickMSISDNPython = By.xpath("//android.widget.TextView[@text=\"MSISDN (MSO Mdoc)\"]");
    public static By onlinePresentation = By.xpath("//android.widget.TextView[@text=\"Online\"]");
    public static By clickPidFromKotlinFromList = By.xpath("//android.view.View[@resource-id=\"eu.europa.ec.euidi:id/add_document_screen_attestation_https://issuer-backend.eudiw.dev_eu.europa.ec.eudi.pid_mso_mdoc,eu.europa.ec.eudi.pid_mso_mdoc_deferred,eu.europa.ec.eudi.pid_vc_sd_jwt,eu.europa.ec.eudi.pid_vc_sd_jwt_deferred\"]/android.view.View");
    public static By clickPowerOfPresentation = By.xpath("//android.widget.TextView[@text=\"Power Of Representation (MSO Mdoc)\"]");
    public static By clickMdlKotlin = By.xpath("//android.view.View[@resource-id=\"eu.europa.ec.euidi:id/add_document_screen_attestation_https://issuer-backend.eudiw.dev_org.iso.18013.5.1.mDL\"]");
}
