package eu.europa.eudi.pages;

import eu.europa.eudi.api.EventsApiVerifier;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.VerifierElements;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WaitsUtils;
import eu.europa.eudi.utils.config.EnvDataConfig;;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.json.JSONObject;
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
import java.util.NoSuchElementException;

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
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdoc)).click();
                clickMsoMdocRealDevice();
            } else {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocReal)).click();
                clickMsoMdocRealDevice();
            }
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
            String url = "https://verifier.eudiw.dev/home";

            try {
                try {
                    driver.terminateApp("eu.europa.ec.euidi");
                } catch (Exception e) {
                }
                driver.activateApp("com.apple.mobilesafari");
                Thread.sleep(3000);
                driver.get(url);
                Thread.sleep(5000);
                driver.context("NATIVE_APP");
            } catch (Exception e) {
                throw new RuntimeException("Failed to launch Safari", e);
            }
        }
    }

    public void insertPIN() {
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

        // Perform tap on center of bounds
        new TouchAction(driver)
                .tap(PointOption.point(centerX, centerY))
                .perform();
    }

    public void clickTransactionsLogs() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickTransactionsLogs)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickTransactionsLogs)).click();
        }
    }

    public void clickTransactionInitialized() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickTransactionInitialized)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
            WebElement element = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickTransactionInitialized));
            test.mobile().wallet().tapAction(element, true);
        }
    }

    public void getTransactionId() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement jsonElement;
            if (test.envDataConfig().getAppiumBrowserstackAndroidDeviceName().equals("Samsung Galaxy S22 Ultra") || test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {
                jsonElement = driver.findElement(By.xpath("//android.view.View[@resource-id=\"cdk-accordion-child-2\"]/android.widget.TextView"));
            } else {
                jsonElement = driver.findElement(By.className("android.widget.TextView"));

            }
            // Get the text
            String rawText = jsonElement.getText();

            JSONObject jsonObject = new JSONObject(rawText);

            // Extract the transaction_id from "key" field
            String transactionId = jsonObject.getString("key");

            // Output the result
            System.out.println("Transaction ID: " + transactionId);

            EventsApiVerifier api = new EventsApiVerifier();
            api.getPresentationEvents(transactionId);
        } else {
            if (test.envDataConfig().getAppiumBrowserstackIosDeviceName().equals("iPhone 15 Pro")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

// Locate the element that contains the JSON text
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                WebElement jsonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//XCUIElementTypeStaticText[contains(@value, 'value')]")
                ));

// Try to extract text from multiple possible attributes
                String jsonText = jsonElement.getText();

                if (jsonText == null || jsonText.trim().isEmpty()) {
                    jsonText = jsonElement.getAttribute("label");
                }
                if (jsonText == null || jsonText.trim().isEmpty()) {
                    jsonText = jsonElement.getAttribute("value");
                }
                if (jsonText == null || jsonText.trim().isEmpty()) {
                    jsonText = jsonElement.getAttribute("name");
                }

// Validate and parse the JSON
                if (jsonText == null || jsonText.trim().isEmpty()) {
                    throw new RuntimeException("Could not find or extract JSON text from the UI.");
                }

                System.out.println("Found JSON text: " + jsonText);

                try {
                    JSONObject jsonObject = new JSONObject(jsonText);
                    String transactionId = jsonObject.getString("key");

                    System.out.println("Transaction ID: " + transactionId);

                    EventsApiVerifier api = new EventsApiVerifier();
                    api.getPresentationEvents(transactionId);

                } catch (Exception e) {
                    System.err.println("Failed to parse JSON: " + e.getMessage());
                    throw new RuntimeException(e);
                }

            } else {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                List<WebElement> elements = driver.findElements(By.xpath("//XCUIElementTypeStaticText"));
                String jsonText = null;

                for (WebElement el : elements) {
                    int x = el.getLocation().getX();
                    int y = el.getLocation().getY();
                    int width = el.getSize().getWidth();
                    int height = el.getSize().getHeight();

                    // Filter element based on known size or position (optional)
                    if (width == 1407 && height == 198 && x == 90 && y == 265) {
                        // Try getText(), label, value, name
                        String text = el.getText();
                        if (text == null || text.trim().isEmpty()) {
                            text = el.getAttribute("label");
                        }
                        if (text == null || text.trim().isEmpty()) {
                            text = el.getAttribute("value");
                        }
                        if (text == null || text.trim().isEmpty()) {
                            text = el.getAttribute("name");
                        }

                        if (text != null && !text.trim().isEmpty()) {
                            jsonText = text;
                            System.out.println("Found JSON text: " + jsonText);
                            break;
                        }
                    }
                }

                // Validate and parse the JSON
                if (jsonText == null || jsonText.trim().isEmpty()) {
                    throw new RuntimeException("Could not find or extract JSON text from the UI.");
                }

                try {
                    JSONObject jsonObject = new JSONObject(jsonText);
                    String transactionId = jsonObject.getString("key");

                    System.out.println("Transaction ID: " + transactionId);

                    EventsApiVerifier api = new EventsApiVerifier();
                    api.getPresentationEvents(transactionId);

                } catch (Exception e) {
                    System.err.println("Failed to parse JSON: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
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

    public void clickNextForAndroid() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickNextForVerifier)).click();
        }
    }

    public void selectSpecificAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesButton)).click();

            for (int i = 0; i < 5; i++) {
                try {
                    By attributeLocator = By.xpath("//android.view.View[@resource-id='mat-mdc-checkbox-" + i + "']/android.view.View");
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(attributeLocator)).click();
                } catch (Exception e) {
                    System.out.println("Could not click attribute " + i + ", continuing...");
                }
            }

            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickSelect)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributesButton)).click();

            for (int i = 1; i < 5; i++) {
                try {
                    By attributeLocator = By.xpath("(//XCUIElementTypeSwitch[@value=\"0\"])[" + i + "]");
                    test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(attributeLocator)).click();
                } catch (Exception e) {
                    System.out.println("Could not click attribute " + i + ", continuing...");
                }
            }
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickSelect)).click();
        }
    }

    public void createVerifierQRScreenshot() throws InterruptedException {
        appOpensSuccessfully();
        selectSpecificAttestation();
        scrollUntilNext();
        clickNext();
        selectSpecificAttributes();
        clickNext();
        clickNext();

        // Wait a bit for QR code to appear
        Thread.sleep(3000);

        // Capture screenshot
        captureScreen();
    }

    private void selectSpecificAttestation() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.specificAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.msoMdoc)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.selectAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.specificAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.msoMdoc)).click();
        }
    }

    public File captureScreen() {
        WebDriver driver;

        // Get correct driver
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = test.mobileWebDriverFactory().getDriverIos();
        }

        // Safety check
        if (driver == null) {
            throw new RuntimeException("Driver is null. Cannot capture screenshot.");
        }

        // Small wait to avoid blank/transition screenshots
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Create filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotsDir = new File("screenshots");

        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        File destFile = new File(screenshotsDir, timestamp + "_verifier.png");

        try {
            // Take screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Copy using stable Java NIO
            Files.copy(
                    srcFile.toPath(),
                    destFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Validate file
            if (!destFile.exists() || destFile.length() == 0) {
                throw new RuntimeException("Screenshot file is empty: " + destFile.getAbsolutePath());
            }

            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());

            this.capturedScreenFile = destFile;
            return destFile;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to capture screenshot at: " + destFile.getAbsolutePath(),
                    e
            );
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

                        // treat aria-disabled=true as disabled too
                        boolean reallyEnabled =
                                (disabled == null) &&
                                        (ariaDisabled == null || ariaDisabled.equalsIgnoreCase("false"));

                        if (displayed && reallyEnabled) return b;

                    } catch (StaleElementReferenceException ignored) {
                    }
                }
                return null;
            });

            // Try normal click then JS click
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

                        // treat aria-disabled=true as disabled too
                        boolean reallyEnabled =
                                (disabled == null) &&
                                        (ariaDisabled == null || ariaDisabled.equalsIgnoreCase("false"));

                        if (displayed && reallyEnabled) return b;

                    } catch (StaleElementReferenceException ignored) {
                    }
                }
                return null;
            });

            // Try normal click then JS click
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
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
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

    public File captureScreenOnWeb() {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        if (driver == null) {
            throw new RuntimeException("Web driver is null. Cannot capture screenshot.");
        }

        // Small stability wait (important for dynamic rendering)
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File screenshotsDir = new File("screenshots");

        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        File destFile = new File(screenshotsDir, timestamp + "_verifier.png");

        try {
            // CORRECT selector
            By qrContainer = By.cssSelector(".vc-verifiable-credential");

            // Wait for container first (more stable than canvas)
            WebElement container = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(qrContainer)
            );

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    container
            );

            // Find canvas INSIDE container (not globally)
            WebElement canvas = wait.until(d -> {
                try {
                    WebElement c = container.findElement(By.cssSelector("qrcode canvas"));
                    return c.isDisplayed() ? c : null;
                } catch (Exception e) {
                    return null;
                }
            });

            // Wait until canvas is actually rendered (CRITICAL FIX)
            wait.until(d -> {
                Long w = (Long) ((JavascriptExecutor) d)
                        .executeScript("return arguments[0].getBoundingClientRect().width;", canvas);

                Long h = (Long) ((JavascriptExecutor) d)
                        .executeScript("return arguments[0].getBoundingClientRect().height;", canvas);

                return w != null && h != null && w > 0 && h > 0;
            });

            // Extra buffer for rendering (BrowserStack safe)
            Thread.sleep(500);

            // Try canvas screenshot first
            File srcFile;
            try {
                srcFile = canvas.getScreenshotAs(OutputType.FILE);
            } catch (Exception e) {
                // 🔥 Fallback: container screenshot (VERY important)
                srcFile = container.getScreenshotAs(OutputType.FILE);
            }

            // Save using NIO
            Files.copy(
                    srcFile.toPath(),
                    destFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Validate file
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

    public void selectSpecificAttributesOnVerifier(String credential) {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.specificAttributes));
                Dimension dropdownSize = dropdown.getSize();
                test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() / 3);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdoc)).click();
                clickMsoMdocRealDevice();
            } else {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocReal)).click();
                clickMsoMdocRealDevice();
            }
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

// reduce implicit wait
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

                // small pause helps Safari WebView refresh
                Thread.sleep(500);
            }

// restore implicit wait
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
                Thread.sleep(500);
            }

            List<WebElement> switches = driver.findElements(AppiumBy.className("XCUIElementTypeSwitch"));
            WebElement el = switches.get(index);
            wait.until(ExpectedConditions.visibilityOf(el));
            wait.until(ExpectedConditions.elementToBeClickable(el));
            el.click();
            Thread.sleep(500);
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
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickDataMdl)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesMdl)).click();
                WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.specificAttributesMdl));
                Dimension dropdownSize = dropdown.getSize();
                test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() - 50);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatMdl)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocMdl)).click();
            } else {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocReal)).click();
                clickMsoMdocRealDevice();
            }
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
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.clickDataMdl)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesMdl)).click();
                WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(VerifierElements.specificAttributesMdl));
                Dimension dropdownSize = dropdown.getSize();
                test.mobile().wallet().tapAction(dropdown, dropdownSize.getWidth() / 2, dropdownSize.getHeight() / 3);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatMdl)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocMdl)).click();
            } else {
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributes)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttribute)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormat)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocReal)).click();
                clickMsoMdocRealDevice();
            }
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

    public void selectSpecificAttributesOnWebForMdl() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickDataOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.selectAttributesOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.firstAttributeSpecificOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickFormatOnWebForMdl)).click();
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.msoMdocOnWebForMdl)).click();
    }

    public void clickSpecificAttributesButtonForMdl() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickSpecificAttributesButtonForMdlOnWeb)).click();
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
            // Wait for header to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    eu.europa.eudi.elements.android.VerifierElements.walletRespondedWebMdlKotlin
            ));

            // If visible → click View Content
            wait.until(ExpectedConditions.elementToBeClickable(
                    eu.europa.eudi.elements.android.VerifierElements.clickViewContentOnWeb
            )).click();

        } catch (TimeoutException e) {
            // Header not visible → refresh
            test.webWebDriverFactory().getDriverWeb().navigate().refresh();
            test.web().verifier().walletRespondedOnWebforMdlKotlin();
            test.web().verifier().clickViewContentOnWeb();
        }
    }
}