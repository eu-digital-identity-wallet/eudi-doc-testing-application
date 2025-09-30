package eu.europa.eudi.pages;

import com.google.common.collect.ImmutableMap;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.stepdefs.GeneralStepDefs;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static java.time.Duration.ofMillis;

public class Wallet {

    TestSetup test;

    public Wallet(TestSetup test) {
        this.test = test;
    }

    public void checkIfPageIsTrue() {
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.welcomeScreen)).getText();
                Assert.assertEquals(Literals.Wallet.WELCOME_HEADER.label, pageHeader);
            } else {
                String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.welcomeScreen)).getText();
                Assert.assertEquals(Literals.Wallet.WELCOME_HEADER_IOS.label, pageHeader);
            }
    }

    public void createAPin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String fullPin = test.envDataConfig().getPin();
            char firstDigit = fullPin.charAt(0);
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            int retries = 3;
            while (retries > 0) {
                try {

                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield1)).sendKeys(String.valueOf(firstDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield2)).sendKeys(String.valueOf(secondDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield3)).sendKeys(String.valueOf(thirdDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield4)).sendKeys(String.valueOf(fourthDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield5)).sendKeys(String.valueOf(fifthDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield6)).sendKeys(String.valueOf(sixthDigit));
                    AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
                    driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                    break;
                } catch (Exception e) {
                    retries--;
                    if (retries == 0) throw e;
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                }
            }
        } else {
            String fullPin = test.envDataConfig().getPin();
            char firstDigit = fullPin.charAt(0);
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield2).sendKeys(String.valueOf(secondDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield3).sendKeys(String.valueOf(thirdDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield4).sendKeys(String.valueOf(fourthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield5).sendKeys(String.valueOf(fifthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield6).sendKeys(String.valueOf(sixthDigit));
        }
    }

    public void clickNextButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.nextButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.nextButton)).click();
        }
    }

    public void renterThePin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String fullPin = test.envDataConfig().getPin();
            char firstDigit = fullPin.charAt(0);
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            int retries = 3;
            while (retries > 0) {
                try {
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield1)).sendKeys(String.valueOf(firstDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield2)).sendKeys(String.valueOf(secondDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield3)).sendKeys(String.valueOf(thirdDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield4)).sendKeys(String.valueOf(fourthDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield5)).sendKeys(String.valueOf(fifthDigit));
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pinTexfield6)).sendKeys(String.valueOf(sixthDigit));
                    AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
                    driver.pressKey(new KeyEvent(AndroidKey.ENTER));
                    break;
                } catch (Exception e) {
                    retries--;
                    if (retries == 0) throw e;
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                }
            }
        } else {
            String fullPin = test.envDataConfig().getPin();
            char firstDigit = fullPin.charAt(0);
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1).sendKeys(String.valueOf(firstDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield2).sendKeys(String.valueOf(secondDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield3).sendKeys(String.valueOf(thirdDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield4).sendKeys(String.valueOf(fourthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield5).sendKeys(String.valueOf(fifthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield6).sendKeys(String.valueOf(sixthDigit));
        }
    }

    public void clickConfirm() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickConfirm)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickConfirm)).click();
        }
    }

    public void successMessageOfSetUpPin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.successMessage)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessage)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE.label, pageHeader);
        }
    }

    public void clickShareButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickShare)).click();
            driver.terminateApp("com.android.chrome");
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickShare)).click();
        }
    }

    public void authenticationSuccessfully() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticationSuccess)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SUCCESS.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.authenticationSuccess)).getText();
            Assert.assertEquals(Literals.Wallet.AUTHENTICATION_SUCCESS.label, pageHeader);
        }
    }

    public void loginPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.loginPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.LOGIN.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.loginPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.LOGIN_IOS.label, pageHeader);
        }
    }

    public void clickContinue() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickContinue)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement elementToDoubleClick = (WebElement) driver.findElement(eu.europa.eudi.elements.ios.WalletElements.clickContinue);
            TouchAction action = new TouchAction(driver);
            action.tap(element(elementToDoubleClick)).perform();
        }
    }

    public void loadSampleDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseSampleData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseSampleData)).click();
        }
    }

    public void welcomePage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.welcomePage)).getText();
            Assert.assertEquals(Literals.Wallet.WELCOME_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.welcomePage)).getText();
            Assert.assertEquals(Literals.Wallet.WELCOME_HEADER.label, pageHeader);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    public void userProfilIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.userProfil)).getText();
            Assert.assertEquals(Literals.Wallet.USER_PROFIL.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.welcomeScreen)).getText();
            Assert.assertEquals(Literals.Wallet.WELCOME_HEADER.label, pageHeader);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    public void clickNationalId() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickPID)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickPID)).click();
        }
    }

    public void nationalIdIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.PIDIsDisplayedOnDocument)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.PIDIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        }
    }

    public void clickMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickMdl)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickMdl));
            tapAction(button);
        }
    }

    public void mdlIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.mdlIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.MDL.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.mdlIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.MDL.label, pageHeader);
        }
    }

    public void clickDeleteButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickDeleteButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickDeleteButton)).click();
        }
    }

    public void confirmsDeletion() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.confirmsDeletion)).click();
        }
    }

    public void dashboardPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.dashboardPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DASHBOARD_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.dashboardPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DASHBOARD_PAGE.label, pageHeader);
        }
    }

    public void unselectData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.unselectData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.unselectData)).click();
        }
    }

    public void correspondingMessageIsDisplayed() {
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.correspondingMessageIsDisplayed)).getText();
                Assert.assertEquals(Literals.Wallet.CORRESPONDING_MESSAGE.label, pageHeader);
            } else {
                //String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.correspondingMessageIsDisplayed)).getText();
                //Assert.assertEquals(Literals.Wallet.CORRESPONDING_MESSAGE.label, pageHeader);
            }
    }

    public void clickAgainData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickaAgainData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickaAgainData)).click();
        }
    }

    public void addDocButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement myDigitalIDButton = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.addDoc));
            tapAction(myDigitalIDButton);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.addDoc)).click();
        }
    }

    public void addDocumentPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.addDocumentPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ADD_DOCUMENT.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.addDocumentPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ADD_DOCUMENT.label, pageHeader);
        }
    }

    public void clickNationalIdButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickNationalIdButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickNationalIdButton)).click();
        }
    }

    public void clickSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSubmit)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickSubmit)).click();
        }
    }

    public void successMessageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.successMessageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_PID.label, pageHeader);
        }
    }

    public void clickDrivingLicenceButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickDrivingLicenceButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickDrivingLicenceButton)).click();
        }
    }

    public void drivingLicenceIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.drivingLicenceIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DRIVING_LICENCE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.drivingLicenceIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DRIVING_LICENCE.label, pageHeader);
        }
    }

    public void successMessageForDrivingIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.successMessageForDrivingIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_DRIVING_LICENCE_ANDROID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessageForDrivingIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_DRIVING_LICENCE.label, pageHeader);
        }
    }

    public void drivingLicenceIsDisplayedInDashboard() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.drivingLicenceIsDisplayedInDashboard)).getText();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.drivingLicenceIsDisplayedInDashboard)).getText();
        }
    }

    public void clickXButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickXButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickXButton)).click();
        }
    }

    public void userOpensVerifier() throws MalformedURLException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));
            String url = "https://verifier.eudiw.dev/home";
            Map<String, Object> args = new HashMap<>();
            args.put("command", "am");
            args.put("args", new String[]{"start", "-a", "android.intent.action.VIEW", "-d", url});
            driver.executeScript("mobile:shell", args);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.runAppInBackground(Duration.ofSeconds(10));
            driver.activateApp("com.apple.mobilesafari");
            String url = "https://verifier.eudiw.dev/home";
            driver.get(url);
            Map<String, Object> args = new HashMap<>();
            args.put("bundleId", "com.apple.mobilesafari");
            driver.executeScript("mobile: launchApp", args);
        }
    }

    public void detailsArePresented() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.issuanceDetails)).getText();
            Assert.assertEquals(Literals.Wallet.ISSUANCE_DETAILS.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.issuanceDetailsNew)).getText();
            Assert.assertEquals(Literals.Wallet.ISSUANCE_DETAILS.label, pageHeader);
        }
    }

    public void clickIssue() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAdd)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickIssue)).click();

        }
    }

    public void successMessageIsDisplayedForIssuer() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.successMessageIsDisplayedForIssuer)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessageIsDisplayedForIssuer)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS.label, pageHeader);
        }
    }

    public void scanQrIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.scanQrIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.SCAN_QR.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessageIsDisplayedForIssuer)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS.label, pageHeader);
        }
    }

    public void previewPid() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.PIDIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessageIsDisplayedForIssuer)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS.label, pageHeader);
        }
    }
    public void startAndStopDriver() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.stopAndroidDriverSession();
            boolean noReset = true;
            TestSetup newTest = new TestSetup(noReset, test.getSystemOperation(), test.getScenario());
            newTest.startAndroidDriverSession();
            test.mobile().issuer().updateTestReference(newTest);
            test = newTest;
        } else {
            test.stopIosDriverSession();
            boolean noReset = true;
            TestSetup newTest = new TestSetup(noReset, test.getSystemOperation(), test.getScenario());
            newTest.startIosDriverSession();
            test.mobile().issuer().updateTestReference(newTest);
            test = newTest;
        }
    }


    public void detailsOfPidIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.detailsOfNationalIdIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_NATIONAL_ID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsOfNationalIdIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_NATIONAL_ID.label, pageHeader);
        }
    }

    public void mdlDetailsAreDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.detailsOfMdlIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_MDL.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsOfMdlIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_MDL.label, pageHeader);
        }
    }

    public void detailsOfDocumentIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.detailsOfDocument)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_DOCUMENT.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsOfDocument)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_DOCUMENT.label, pageHeader);
        }
    }

    public void clickSecondPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSecondPID)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickSecondPID)).click();
        }
    }

    public void optionalDataIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.optionalDataIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.OPTIONAL_DATA.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.optionalDataIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.OPTIONAL_DATA.label, pageHeader);
        }
    }
    public void clickEyeIcon() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            try {
                Thread.sleep(500);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickEyeIcon)).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickEyeIcon)).click();
        }
    }




    public void actualDataAreDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.actuallDataIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ACTUAL_DATA.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.actuallDataIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ACTUAL_DATA.label, pageHeader);
        }
    }

    public void clickExpandVerification() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpandVerification)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickExpandVerification)).click();
        }
    }

    public void verificationDetailsAreDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.verificationDetails)).getText();
            Assert.assertEquals(Literals.Wallet.VERIFICATION_DETAILS.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.verificationDetails)).getText();
            Assert.assertEquals(Literals.Wallet.VERIFICATION_DETAILS.label, pageHeader);
        }
    }

    public void pinFieldIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.pinFieldIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PIN_FIELD_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.pinFieldIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PIN_FIELD_IS_DISPLAYED_IOS.label, pageHeader);
        }
    }

    public void clickAddMyDigitalID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement myDigitalIDButton = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickAddMyDigitalID));
            tapAction(myDigitalIDButton);
        } else {
            WebElement myDigitalIDButton = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickAddMyDigitalID));
            tapAction(myDigitalIDButton);
        }
    }

    private void tapAction(WebElement myDigitalIDButton) {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            Point center = myDigitalIDButton.getLocation();
            int x = center.getX() + myDigitalIDButton.getSize().getWidth() / 2;
            int y = center.getY() + myDigitalIDButton.getSize().getHeight() / 2;
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(ofMillis(0),
                            PointerInput.Origin.viewport(),
                            x, y))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.ordinal()))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.ordinal()));
            ((AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid()).perform(Collections.singletonList(tap));
        } else {
            Point center = myDigitalIDButton.getLocation();
            int x = center.getX() + myDigitalIDButton.getSize().getWidth() / 2;
            int y = center.getY() + myDigitalIDButton.getSize().getHeight() / 2;
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(ofMillis(0),
                            PointerInput.Origin.viewport(),
                            x, y))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.ordinal()))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.ordinal()));
            ((AppiumDriver) test.mobileWebDriverFactory().getDriverIos()).perform(Collections.singletonList(tap));
        }
    }

    public void clickPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickPID)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickPID));
            tapAction(button);
        }
    }

    public void clickPIDOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickPIDOnDocuments)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickPID));
            tapAction(button);
        }
    }

    public void clickOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickDocuments)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickOnDocuments)).click();
        }
    }

    public void clickClose() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickClose)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.authorize)).click();
        }
    }

    public void documentsPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.documentsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DOCUMENTS_PAGE_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.documentsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DOCUMENTS_PAGE_IS_DISPLAYED.label, pageHeader);
        }
    }

    public void clickToAddDocument() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement myDigitalIDButton = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickAdd));
            tapAction(myDigitalIDButton);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickToAddDocument)).click();
        }
    }

    public void clickFromList() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickFromListNew)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickFromList));
            tapAction(button);
        }
    }

    public void clickBackButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickBackButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickBackButton)).click();
        }
    }

    public void clickHome() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickHomeButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickHomeButton)).click();
        }
    }

    public void scrollUntilPID() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            By pidLocator = By.xpath("//android.widget.TextView[@text=\"eu.europa.ec.eudi.pid_mdoc\"]");
            int maxSwipes = 15;

            for (int i = 0; i < maxSwipes; i++) {

                if (!driver.findElements(pidLocator).isEmpty()) {
                    System.out.println("Βρέθηκε το στοιχείο PID!");
                    break;
                }

                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.7);
                int endY = (int) (size.height * 0.3);


                new TouchAction<>(driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(ofMillis(800)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                Thread.sleep(50);
            }
        } else {
            int i = 1;
            while (i < 3) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                WebElement scrollView = driver.findElement(MobileBy.className("XCUIElementTypeScrollView"));
                String elementId = ((RemoteWebElement) scrollView).getId();
                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
                params.put("element", elementId);
                driver.executeScript("mobile: swipe", params);
                i++;
            }
        }
    }

    public void secondPIDIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        }
    }

    public void secondPIDIsNotDisplayed() {
        WebElement element = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.secondPidIsDisplayed));
        Assert.assertFalse(element.isDisplayed());
    }

    public void clickDeleteDocument() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickDeleteDocument)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickDeleteDocument)).click();
        }
    }

    public void scrollUntilYouFindDelete() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(50)" +
                            ".scrollIntoView(new UiSelector().text(\"Digital Credentials Issuer\"))"
            ));
        }
        else{
            int i = 1;
            while (i < 4) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                WebElement scrollView = driver.findElement(MobileBy.className("XCUIElementTypeScrollView"));
                String elementId = ((RemoteWebElement) scrollView).getId();
                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
                params.put("element", elementId);
                driver.executeScript("mobile: swipe", params);
                i++;
            }
        }
    }

    public void skippedTest() {
        boolean shouldRunTest = false;
        Assume.assumeTrue("Test is skipped because the required condition is not met", shouldRunTest);
    }

    public void clickDone() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickClose)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickDone)).click();
        }
    }

    public void homePageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.homePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.HOME_PAGE_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.homePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.HOME_PAGE_IS_DISPLAYED.label, pageHeader);
        }
    }
    public void detailsAreBlurred() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement eyeElement = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.detailsAreBlurred));
            String elementLabel = eyeElement.getAttribute("content-desc");
            Assert.assertEquals(Literals.Wallet.DETAILS_ARE_BLURRED.label, elementLabel);
        } else {
            WebElement eyeElement = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsAreBlurred));
            String elementLabel = eyeElement.getAttribute("label");
            Assert.assertEquals(Literals.Wallet.DETAILS_ARE_BLURRED.label, elementLabel);
        }
    }

    public void eyeIconIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            boolean pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.eyeIcon)).isDisplayed();
            Assert.assertTrue(pageHeader);
        } else {
            boolean pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.eyeIcon)).isDisplayed();
            Assert.assertTrue(pageHeader);
        }
    }

    public void detailsAreNotBlurred() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement eyeElement = test.mobileWebDriverFactory().getDriverAndroid().findElement(MobileBy.AccessibilityId("Show"));
            String contentDesc = eyeElement.getAttribute("contentDescription");
            Assert.assertEquals(Literals.Wallet.DETAILS_ARE_NOT_BLURRED.label, contentDesc);
        } else {
            WebElement eyeElement = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsAreNotBlurred));
            String elementLabel = eyeElement.getAttribute("label");
            Assert.assertEquals(Literals.Wallet.DETAILS_ARE_NOT_BLURRED.label, elementLabel);
        }
    }

    public void scrollUntilmDL() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            By pidLocator = By.xpath("//android.widget.TextView[@text=\"eu.europa.ec.eudi.mdl_mdoc\"]");
            int maxSwipes = 15;

            for (int i = 0; i < maxSwipes; i++) {

                if (!driver.findElements(pidLocator).isEmpty()) {
                    System.out.println("Βρέθηκε το στοιχείο PID!");
                    break;
                }

                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.7);
                int endY = (int) (size.height * 0.3);


                new TouchAction<>(driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(ofMillis(800)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                Thread.sleep(50);
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            Map<String, Object> params = new HashMap<>();
            params.put("direction", "up");
            driver.executeScript("mobile: swipe", params);
        }
    }

    public void credentialsProviderIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.credentialsProviderDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.CREDENTIALS_PROVIDER_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.credentialsProviderDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.CREDENTIALS_PROVIDER_DISPLAYED.label, pageHeader);
        }
    }

    public void clickToViewDetails() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickDownArrow)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickDownArrow)).click();
        }
    }

    public void closeCorrespondingMessage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.closeCorrespondingMessage)).click();
        }
    }

    public void addPIDPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.textToBePresentInElementLocated(
                            eu.europa.eudi.elements.android.WalletElements.addPIDPageIsDisplayed,
                            Literals.Wallet.ADD_PID_PAGE.label
                    ));
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.addPIDPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ADD_PID_PAGE.label, pageHeader);
        }
    }

    public void successMessageIsDisplayedForVerifier() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.successMessageForVerifier)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_VERIFIER.label, pageHeader);
        }
    }

    public void predefinedListIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.addPIDPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ADD_PID_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.addPIDPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.ADD_PID_PAGE.label, pageHeader);
        }
    }

    public void clickButtonIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickButtonIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.CLOSE_BUTTON.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickButtonIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DONE_BUTTON.label, pageHeader);
        }

    }

    public void sendTrasactionCode() {
        String code = test.getTransactionCode();
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.transactionCodeField)).sendKeys(code);
    }

    public void verfiricationIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.verificationIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.VERIFICATION_IS_DISPLAYED.label, pageHeader);
        }
    }

    public void detailsOfAgeOver18IsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.detailsOfOver18)).getText();
            Assert.assertEquals(Literals.Wallet.DETAILS_OVER_18.label, pageHeader);
        }
    }

    public void clickAdd() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickAdd)).click();
    }

    public void detailsArePresentedForDeferred() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.detailsOfDeferredIsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.DETAILS_FOR_DEFERRED.label, pageHeader);
    }

    public void clickAuthentication() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickAuthentication)).click();
    }

    public void signDocument() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.signDocument)).click();
    }

    public void clickFromDevice() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.fromDevice)).click();
    }

    public void signDocumentPageIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.signDocumentIsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.SIGN_DOCUMENT.label, pageHeader);
    }

    public void clickSelectDocument() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickSelectDocument)).click();
    }

    public void chooseDocument() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.chooseDocument)).click();
    }

    public void clickSelectSigningService() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickSelectSigningService)).click();
    }

    public void clickDoneOnSigningService() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickDoneOnSigningService)).click();
    }

    public void selectSigningIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.selectSigningIsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.SELECT_SIGNING.label, pageHeader);
    }

    public void signingServicesIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.signingServiceIsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.SIGNING_SERVICES.label, pageHeader);
    }

    public void clickAbortToSigning() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickAbortToSigning)).click();
    }

    public void cancelSigningIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.cancelSigningIsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.CANCEL_SIGNING_PROCESS.label, pageHeader);
    }

    public void clickCancelSigning() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickCancelSigning)).click();
    }

    public void selectSigningCertificateIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.selectSigningCertificate)).getText();
        Assert.assertEquals(Literals.Wallet.SELECT_SIGNING_CERTIFICATE.label, pageHeader);
    }

    public void clickSelectSigningCertificate() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.selectSigningCertificate)).click();
    }

    public void clickDoneSigningCertificate() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.doneSigningCertificate)).click();
    }

    public void clickContinueSignDocument() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickContinueSignDocument)).click();
    }

    public void successScreebWithSignedDocument() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.successScreenWithSignedDocument)).getText();
        Assert.assertEquals(Literals.Wallet.SUCCESS_SCREEN_WITH_SIGNED_DOCUMENT.label, pageHeader);
    }

    public void clickToViewSignDocument() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickViewSignDocument)).click();
    }

    public void clickBackWallet() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickBackWallet)).click();
    }

    public void closeIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.closeButton)).getText();
        Assert.assertEquals(Literals.Wallet.CLOSE_BUTTON.label, pageHeader);
    }

    public void shareIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.shareButton)).getText();
        Assert.assertEquals(Literals.Wallet.SHARE_BUTTON.label, pageHeader);
    }

    public void createAPinIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.createApinIsDsiaplyed)).getText();
        Assert.assertEquals(Literals.Wallet.PIN_PAGE.label, pageHeader);
    }

    public void TestProviderFormIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.testProviderFormIsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.TEST_PROVIDER_FORM.label, pageHeader);
    }

    public void ageOver18IsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.ageOver18IsDisplayed)).getText();
        Assert.assertEquals(Literals.Wallet.AGE_OVER_18.label, pageHeader);
    }

    public void isOnWallet() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.isOnWallet)).getText();
        Assert.assertEquals(Literals.Wallet.IS_ON_WALLET.label, pageHeader);
    }

    public void requestInProgressIsDisplayed() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.messageInProgress)).getText();
        Assert.assertEquals(Literals.Wallet.MESSAGE_IN_PROGRESS.label, pageHeader);
    }

    public void clickOk() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickOk)).click();
    }

    public void documentInPendingState() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.messageInPending)).getText();
        Assert.assertEquals(Literals.Wallet.MESSAGE_IN_PENDING.label, pageHeader);
    }

    public void documentIsIssued() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.documentIssued)).getText();
        Assert.assertEquals(Literals.Wallet.DOCUMENT_ISSUED.label, pageHeader);
    }

    public void clickThreeLine() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickThreeLine)).click();
    }

    public void clickSettings() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickSettings)).click();
    }

    public void enableBatchIssuance() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.enableBatchIssuance)).click();
    }

    public void openWallet() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.activateApp("eu.europa.ec.euidi.dev");
    }

    public void instanceHasReduced() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.instance)).getText();
        Assert.assertFalse(Literals.Wallet.ISSUANCE_FAILED.label, pageHeader.contains(Literals.Wallet.ISSUANCE_FAILED.label));
    }

    public void counterOfIssuedAttestation() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.instanceInitial)).getText();
        Assert.assertEquals(Literals.Wallet.INSTANCE_INITIAL.label, pageHeader);
    }

    public void clickToAddDocumentOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickAddDocument)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickAddDocument)).click();
        }
    }

    public void clickToSeeDocument() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickToSeeDocument)).click();
    }

    public void documentOpened() {
        String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.documentOpened)).getText();
        Assert.assertEquals(Literals.Wallet.DOCUMENT_OPENED.label, pageHeader);
    }

    public void informUserAboutAttestation() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.informUserAboutAttestation)).getText();
            Assert.assertEquals(Literals.Wallet.INFORM_ATTESTATION.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.informUserAboutAttestation)).getText();
            Assert.assertEquals(Literals.Wallet.INFORM_ATTESTATION_IOS.label, pageHeader);
        }
    }

    public void selectWalletCentric() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.walletCentric)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.selectWalletCentric)).click();
        }
    }
}