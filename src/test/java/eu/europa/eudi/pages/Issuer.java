package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.ios.IssuerElements;
import eu.europa.eudi.elements.ios.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
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

    public void updateTestReference(TestSetup newTest) {
        this.test = newTest;
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.checkPID)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickPID)).click();
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickFormEu)).click();
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
            //test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickScreen)).click();
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
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".scrollForward()" +
                            ".setMaxSearchSwipes(10)" +
                            ".scrollIntoView(new UiSelector().text(\"Issue Date:\"))"
            ));
        } else {
            int i = 1;
            while (i < 2) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
                driver.executeScript("mobile: swipe", params);
                i++;
            }

        }
    }

    public void scrollUntilFindSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(10)" +
                            ".scrollIntoView(new UiSelector().text(\"Submit\"))"
            ));
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
        int i = 1;
        while (i < 5) {
            Map<String, Object> params = new HashMap<>();
            params.put("direction", "up");
            driver.executeScript("mobile: swipe", params);
            i++;
        }

        }
    }

    public void scrollUntilAuthorize() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(10)" +
                            ".scrollIntoView(new UiSelector().text(\"Authorize\"))"
            ));
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 5) {

                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
                driver.executeScript("mobile: swipe", params);
                i++;
            }

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
        enterCountry();
        scrollUntilCountryCode();
        enterCountryCode();
        scrollUntilFindSubmit();
        clickSubmit();
        authorizeIsDisplayed();
        scrollUntilAuthorize();
        clickAuthorize();
        successfullySharedMessage();
        test.mobile().wallet().clickDone();
    }

    private void enterCountry() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickCountry)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement givenFamily = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickCountry);
            givenFamily.clear();
            givenFamily.sendKeys("Greece");
        } else {
           }
    }

    private void scrollUntilCountryCode() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        TouchAction<?> touchAction = new TouchAction<>(driver);
        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.2);
        int endY = (int) (size.height * 0.1);

        touchAction
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();

// Then attempt to find the element
        try {
            driver.findElement(By.xpath("//android.widget.TextView[@text=\"Nationality\"]")).click();
        } catch (NoSuchElementException e) {
            System.out.println("Element not found after single scroll");
        }
    }

    public void authorizeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authorizePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authorizePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
        }
    }

    public void successfullySharedMessage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED_IOS.label, pageHeader);
        }
    }

    public void enterCountryCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickCountryCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement countryCode = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickCountryCode);
            countryCode.clear();
            countryCode.sendKeys("GR");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.closeKeyboard)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickCountryCode)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement countryCode = driver.findElement(WalletElements.clickCountryCode);
            countryCode.clear();
            countryCode.sendKeys("GR");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.closeKeyboard)).click();
        }
    }

    public void enterBirthPlace() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickBirthPlace)).click();
//            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
//            WebElement birthPlace = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickBirthPlace);
//            birthPlace.clear();
//            birthPlace.sendKeys("Thessaloniki");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickBirthPlace)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement birthPlace = driver.findElement(WalletElements.clickBirthPlace);
            birthPlace.clear();
            birthPlace.sendKeys("Thessaloniki");        }

    }

    public void selectCountryOfOrigin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.selectCountryOfOriginIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label, pageHeader);
        }
    }
}
