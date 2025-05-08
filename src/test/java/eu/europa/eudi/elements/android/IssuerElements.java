package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By clickTestCredentialOffer = By.xpath("//android.widget.Button[@text=\"WALLET TEST Credential Offer\"]") ;
    public static By requestCredentialsPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Request Credentials for your EUDI Wallet\"]");
    public static By clickPersonalIdentificationData = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[8]/android.view.View/android.widget.CheckBox");
    public static By clickSubmitButton = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//android.widget.TextView[@text=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//android.widget.TextView[@text=\"Use EUDIW\"]");
    public static By issuerServicePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Testing OpenID for Verifiable Credential Issuance - draft 13\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//android.widget.RadioButton[@text=\"Country Selection\"]");
    public static By formIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Enter the data for your EUDI Wallet\"]");
    public static By authenticationMethodSelection = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By issuanceDate = By.xpath("//android.widget.CheckBox[@text=\"Issuance date\"]");
    public static By selectAttributes = By.xpath("//android.widget.Button[@text=\"Select Attributes\"]");
    public static By firstAttribute = By.xpath("//android.view.View[@resource-id=\"mat-mdc-checkbox-1\"]/android.view.View/android.view.View/android.widget.TextView[3]");
    public static By secondAttribute = By.xpath("//android.view.View[@resource-id=\"mat-mdc-checkbox-2\"]/android.view.View/android.view.View/android.widget.TextView[3]");
    public static By clickSelect = By.xpath("//android.widget.Button[@text=\"Select\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("");
    public static By successfullyShared = By.xpath("");
    public static By clickShareAttributes = By.xpath("");
}
