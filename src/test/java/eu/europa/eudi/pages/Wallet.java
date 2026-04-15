package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.data.yml.FormYml;
import eu.europa.eudi.elements.android.IssuerElements;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WaitsUtils;
import eu.europa.eudi.utils.YmlLoader;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wallet {

    TestSetup test;
    EnvDataConfig envDataConfig;


    public Wallet(TestSetup test) {
        this.test = test;
    }

    public void launchApp() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.activateApp(test.envDataConfig().getAppiumAndroidAppPackage());
        } else {
            test.mobileWebDriverFactory().iosDriver.activateApp(test.envDataConfig().getAppiumIosBundleId());
        }
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
                    break;
                } catch (Exception e) {
                    retries--;
                    if (retries == 0) throw e;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        } else {
            String fullPin = test.envDataConfig().getPin();
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            // 1. Wait for PIN screen to actually be ready (NOT element-specific)
            wait.until(d ->
                    !d.findElements(By.className("XCUIElementTypeSecureTextField")).isEmpty()
                            || !d.findElements(By.className("XCUIElementTypeTextField")).isEmpty()
            );

            // 2. Small stabilization pause (important for iOS animations)
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            // 3. Use ACTIVE ELEMENT (most stable approach on iOS)
            driver.switchTo().activeElement().sendKeys("1");
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
                    break;
                } catch (Exception e) {
                    retries--;
                    if (retries == 0) throw e;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
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

    public void nationalIdIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.PIDIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.PIDIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        }
    }

    public void clickMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickMdlPython)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickMdl));
            tapAction(button, false);
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

    public void confirmsDeletion() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.confirmsDeletion)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.confirmsDeletion)).click();

        }
    }

    public void dashboardPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.dashboardPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.DASHBOARD_PAGE.label, pageHeader);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.dashboardPageIsDisplayed)).isDisplayed();
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
            //nothing
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

    public void clickSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSubmit)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickSubmit)).click();
        }
    }

    public void clickDrivingLicenceButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickDrivingLicenceButtonOnDocuments));
            tapAction(button, false);
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickMdl));
            tapAction(button, false);        }
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

    public void userOpensVerifier() throws MalformedURLException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));

            String url = "tneal";
            String env = test.envDataConfig().getExecutionEnvironment();

            if ("browserstack".equalsIgnoreCase(env)) {
                // Safe for BrowserStack
                Map<String, Object> deepLinkArgs = new HashMap<>();
                deepLinkArgs.put("url", "https://verifier.eudiw.dev/home");
                deepLinkArgs.put("package", "com.android.chrome");
                driver.executeScript("mobile:deepLink", deepLinkArgs);
            } else {
                // Works locally via ADB
                Map<String, Object> args = new HashMap<>();
                args.put("command", "am");
                args.put("args", new String[]{"start", "-a", "android.intent.action.VIEW", "-d", url});
                driver.executeScript("mobile:shell", args);
            }
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
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER.label, pageHeader);
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

    public void clickExpandVerification() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpandVerification)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickExpandVerification)).click();
        }
    }
    public void clickExpandVerificationOnSecondPIDFromKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpandVerificationSecond)).click();
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
            tapAction(myDigitalIDButton, false);
        } else {
            WebElement myDigitalIDButton = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickAddMyDigitalID));
            tapAction(myDigitalIDButton, false);
        }
    }

    public void tapAction(WebElement element, int xOffset, int yOffset) {
        AppiumDriver driver;

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverIos();
        }

        Point location = element.getLocation();
        int x = location.getX() + xOffset;
        int y = location.getY() + yOffset;

        // ---- ANDROID-ONLY FIX ----
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            int viewportTop = 75;  // BrowserStack top offset
            y = Math.max(y, viewportTop + 1);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), x, y));

        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));  // FIXED

        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));    // FIXED

        driver.perform(Collections.singletonList(tap));
    }

    public void tapAction(WebElement element, boolean clickLeft) {
        Dimension size = element.getSize();
        if (clickLeft) {
            tapAction(element, 10, size.getHeight() / 2);
        } else {
            tapAction(element, size.getWidth() / 2, size.getHeight() / 2);
        }
    }

    public void clickPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickPID)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickPID));
            tapAction(button, false);
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickDone)).click();
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
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickFromList));
            tapAction(button, false);
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
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 10; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickPID);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll(driver);  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 15; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            }
        }
    }

    private void slowScroll(AndroidDriver driver) {
        int startX = driver.manage().window().getSize().width / 2;
        int startY = (int) (driver.manage().window().getSize().height * 0.8);
        int endY   = (int) (driver.manage().window().getSize().height * 0.4);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY)); // slow scroll
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

    public void secondPIDKotlinIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID_KOTLIN.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        }
    } public void secondPIDIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.secondPidIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID.label, pageHeader);
        }
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
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(50)" +
                            ".scrollIntoView(new UiSelector().text(\"Digital Credentials Issuer\"))"
            ));
        } else {
            int i = 1;
            while (i < 4) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                WebElement scrollView = driver.findElement(AppiumBy.className("XCUIElementTypeScrollView"));
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
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                WebElement eyeElement = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsAreBlurred));
                String elementLabel = eyeElement.getAttribute("label");
                Assert.assertEquals(Literals.Wallet.DETAILS_ARE_BLURRED.label, elementLabel);
            } else {
                WebElement eyeElement = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.detailsAreBlurredReal));
                String elementLabel = eyeElement.getAttribute("label");
                Assert.assertEquals(Literals.Wallet.DETAILS_ARE_BLURRED.label, elementLabel);
            }
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
            WebElement eyeElement = test.mobileWebDriverFactory().getDriverAndroid().findElement(AppiumBy.accessibilityId("Show"));
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
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 5; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickMdlPython);
                    if (pidElement.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    driver.findElement(AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
                    ));
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 22; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 6; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            }
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
        }else{
//            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.closeCorrespondingMessage)).click();
        }
    }

    public void addPIDPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.android.WalletElements.addPIDPageIsDisplayed,
                    Literals.Wallet.ADD_PID_PAGE.label,
                    driver,
                    50
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.WalletElements.addPIDPageIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Wallet.ADD_PID_PAGE.label, headerText);
        } else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.ios.WalletElements.addPIDPageIsDisplayed,
                    Literals.Wallet.ADD_PID_PAGE.label,
                    driver,
                    50
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.ios.WalletElements.addPIDPageIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Wallet.ADD_PID_PAGE.label, headerText);
        }
    }

    public void successMessageIsDisplayedForVerifier() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.successMessageForVerifier)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_VERIFIER.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.successMessageForVerifier)).getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_VERIFIER.label, pageHeader);
        }
    }


    public void clickPIDOnDocuments() {
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickPIDOnDocuments));
                tapAction(button, false);
            } else {
                WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickPID));
                tapAction(button, false);
        }
    }

    public void scrollUntilPIDOnDocuments() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 10; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickPIDOnDocumentsSecond);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll(driver);  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 30; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            }
        }
    }

    public void scrollUntilPIDFirst() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 10; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickPID);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScrollFirst(driver);  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 10; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            }
        }
    }

    private void slowScrollFirst(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.60);   // finger starts in the middle
        int endY   = (int) (size.height * 0.30);   // finger ends higher on the screen

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX,
                startY));

        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(500),             // slow scroll
                PointerInput.Origin.viewport(),
                startX,
                endY));

        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    public void scrollUntilmDLOnDocuments() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 5; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickMdlPython);
                    if (pidElement.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    slowScroll(driver);  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 22; i++) {
                    try {
                        WebElement mdlElement = driver.findElement(eu.europa.eudi.elements.ios.WalletElements.clickMdl);
                        if (mdlElement.isDisplayed()) {
                            break;
                        }
                    } catch (Exception e) {
                        // element not visible yet, continue scrolling
                    }
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                 Scroll until visible


            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 6; i++) {
                    try {
                        WebElement mdlElement = driver.findElement(eu.europa.eudi.elements.ios.WalletElements.clickMdl);
                        if (mdlElement.isDisplayed()) {
                            break;
                        }
                    } catch (Exception e) {
                        // element not visible yet, continue scrolling
                    }
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            }
        }
    }

    public void scrollUntilPIDTwoPid() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 10; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickPID);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll(driver);  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 30; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    // Get screen size
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    // --- START: REPLACEMENT FOR TouchAction ---
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    // This replaces your waitAction
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                    // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                    Thread.sleep(50);
                }
            }
        }
    }

    public void scanQrIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(
                    eu.europa.eudi.elements.android.WalletElements.scanQrIsDisplayed))
                .getText();
            Assert.assertEquals(Literals.Wallet.SCAN_QR.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait()
                .until(ExpectedConditions.visibilityOfElementLocated(
                    eu.europa.eudi.elements.ios.WalletElements.successMessageIsDisplayedForIssuer))
                .getText();
            Assert.assertEquals(Literals.Wallet.SUCCESS_MESSAGE_IS_DISPLAYED_FOR_ISSUER_IOS.label, pageHeader);
        }
    }

    public void clickQROption() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait()
                .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.scanQRButton)).click();
        }else{
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.scanQR)).click();
        }
    }

    public void onlyThisTimeQR() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.android.WalletElements.onlyThisTimeQR))
                    .click();
        }
    }

    public void theQRScannerIsActivated() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.scanQRIsActivated)).getText();
            Assert.assertEquals(Literals.Wallet.QR_SCANNER_IS_ACTIVATED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.scanQRIsActivated)).getText();
            Assert.assertEquals(Literals.Wallet.QR_SCANNER_IS_ACTIVATED_FOR_ISSUANCE.label, pageHeader);
        }
    }

    public void mockQRInject(File qrImagePath) {
        try {
            // Validate input file
            if (qrImagePath == null) {
                throw new IllegalArgumentException("QR image file path is null");
            }
            if (!qrImagePath.exists()) {
                throw new IllegalArgumentException("QR image file does not exist: " + qrImagePath.getAbsolutePath());
            }

            // Read QR code image and get its content
            BufferedImage bufferedImage = ImageIO.read(qrImagePath);
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, Collections.singletonList(BarcodeFormat.QR_CODE));
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            Result result = new MultiFormatReader().decode(bitmap, hints);
            String qrContent = result.getText();

            // Inject the QR content based on platform
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                // Android deep link injection
                test.mobileWebDriverFactory().androidDriver.executeScript("mobile: deepLink",
                        ImmutableMap.of(
                                "url", qrContent,
                                "package", test.envDataConfig().getAppiumAndroidAppPackage()
                        ));
            } else {
                test.mobileWebDriverFactory().iosDriver.get(qrContent);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process QR code: " + e.getMessage());
        }
    }

    public void clickAuthenticate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticateButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.authenticateButton)).click();
        }
    }

    public void clickOnline() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.onlineOption)).click();
        } else {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.scanQR)).click();
//            test.mobileWebDriverFactory().getWait()
//                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.onlineOption)).click();
        }
    }

    public void clickAddButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.addButton)).click();
        } else {

            By issueBtn = By.xpath("//XCUIElementTypeButton[@label=\"Issue\"]");

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.refreshed(
                            ExpectedConditions.elementToBeClickable(issueBtn)
                    ))
                    .click();
//            test.mobileWebDriverFactory().getWait()
//                    .until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.issueButton)).click();
        }
    }

    public void detailsArePresentedKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.kotlinIssuanceDetails)).getText();
            Assert.assertEquals(Literals.Wallet.ISSUANCE_DETAILS_KOTLIN.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.kotlinIssuanceDetails)).getText();
            Assert.assertEquals(Literals.Wallet.ISSUANCE_DETAILS_KOTLIN.label, pageHeader);
        }
    }

    public void openIssuedPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.issuedPID)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.issuedPID));
            tapAction(button, false);
        }
    }

    public void clickPIDKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickPIDKotlin)).click();
        } else {
        }
    }


    public void insertPidFromList() throws InterruptedException {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickToAddDocument();
        test.mobile().wallet().addDocumentPageIsDisplayed();
        test.mobile().wallet().clickFromList();
        test.mobile().wallet().scrollUntilPIDOnDocuments();
        test.mobile().wallet().clickPIDOnDocuments();
        test.mobile().issuer().issuePID();

    }

    public void checkDataOnWalletFromVerifier() {
        FormYml yml = YmlLoader.load("testdata/PID/share_kotlin_data_on_wallet_from_verfier.yml", FormYml.class);

        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        yml.fields.forEach((fieldKey, cfg) -> {
            if (!cfg.required) return;

            // For nested like "Birth Place.country"
            String[] labels = fieldKey.split("\\.");

            // 1) Make sure each label exists (with scroll)
            for (String label : labels) {
                assertTextVisibleWithScroll(driver, label, 15);
            }

            // 2) If YAML has a value -> assert value under the LAST label
            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                String lastLabel = labels[labels.length - 1];
                String actual = readValueBelowLabel(driver, lastLabel);
                org.junit.Assert.assertEquals(
                        "Wrong value for label: " + fieldKey,
                        cfg.value.trim(),
                        actual.trim()
                );
            }
        });
    }

    private void assertTextVisibleWithScroll(AndroidDriver driver, String label, int maxScrolls) {
        By labelLocator = By.xpath(
                "//*[@class='android.widget.TextView' and @text=\"" + label + "\"]"
        );

        for (int i = 0; i < maxScrolls; i++) {
            if (!driver.findElements(labelLocator).isEmpty()) return;
            slowScroll(driver); // your existing swipe
        }

        throw new AssertionError("Label not found on screen: " + label);
    }

    private String readValueBelowLabel(AndroidDriver driver, String lastLabel) {
        WebElement valueEl = null;

        try {
            By directSiblingLocator = By.xpath(
                    "//*[@class='android.widget.TextView' and @text=\"" + lastLabel + "\"]/following-sibling::android.widget.TextView[1]"
            );
            valueEl = test.mobileWebDriverFactory().getWait()
                    .withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(directSiblingLocator));
            return valueEl.getText().trim();
        } catch (TimeoutException e) {
            // fallback
        }

        try {
            By flexibleLocator = By.xpath(
                    "//*[@class='android.widget.TextView' and @text=\"" + lastLabel + "\"]/following::android.widget.TextView[1]"
            );
            valueEl = test.mobileWebDriverFactory().getWait()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(flexibleLocator));
            return valueEl.getText().trim();
        } catch (TimeoutException e) {
            // fallback
        }

        try {
            By parentContainerLocator = By.xpath(
                    "//*[@class='android.widget.TextView' and @text=\"" + lastLabel + "\"]/parent::*//android.widget.TextView[not(@text='" + lastLabel + "')]"
            );
            valueEl = test.mobileWebDriverFactory().getWait()
                    .withTimeout(Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(parentContainerLocator));
            return valueEl.getText().trim();
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Cannot find value for label: " + lastLabel);
        }
    }

    public void selectDataAgain() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.unselectData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.unselectData)).click();
        }
    }

    public void checkDataOnVerifierFromWallet() {
            FormYml yml = YmlLoader.load("testdata/PID/share_py_data_on_wallet.yml", FormYml.class);

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            yml.fields.forEach((attrKey, cfg) -> {
                if (!cfg.required) return;

                // 1) attribute key exists
                assertAttributeVisibleWithScroll(driver, attrKey, 15);

                // 2) value check
                if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                    String actual = readValueForAttributeKey(driver, attrKey);
                    Assert.assertEquals("Wrong value for attribute: " + attrKey,
                            cfg.value.trim(),
                            actual.trim());
                }
            });
        }

    private void assertAttributeVisibleWithScroll(AndroidDriver driver, String attrKey, int maxSwipes) {

        By keyLocator = By.xpath("//android.widget.TextView[@text=\"" + attrKey + "\"]");

        for (int i = 0; i < maxSwipes; i++) {
            if (!driver.findElements(keyLocator).isEmpty()) return;
            slowScroll(driver); // your existing helper
        }

        Assert.fail("Attribute key not found: " + attrKey);
    }

    private String readValueForAttributeKey(AndroidDriver driver, String attrKey) {

        // take the first TextView after the key that is not empty and not the literal words "value" or "tag"
        By valueLocator = By.xpath(
                "//android.widget.TextView[@text=\"" + attrKey + "\"]" +
                        "/following::android.widget.TextView[" +
                        "normalize-space(@text) != '' and " +
                        "normalize-space(@text) != 'value' and " +
                        "normalize-space(@text) != 'tag'" +
                        "][1]"
        );

        for (int i = 0; i < 5; i++) {
            if (!driver.findElements(valueLocator).isEmpty()) {
                return driver.findElement(valueLocator).getText();
            }
            slowScroll(driver);
        }

        throw new AssertionError("Value not found for attribute key: " + attrKey);
    }


    public void clickExpandVerificationDown() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickExpandDetails)).click();
    }

    public void scrollUntilNationality() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        for (int i = 0; i < 10; i++) {
            try {
                WebElement pidElement = driver.findElement(IssuerElements.nationality);
                if (pidElement.isDisplayed()) break;
            } catch (Exception e) {
                slowScroll(driver);  // ← slow scroll instead of UiScrollable
            }
        }

    }
    public void scrollUpUntilBirthDate() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        for (int i = 0; i < 3; i++) {
            try {
                WebElement pidElement = driver.findElement(IssuerElements.birthDate);
                if (pidElement.isDisplayed()) break;
            } catch (Exception e) {
                slowScrollUp();  // ← slow scroll instead of UiScrollable
            }
        }
    }

    public void scrollUp() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        for (int i = 0; i < 3; i++) {
            try {
                WebElement pidElement = driver.findElement(IssuerElements.nationality);
                if (pidElement.isDisplayed()) break;
            } catch (Exception e) {
                slowScrollUp();  // ← slow scroll instead of UiScrollable
            }
        }
    }

    public void scrollDown() {
        AndroidDriver driver =
                (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        for (int i = 0; i < 10; i++) {
            try {
                WebElement element = driver.findElement(IssuerElements.birthDate);

                if (element.isDisplayed()) {
                    break;
                }

            } catch (Exception e) {
                slowScrollDown(); // scroll to upper content
            }
        }
    }

    private void slowScrollDown() {
        AndroidDriver driver =
                (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        Dimension size = driver.manage().window().getSize();

        int width = size.width;
        int height = size.height;

        int x = width / 2;

        // Finger moves TOP → BOTTOM (scroll down)
        int startY = (int) (height * 0.20); // near top
        int endY   = (int) (height * 0.80); // near bottom

        PointerInput finger =
                new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                x,
                startY
        ));

        swipe.addAction(finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()
        ));

        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(900),
                PointerInput.Origin.viewport(),
                x,
                endY
        ));

        swipe.addAction(finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()
        ));

        driver.perform(Arrays.asList(swipe));

        try {
            Thread.sleep(250);
        } catch (InterruptedException ignored) {}
    }

    public void slowScrollUp() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            Dimension size = driver.manage().window().getSize();
            int width = size.width;
            int height = size.height;

            int x = width / 2;

            // Finger goes DOWN to scroll page UP
            int startY = (int) (height * 0.25);  // start near top
            int endY = (int) (height * 0.80);  // end near bottom

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));

            // optional: tiny settle time helps some apps
            try {
                Thread.sleep(150);
            } catch (InterruptedException ignored) {
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            Dimension size = driver.manage().window().getSize();
            int width = size.width;
            int height = size.height;

            int x = width / 2;

// Finger goes DOWN to scroll page UP
            int startY = (int) (height * 0.25);  // start near top
            int endY = (int) (height * 0.80);    // end near bottom

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));

// optional: small pause helps UI settle
            try {
                Thread.sleep(150);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void scrollUntilSex() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        for (int i = 0; i < 5; i++) {
            try {
                WebElement pidElement = driver.findElement(WalletElements.sexButton);
                if (pidElement.isDisplayed()) break;
            } catch (Exception e) {
                slowScroll(driver);  // ← slow scroll instead of UiScrollable
            }
        }
    }

    public void checkDataOnWalletFromVerifierFromKotlin() {
        FormYml yml = YmlLoader.load("testdata/PID/kotlin_data_on_wallet.yml", FormYml.class);

        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        yml.fields.forEach((fieldKey, cfg) -> {
            if (!cfg.required) return;

            // For nested like "Birth Place.country"
            String[] labels = fieldKey.split("\\.");

            // 1) Make sure each label exists (with scroll)
            for (String label : labels) {
                assertTextVisibleWithScroll(driver, label, 30);
            }

            // 2) If YAML has a value -> assert value under the LAST label
            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                String lastLabel = labels[labels.length - 1];
                String actual = readValueBelowLabel(driver, lastLabel);
                org.junit.Assert.assertEquals(
                        "Wrong value for label: " + fieldKey,
                        cfg.value.trim(),
                        actual.trim()
                );
            }
        });
    }

    public void scrollUntilResidentStreet() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        for (int i = 0; i < 5; i++) {
            try {
                WebElement pidElement = driver.findElement(WalletElements.residentStreet);
                if (pidElement.isDisplayed()) break;
            } catch (Exception e) {
                slowScroll(driver);  // ← slow scroll instead of UiScrollable
            }
        }
    }

    public void clickPIDFromKotlin() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickPidFromKotlin)).click();
    }
    public void clickMDLFromKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            safeClick(WalletElements.mdlIsDisplayedKotlin);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickPidFromKotlin)).click();
        }
    }

    private void safeClick(By locator) {
        for (int i = 0; i < 3; i++) {
            try {
                test.mobileWebDriverFactory().getWait()
                        .until(ExpectedConditions.elementToBeClickable(locator))
                        .click();
                return;
            } catch (StaleElementReferenceException e) {
                // retry
            }
        }

        throw new RuntimeException("Could not click element: " + locator);
    }

    public void checkFormOnWalletFromVerifier() {
        test.mobile().wallet().scrollUntilResidentStreet();
        test.mobile().wallet().clickExpandVerificationDown();
        test.mobile().wallet().clickExpandVerificationDown();
        test.mobile().wallet().scrollDown();
        test.mobile().wallet().checkDataOnWalletFromVerifierFromKotlin();
    }

    public void checkFormOnWalletFromVerifierRoundTwo() {
        test.mobile().wallet().scrollUntilResidentStreet();
        test.mobile().wallet().clickExpandVerificationDown();
        test.mobile().wallet().clickExpandVerificationDown();
        test.mobile().wallet().scrollDown();
        test.mobile().wallet().checkDataOnWalletFromVerifier();
    }

    public void checkFormOnWallerFromKotlinIssuer() {
        test.mobile().wallet().scrollUntilSex();
        test.mobile().wallet().clickExpandVerificationDown();
        test.mobile().wallet().clickExpandVerificationDown();
        test.mobile().wallet().scrollDown();
        test.mobile().issuer().ckeckFieldsOnWalletFromKotlinIssuer();
    }

    public void rotateScreen() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    public void theQRScannerIsActivatedForIssuance() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.scanQRIsActivatedForIssuance)).getText();
            Assert.assertEquals(Literals.Wallet.QR_SCANNER_IS_ACTIVATED_FOR_ISSUANCE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.scanQRIsActivated)).getText();
            Assert.assertEquals(Literals.Wallet.QR_SCANNER_IS_ACTIVATED_FOR_ISSUANCE.label, pageHeader);
        }
    }

    public void insertMdlFromList() throws InterruptedException {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickToAddDocument();
        test.mobile().wallet().addDocumentPageIsDisplayed();
        test.mobile().wallet().clickFromList();
        test.mobile().wallet().scrollUntilmDLOnDocuments();
        test.mobile().wallet().clickMdl();
        test.mobile().issuer().issueMDL();
    }

    public void mdlIsDisplayedKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.mdlIsDisplayedKotlin)).getText();
            Assert.assertEquals(Literals.Wallet.MDL_KOTLIN.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.mdlIsDisplayedKotlin)).getText();
            Assert.assertEquals(Literals.Wallet.MDL_KOTLIN.label, pageHeader);
        }
    }

    public void unselectDataForMdlPython() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.unselectDataForMdlPython)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.unselectData)).click();
        }
    }

    public void unselectDataForMdlKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.unselectDataForMdlKotlin)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.unselectDataForMdlKotlin)).click();
        }
    }

    public void viewDataPage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    eu.europa.eudi.elements.android.VerifierElements.viewDataPage,
                    Literals.Verifier.VIEW_DATA_PAGE.label
            ));

            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.VerifierElements.viewDataPage).getText().trim();

            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, headerText);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        }
    }

    public void scrollUpForBirthDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
            int maxScrolls = 10;     // safety limit
            int count = 0;
            By locator = By.xpath("//android.widget.TextView[@text=\"Mandatory Information\"]");
            while (driver.findElements(locator).isEmpty() && count < maxScrolls) {
                slowScrollUp();
                count++;
            }

            if (driver.findElements(locator).isEmpty()) {
                throw new RuntimeException("Mandatory Information not found after scrolling up");
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            int maxScrolls = 10;
            int count = 0;

            By locator = By.xpath(
                    "//XCUIElementTypeStaticText[@name='Mandatory Information' " +
                            "or @label='Mandatory Information' " +
                            "or @value='Mandatory Information']"
            );

            while (driver.findElements(locator).isEmpty() && count < maxScrolls) {
                slowScrollUp();
                count++;
            }

            if (driver.findElements(locator).isEmpty()) {
                throw new RuntimeException("Mandatory Information not found after scrolling up");
            }
        }
    }

    public void unselectDataForMdlKotlinAllAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.unselectDataForMdlKotlinAllAttributes)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.unselectData)).click();
        }
    }

    public void clickToViewDetailsSecond() {
        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickToViewDetailsSecond)).click();
    }

}