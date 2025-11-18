package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By requestCredentialsPageIsDisplayed = By.xpath("//XCUIElementTypeOther[@name=\"Request Credentials for your EUDI Wallet\" and @value=\"1\"]");
    public static By clickSubmitButton = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//XCUIElementTypeStaticText[@name=\"Use EUDIW\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//XCUIElementTypeStaticText[@name=\"Country Selection\"]");
    public static By formIsDisplayed = By.xpath("//*[@name='EUDI Wallet Credential']");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Please select your country of origin\"]");
    public static By successfullyShared = By.xpath("//XCUIElementTypeStaticText[@name=\"You successfully shared the following information with\"]");
    public static By authorizePageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Please review the selected attributes before sending to the EudiWallet demo app.\"]");
    public static By clickPID = By.xpath("//XCUIElementTypeStaticText[@name=\"PID (MSO Mdoc)\"]/preceding-sibling::*[1]\n");
    public static By clickCountry = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[13]/XCUIElementTypeTextField[1]");
    public static By clickPlaceOfBirth = By.xpath("//XCUIElementTypeStaticText[@name=\"Place Of Birth\"]");
    public static By clickFormEu = By.xpath("//XCUIElementTypeStaticText[@name=\"FormEU\"]");
    public static By clickGivenName = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeTextField[2]");
    public static By givenNameField = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeTextField[2]");
    public static By clickFamilyName = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeTextField[1]");
    public static By familyNameField = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeTextField[1]");
    public static By clickBirthDate = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[7]");
    public static By chooseSet = By.xpath("//XCUIElementTypeButton[@name=\"Done\"]");
    public static By enterDocumentNumber = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeTextField[1]");
    public static By documentNumberField = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeTextField[1]");
    public static By closeKeyboardBefore03 = By.xpath("//XCUIElementTypeStaticText[@name=\"Document Number\"]");
    public static By clickIssueDate = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[10]/XCUIElementTypeOther[6]");
    public static By clickExpiryDate = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[10]/XCUIElementTypeOther[4]");
    public static By clickSubmit = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By clickScreen = By.xpath("//XCUIElementTypeStaticText[@name=\"Expiry Date\"]");
    public static By authorize = By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]");
    public static By authenticationMethodSelection = By.xpath("//XCUIElementTypeStaticText[@name=\"Authentication Method Selection\"]");
    public static By clickCountryCode = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[11]/XCUIElementTypeTextField");
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
    public static By enterCode = By.xpath("//XCUIElementTypeOther[@name=\"main\"]/XCUIElementTypeOther[10]/XCUIElementTypeOther[2]/XCUIElementTypeTextField[1]");
    public static By clickCode = By.xpath("//XCUIElementTypeStaticText[@name=\"Code\"]");
}