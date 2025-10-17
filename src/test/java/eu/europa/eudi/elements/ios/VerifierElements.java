package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class VerifierElements {
    public static By clickPersonIdentificationData = By.xpath("//XCUIElementTypeButton[@name='Person Identification Data (PID)']");
    public static By clickNext = By.xpath("//XCUIElementTypeButton[@name=\"Next\"]");
    public static By chooseWallet = By.xpath("//XCUIElementTypeStaticText[@name=\"OPEN WITH YOUR WALLET\"]");
    public static By viewDataPage = By.xpath("//XCUIElementTypeStaticText[@name=\"The following transaction requires your permission and authentication.\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Proceed to authentication\"]");
    public static By chooseData2 = By.xpath("//XCUIElementTypeSwitch[@name=\"Given name\"]");
    public static By walletResponded = By.xpath("//XCUIElementTypeStaticText[@name=\"eu.europa.ec.eudi.pid.1\"]");
    public static By clickSelect = By.xpath("//XCUIElementTypeButton[@name=\"Select\"]");
    public static By clickTransactionsLogs = By.xpath("//XCUIElementTypeStaticText[@name=\"transaction log\"]");
    public static By clickTransactionInitialized = By.xpath("//XCUIElementTypeButton[contains(@name, \"Transaction initializedVerifier\")]");
    public static By chooseWalletPageDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Invoke Wallet\"]");
    public static By selectAttributesBy = By.xpath("(//XCUIElementTypeButton[@name=\"-- attributes by --\"])[3]");
    public static By selectSpecificAtt = By.xpath("//XCUIElementTypeOther[@name=\"Specific attributes\"]");
    public static By allAttributes = By.xpath("//XCUIElementTypeOther[@name=\"All attributes\"]");
    public static By clickFormat = By.xpath("(//XCUIElementTypeButton[@name=\"-- format --\"])[3]");
    public static By msoMdoc = By.xpath("//XCUIElementTypeOther[@name=\"mso_mdoc\"]");
    public static By clickShareAttributes = By.xpath("//XCUIElementTypeButton[@name=\"Select Attributes\"]");
    public static By firstAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Family name\"]");
    public static By secondAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Given name\"]");
    public static By thirdAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Birthdate\"]");
    public static By fourthAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Age over 18\"]");
    public static By fifthAttribute = By.xpath("//XCUIElementTypeStaticText[@name=\"Birth place\"]");
}