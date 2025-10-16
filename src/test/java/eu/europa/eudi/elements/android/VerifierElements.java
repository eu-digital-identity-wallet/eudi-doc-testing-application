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
}