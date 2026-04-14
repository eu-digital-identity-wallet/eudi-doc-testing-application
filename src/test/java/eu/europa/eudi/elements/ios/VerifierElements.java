package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class VerifierElements {
    public static By clickPersonIdentificationData = By.xpath("//XCUIElementTypeButton[@name=\"Person Identification Data (PID)\"]");
    public static By clickNext = By.xpath("//XCUIElementTypeButton[@name=\"Next\"]");
    public static By chooseWallet = By.xpath("//XCUIElementTypeStaticText[@name=\"OPEN WITH YOUR WALLET\"]");
    public static By viewDataPage = By.id("document_offer_screen_content_header_description");
    public static By walletResponded = By.xpath("//XCUIElementTypeStaticText[@name=\"eu.europa.ec.eudi.pid.1\"]");
    public static By clickTransactionsLogs = By.xpath("//XCUIElementTypeStaticText[@name=\"transaction log\"]");
    public static By clickTransactionInitialized = By.xpath("//XCUIElementTypeButton[contains(@name, 'Transaction initializedVerifier')] | (//XCUIElementTypeButton)[1]");
    public static By chooseWalletPageDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Invoke Wallet\"]");
    public static By selectAttributesBy = By.xpath("(//XCUIElementTypeButton[@name='-- attributes by --'])[3] | (//XCUIElementTypeOther[@name='-- attributes by --'])[2]");
    public static By allAttributes = By.xpath("//XCUIElementTypeOther[@name=\"All attributes\"]");
    public static By clickFormat = By.xpath("(//XCUIElementTypeButton[@name='-- format --'])[3] | (//XCUIElementTypeOther[@name='-- format --'])[2]");
    public static By msoMdoc = By.xpath("//XCUIElementTypeOther[@name=\"mso_mdoc\"]");
    public static By clickData = By.xpath("//XCUIElementTypeButton[@name=\"Person Identification Data (PID)\"]");
    public static By selectAttributes = By.xpath("(//XCUIElementTypeOther[@name=\"-- attributes by --\"])[1]");
    public static By specificAttributes = By.xpath("//XCUIElementTypeOther[@name=\"Specific attributes\"]");
    public static By selectAttributesButton = By.xpath("//XCUIElementTypeButton[@name=\"Select Attributes\"]");
    public static By clickSelect = By.xpath("//XCUIElementTypeButton[@name=\"Select\"]");
    public static By clickSubmit = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By clickDataMdl = By.xpath("//XCUIElementTypeButton[@label=\"Mobile Driving Licence (MDL)\"]");
    public static By selectAttributesMdl = By.xpath("(//XCUIElementTypeOther[@label=\"-- attributes by --\"])[2]");
    public static By specificAttributesMdl = By.xpath("//XCUIElementTypeOther[@name=\"Specific attributes\"]");
    public static By clickFormatMdl = By.xpath("(//XCUIElementTypeOther[@label=\"-- format --\"])[2]");
    public static By msoMdocMdl = By.xpath("//XCUIElementTypeOther[@name=\"mso_mdoc\"]");
    public static By selectAttributeButton = By.xpath("//XCUIElementTypeButton[@name=\"Select Attributes\"]");
    public static By selectFirstAttribute = By.xpath("//XCUIElementTypeOther[@label=\"Mobile Driving Licence (MDL), web dialog\"]/XCUIElementTypeOther[3]");
    public static By selectSecondAttribute = By.xpath("//XCUIElementTypeOther[@label=\"Mobile Driving Licence (MDL), web dialog\"]/XCUIElementTypeOther[4]");
    public static By selectThirdAttribute = By.xpath("//XCUIElementTypeOther[@label=\"Mobile Driving Licence (MDL), web dialog\"]/XCUIElementTypeOther[5]");
    public static By clickSelectAttributes = By.xpath("//XCUIElementTypeButton[@name=\"Select\"]");
    public static By specificAttributesPageIsDisplayed = By.xpath("//XCUIElementTypeStaticText[@label=\"Mobile Driving Licence (MDL)\"]");;
    public static By viewDataPageOnWallet = By.id("request_screen_description");
    public static By walletRespondedMdlKotlin = By.id("org.iso.18013.5.1.mDL");
    public static By clickViewContent = By.id("View Content");
    public static By attributesPage = By.xpath("//XCUIElementTypeStaticText[@label=\"Mobile Driving Licence (MDL)\"]");
}