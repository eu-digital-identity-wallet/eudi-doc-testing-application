package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.IssuerElements;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WaitsUtils;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
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

    public void createAPin() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String fullPin = test.envDataConfig().getPin();

            AndroidDriver driver =
                    (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            WebElement pinField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            AppiumBy.className("android.widget.EditText")
                    )
            );

            pinField.click();

            for (char digit : fullPin.toCharArray()) {
                driver.pressKey(
                        new KeyEvent(
                                AndroidKey.valueOf("DIGIT_" + digit)
                        )
                );
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


            WebElement pinField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            AppiumBy.className("XCUIElementTypeTextField")
                    )
            );
            pinField.click();

            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1).sendKeys(String.valueOf(secondDigit));
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

    public void renterThePin() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String fullPin = test.envDataConfig().getPin();

            AndroidDriver driver =
                    (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            WebElement pinField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            AppiumBy.className("android.widget.EditText")
                    )
            );
            pinField.click();
            for (char digit : fullPin.toCharArray()) {
                driver.pressKey(
                        new KeyEvent(
                                AndroidKey.valueOf("DIGIT_" + digit)
                        )
                );
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


            WebElement pinField = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            AppiumBy.className("XCUIElementTypeTextField")
                    )
            );
            pinField.click();

            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1).sendKeys(String.valueOf(secondDigit));
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
            Assert.assertEquals(Literals.Wallet.LOGIN_ANDROID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.loginPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.LOGIN.label, pageHeader);
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickMdlPython)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickMdl)).click();

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
            String url = test.envDataConfig().getVerifierUrl();
            String env = test.envDataConfig().getExecutionEnvironment();

            if ("browserstack".equalsIgnoreCase(env)) {
                Map<String, Object> deepLinkArgs = new HashMap<>();
                deepLinkArgs.put("url", url);
                deepLinkArgs.put("package", "com.android.chrome");
                driver.executeScript("mobile:deepLink", deepLinkArgs);
            } else {
                Map<String, Object> args = new HashMap<>();
                args.put("command", "am");
                args.put("args", new String[]{"start", "-a", "android.intent.action.VIEW", "-d", url});
                driver.executeScript("mobile:shell", args);
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.runAppInBackground(Duration.ofSeconds(10));
            driver.activateApp("com.apple.mobilesafari");
            String url = test.envDataConfig().getVerifierUrl();
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

    public void clickExpandVerification() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpandVerification)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickExpandVerification)).click();
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

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            int viewportTop = 75;
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
                    slowScroll(driver);
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 15; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
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
                    slowScroll(driver);
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 30; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
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
                    slowScrollFirst(driver);
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 10; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                }
            }
        }
    }

    private void slowScrollFirst(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.60);
        int endY   = (int) (size.height * 0.30);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX,
                startY));

        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(500),
                PointerInput.Origin.viewport(),
                startX,
                endY));

        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    public void scrollUntilmDLOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            WebElement element = null;

            for (int i = 0; i < 80; i++) {
                try {
                    element = driver.findElement(WalletElements.clickMSISDNPython);

                    if (element.isDisplayed() && element.isEnabled()) {
                        break;
                    }

                } catch (Exception ignored) {
                    slowScroll(driver);
                }
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 80; i++) {

                if (isElementVisible(driver)) {
                    break;
                }

                Dimension size = driver.manage().window().getSize();

                int startX = size.width / 2;
                int startY = (int) (size.height * 0.80);
                int endY   = (int) (size.height * 0.40);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);

                swipe.addAction(finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                ));

                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

                swipe.addAction(new Pause(finger, Duration.ofMillis(120)));

                swipe.addAction(finger.createPointerMove(
                        Duration.ofMillis(350),
                        PointerInput.Origin.viewport(),
                        startX,
                        endY
                ));

                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
            }

        }
    }

    private boolean isElementVisible(IOSDriver driver) {
        return !driver.findElements(eu.europa.eudi.elements.ios.WalletElements.clickMdl).isEmpty()
                && driver.findElements(eu.europa.eudi.elements.ios.WalletElements.clickMdl).get(0).isDisplayed();
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
                    slowScroll(driver);
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 30; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                }
            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 12; i++) {
                    Dimension size = driver.manage().window().getSize();
                    int startX = size.width / 2;
                    int startY = (int) (size.height * 0.6);
                    int endY = (int) (size.height * 0.5);
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);

                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(new Pause(finger, Duration.ofMillis(500)));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                    driver.perform(Collections.singletonList(swipe));
                }
            }
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
        int maxAttempts = 2;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                if (qrImagePath == null) {
                    throw new IllegalArgumentException("QR image file path is null");
                }
                if (!qrImagePath.exists()) {
                    throw new IllegalArgumentException("QR image file does not exist: " + qrImagePath.getAbsolutePath());
                }

                BufferedImage bufferedImage = ImageIO.read(qrImagePath);

                BufferedImage grayImage = new BufferedImage(
                        bufferedImage.getWidth(),
                        bufferedImage.getHeight(),
                        BufferedImage.TYPE_BYTE_GRAY
                );
                grayImage.getGraphics().drawImage(bufferedImage, 0, 0, null);

                LuminanceSource source = new BufferedImageLuminanceSource(grayImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
                hints.put(DecodeHintType.POSSIBLE_FORMATS, Collections.singletonList(BarcodeFormat.QR_CODE));
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

                Result result = new MultiFormatReader().decode(bitmap, hints);
                String qrContent = result.getText();

                System.out.println("QR decoded successfully on attempt " + attempt);

                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                    test.mobileWebDriverFactory().androidDriver.executeScript("mobile: deepLink",
                            ImmutableMap.of(
                                    "url", qrContent,
                                    "package", test.envDataConfig().getAppiumAndroidAppPackage()
                            ));
                } else {
                    test.mobileWebDriverFactory().iosDriver.get(qrContent);
                }

                return;

            } catch (com.google.zxing.NotFoundException e) {
                System.out.println("QR not found in image (attempt " + attempt + ")");

                if (attempt == maxAttempts) {
                    throw new RuntimeException("QR code not found after retries: " + qrImagePath.getAbsolutePath(), e);
                }

            } catch (Exception e) {
                System.out.println("General QR processing error (attempt " + attempt + "): " + e.getMessage());

                if (attempt == maxAttempts) {
                    throw new RuntimeException("Failed to process QR code after retries", e);
                }
            }
        }
    }

    public void clickAuthenticate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticateButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.authenticateButton)).click();
        }
    }

    public void clickAddButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.addButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickIssue)).click();
        }
    }

    public void insertPidFromList() throws InterruptedException {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickToAddDocument();
        test.mobile().wallet().addDocumentPageIsDisplayed();
        test.mobile().wallet().clickFromList();
        test.mobile().wallet().scrollUntilmDLOnDocuments();
        test.mobile().wallet().clickPID();
        test.mobile().issuer().issuePID();

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
                slowScroll(driver);
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
                slowScrollUp();
            }
        }
    }

    public void slowScrollUp() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            Dimension size = driver.manage().window().getSize();
            int width = size.width;
            int height = size.height;

            int x = width / 2;

            int startY = (int) (height * 0.25);
            int endY = (int) (height * 0.80);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            Dimension size = driver.manage().window().getSize();
            int width = size.width;
            int height = size.height;

            int x = width / 2;

            int startY = (int) (height * 0.25);
            int endY = (int) (height * 0.80);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));
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
            driver.context("NATIVE_APP");

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

    public boolean isQrVisible() {
        if (!test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            return false;
        }
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        return !driver.findElements(eu.europa.eudi.elements.android.WalletElements.onlyThisTimeQR).isEmpty();
    }

    public void clickOnlinePresentation() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.onlinePresentation)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.onlinePresentation)).click();
        }
    }
}