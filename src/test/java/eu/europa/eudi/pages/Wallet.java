package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.data.yml.FormYml;
import eu.europa.eudi.elements.android.IssuerElements;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.MobileActionsUtils;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WalletActionsUtils;
import eu.europa.eudi.utils.yaml.YmlLoader;
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

    public String selectiveDisclosure;
    public String issuerType;
    public String credential;
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

    public void renterThePin() {
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

    public void clickMdl() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickMdlPython)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickMdl)).click();

        }
    }

    public void clickMdlKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickMdlKotlin)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickMdlKotlin)).click();

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

    public void userOpensVerifier() {
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
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");
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

    public void clickExpandVerificationMSODocIOS() {
        test.mobileWebDriverFactory().getWait().until(
            ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickExpandVerificationMSODoc)).click();
    }

    public void clickExpandNationalityIOS() {
        scrollToAndClickIOS(eu.europa.eudi.elements.ios.WalletElements.clickExpandNationality);
    }

    public void clickExpandPlaceOfBirthIOS() {
        scrollToAndClickIOS(eu.europa.eudi.elements.ios.WalletElements.clickExpandPlaceOfBirth);
    }

    private void scrollToAndClickIOS(By locator) {
        IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
        driver.setSetting("waitForQuiescence", false);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        try {
            for (int i = 0; i < 12; i++) {
                List<WebElement> els = driver.findElements(locator);
                if (!els.isEmpty() && els.get(0).isDisplayed()) {
                    WebElement el = els.get(0);
                    // if element is too low (Done button area), scroll up a bit first
                    if (el.getLocation().getY() > size.height * 0.72) {
                        PointerInput nudge = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                        Sequence nudgeUp = new Sequence(nudge, 1);
                        nudgeUp.addAction(nudge.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, (int)(size.height * 0.58)));
                        nudgeUp.addAction(nudge.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                        nudgeUp.addAction(nudge.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, (int)(size.height * 0.40)));
                        nudgeUp.addAction(nudge.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                        driver.perform(Collections.singletonList(nudgeUp));
                        els = driver.findElements(locator);
                        if (!els.isEmpty()) { els.get(0).click(); return; }
                    } else {
                        el.click();
                        return;
                    }
                }
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence scroll = new Sequence(finger, 1);
                scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, (int)(size.height * 0.62)));
                scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                scroll.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, (int)(size.height * 0.43)));
                scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(Collections.singletonList(scroll));
            }
            throw new RuntimeException("Element not found after scroll: " + locator);
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.setSetting("waitForQuiescence", true);
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
            MobileActionsUtils.tapActionWallet(myDigitalIDButton, false);
        } else {
            WebElement myDigitalIDButton = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickAddMyDigitalID));
            MobileActionsUtils.tapActionWallet(myDigitalIDButton, false);
        }
    }

    public void clickPID() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
//            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickPID)).click();
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickPID));
            MobileActionsUtils.tapActionWallet(button, false);
            Thread.sleep(5000); // 3-second delay

        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickPID));
            MobileActionsUtils.tapActionWallet(button, false);
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
            MobileActionsUtils.tapActionWallet(button, false);
        }
    }

    public void clickBackButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.clickBackButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickBackButton)).click();
        }
    }

    public void scrollUntilPID(){
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
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickDownArrow)).click();
        }
    }

    public void scrollUntilmDLOnDocuments(String issuerType) {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            WebElement element = null;

            By mdlLocator = "kotlin".equalsIgnoreCase(issuerType)
                    ? WalletElements.clickMdlKotlin
                    : WalletElements.clickMdlPython;

            driver.manage().timeouts().implicitlyWait(Duration.ZERO);

            for (int i = 0; i < 30; i++) {
                try {
                    element = driver.findElement(mdlLocator);

                    if (element.isDisplayed() && element.isEnabled()) {
                        break;
                    }

                } catch (Exception ignored) {
                    slowScroll(driver);
                }
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            driver.manage().timeouts().implicitlyWait(Duration.ZERO);

            for (int i = 0; i < 80; i++) {

                if (isElementVisible(driver, issuerType)) {
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

    public void scrollUntilPidOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement element = null;

            driver.manage().timeouts().implicitlyWait(Duration.ZERO);

            for (int i = 0; i < 80; i++) {
                try {
                    element = driver.findElement(WalletElements.clickPowerOfPresentation);

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

                if (isElementVisiblePID(driver)) {
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

    private boolean isElementVisible(IOSDriver driver, String issuerType) {
        By mdlLocator = "kotlin".equalsIgnoreCase(issuerType)
                ? eu.europa.eudi.elements.ios.WalletElements.clickMdlKotlin
                : eu.europa.eudi.elements.ios.WalletElements.clickMdl;
        return !driver.findElements(mdlLocator).isEmpty()
                && driver.findElements(mdlLocator).get(0).isDisplayed();
    }


    private boolean isElementVisiblePID(IOSDriver driver) {
        return !driver.findElements(eu.europa.eudi.elements.ios.WalletElements.clickPID).isEmpty()
                && driver.findElements(eu.europa.eudi.elements.ios.WalletElements.clickPID).get(0).isDisplayed();
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
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.authenticateButton)).click();
            try {
                driver.executeScript("mobile: alert", ImmutableMap.of("action", "accept", "buttonLabel", "Online"));
            } catch (Exception ignored) {
            }
        }
    }

    public void clickAddButton() throws InterruptedException {
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
        test.mobile().wallet().scrollUntilPidOnDocuments();
        test.mobile().wallet().clickPID();
        test.mobile().issuer().issuePID(this.credential);
    }

    public void clickExpandVerificationDown() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickExpandDetails)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickExpandDetails)).click();
        }
    }

    public void scrollUntilNationality() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
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

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 3; i++) {
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

    public void clickPIDFromKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.clickPidFromKotlin)).click();
        }else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.secondPidIsDisplayed)).click();
        }
    }
    public void clickMDLFromKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WalletActionsUtils.safeClick(WalletElements.mdlIsDisplayedKotlin);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.clickMdlKotlin)).click();
        }
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
        test.mobile().wallet().scrollUntilmDLOnDocuments("python");
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
        }
    }

    public void scrollUpForBirthDateOnPID() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        int maxScrolls = 10;     // safety limit
        int count = 0;
        By locator = By.xpath("//android.widget.TextView[@text=\"Birth Date\"]");
        while (driver.findElements(locator).isEmpty() && count < maxScrolls) {
            MobileActionsUtils.slowScrollUp();
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
                "//XCUIElementTypeStaticText[@name='Birth Date' " +
                        "or @label='Birth Date' " +
                        "or @value='Birth Date']"
        );

        while (driver.findElements(locator).isEmpty() && count < maxScrolls) {
            MobileActionsUtils.slowScrollUp();
            count++;
        }

        if (driver.findElements(locator).isEmpty()) {
            throw new RuntimeException("Mandatory Information not found after scrolling up");
        }
    }
    }


    public void scrollUntilKotlinPidOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            WebElement element = null;

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            try {
                for (int i = 0; i < 80; i++) {
                    try {
                        element = driver.findElement(WalletElements.clickPidFromKotlinFromList);

                        if (element.isDisplayed() && element.isEnabled()) {
                            break;
                        }

                    } catch (Exception ignored) {
                        slowScroll(driver);
                    }
                }
            } finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            try {
                driver.executeScript("mobile: scroll", ImmutableMap.of(
                    "predicateString", "name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_sdJwtPid' OR name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_mDocPid'",
                    "direction", "down"
                ));
            } catch (Exception ignored) {
                // element already visible or scroll not needed
            }
        }
    }

    public void insertPidFromListKotlin() throws InterruptedException {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickToAddDocument();
        test.mobile().wallet().addDocumentPageIsDisplayed();
        test.mobile().wallet().clickFromList();
        test.mobile().wallet().scrollUntilKotlinPidOnDocuments();
        test.mobile().wallet().clickKotlinPIDFromList();
        test.mobile().issuer().signInUser();
        test.mobile().issuer().fillLoginForm();
    }

    private void clickKotlinPIDFromList() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickPidFromKotlinFromList)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            By locator = AppiumBy.iOSNsPredicateString(
                "name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_sdJwtPid' OR name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_mDocPid'"
            );
            driver.setSetting("waitForQuiescence", false);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            try {
                for (int attempt = 0; attempt < 5; attempt++) {
                    try {
                        driver.findElement(locator).click();
                        return;
                    } catch (Exception e) {
                        driver.executeScript("mobile: scroll", ImmutableMap.of(
                            "predicateString", "name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_sdJwtPid' OR name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_mDocPid'",
                            "direction", "down"
                        ));
                    }
                }
                throw new RuntimeException("PID Combined (Kotlin) not clickable after retries");
            } finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                driver.setSetting("waitForQuiescence", true);
            }
        }
    }

    public void scrollUntilPlaceOfBirth() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 10; i++) {
                try {
                    WebElement pidElement = driver.findElement(IssuerElements.clickPlaceOfBirth);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll(driver);
                }
            }

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 2; i++) {
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

    public void verifyMandatoryInfoLabelsPresentInAuthorizePageOnWeb(String yamlPath) {
        FormYml yml = YmlLoader.load(yamlPath, FormYml.class);
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();

        String firstRequiredKey = yml.fields.entrySet().stream()
                .filter(e -> e.getValue().required)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        if (firstRequiredKey != null) {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(d -> d.findElement(By.tagName("body")).getText().contains(firstRequiredKey));
        }

        String pageText = driver.findElement(By.tagName("body")).getText();

        yml.fields.forEach((fieldKey, cfg) -> {

            if (!cfg.required) return;

            if (!pageText.contains(fieldKey)) {
                throw new AssertionError("Label not found: " + fieldKey);
            }

            if (cfg.value != null && !cfg.value.trim().isEmpty()) {

                if (!pageText.contains(cfg.value.trim())) {
                    throw new AssertionError(
                            "Wrong value for " + fieldKey +
                                    " expected: " + cfg.value
                    );
                }
            }
        });
    }

    public String collectAllTextsIOS(IOSDriver driver) {

        List<WebElement> elements = driver.findElements(
                AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeStaticText'")
        );

        StringBuilder allText = new StringBuilder();

        for (WebElement el : elements) {
            String text = el.getAttribute("name");

            if (text != null && !text.trim().isEmpty()) {
                allText.append(text.toLowerCase()).append(" ");
            }
        }

        return allText.toString();
    }

    public String getPageText(AndroidDriver driver) {
        return driver.findElement(By.tagName("body")).getText();
    }

    public void switchToWebView(AndroidDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(d -> {
            for (String context : driver.getContextHandles()) {

                System.out.println("Found context: " + context);

                if (context.contains("WEBVIEW")) {
                    try {
                        driver.context(context);

                        if (driver.getPageSource().length() > 0) {
                            System.out.println("Switched to WebView: " + context);
                            return true;
                        }

                    } catch (Exception e) {
                        System.out.println("WebView not ready yet...");
                    }
                }
            }
            return false;
        });
    }

    public void verifyMandatoryInfoLabelsPresentInAuthorizePage(String yamlPath) {

        FormYml yml = YmlLoader.load(yamlPath, FormYml.class);

        boolean isAndroid = test.getSystemOperation().equals(Literals.General.ANDROID.label);

        AppiumDriver driver = isAndroid
                ? (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid()
                : (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

        Set<String> screenTexts = WalletActionsUtils.collectAllTexts(driver, isAndroid, 8);

        yml.fields.forEach((fieldKey, cfg) -> {

            if (!cfg.required) return;

            if (!screenTexts.contains(fieldKey)) {
                throw new AssertionError("Missing label: " + fieldKey);
            }

            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                if (!screenTexts.contains(cfg.value.trim())) {
                    throw new AssertionError("Missing value: " + cfg.value);
                }
            }
        });
    }

    public void restartApp() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            Thread.sleep(500);
            driver.terminateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(d -> {
                        try {
                            return ((AndroidDriver) d).getPageSource() != null;
                        } catch (Exception e) {
                            return false;
                        }
                    });
            driver.activateApp(test.envDataConfig().getAppiumAndroidAppPackage());
            test.mobile().wallet().loginPageIsDisplayed();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            Thread.sleep(500);
            driver.terminateApp(test.envDataConfig().getAppiumIosBundleId());
            new WebDriverWait(driver, Duration.ofSeconds(50))
                    .until(d -> {
                        try {
                            return ((IOSDriver) d).getPageSource() != null;
                        } catch (Exception e) {
                            return false;
                        }
                    });
            driver.activateApp(test.envDataConfig().getAppiumIosBundleId());
            test.mobile().wallet().loginPageIsDisplayed();
        }
    }

    public void initiateCredential(String credential, String issuerType) {

                    test.mobile().wallet().launchApp();
                    test.mobile().wallet().checkIfPageIsTrue();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().renterThePin();
                    test.mobile().wallet().successMessageOfSetUpPin();
                    test.mobile().wallet().clickAddMyDigitalID();

        }


    public void credentialStoredInWallet(String credential, String issuerType) {
        this.credential = credential;
        this.issuerType = issuerType;
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            if ("PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickClose();
                test.mobile().wallet().clickOnDocuments();
                test.mobile().wallet().pidSdJwtIsDisplayedOnDocuments();
            }else if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickClose();
                test.mobile().wallet().clickOnDocuments();
                test.mobile().wallet().secondPIDKotlinIsDisplayed();
            } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().clickClose();
                test.mobile().wallet().clickOnDocuments();
                test.mobile().wallet().mdlIsDisplayedKotlin();
            }
        } else {
            test.mobile().wallet().clickDone();
            test.mobile().wallet().clickOnDocuments();
            if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                test.mobile().wallet().pidMdocIsDisplayed();
            }else if ("PID (SD-JWT)".equalsIgnoreCase(this.credential)){
                test.mobile().wallet().pidSdJwtIsDisplayedOnDocuments();
            }
        }
    }

    private void pidSdJwtIsDisplayedOnDocuments() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.WalletElements.pidSdJwtIsDisplayedOnDocuments)).getText();
            Assert.assertEquals(Literals.Wallet.PID_SD_JWT_ON_DOCUMENTS_ANDROID.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.pidSdJwtIsDisplayedOnDocuments)).getText();
            Assert.assertEquals(Literals.Wallet.PID_SD_JWT_ON_DOCUMENTS_IOS.label, pageHeader);
        }
    }

    public void pidMdocIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            //todo
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.pidMdocIsDisplayed)).getText();
            Assert.assertEquals(Literals.Wallet.PID_IOS.label, pageHeader);
        }
    }

    public void presentCredential(String verifierType) {
        switch (verifierType.toLowerCase()) {
            case "web verifier":
                test.mobile().wallet().userOpensVerifier();
                break;
        }
    }

    public void performPresentation(String presentationScenario, String credential, String selectiveDisclosure, String issuerType) throws InterruptedException {
        this.selectiveDisclosure = selectiveDisclosure;
        this.issuerType = issuerType;
        if ("PID (MSO Mdoc)".equalsIgnoreCase(credential) || "PID (SD-JWT)".equalsIgnoreCase(credential)) {

            switch (presentationScenario.toLowerCase()) {

                case "same device":

                    switch (this.selectiveDisclosure.toLowerCase()) {

                        case "specific attributes":

                            test.mobile().verifier().launchSafari();
                            test.mobile().wallet().rotateScreen();
                            test.mobile().verifier().appOpensSuccessfully();
                            test.mobile().verifier().selectSpecificAttributesOnVerifier(this.credential);
                            test.mobile().verifier().scrollUntilNext();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().selectAttributes();
                            test.mobile().verifier().clickSpecificAttributes();
                            test.mobile().verifier().clickSelect();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().scrollUntilSumbit();
                            test.mobile().verifier().clickSubmit();
                            break;
                    }

                    test.mobile().verifier().chooseWallet();
                    if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
                        test.mobile().wallet().pinFieldIsDisplayed();
                        test.mobile().verifier().insertPIN();
                    }
                    test.mobile().verifier().viewDataPage();

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {
                            test.mobile().wallet().clickPIDFromKotlin();
                        } else {
                            test.mobile().wallet().clickPIDSdjwt();
                        }
                    } else {
                        test.mobile().wallet().clickToViewDetails();
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if ("PID (MSO Mdoc)".equalsIgnoreCase(credential)) {
                            if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet.yml");
                            }
                        } else if ("PID (SD-JWT)".equalsIgnoreCase(credential)) {
                            if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                                if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
                                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                            "testdata/PID/pre_final_shared_data_on_wallet_sdjwt.yml");
                                } else {
                                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                            "testdata/PID/pre_final_shared_data_on_wallet_sdjwt_android.yml");
                                }
                            }
                        }
                    }

                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().pinFieldIsDisplayed();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().authenticationSuccessfully();
                    break;

                case "cross device":

                    switch (this.selectiveDisclosure.toLowerCase()) {

                        case "specific attributes":

                            test.webWebDriverFactory().startWebDriverSession();

                            try {
                                test.envDataConfig();
                                String url = test.envDataConfig().getVerifierUrl();
                                test.webWebDriverFactory().getDriverWeb().get(url);
                                test.web().verifier().appOpensSuccessfullyOnWeb();
                                test.web().verifier().selectSpecificAttributesOnWebForPID(this.credential);
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().pidIsDisplayedOnWeb();
                                test.web().verifier().clickSpecificAttributesButtonForPID();
                                test.web().verifier().selectSpecificAttributesOnWeb();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().uriMethodIsDisplayed();
                                test.web().verifier().scrollUntilSubmitOnWeb();
                                test.web().verifier().assertQrCodeIsVisible();
                                test.web().verifier().captureScreenOnWeb();

                            } catch (org.openqa.selenium.WebDriverException e) {

                                e.printStackTrace();
                            }

                            break;
                    }

                    test.mobile().wallet().restartApp();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickAuthenticate();
                    test.mobile().wallet().clickOnlinePresentation();

                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }
                    test.mobile().wallet().mockQRInject(test.web().verifier().getCapturedScreenFile());

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        if ("PID (SD-JWT)".equalsIgnoreCase(credential)) {
                            test.mobile().wallet().clickPIDSdjwt();
                        } else {
                            test.mobile().wallet().clickPIDFromKotlin();
                        }
                    } else {
                        test.mobile().wallet().clickToViewDetails();
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            if ("PID (SD-JWT)".equalsIgnoreCase(credential)) {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet_sdjwt_android.yml");
                            } else {
                                test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                        "testdata/PID/pre_final_shared_data_on_wallet.yml");
                            }
                        }
                        test.mobile().wallet().clickShareButton();
                        test.mobile().wallet().createAPin();
                        test.mobile().wallet().authenticationSuccessfully();
                        break;
                    } else {

                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_pre_final_shared_data_on_wallet.yml");
                        }
                        test.mobile().wallet().clickShareButton();
                        test.mobile().wallet().createAPin();
                        test.mobile().wallet().authenticationSuccessfully();
                        break;
                    }
            }
        } else {
            switch (presentationScenario.toLowerCase()) {
                case "same device":
                    switch (this.selectiveDisclosure.toLowerCase()) {
                        case "specific attributes":
                            test.mobile().verifier().launchSafari();
                            test.mobile().verifier().appOpensSuccessfully();
                            test.mobile().verifier().selectSpecificAttributesForMdl();
                            test.mobile().verifier().scrollUntilNext();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().selectAttributes();
                            test.mobile().verifier().clickSpecificAttributes();
                            test.mobile().verifier().clickSelect();
                            test.mobile().verifier().clickNext();
                            test.mobile().verifier().scrollUntilSumbit();
                            test.mobile().verifier().clickSubmit();
                            break;
                    }

                    test.mobile().verifier().chooseWallet();
                    if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
                        test.mobile().wallet().pinFieldIsDisplayed();
                        test.mobile().verifier().insertPIN();
                    }
                    test.mobile().verifier().viewDataPage();

                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        test.mobile().wallet().clickMDLFromKotlin();
                    } else {
                        test.mobile().wallet().clickToViewDetails();
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet.yml");
                        }
                    } else {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/kotlin_pre_final_shared_data_on_wallet.yml");
                        }
                    }

                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().pinFieldIsDisplayed();
                    test.mobile().verifier().insertPIN();
                    test.mobile().wallet().authenticationSuccessfully();
                    break;
                case "cross device":
                    switch (this.selectiveDisclosure.toLowerCase()) {
                        case "specific attributes":
                            test.webWebDriverFactory().startWebDriverSession();
                            try {
                                test.envDataConfig();
                                String url = test.envDataConfig().getVerifierUrl();
                                test.webWebDriverFactory().getDriverWeb().get(url);
                                test.web().verifier().appOpensSuccessfullyOnWeb();
                                test.web().verifier().selectSpecificAttributesOnWebForMdl();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().mdlIsDisplayedOnWeb();
                                test.web().verifier().clickSpecificAttributesButtonForMdl();
                                test.web().verifier().selectSpecificAttributesOnWeb();
                                test.web().verifier().scrollUntilNextOnWeb();
                                test.web().verifier().uriMethodIsDisplayed();
                                test.web().verifier().scrollUntilSubmitOnWeb();
                                test.web().verifier().assertQrCodeIsVisible();
                                test.web().verifier().captureScreenOnWeb();
                            } catch (org.openqa.selenium.WebDriverException e) {

                                e.printStackTrace();
                            }
                            break;
                    }
                    test.mobile().wallet().restartApp();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickAuthenticate();
                    test.mobile().wallet().clickOnlinePresentation();
                        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }
                    test.mobile().wallet().mockQRInject(test.web().verifier().getCapturedScreenFile());
                    if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        test.mobile().wallet().clickMDLFromKotlin();
                    } else {
                        test.mobile().wallet().clickToViewDetails();
                    }

                    if ("Python".equalsIgnoreCase(this.issuerType)) {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/pre_final_shared_data_on_wallet.yml");
                        }
                    } else {
                        if (selectiveDisclosure.equalsIgnoreCase("specific attributes")) {
                            test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage(
                                    "testdata/mDL/kotlin_pre_final_shared_data_on_wallet.yml");
                        }
                    }
                    test.mobile().wallet().clickShareButton();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().authenticationSuccessfully();
                    break;
            }
        }
    }


    private void clickPIDSdjwt() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickPidSDJWTFromKotlin)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.pidSdJwtIsDisplayedOnDocuments)).click();
        }
    }

    public void insertMdlFromListKotlin() throws InterruptedException {
            test.mobile().wallet().clickOnDocuments();
            test.mobile().wallet().clickToAddDocument();
            test.mobile().wallet().addDocumentPageIsDisplayed();
            test.mobile().wallet().clickFromList();
            test.mobile().wallet().scrollUntilmDLOnDocuments("kotlin");
            test.mobile().wallet().clickMdlKotlin();
            test.mobile().issuer().signInUser();
            test.mobile().issuer().fillLoginForm();
    }

    public void insertPidSdjwtFromListKotlin() throws InterruptedException {
        test.mobile().wallet().clickOnDocuments();
        test.mobile().wallet().clickToAddDocument();
        test.mobile().wallet().addDocumentPageIsDisplayed();
        test.mobile().wallet().clickFromList();
        test.mobile().wallet().scrollUntilKotlinPidOnDocuments();
        test.mobile().wallet().clickKotlinPIDSDWJTFromList();
        test.mobile().issuer().signInUser();
        test.mobile().issuer().fillLoginForm();
    }

    private void clickKotlinPIDSDWJTFromList() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            Thread.sleep(5000); // 3-second delay
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.clickPidSDJWTFromKotlinFromList)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            By locator = AppiumBy.iOSNsPredicateString(
                    "name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_sdJwtPid' OR name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_mDocPid'"
            );
            driver.setSetting("waitForQuiescence", false);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            try {
                for (int attempt = 0; attempt < 5; attempt++) {
                    try {
                        driver.findElement(locator).click();
                        return;
                    } catch (Exception e) {
                        driver.executeScript("mobile: scroll", ImmutableMap.of(
                                "predicateString", "name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_sdJwtPid' OR name == 'add_document_screen_attestation_issuer-backend.eudiw.dev_mDocPid'",
                                "direction", "down"
                        ));
                    }
                }
                throw new RuntimeException("PID Combined (Kotlin) not clickable after retries");
            } finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                driver.setSetting("waitForQuiescence", true);
            }
        }
    }

    public void clickExpandVerificationForSDJWT() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickExpandVerificationSDJWT)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickExpandVerification)).click();
        }
    }
}