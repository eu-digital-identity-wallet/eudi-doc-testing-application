package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.VerifierElements;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WaitsUtils;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Verifier {
    TestSetup test;
    EnvDataConfig envDataConfig;
    private File capturedScreenFile;


    public Verifier(TestSetup test) {
        this.test = test;
    }

    public void clickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickNextForVerifier)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickNext)).click();
        }
    }

    public void assertAndClickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.presentationQueryTypeIsVisible)).getText();
            Assert.assertEquals(Literals.Verifier.PRESENTATION_QUERY_TYPE.label, pageHeader);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickNextForVerifier)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickNext)).click();
        }
    }

    public void chooseWallet() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.chooseWallet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.chooseWallet)).click();
        }
    }

    public void viewDataPage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.android.VerifierElements.viewDataPage,
                    Literals.Verifier.VIEW_DATA_PAGE.label,
                    driver,
                    50
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.VerifierElements.viewDataPage
            ).getText().trim();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, headerText);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.viewDataPageOnWallet)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        }
    }

    public void appOpensSuccessfully() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
        } else {
            //nothing
        }
    }

    public void selectAllAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
            WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute));
            Dimension dropdownSize = dropdown.getSize();
            test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() - 50);
//            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdoc)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.clickPersonIdentificationData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributesBy)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.allAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.msoMdoc)).click();
        }
    }

    public void scrollUntilNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(50)" +
                            ".scrollIntoView(new UiSelector().text(\"Next\"))"
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

    public void launchSafari() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            try {
                try {
                    driver.terminateApp("eu.europa.ec.euidi");
                } catch (Exception e) {
                }
                driver.activateApp("com.apple.mobilesafari");
                WebDriverWait waitNativeAppTransition = new WebDriverWait(driver, Duration.ofSeconds(2000));
                waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));
                driver.context("NATIVE_APP");
            } catch (Exception e) {
                throw new RuntimeException("Failed to launch Safari", e);
            }
        }
    }

    public void insertPIN() {
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
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1Ver).click();
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield2Ver).sendKeys(String.valueOf(secondDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield3Ver).sendKeys(String.valueOf(thirdDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield4Ver).sendKeys(String.valueOf(fourthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield5Ver).sendKeys(String.valueOf(fifthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield6Ver).sendKeys(String.valueOf(sixthDigit));
        }
    }

    public void insertPIN2() {
        if (test.getSystemOperation().equals(Literals.General.IOS.label)) {
            String fullPin = test.envDataConfig().getPin();
            char secondDigit = fullPin.charAt(1);
            char thirdDigit = fullPin.charAt(2);
            char fourthDigit = fullPin.charAt(3);
            char fifthDigit = fullPin.charAt(4);
            char sixthDigit = fullPin.charAt(5);
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1Ver).click();
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield2Ver).sendKeys(String.valueOf(secondDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield3Ver).sendKeys(String.valueOf(thirdDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield4Ver).sendKeys(String.valueOf(fourthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield5Ver).sendKeys(String.valueOf(fifthDigit));
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield6Ver).sendKeys(String.valueOf(sixthDigit));
        }
    }

    public void walletResponded() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.walletResponded)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED.label, pageHeader);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.walletResponded)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED.label, pageHeader);
        }
    }

    public void clickMsoMdocRealDevice() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        int centerX = 545;
        int centerY = 1715;

        new TouchAction(driver)
                .tap(PointOption.point(centerX, centerY))
                .perform();
    }

    public void chooseWalletPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.chooseWalletPageDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.CHOOSE_WALLET_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.chooseWalletPageDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.CHOOSE_WALLET_DISPLAYED.label, pageHeader);
        }
    }

    public File captureScreen() throws InterruptedException {
        WebDriver driver;

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = test.mobileWebDriverFactory().getDriverIos();
        }

        if (driver == null) {
            throw new RuntimeException("Driver is null.");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[src^='data:image/png']")));
        } catch (Exception e) {
            System.out.println("Warning: Stability element not found.");
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotsDir = new File("screenshots");
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }
        File destFile = new File(screenshotsDir, timestamp + "_verifier.png");

        int maxRetries = 3;
        int attempts = 0;
        boolean success = false;

        while (attempts < maxRetries && !success) {
            try {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                if (destFile.exists() && destFile.length() > 0) {
                    success = true;
                } else {
                    attempts++;
                }
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxRetries) {
                    throw new RuntimeException("Screenshot failed after " + maxRetries + " attempts: " + e.getMessage(), e);
                }
            }
        }

        if (success) {
            this.capturedScreenFile = destFile;
            return destFile;
        } else {
            throw new RuntimeException("Failed to capture a non-empty screenshot.");
        }
    }

    public File getCapturedScreenFile() {
        return capturedScreenFile;
    }

    public void appOpensSuccessfullyOnWeb() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.appOpensSuccessfullyOnWeb)).getText();
        Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
    }

    public void selectAllAttributesOnWeb() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickDataOnWeb)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesOnWeb)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttributeOnWeb)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatOnWeb)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocOnWeb)).click();
    }

    public void scrollUntilNextOnWeb() {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        By nextCandidates = By.xpath("//button[.//*[normalize-space(.)='Next'] or normalize-space(.)='Next']");

        try {
            WebElement btn = wait.until(d -> {
                List<WebElement> buttons = d.findElements(nextCandidates);

                for (int i = 0; i < buttons.size(); i++) {
                    WebElement b = buttons.get(i);
                    try {
                        String disabled = b.getAttribute("disabled");
                        String ariaDisabled = b.getAttribute("aria-disabled");
                        boolean displayed = b.isDisplayed();
                        boolean enabled = b.isEnabled();

                        if (displayed) {
                            ((JavascriptExecutor) d).executeScript(
                                    "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", b
                            );
                        }

                        boolean reallyEnabled =
                                (disabled == null) &&
                                        (ariaDisabled == null || ariaDisabled.equalsIgnoreCase("false"));

                        if (displayed && reallyEnabled) return b;

                    } catch (StaleElementReferenceException ignored) {
                    }
                }
                return null;
            });

            try {
                btn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            }

        } catch (TimeoutException e) {
            throw e;
        }
    }

    public void scrollUntilSubmitOnWeb() {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        By nextCandidates = By.xpath("//button[.//*[normalize-space(.)='Submit'] or normalize-space(.)='Submit']");

        try {
            WebElement btn = wait.until(d -> {
                List<WebElement> buttons = d.findElements(nextCandidates);

                for (int i = 0; i < buttons.size(); i++) {
                    WebElement b = buttons.get(i);
                    try {
                        String disabled = b.getAttribute("disabled");
                        String ariaDisabled = b.getAttribute("aria-disabled");
                        boolean displayed = b.isDisplayed();
                        boolean enabled = b.isEnabled();

                        if (displayed) {
                            ((JavascriptExecutor) d).executeScript(
                                    "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", b
                            );
                        }

                        boolean reallyEnabled =
                                (disabled == null) &&
                                        (ariaDisabled == null || ariaDisabled.equalsIgnoreCase("false"));

                        if (displayed && reallyEnabled) return b;

                    } catch (StaleElementReferenceException ignored) {
                    }
                }
                return null;
            });

            try {
                btn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            }

        } catch (TimeoutException e) {
            throw e;
        }
    }

    public void assertQrCodeIsVisible() {
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        By qrCanvas = By.xpath("//qrcode//canvas");
        WebElement canvas = wait.until(
                ExpectedConditions.visibilityOfElementLocated(qrCanvas)
        );
        Assert.assertTrue("QR Code canvas is not displayed", canvas.isDisplayed());
    }

    public void pidIsDisplayedOnWeb() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.pidIdDisplayedOnWeb)).getText();
        Assert.assertEquals(Literals.Verifier.PID_IS_DISPLAYED_ON_WEB.label, pageHeader);
    }

    public void mdlIsDisplayedOnWeb() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.mdlIdDisplayedOnWeb)).getText();
        Assert.assertEquals(Literals.Verifier.MDL_IS_DISPLAYED_ON_WEB_KOTLIN.label, pageHeader);
    }

    public void uriMethodIsDisplayed() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.uriMethodIdDisplayedOnWeb)).getText();
        Assert.assertEquals(Literals.Verifier.URI_METHOD_IS_DISPLAYED_ON_WEB.label, pageHeader);
    }

    public File captureScreenOnWeb() throws InterruptedException {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        if (driver == null) {
            throw new RuntimeException("Web driver is null. Cannot capture screenshot.");
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotsDir = new File("screenshots");

        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        File destFile = new File(screenshotsDir, timestamp + "_verifier.png");

        try {
            By qrContainerSelector = By.cssSelector(".vc-verifiable-credential");

            WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(qrContainerSelector));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    container
            );

            WebElement canvas = wait.until(d -> {
                try {
                    WebElement c = container.findElement(By.cssSelector("qrcode canvas"));
                    return c.isDisplayed() ? c : null;
                } catch (Exception e) {
                    return null;
                }
            });

            wait.until(d -> {
                try {
                    Object width = ((JavascriptExecutor) d).executeScript(
                            "return arguments[0].getBoundingClientRect().width;", canvas);
                    Object height = ((JavascriptExecutor) d).executeScript(
                            "return arguments[0].getBoundingClientRect().height;", canvas);

                    return width instanceof Number && height instanceof Number
                            && ((Number) width).doubleValue() > 0
                            && ((Number) height).doubleValue() > 0;
                } catch (Exception e) {
                    return false;
                }
            });

            File srcFile;
            try {
                srcFile = canvas.getScreenshotAs(OutputType.FILE);
            } catch (Exception e) {
                srcFile = container.getScreenshotAs(OutputType.FILE);
            }

            Files.copy(
                    srcFile.toPath(),
                    destFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            if (!destFile.exists() || destFile.length() == 0) {
                throw new RuntimeException("Screenshot file is empty: " + destFile.getAbsolutePath());
            }

            System.out.println("Web QR screenshot saved: " + destFile.getAbsolutePath());
            this.capturedScreenFile = destFile;
            return destFile;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to capture QR screenshot (web) at: " + destFile.getAbsolutePath(),
                    e
            );
        }
    }

    public void scrollUntilSumbit() {

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".setAsVerticalList()" +
                            ".flingForward()" +
                            ".setMaxSearchSwipes(50)" +
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

    public void clickSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickSubmit)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickSubmit)).click();
        }
    }

    public void selectSpecificAttributesOnVerifier() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.specificAttributes));
                Dimension dropdownSize = dropdown.getSize();
                test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() / 3);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdoc)).click();
                clickMsoMdocRealDevice();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.clickPersonIdentificationData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributesBy)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.allAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.msoMdoc)).click();
        }
    }

    public void selectAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributeButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributeButton)).click();
        }
    }

    public void clickSpecificAttributes() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectFirstAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectSecondAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectThirdAttribute)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();


            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

            List<WebElement> switches = wait.until(d ->
                    driver.findElements(AppiumBy.className("XCUIElementTypeSwitch"))
            );

            for (int i = 0; i < 3; i++) {

                switches = driver.findElements(AppiumBy.className("XCUIElementTypeSwitch"));

                WebElement el = switches.get(i);

                wait.until(ExpectedConditions.visibilityOf(el));
                wait.until(ExpectedConditions.elementToBeClickable(el));

                el.click();
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        }
    }

    public void selectTheMandatoryAttributes() throws InterruptedException {
        IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
        WebDriverWait wait = test.mobileWebDriverFactory().getWait();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        int[] mandatoryIndices = {0, 1, 2, 3, 4, 9, 11, 12, 13, 14, 15};

        for (int index : mandatoryIndices) {

            if (index == 11) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.65);
                int endY = (int) (size.height * 0.35);
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(Collections.singletonList(swipe));
            }

            List<WebElement> switches = driver.findElements(AppiumBy.className("XCUIElementTypeSwitch"));
            WebElement el = switches.get(index);
            wait.until(ExpectedConditions.visibilityOf(el));
            wait.until(ExpectedConditions.elementToBeClickable(el));
            String prevValue = el.getAttribute("value");
            el.click();
            wait.until(d -> !prevValue.equals(el.getAttribute("value")));
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
    }

    public void clickSelect() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickSelectAttributes)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickSelectAttributes)).click();
        }
    }

    public void clickViewContent() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickViewContent)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickViewContent)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        }
    }

    public void clickViewContentOnWeb() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickViewContentOnWeb)).click();
    }

    public void clickCloseOnVerifier() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickCloseOnVerifier)).click();
    }

    public void selectAllAttributesForMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickDataMdl)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesMdl)).click();
            WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.specificAttributesMdl));
            Dimension dropdownSize = dropdown.getSize();
            test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() - 50);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatMdl)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocMdl)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickDataMdl)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributesMdl)).click();
            WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.allAttributes));
            Dimension dropdownSize = dropdown.getSize();
            test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() / 3);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickFormatMdl)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.msoMdocMdl)).click();
        }
    }

    public void selectSpecificAttributesForMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickDataMdl)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesMdl)).click();
                WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.specificAttributesMdl));
                Dimension dropdownSize = dropdown.getSize();
                test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() / 3);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatMdl)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocMdl)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickDataMdl)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributesMdl)).click();
            WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.specificAttributesMdl));
            Dimension dropdownSize = dropdown.getSize();
            test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() / 3);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickFormatMdl)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.msoMdocMdl)).click();
        }
    }

    public void walletRespondedMdlKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.walletRespondedMdlKotlin)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED_MDL_KOTLIN.label, pageHeader);
        }else{
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.walletRespondedMdlKotlin)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED_MDL_KOTLIN.label, pageHeader);
        }
    }

    public void selectAllAttributesOnWebForMdl() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickDataOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttributeOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocOnWebForMdl)).click();
    }

    public void selectAllAttributesOnWebForPID() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickDataOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttributeOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocOnWebForPID)).click();
    }

    public void selectSpecificAttributesOnWebForMdl() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickDataOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttributeSpecificOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocOnWebForMdl)).click();
    }

    public void selectSpecificAttributesOnWebForPID() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickDataOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttributeSpecificOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatOnWebForPID)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocOnWebForPID)).click();
    }

    public void clickSpecificAttributesButtonForMdl() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickSpecificAttributesButtonForMdlOnWeb)).click();
    }

    public void clickSpecificAttributesButtonForPID() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickSpecificAttributesButtonForPIDOnWeb)).click();
    }

    public void selectSpecificAttributesOnWeb() {
        for (int i = 0; i < 3; i++) {
            try {
                By attributeLocator = By.xpath("//label[@for='mat-mdc-checkbox-" + i + "-input']");
                test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(attributeLocator)).click();
            } catch (Exception e) {
                System.out.println("Could not click attribute " + i + ", continuing...");
            }
        }
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='Select']]"))).click();
    }

    public void selectMandatoryAttributesOnWeb() {
        int[] mandatoryIndices = {0, 1, 2, 3, 4, 9, 11, 12, 13, 14, 15};
        for (int i : mandatoryIndices) {
            try {
                By attributeLocator = By.xpath("//label[@for='mat-mdc-checkbox-" + i + "-input']");
                test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(attributeLocator)).click();
            } catch (Exception e) {
                System.out.println("Could not click attribute " + i + ", continuing...");
            }
        }
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space()='Select']]"))).click();
    }

    public void walletRespondedOnWebforMdlKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.walletRespondedWebMdlKotlin)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED_MDL_KOTLIN.label, pageHeader);
        }
    }

    public void clickCloseOnVerifierWeb() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickCloseVerifierOnWeb)).click();
    }

    public void checkTheResponse() {
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    eu.europa.eudi.elements.android.VerifierElements.walletRespondedWebMdlKotlin
            ));

            wait.until(ExpectedConditions.elementToBeClickable(
                    eu.europa.eudi.elements.android.VerifierElements.clickViewContentOnWeb
            )).click();

        } catch (TimeoutException e) {
            test.webWebDriverFactory().getDriverWeb().navigate().refresh();
            test.web().verifier().walletRespondedOnWebforMdlKotlin();
            test.web().verifier().clickViewContentOnWeb();
        }
    }
}