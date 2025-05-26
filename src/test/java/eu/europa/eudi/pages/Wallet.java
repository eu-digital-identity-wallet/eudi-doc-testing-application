package eu.europa.eudi.pages;

import com.google.common.collect.ImmutableMap;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.MobileBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class Wallet {
    TestSetup test;

    public Wallet(TestSetup test) {
        this.test = test;
    }

    public void checkIfPageIsTrue() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.welcomeScreen)).getText();
            Assert.assertEquals(Literals.Wallet.WELCOME_HEADER.label, pageHeader);
            driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.welcomeScreen)).getText();
            Assert.assertEquals(Literals.Wallet.WELCOME_HEADER_IOS.label, pageHeader);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
    }

    public void createAPin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver driver = test.mobileWebDriverFactory().getDriverAndroid();
            String fullPin = test.envDataConfig().getPin();
            char firstDigit = fullPin.charAt(0);
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield1).sendKeys(String.valueOf(firstDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield2).sendKeys(String.valueOf(secondDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield3).sendKeys(String.valueOf(thirdDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield4).sendKeys(String.valueOf(fourthDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield5).sendKeys(String.valueOf(fifthDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield6).sendKeys(String.valueOf(sixthDigit));
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
            WebDriver driver = test.mobileWebDriverFactory().getDriverAndroid();
            String fullPin = test.envDataConfig().getPin();
            char firstDigit = fullPin.charAt(0);
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield1).sendKeys(String.valueOf(firstDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield2).sendKeys(String.valueOf(secondDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield3).sendKeys(String.valueOf(thirdDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield4).sendKeys(String.valueOf(fourthDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield5).sendKeys(String.valueOf(fifthDigit));
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield6).sendKeys(String.valueOf(sixthDigit));
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessage)).getText();
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.loginPageIsDisplayed)).getText();
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.PIDIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.PIDIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        }
    }

    public void clickMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickMdl)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickMdl)).click();
        }
    }

    public void mdlIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.mdlIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.MDL.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.mdlIsDisplayed)).getText();
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.addDoc)).click();
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
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_DRIVING_LICENCE.label, pageHeader);
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

    public void userOpensVerifier() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));
            String url = "https://dev.verifier.eudiw.dev/home";
            Map<String, Object> args = new HashMap<>();
            args.put("command", "am");
            args.put("args", new String[]{"start", "-a", "android.intent.action.VIEW", "-d", url});
            driver.executeScript("mobile:shell", args);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.runAppInBackground(Duration.ofSeconds(10));
            driver.activateApp("com.apple.mobilesafari");
            String url = "https://dev.verifier.eudiw.dev/home";
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.issuanceDetailsNew)).getText();
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickSecondPID)).click();
        }
    }

    public void optionalDataIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.optionalDataIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.OPTIONAL_DATA.label, pageHeader);
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.pinFieldIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PIN_FIELD_IS_DISPLAYED_IOS.label, pageHeader);
        }
    }

    public void clickAddMyDigitalID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAddMyDigitalID)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickAddMyDigitalID)).click();
        }
    }

    public void clickPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickPID)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickPID)).click();
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickToAddDocument)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickToAddDocument)).click();
        }
    }

    public void clickFromList() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickFromList)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickFromList)).click();
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

    public void scrollUntilPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(10)" +
                            ".scrollIntoView(new UiSelector().text(\"PID\"))"
            ));
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 3) {
                JavascriptExecutor js = (JavascriptExecutor) driver;

                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
                js.executeScript("mobile: swipe", params);
                i++;
            }
        }
    }

    public void secondPIDIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.secondPidIsDisplayed)).getText();
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickDeleteDocument)).click();
        }
    }

    public void scrollUntilYouFindDelete() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(10)" +
                            ".scrollIntoView(new UiSelector().text(\"Digital Credentials Issuer\"))"
            ));
        }
        else{
            int i = 1;
            while (i < 4) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickClose)).click();
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

    public void scrollUntilmDL() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(MobileBy.AndroidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".scrollForward()" +
                            ".setMaxSearchSwipes(10)" +
                            ".scrollIntoView(new UiSelector().text(\"mDL\"))"
            ));
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
}