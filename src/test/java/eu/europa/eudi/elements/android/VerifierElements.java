package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class VerifierElements {
    public static By clickData = By.xpath("//android.widget.Button[@resource-id=\"mat-expansion-panel-header-1\"]");
    public static By clickNextForVerifier = By.xpath("//android.widget.Button[@text=\"Next\"]");
    public static By presentationQueryTypeIsVisible = By.xpath("//android.widget.TextView[@text=\"Request URI Method\"]");
    public static By chooseWallet = By.xpath("//android.widget.TextView[@text=\"OPEN WITH YOUR WALLET\"]");
    public static By viewDataPage = By.xpath("//android.widget.TextView[@text=\"The following transaction requires your permission and authentication.\"]");
    public static By appOpensSuccessfully = By.xpath("//android.widget.TextView[@text=\"Define your presentation request\"]");
    public static By walletResponded = By.xpath("//android.widget.TextView[@text=\"eu.europa.ec.eudi.pid.1\"]");
    public static By clickTransactionsLogs = By.xpath("//android.widget.TextView[@text=\"transaction log\"]");
    public static By clickTransactionInitialized = By.xpath("//android.widget.Button[@resource-id=\"mat-expansion-panel-header-2\"]");
    public static By chooseWalletPageDisplayed = By.xpath("//android.widget.TextView[@text=\"Invoke Wallet\"]");
    public static By selectAttributes = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By firstAttribute = By.xpath("//android.view.View[@resource-id='mat-option-1'] | //android.widget.ListView[@resource-id='mat-select-0-panel'][1]");
    public static By clickFormat = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[3]/android.view.View");
    public static By msoMdoc = By.xpath("//android.view.View[@resource-id='mat-option-2'] | //android.widget.ListView[@resource-id='mat-select-1-panel'][1]");
    public static By msoMdocReal = By.xpath("//android.view.View[@resource-id=\"mat-option-23\"]");
    public static By specificAttributes = By.xpath("//XCUIElementTypeOther[@name=\"Specific attributes\"]");
    public static By selectAttributesButton = By.xpath("//android.widget.Button[@text=\"Select Attributes\"]");
    public static By clickSelect = By.xpath("//android.widget.Button[@text=\"Select\"]");
    public static By appOpensSuccessfullyOnWeb =  By.xpath("//h4[normalize-space()='Define your presentation request']");;
    public static By clickDataOnWeb = By.xpath("//mat-expansion-panel-header[.//mat-panel-title[normalize-space()='Person Identification Data (PID)']]");
    public static By selectAttributesOnWeb = By.xpath("//mat-form-field[.//mat-label[normalize-space()='-- attributes by --']]//div[contains(@class,'mat-mdc-select-trigger')]");
    public static By firstAttributeOnWeb =  By.xpath("//mat-option//span[normalize-space()='All attributes']");
    public static By clickFormatOnWeb = By.xpath("//mat-form-field[.//mat-label[normalize-space()='-- format --']]//div[contains(@class,'mat-mdc-select-trigger')]");
    public static By msoMdocOnWeb = By.xpath("//mat-option//span[normalize-space()='mso_mdoc']");
    public static By nextButton = By.xpath("//button[.//span[normalize-space()='Next']]");
    public static By pidIdDisplayedOnWeb = By.xpath("//mat-card-title[normalize-space()='Person Identification Data (PID)']");
    public static By uriMethodIdDisplayedOnWeb = By.xpath("//div[contains(@class,'config-option-label') and normalize-space()='Request URI Method']");
}