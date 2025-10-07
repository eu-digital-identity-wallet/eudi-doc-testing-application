package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By clickTestCredentialOffer = By.xpath("//android.widget.Button[@text=\"WALLET TEST Credential Offer\"]") ;
    public static By requestCredentialsPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Request Credentials for your EUDI Wallet\"]");
    public static By checkPID = By.xpath("//android.widget.TextView[@text=' PID']/preceding-sibling::android.widget.CheckBox");
    public static By clickSubmitButton = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//android.widget.TextView[@text=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//android.widget.TextView[@text=\"Use EUDIW\"]");
    public static By issuerServicePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Testing OpenID for Verifiable Credential Issuance - draft 13\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//android.widget.RadioButton[@text=\"Country Selection\"]");
    public static By formIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Enter the data for your EUDI Wallet\"]");
    public static By authenticationMethodSelection = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By issuanceDate = By.xpath("//android.widget.CheckBox[@text=\"Issuance date\"]");
    public static By selectAttributes = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By selectAttributesEmulator = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By firstAttribute = By.xpath("//android.widget.ListView[@resource-id=\"mat-select-0-panel\"]");
    public static By secondAttribute = By.xpath("//android.widget.ListView[@resource-id='mat-select-1-panel']/*[1]");
    public static By clickSelect = By.xpath("//android.widget.Button[@text=\"Select\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please select your country of origin\"]");
    public static By successfullyShared = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");
    public static By clickShareAttributes = By.xpath("//android.widget.Button[@text=\"Select Attributes\"]");
    public static By clickFormat = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[3]/android.view.View");
    public static By selectAttributesBy = By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-1\"]/android.view.View/android.view.View[1]/android.view.View");
    public static By selectSpecificAtt = By.xpath("//android.view.View[@resource-id=\"mat-option-0\"]");
    public static By msoMdoc = By.xpath("//android.view.View[@resource-id=\"mat-option-2\"]");
//    public static By msoMdoc = By.xpath("//android.widget.ListView[@resource-id=\"mat-select-1-panel\"]");
    public static By authorizePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Review & Send\"]");
    public static By familyNameAtt = By.xpath("//android.widget.CheckBox[@text=\"Family name\"]");
    public static By givenNameAtt = By.xpath("//android.widget.CheckBox[@text=\"Given name\"]");
    public static By birthDateAtt = By.xpath("//android.widget.CheckBox[@text=\"Birthdate\"]");
    public static By ageOver18Att = By.xpath("//android.widget.CheckBox[@text=\"Age over 18\"]");
    public static By birthPlaceAtt = By.xpath("//android.widget.CheckBox[@text=\"Birth place\"]");
    public static By issuerServiceIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Request Credentials for your EUDI Wallet\"]");
    public static By transactionCodeIsDisplayed = By.xpath("//android.view.View[@text=\"Transaction Code\"]");
    public static By getTransactionCode = By.xpath("//android.widget.EditText");
}
