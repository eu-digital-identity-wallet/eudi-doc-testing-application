package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By issuerServicePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Testing OpenID for Verifiable Credential Issuance - draft 13\"]");
    public static By clickTestCredentialOffer = By.xpath("//XCUIElementTypeButton[@name=\"WALLET TEST Credential Offer\"]");
    public static By requestCredentialsPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Request Credentials for your EUDI Wallet\"]");
    public static By clickPersonalIdentificationData = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[7]/XCUIElementTypeSwitch");
    public static By clickSubmitButton = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//XCUIElementTypeStaticText[@name=\"Use EUDIW\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//XCUIElementTypeStaticText[@name=\"Country Selection\"]");
}
