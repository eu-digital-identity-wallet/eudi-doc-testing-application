package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.IssuerElements;
import eu.europa.eudi.elements.ios.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class Verifier {
    TestSetup test;
    public Verifier(TestSetup test) {
        this.test = test;
    }

    public void selectShareAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickShareData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickShareData)).click();
        }
    }

    public void clickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickNext)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickNext)).click();
        }
    }

    public void chooseData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseData)).click();
        }
    }

    public void chooseWallet() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseWallet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseWallet)).click();
        }
    }

    public void viewDataPage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE_IOS.label, pageHeader);
        }
    }

    public void authorizeData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver driver = test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield1).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield2).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield3).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield4).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield5).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield6).sendKeys("1");
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.findElement(WalletElements.pinTexfield1).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield2).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield3).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield4).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield5).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield6).sendKeys("1");        }
    }

    public void appOpensSuccefully() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
        }
    }

    public void AuthenticationPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.AUTHENTICATION_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.AUTHENTICATION_PAGE.label, pageHeader);
        }
    }

    public void chooseData2() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseData2)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseData)).click();
        }
    }

    public void scrollUntilFindIssuanceDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver webDriver = test.mobileWebDriverFactory().getDriverAndroid();
            if (!(webDriver instanceof AppiumDriver)) {
                throw new IllegalStateException(Literals.General.WEB_DRIVER_NOT_INSTANCE_APPIUM_MESSAGE.label);
            }
            AppiumDriver appiumDriver = (AppiumDriver) webDriver;
            while (true) {
                List<WebElement> elements = appiumDriver.findElements(eu.europa.eudi.elements.android.IssuerElements.issuanceDate);
                if (!elements.isEmpty()) {
                    // Element is found, break the loop.
                    break;
                } else {
                    //element not found, scroll once and then check again.
                    Dimension dimension = appiumDriver.manage().window().getSize();
                    int startX = dimension.width / 2;
                    int startY = dimension.height / 2;  // Start from the middle of the screen
                    int endY = (int) (dimension.height * 0.2);  // Adjust the endY as needed

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

    public void clickIssuanceDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.issuanceDate)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseData)).click();
        }
    }
}