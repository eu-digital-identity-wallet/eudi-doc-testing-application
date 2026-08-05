package eu.europa.eudi.elements.ios;

import org.openqa.selenium.By;

public class VerifierElements {
    public static By clickPersonIdentificationData = By.xpath("//XCUIElementTypeButton[@name=\"Person Identification Data (PID)\"]");
    public static By clickNext = By.xpath("//XCUIElementTypeButton[@name=\"Next\"]");
    public static By chooseWallet = By.xpath("//XCUIElementTypeStaticText[@name=\"OPEN WITH YOUR WALLET\"]");
    public static By viewDataPage = By.id("document_offer_screen_content_header_description");
    public static By walletResponded = By.xpath("//XCUIElementTypeStaticText[@name=\"eu.europa.ec.eudi.pid.1\"]");
    public static By chooseWalletPageDisplayed = By.xpath("//XCUIElementTypeStaticText[@name=\"Invoke Wallet\"]");
    public static By selectAttributesBy = By.xpath("(//XCUIElementTypeButton[@name='-- attributes by --'])[3] | (//XCUIElementTypeOther[@name='-- attributes by --'])[2]");
    public static By clickFormat = By.xpath("(//XCUIElementTypeButton[@name='-- format --'])[3] | (//XCUIElementTypeOther[@name='-- format --'])[2]");
    public static By msoMdoc = By.xpath("//XCUIElementTypeOther[@name=\"mso_mdoc\"]");
    public static By clickSubmit = By.xpath("//XCUIElementTypeButton[@name=\"Submit\"]");
    public static By clickDataMdl = By.xpath("//XCUIElementTypeButton[@label=\"Mobile Driving Licence (MDL)\"]");
    public static By selectAttributesMdl = By.xpath("(//XCUIElementTypeButton[@label=\"-- attributes by --\"])[2]");
    public static By specificAttributesMdl = By.xpath("//XCUIElementTypeOther[@name=\"Specific attributes\"]");
    public static By clickFormatMdl = By.xpath("(//XCUIElementTypeButton[@label=\"-- format --\"])[2]");
    public static By msoMdocMdl = By.xpath("//XCUIElementTypeOther[@name=\"mso_mdoc\"]");
    public static By selectAttributeButton = By.xpath("//XCUIElementTypeButton[@name=\"Select Attributes\"]");
    public static By clickSelectAttributes = By.xpath("//XCUIElementTypeButton[@name=\"Select\"]");
    public static By viewDataPageOnWallet = By.id("request_screen_description");
    public static By walletRespondedMdlKotlin = By.id("org.iso.18013.5.1.mDL");
    public static By clickViewContent = By.id("View Content");
}