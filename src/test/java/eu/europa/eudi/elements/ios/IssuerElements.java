package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By issuerServicePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Testing OpenID for Verifiable Credential Issuance - draft 13\"]");
    public static By clickTestCredentialOffer = By.xpath("//XCUIElementTypeButton[@name=\"WALLET TEST Credential Offer\"]");
    public static By requestCredentialsPageIsDisplayed = By.xpath("//XCUIElementTypeOther[@name=\"Request Credentials for your EUDI Wallet\" and @value=\"1\"]");
    public static By clickPersonIdentificationData = By.xpath("//XCUIElementTypeButton[@name=\"Person Identification Data (PID)\"]");
    public static By clickSubmitButton = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//XCUIElementTypeStaticText[@name=\"Use EUDIW\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//XCUIElementTypeStaticText[@name=\"Country Selection\"]");
    public static By issuanceDate = By.xpath("//XCUIElementTypeSwitch[@name=\"Issuance date\"]");
    public static By formIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Enter the data for your EUDI Wallet\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Please select your country of origin\"]");
    public static By successfullyShared = By.xpath("//XCUIElementTypeStaticText[@name=\"You successfully shared the following information with\"]");
    public static By selectAttributesBy = By.xpath("(//XCUIElementTypeButton[@name=\"-- attributes by --\"])[3]");
    public static By allAttributes = By.xpath("//XCUIElementTypeOther[@name=\"All attributes\"]");
    public static By clickFormat = By.xpath("(//XCUIElementTypeButton[@name=\"-- format --\"])[3]");
    public static By msoMdoc = By.xpath("//XCUIElementTypeOther[@name=\"mso_mdoc\"]");
    public static By clickNext = By.xpath("//XCUIElementTypeButton[@name=\"Next\"]");
    public static By selectSpecificAtt = By.xpath("//XCUIElementTypeOther[@name=\"Specific attributes\"]");
    public static By clickShareAttributes = By.xpath("//XCUIElementTypeButton[@name=\"Select Attributes\"]");
    public static By firstAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Family name\"]");
    public static By secondAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Given name\"]");
    public static By thirdAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Birthdate\"]");
    public static By fourthAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Age over 18\"]");
    public static By fifthAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Birth place\"]");
    public static By clickSelect = By.xpath("//XCUIElementTypeButton[@name=\"Select\"]");
    public static By authorizePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Authorize data from your EUDI Wallet\"]");
    public static By clickPID = By.xpath("(//XCUIElementTypeSwitch[@value=\"0\"])[22]");
    public static By clickCountry = By.xpath("//XCUIElementTypeTextField[@name=\"Country\"]");
    public static By clickPlaceOfBirth = By.xpath("//XCUIElementTypeStaticText[@name=\"Place Of Birth\"]");
}
