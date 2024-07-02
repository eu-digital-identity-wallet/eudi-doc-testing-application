package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By clickTestCredentialOffer = By.xpath("//android.widget.Button[@text=\"WALLET TEST Credential Offer\"]") ;
    public static By requestCredentialsPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Request Credentials for your EUDI Wallet\"]");
    public static By clickPersonalIdentificationData = By.xpath("//android.webkit.WebView[@text=\"Request Credentials for your EUDI Wallet\"]/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View/android.view.View[6]/android.view.View/android.widget.CheckBox");
    public static By clickSubmitButton = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//android.widget.TextView[@text=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//android.widget.TextView[@text=\"Use EUDIW\"]");
    public static By issuerServicePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Testing OpenID for Verifiable Credential Issuance - draft 13\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//android.widget.RadioButton[@text=\"Country Selection\"]");
}
