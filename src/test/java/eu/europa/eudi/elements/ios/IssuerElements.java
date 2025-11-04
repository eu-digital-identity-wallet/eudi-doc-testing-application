package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By requestCredentialsPageIsDisplayed = By.xpath("//XCUIElementTypeOther[@name=\"Request Credentials for your EUDI Wallet\" and @value=\"1\"]");
    public static By clickSubmitButton = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//XCUIElementTypeStaticText[@name=\"Use EUDIW\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//XCUIElementTypeStaticText[@name=\"Country Selection\"]");
    public static By formIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Enter the data for your EUDI Wallet\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Please select your country of origin\"]");
    public static By successfullyShared = By.xpath("//XCUIElementTypeStaticText[@name=\"You successfully shared the following information with\"]");
    public static By authorizePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Authorize data from your EUDI Wallet\"]");
    public static By clickPID = By.xpath("(//XCUIElementTypeSwitch[@value=\"0\"])[23]");
    public static By clickCountry = By.xpath("//XCUIElementTypeTextField[@name=\"Country\"]");
    public static By clickPlaceOfBirth = By.xpath("//XCUIElementTypeStaticText[@name=\"Place Of Birth\"]");
    public static By clickFormEu = By.xpath("//XCUIElementTypeStaticText[@name=\"FormEU\"]");
    public static By clickGivenName = By.xpath("//XCUIElementTypeTextField[@name=\"Given Name\"]");
    public static By givenNameField = By.xpath("//XCUIElementTypeTextField[@name=\"Given Name\"]");
    public static By clickFamilyName = By.xpath("//XCUIElementTypeTextField[@name=\"Family Name\"]");
    public static By familyNameField = By.xpath("//XCUIElementTypeTextField[@name=\"Family Name\"]");
    public static By clickBirthDate = By.xpath("//XCUIElementTypeOther[@name=\"Birth Date\"]");
    public static By chooseSet = By.xpath("//XCUIElementTypeButton[@name=\"Done\"]");
    public static By enterDocumentNumber = By.xpath("//XCUIElementTypeTextField[@name=\"Document Number\"]");
    public static By documentNumberField = By.xpath("//XCUIElementTypeTextField[@name=\"Document Number\"]");
    public static By closeKeyboardBefore03 = By.xpath("//XCUIElementTypeStaticText[@name=\"Portrait:\"]");
    public static By clickIssueDate = By.xpath("//XCUIElementTypeOther[@name=\"Issue Date\"]");
    public static By clickExpiryDate = By.xpath("//XCUIElementTypeOther[@name=\"Expiry Date\"]");
    public static By clickSubmit = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By clickScreen = By.xpath("//XCUIElementTypeStaticText[@name=\"Driving Privileges\"]");
    public static By authorize = By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]");
    public static By authenticationMethodSelection = By.xpath("//XCUIElementTypeStaticText[@name=\"Authentication Method Selection\"]");
    public static By clickCountryCode = By.xpath("//XCUIElementTypeTextField[@name=\"Country Code\"]");
    public static By closeKeyboard = By.xpath("//XCUIElementTypeStaticText[@name=\"Nationality\"]");
    public static By clickBirthPlace = By.xpath("//XCUIElementTypeTextField[@name=\"Birth Place\"]");
    public static By selectCountryOfOriginIsDisplayedDev = By.xpath("//XCUIElementTypeStaticText[@name=\"Please select your country of origin\"]");
    public static By formIsDisplayedDev = By.xpath("//XCUIElementTypeStaticText[@name=\"For testing purposes only.\"]");
    public static By familyNameFieldDev = By.xpath("//XCUIElementTypeOther[@name=\"form\"]/XCUIElementTypeTextField[1]");
    public static By givenNameFieldDev = By.xpath("//XCUIElementTypeOther[@name=\"form\"]/XCUIElementTypeTextField[2]");
    public static By clickCountryDev = By.xpath("//XCUIElementTypeOther[@name=\"Country Region Locality\"]/XCUIElementTypeTextField[1]");
    public static By clickCountryCodeDev = By.xpath("(//XCUIElementTypeOther[@name=\"Country Code\"])[1]/XCUIElementTypeTextField");
    public static By clickConfirm = By.xpath("//XCUIElementTypeButton[@name=\"Confirm\"]");
    public static By authorizePageIsDisplayedDev = By.xpath("//XCUIElementTypeStaticText[@name=\"Review & Send\"]");
    public static By clickBirthDateDev = By.xpath("//XCUIElementTypeOther[@name=\"form\"]/XCUIElementTypeOther[5]");
}