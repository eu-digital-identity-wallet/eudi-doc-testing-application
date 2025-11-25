package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By requestCredentialsPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please select credentials\"]");
    public static By checkPID = By.xpath("//android.widget.TextView[@text=\" PID (MSO Mdoc)\"]/preceding-sibling::android.widget.CheckBox"); //(//android.widget.CheckBox[@resource-id="check"])[8]
    public static By clickSubmitButton = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//android.widget.TextView[@text=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//android.widget.TextView[@text=\"Use EUDIW\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//android.widget.RadioButton[@text=\"Country Selection\"]");
    public static By formIsDisplayed = By.xpath("//*[@text='EUDI Wallet Credential']");
    public static By authenticationMethodSelection = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please select your country of origin\"]");
    public static By successfullyShared = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");
    public static By authorizePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please review the selected attributes before sending to the EudiWallet demo app.\"]");
    public static By clickFormEu = By.xpath("//android.widget.TextView[@text=\"FormEU\"]");
    public static By clickGivenName = By.xpath("//android.view.View[@text=\"Given Name\"]/following-sibling::android.widget.EditText[1]\n");
    public static By clickFamilyName = By.xpath("//android.view.View[@text=\"Family Name\"]/following-sibling::android.widget.EditText[1]\n");
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
    public static By closeKeyboardForm = By.xpath("//android.view.View[@text=\"Family Name\"]");
    public static By selectCountryOfOriginIsDisplayedDev = By.xpath("//android.widget.TextView[@text=\"Please select your country of origin\"]");
    public static By formIsDisplayedDev = By.xpath("//android.widget.TextView[@text=\"For testing purposes only.\"]");
    public static By clickFamilyNameDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.widget.EditText[1]");
    public static By givenNameFieldDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.widget.EditText[2]");
    public static By clickCountryDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.view.View[4]/android.widget.EditText[1]");
    public static By clickCountryCodeDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.view.View[2]/android.widget.EditText");
    public static By clickConfirm = By.xpath("//android.widget.Button[@text=\"Confirm\"]");
    public static By authorizePageIsDisplayedDev = By.xpath("//android.widget.TextView[@text=\"Review & Send\"]");
    public static By clickedCountryDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.view.View[2]/android.widget.EditText[1]");
    public static  By enterCode = By.xpath("//android.view.View[@text='Code']/following-sibling::*[1]");
    public static By clickCode = By.xpath("//android.widget.TextView[@text=\"Codes\"]");
    public static By codeIsVisible = By.xpath("//android.view.View[@text='Code']");

}
