package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class VerifierElements {
    public static By clickData = By.xpath("//android.widget.Button[@resource-id=\"mat-expansion-panel-header-1\"]");
    public static By clickNextForVerifier = By.xpath("//android.widget.Button[@text=\"Next\"]");
    public static By presentationQueryTypeIsVisible = By.xpath("//android.widget.TextView[@text=\"Request URI Method\"]");
    public static By chooseWallet = By.xpath("//android.widget.TextView[@text=\"OPEN WITH YOUR WALLET\"]");
    public static By viewDataPage = By.xpath("//android.widget.TextView[@text=\"The following transaction requires your permission and authentication.\"]");
    public static By appOpensSuccessfully = By.xpath("//android.widget.TextView[@text=\"Define your presentation request\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Proceed to authentication\"]");
    public static By chooseData2 = By.xpath("//android.widget.CheckBox[@text=\"Given name\"]");
    public static By walletResponded = By.xpath("//android.widget.TextView[@text=\"eu.europa.ec.eudi.pid.1\"]");
    public static By clickSelect = By.xpath("//android.widget.Button[@text=\"Select\"]");
    public static By clickTransactionsLogs = By.xpath("//android.widget.TextView[@text=\"transaction log\"]");
    public static By clickTransactionInitialized = By.xpath("//android.widget.Button[@resource-id=\"mat-expansion-panel-header-2\"]");
    public static By chooseWalletPageDisplayed = By.xpath("//android.widget.TextView[@text=\"Invoke Wallet\"]");
    public static By selectAttributes = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By selectAttributesEmulator = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By firstAttribute = By.xpath("//android.view.View[@resource-id=\"mat-option-1\"]");
    public static By clickFormat = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[3]/android.view.View");
    public static By msoMdoc = By.xpath("//android.view.View[@resource-id=\"mat-option-2\"]");
    public static By selectAttributesBy = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By clickShareAttributes = By.xpath("//android.widget.Button[@text=\"Select Attributes\"]");
    public static By familyNameAtt = By.xpath("//android.widget.CheckBox[@text=\"Family name\"]");
    public static By givenNameAtt = By.xpath("//android.widget.CheckBox[@text=\"Given name\"]");
    public static By birthDateAtt = By.xpath("//android.widget.CheckBox[@text=\"Birthdate\"]");
    public static By ageOver18Att = By.xpath("//android.widget.CheckBox[@text=\"Age over 18\"]");
    public static By birthPlaceAtt = By.xpath("//android.widget.CheckBox[@text=\"Birth place\"]");
}