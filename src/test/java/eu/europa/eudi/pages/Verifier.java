package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.IssuerElements;
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
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Verifier {
    TestSetup test;
    public Verifier(TestSetup test) {
        this.test = test;
    }

    public void selectShareAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickShareData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickPidAuthentication)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAttributes)).click();
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAllAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickFormat)).click();
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSpecificFormat)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickShareData)).click();
        }
    }

    public void clickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickNextForVerifier)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickNext)).click();
        }
    }

    public void chooseData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseData)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseData)).click();
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.viewDataPage)).getText();
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY_IOS.label, pageHeader);
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseData2)).click();
        }
    }

    public void scrollUntilFindIssuanceDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            WebDriver driver = test.mobileWebDriverFactory().getDriverAndroid();
            By locator = By.xpath("//android.widget.CheckBox[@text=\"Issuance date\"]");
            WebElement element = null;
            int i=1;
            while (i<4) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = size.height / 2;  // Start from the middle of the screen
                int endY = (int) (size.height * 0.2);  // Adjust the endY as needed
                new TouchAction<>((PerformsTouchActions) driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                i++;
            }

        } else {
            WebDriver driver = test.mobileWebDriverFactory().getDriverIos();
            int i=1;
            while (i<4) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = size.height / 2;  // Start from the middle of the screen
                    int endY = (int) (size.height * 0.2);  // Adjust the endY as needed
                    new TouchAction<>((PerformsTouchActions) driver)
                            .press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                            .moveTo(PointOption.point(startX, endY))
                            .release()
                            .perform();
                    i++;
                }
            }
        }


    public void clickIssuanceDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.issuanceDate)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.issuanceDate)).click();
        }
    }

    public void selectAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.selectAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.firstAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.secondAttribute)).click();
            test.mobile().verifier().scrollUntilFindIssuanceDate();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.issuanceDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickSelect)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.issuanceDate)).click();
        }
    }
}