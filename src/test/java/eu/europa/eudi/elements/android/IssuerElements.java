package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By requestCredentialsPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please select credentials\"]");
    public static By checkPID = By.xpath("//android.widget.TextView[@text=\" PID (MSO Mdoc)\"]/preceding-sibling::android.widget.CheckBox"); //(//android.widget.CheckBox[@resource-id="check"])[8]
    public static By clickSubmitButton = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//android.widget.TextView[@text=\"QR Code\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//android.widget.RadioButton[@text=\"Country Selection\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please select your country of origin\"]");
    public static By successfullyShared = By.id("document_success_screen_content_header_description");
    public static By authorizePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please review the selected attributes before sending to the EudiWallet demo app.\"]");
    public static By clickFormEu = By.xpath("//android.widget.TextView[@text=\"FormEU\"]");
    public static By clickGivenName = By.xpath("//android.view.View[@text=\"Given Name\"]/following-sibling::android.widget.EditText[1]\n");
    public static By clickFamilyName = By.xpath("//android.view.View[@text='Family Name']/../android.widget.EditText");
    public static By clickBirthDate = By.xpath("//android.widget.Spinner");
    public static By chooseSet = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    public static By enterDocumentNumber = By.xpath("//android.view.View[@text=\"Document Number\"]/following-sibling::android.widget.EditText[1]\n");
    public static By closeKeyboardBefore03 = By.xpath("//android.view.View[@text=\"Document Number\"]");
    public static By clickIssueDate = By.xpath("//android.view.View[@text='Issue Date']/following-sibling::android.widget.Spinner[1]\n");
    public static By clickExpiryDate = By.xpath("//android.view.View[@text='Expiry Date']/following-sibling::android.widget.Spinner[1]\n");
    public static By clickSubmit = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By clickScreen = By.xpath("//android.view.View[@text=\"Expiry Date\"]");
    public static By authorize = By.xpath("//android.widget.Button[@text=\"Authorize\"]");
    public static By clickCountry = By.xpath("//android.view.View[@text=\"Country\"]/following-sibling::android.widget.EditText[1]");
    public static By clickPlaceOfBirth = By.xpath("//android.widget.TextView[@text=\"Place Of Birth\"]");
    public static By clickCountryCode = By.xpath("//android.view.View[@text=\"Country Code\"]/following-sibling::android.widget.EditText[1]");
    public static By closeKeyboard = By.xpath("//android.widget.TextView[@text=\"Nationality\" or @text=\"Nationalities\"]\n");
    public static By closeKeyboardForm = By.xpath("//android.view.View[@text=\"Given Name\"]");
    public static By clickConfirm = By.xpath("//android.widget.Button[@text=\"Confirm\"]");
    public static  By enterCode = By.xpath("//android.view.View[@text='Code']/following-sibling::android.widget.EditText[1]\n");
    public static By clickCode = By.xpath("//android.widget.TextView[@text=\"Codes\"]");
    public static By clickGenerateButton = By.xpath("//android.widget.Button[@text=\"Generate\"]");
    public static By clickUsername = By.xpath("//android.widget.EditText[@resource-id=\"username\"]");
    public static By clickPassword = By.xpath("//android.widget.EditText[@resource-id=\"password\"]");
    public static By pidMsoMdoc = By.xpath("//android.view.View[@resource-id=\"generate-credential-offer-form\"]/android.view.View/android.view.View[1]");
    public static By clickSignIn = By.xpath("//android.widget.Button[@resource-id=\"kc-login\"]");
    public static By nationality = By.xpath("//android.widget.TextView[@text=\"Nationality\"]");
    public static By signPageIsDisplayed = By.xpath("//android.widget.TextView[@resource-id=\"kc-page-title\"]");
}
