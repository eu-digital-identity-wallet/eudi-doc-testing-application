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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
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
//            driver.activateApp("com.android.chrome");
            String url = "https://tester.issuer.eudiw.dev/";
            Map<String, Object> args = new HashMap<>();
            args.put("command", "am");
            args.put("args", new String[]{"start", "-a", "android.intent.action.VIEW", "-d", url});
            driver.executeScript("mobile:shell", args);
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.runAppInBackground(Duration.ofSeconds(10));
            driver.activateApp("com.apple.mobilesafari");
            String url = "https://tester.issuer.eudiw.dev/";
            driver.get(url);
            Map<String, Object> args = new HashMap<>();
            args.put("bundleId", "com.apple.mobilesafari");
            driver.executeScript("mobile: launchApp", args);
        }
    }

    public void selectIssueTest() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickTestCredentialOffer)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement elementToDoubleClick = (WebElement) driver.findElement(IssuerElements.clickTestCredentialOffer);
            TouchAction action = new TouchAction(driver);
            action.tap(element(elementToDoubleClick)).perform();
        }
    }

    public void requestCredentialsPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.requestCredentialsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.requestCredentialsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE.label, pageHeader);
        }
    }

    public void clickPersonalIdentificationData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickPersonalIdentificationData)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement elementToDoubleClick = (WebElement) driver.findElement(IssuerElements.clickPersonalIdentificationData);
            TouchAction action = new TouchAction(driver);
            action.tap(element(elementToDoubleClick)).perform();
        }
    }

    public void clickSubmitButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickSubmitButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.clickSubmitButton)).click();
        }
    }

    public void qrCodeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.qrCodeIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.qrCodeIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, pageHeader);
        }
    }

    public void clickUseEudiw() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickEudiwButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.clickEudiwButton)).click();
        }
    }

    public void issuerServicePageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.issuerServicePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.ISSUER_SERVICE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.issuerServicePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.ISSUER_SERVICE.label, pageHeader);
        }
    }

        public void authenticationPageIsDisplayed() {
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authenticationPageIsDisplayed)).getText();
                Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
            } else {
                String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.authenticationPageIsDisplayed)).getText();
                Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
            }
        }

    public void clickCountrySelection() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountrySelection)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement elementToDoubleClick = driver.findElement(IssuerElements.clickCountrySelection);
            TouchAction action = new TouchAction(driver);
            action.tap(element(elementToDoubleClick)).perform();
        }
    }

    public void clickFormEu() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickFormEu)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickFormEu)).click();
        }
    }

    public void dataPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.dataPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DATA_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.dataPageIsDisplayed)).getText();
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
            WebElement givenName = driver.findElement(WalletElements.givenNameField);
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
            WebElement givenFamily = (WebElement) driver.findElement(WalletElements.givenFamilyField);
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");        }
    }

    public void chooseBirthDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickBirthDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickBirthDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseSet)).click();        }
    }

    public void enterDocumentNumber() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.enterDocumentNumber)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(eu.europa.eudi.elements.android.WalletElements.documentNumberField);
            searchBar.clear();
            searchBar.sendKeys("1234");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.enterDocumentNumber)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement searchBar = driver.findElement(WalletElements.documentNumberField);
            searchBar.clear();
            searchBar.sendKeys("1234");        }
    }

    public void chooseIssueDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickIssueDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickIssueDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseSet)).click();        }
    }

    public void chooseExpiryDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpiryDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickExpiryDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseSet)).click();        }
    }

    public void clickSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSubmit)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickSubmit)).click();
        }
    }

    public void clickScreen() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickScreen)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickScreen)).click();
        }
    }

    public void authenticationMethodSelection() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);
        }
    }

    public void scrollUntilFindDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver webDriver = test.mobileWebDriverFactory().getDriverAndroid();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = appiumDriver.findElements(eu.europa.eudi.elements.android.WalletElements.expiryDate);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    //element not found, scroll once and then check again.
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = (int) (dimension.height * 0.8);
                    int endY = (int) (dimension.height * 0.2);
                    new TouchAction((PerformsTouchActions) appiumDriver)
                            .press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .moveTo(PointOption.point(startX, endY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .release()
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .perform();
                }
            }
        } else {
            IOSDriver webDriver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = appiumDriver.findElements(WalletElements.expiryDate);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    //element not found, scroll once and then check again.
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = (int) (dimension.height * 0.8);
                    int endY = (int) (dimension.height * 0.2);
                    new TouchAction((PerformsTouchActions) appiumDriver)
                            .press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .moveTo(PointOption.point(startX, endY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .release()
                            .perform();
                }
            }
        }
    }

    public void scrollUntilFindSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver webDriver = test.mobileWebDriverFactory().getDriverAndroid();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = webDriver.findElements(eu.europa.eudi.elements.android.IssuerElements.clickSubmitButton);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = (int) (dimension.height * 0.8);
                    int endY = (int) (dimension.height * 0.2);
                    new TouchAction((PerformsTouchActions) appiumDriver)
                            .press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .moveTo(PointOption.point(startX, endY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .release()
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .perform();
                }
            }
        } else {
            IOSDriver webDriver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = appiumDriver.findElements(eu.europa.eudi.elements.ios.IssuerElements.clickSubmitButton);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    //element not found, scroll once and then check again.
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = (int) (dimension.height * 0.8);
                    int endY = (int) (dimension.height * 0.2);
                    new TouchAction((PerformsTouchActions) appiumDriver)
                            .press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .moveTo(PointOption.point(startX, endY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .release()
                            .perform();
                }
            }
        }
    }

    public void scrollUntilAuthorize() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver webDriver = test.mobileWebDriverFactory().getDriverAndroid();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = appiumDriver.findElements(eu.europa.eudi.elements.android.WalletElements.authorize);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    //element not found, scroll once and then check again.
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = (int) (dimension.height * 0.8);
                    int endY = (int) (dimension.height * 0.2);
                    new TouchAction((PerformsTouchActions) appiumDriver)
                            .press(PointOption.point(startX, startY))
                            .moveTo(PointOption.point(startX, endY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .release()
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .perform();
                }
            }
        } else {
            IOSDriver webDriver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = appiumDriver.findElements(WalletElements.expiryDate);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    //element not found, scroll once and then check again.
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = (int) (dimension.height * 0.8);
                    int endY = (int) (dimension.height * 0.2);
                    new TouchAction((PerformsTouchActions) appiumDriver)
                            .press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .moveTo(PointOption.point(startX, endY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(test.envDataConfig().getAppiumShortWaitInMilliseconds())))
                            .release()
                            .perform();
                }
            }
        }
    }

    public void clickAuthorize() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.authorize)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickScreen)).click();
        }
    }

    public void countrySelectionIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);
        }
    }

    public void formIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.formIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.FORM.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SELECTION.label, pageHeader);
        }
    }
}
