package eu.europa.eudi.elements.android;

import org.openqa.selenium.By;

public class IssuerElements {
    public static By requestCredentialsPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Request Credentials for your EUDI Wallet\"]");
    public static By checkPID = By.xpath("(//android.widget.CheckBox[@resource-id=\"check\"])[7]"); //(//android.widget.CheckBox[@resource-id="check"])[8]
    public static By clickSubmitButton = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By qrCodeIsDisplayed = By.xpath("//android.widget.TextView[@text=\"QR Code\"]");
    public static By clickEudiwButton = By.xpath("//android.widget.TextView[@text=\"Use EUDIW\"]");
    public static By authenticationPageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By clickCountrySelection = By.xpath("//android.widget.RadioButton[@text=\"Country Selection\"]");
    public static By formIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Test Provider Form\"]");
    public static By authenticationMethodSelection = By.xpath("//android.widget.TextView[@text=\"Authentication Method Selection\"]");
    public static By selectCountryOfOriginIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Please select your country of origin\"]");
    public static By successfullyShared = By.xpath("//android.widget.TextView[@text=\"You have successfully added the following to your wallet\"]");
    public static By authorizePageIsDisplayed = By.xpath("//android.widget.TextView[@text=\"Authorize data from your EUDI Wallet\"]");
    public static By clickFormEu = By.xpath("//android.widget.RadioButton[@resource-id=\"FC\"]");
    public static By clickGivenName = By.xpath("//android.view.View[@resource-id=\"eidasCountries\"]/android.view.View[2]/android.widget.EditText");
    public static By clickFamilyName = By.xpath("//android.view.View[@resource-id=\"eidasCountries\"]/android.view.View[1]/android.widget.EditText");
    public static By clickBirthDate = By.xpath("//android.widget.Spinner");
    public static By chooseSet = By.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]");
    public static By enterDocumentNumber = By.xpath("//android.webkit.WebView[@text=\"Form for your EUDI Wallet\"]/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[4]/android.widget.EditText");
    public static By documentNumberField = By.xpath("//android.webkit.WebView[@text=\"Form for your EUDI Wallet\"]/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[4]/android.widget.EditText");
    public static By closeKeyboardBefore03 = By.xpath("//android.view.View[@text=\"Portrait:\"]");
    public static By clickIssueDate = By.xpath("//android.view.View[@resource-id=\"DrivingP\"]/android.widget.Spinner[1]");
    public static By clickExpiryDate = By.xpath("//android.view.View[@resource-id=\"DrivingP\"]/android.widget.Spinner[2]");
    public static By clickSubmit = By.xpath("//android.widget.Button[@text=\"Submit\"]");
    public static By clickScreen = By.xpath("//android.view.View[@text=\"Driving Privileges\"]");

    public static By authorize = By.xpath("//android.widget.Button[@text=\"Authorize\"]");
    public static By clickCountry = By.xpath("//android.view.View[@resource-id=\"place_of_birth--container\"]/android.widget.EditText[1]");
    public static By clickPlaceOfBirth = By.xpath("//android.widget.TextView[@text=\"Place Of Birth\"]");
    public static By clickCountryCode = By.xpath("//android.view.View[@resource-id=\"nationality--container\"]/android.widget.EditText");
    public static By closeKeyboard = By.xpath("//android.widget.TextView[@text=\"Nationality\"]");
    public static By clickBirthPlace = By.xpath("//android.view.View[@resource-id=\"eidasCountries\"]/android.view.View[4]/android.widget.EditText");
    public static By closeKeyboardForm = By.xpath("//android.widget.TextView[@text=\"Test Provider Form\"]");
    public static By selectCountryOfOriginIsDisplayedDev = By.xpath("//android.widget.TextView[@text=\"Please select your country of origin\"]");
    public static By formIsDisplayedDev = By.xpath("//android.widget.TextView[@text=\"For testing purposes only.\"]");
    public static By clickFamilyNameDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.widget.EditText[1]");
    public static By givenNameFieldDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.widget.EditText[2]");
    public static By clickCountryDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.view.View[4]/android.widget.EditText[1]");
    public static By clickCountryCodeDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.view.View[2]/android.widget.EditText");
    public static By clickConfirm = By.xpath("//android.widget.Button[@text=\"Confirm\"]");
    public static By authorizePageIsDisplayedDev = By.xpath("//android.widget.TextView[@text=\"Review & Send\"]");
    public static By clickedCountryDev = By.xpath("//android.view.View[@resource-id=\"selectCountryForm\"]/android.view.View[2]/android.widget.EditText[1]");
}
