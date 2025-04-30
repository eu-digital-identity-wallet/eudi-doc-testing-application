package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.ios.IssuerElements;
import eu.europa.eudi.elements.ios.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class Issuer {
    TestSetup test;

    public Issuer(TestSetup test) {
        this.test = test;
    }

    public void issuerService(){
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));
            String url = "https://issuer.eudiw.dev/credential_offer_choice";
            Map<String, Object> args = new HashMap<>();
            args.put("command", "am");
            args.put("args", new String[]{"start", "-a", "android.intent.action.VIEW", "-d", url});
            driver.executeScript("mobile:shell", args);
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.runAppInBackground(Duration.ofSeconds(10));
            driver.activateApp("com.apple.mobilesafari");
            String url = "https://issuer.eudiw.dev/credential_offer_choice";
            driver.get(url);
            Map<String, Object> args = new HashMap<>();
            args.put("bundleId", "com.apple.mobilesafari");
            driver.executeScript("mobile: launchApp", args);
        }
    }

    public void requestCredentialsPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.requestCredentialsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.requestCredentialsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE.label, pageHeader);
        }
    }

    public void clickPersonalIdentificationData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickPersonalIdentificationData)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement elementToDoubleClick = (WebElement) driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickPersonalIdentificationData);
            TouchAction action = new TouchAction(driver);
            action.tap(element(elementToDoubleClick)).perform();
        }
    }

    public void clickSubmitButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickSubmitButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickSubmitButton)).click();
        }
    }

    public void qrCodeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.qrCodeIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.qrCodeIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, pageHeader);
        }
    }

    public void clickUseEudiw() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickEudiwButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickEudiwButton)).click();
        }
    }

    public void authenticationPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
        }
    }

    public void clickCountrySelection() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountrySelection)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement elementToDoubleClick = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountrySelection);
            TouchAction action = new TouchAction(driver);
            action.tap(element(elementToDoubleClick)).perform();
        }
    }

    public void clickFormEu() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickFormEu)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickFormEu)).click();
        }
    }

    public void dataPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.dataPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DATA_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.dataPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DATA_PAGE.label, pageHeader);
        }
    }

    public void enterGivenName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickGivenName)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.android.WalletElements.givenNameField);
            givenName.clear();
            givenName.sendKeys("Foteini");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickGivenName)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.ios.WalletElements.givenNameField);
            givenName.clear();
            givenName.sendKeys("Foteini");        }
    }

    public void enterFamilyName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickFamilyName)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement givenFamily = driver.findElement(eu.europa.eudi.elements.android.WalletElements.givenFamilyField);
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickFamilyName)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenFamily = (WebElement) driver.findElement(eu.europa.eudi.elements.ios.WalletElements.givenFamilyField);
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");        }
    }

    public void chooseBirthDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickBirthDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickBirthDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseSet)).click();        }
    }

    public void enterDocumentNumber() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.enterDocumentNumber)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement searchBar = driver.findElement(eu.europa.eudi.elements.android.WalletElements.documentNumberField);
            searchBar.clear();
            searchBar.sendKeys("1234");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.enterDocumentNumber)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement searchBar = driver.findElement(eu.europa.eudi.elements.ios.WalletElements.documentNumberField);
            searchBar.clear();
            searchBar.sendKeys("1234");        }
    }

    public void chooseIssueDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickIssueDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickIssueDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseSet)).click();        }
    }

    public void chooseExpiryDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpiryDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickExpiryDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseSet)).click();        }
    }

    public void clickSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSubmit)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickSubmit)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
    }

    public void clickScreen() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickScreen)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickScreen)).click();
        }
    }

    public void authenticationMethodSelection() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);
        }
    }

    public void scrollUntilFindDate() {
        WebDriver driver;

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = test.mobileWebDriverFactory().getDriverIos();
        }
        int i = 1;
        while (i < 2) {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            Map<String, Object> params = new HashMap<>();
            params.put("direction", "up");
            js.executeScript("mobile: swipe", params);
            i++;

        }
    }

    public void scrollUntilFindSubmit() {
        WebDriver driver;

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = test.mobileWebDriverFactory().getDriverIos();
        }
        int i = 1;
        while (i < 4) {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            Map<String, Object> params = new HashMap<>();
            params.put("direction", "up");
            js.executeScript("mobile: swipe", params);
            i++;

        }
    }




    public void scrollUntilAuthorize() {
        WebDriver driver;

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = test.mobileWebDriverFactory().getDriverIos();
        }
        int i = 1;
        while (i < 3) {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            Map<String, Object> params = new HashMap<>();
            params.put("direction", "up");
            js.executeScript("mobile: swipe", params);
            i++;

        }
    }

    public void clickAuthorize () {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authorize)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.authorize)).click();
        }
    }

    public void formIsDisplayed () {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.formIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.FORM.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.formIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.FORM.label, pageHeader);
        }
    }

    public void clickRemove() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickRemove)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.authorize)).click();
        }
    }

    public void issuePID() {
        selectCountryOfOrigin();
        clickFormEu();
        clickSubmit();
        enterFamilyName();
        enterGivenName();
        chooseBirthDate();
        enterBirthPlace();
        enterCountryCode();
        scrollUntilFindSubmit();
        clickSubmit();
        scrollUntilAuthorize();
        clickAuthorize();
        successfullySharedMessage();
        test.mobile().wallet().clickDone();
    }

    private void successfullySharedMessage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED.label, pageHeader);
        }
    }

    private void enterCountryCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickCountryCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement countryCode = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickCountryCode);
            countryCode.clear();
            countryCode.sendKeys("GR");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickCountryCode)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement countryCode = driver.findElement(WalletElements.clickCountryCode);
            countryCode.clear();
            countryCode.sendKeys("GR");        }
    }

    private void enterBirthPlace() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickBirthPlace)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement birthPlace = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickBirthPlace);
            birthPlace.clear();
            birthPlace.sendKeys("Thessaloniki");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickBirthPlace)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement birthPlace = driver.findElement(WalletElements.clickBirthPlace);
            birthPlace.clear();
            birthPlace.sendKeys("Thessaloniki");        }

    }

    private void selectCountryOfOrigin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.selectCountryOfOriginIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label, pageHeader);
        }
    }
}
