package eu.europa.eudi.pages;

import eu.europa.eudi.api.EventsApiVerifier;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.android.VerifierElements;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.io.FileHandler;

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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        }
    }

    public void appOpensSuccessfully() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
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
            driver.findElement(MobileBy.AndroidUIAutomator(
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
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickTransactionsLogs)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.VerifierElements.clickTransactionsLogs)).click();
        }
    }

    public void clickTransactionInitialized() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickTransactionInitialized)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
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
            JSONObject valueObject = jsonObject.getJSONObject("value");

            // Step 4: Extract the transaction_id
            String transactionId = valueObject.getString("transaction_id");

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
                    JSONObject valueObject = jsonObject.getJSONObject("value");
                    String transactionId = valueObject.getString("transaction_id");

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
                    JSONObject valueObject = jsonObject.getJSONObject("value");
                    String transactionId = valueObject.getString("transaction_id");

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
                    By attributeLocator = By.xpath("(//XCUIElementTypeSwitch[@value=\"0\"])["+ i +"]");
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

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = test.mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = test.mobileWebDriverFactory().getDriverIos();
        }

        // Generate a unique filename based on the current timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timestamp + "_verifier.jpg";
        File destFile = new File("screenshots/" + filename);
        try {
            // Wait for QR code to be stable
            Thread.sleep(2000);

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            FileHandler.copy(srcFile, destFile);
            System.out.println("Verifier QR Screenshot saved at: " + destFile.getAbsolutePath());
            this.capturedScreenFile = destFile;
            return destFile;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to capture verifier QR screenshot: " + e.getMessage());
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

        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("Title: " + driver.getTitle());
        System.out.println("iframes: " + driver.findElements(By.tagName("iframe")).size());

        try {
            WebElement btn = wait.until(d -> {
                List<WebElement> buttons = d.findElements(nextCandidates);
                System.out.println("Next candidates found: " + buttons.size());

                for (int i = 0; i < buttons.size(); i++) {
                    WebElement b = buttons.get(i);
                    try {
                        String disabled = b.getAttribute("disabled");
                        String ariaDisabled = b.getAttribute("aria-disabled");
                        boolean displayed = b.isDisplayed();
                        boolean enabled = b.isEnabled();

                        String html = (String) ((JavascriptExecutor) d).executeScript(
                                "return arguments[0].outerHTML;", b
                        );
                        if (html != null && html.length() > 300) html = html.substring(0, 300) + "...";

                        System.out.println("[" + i + "] displayed=" + displayed
                                + " enabled=" + enabled
                                + " disabledAttr=" + disabled
                                + " ariaDisabled=" + ariaDisabled
                                + " html=" + html
                        );

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

                    } catch (StaleElementReferenceException ignored) {}
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
            // final snapshot-like info (no screenshot, but key page indicators)
            System.out.println("Timed out waiting for enabled+displayed Next.");
            System.out.println("URL at timeout: " + driver.getCurrentUrl());
            System.out.println("Title at timeout: " + driver.getTitle());
            throw e;
        }
    }

    public void clickNextOnWeb() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.nextButton)).click();
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

    public void uriMethodIsDisplayed() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.uriMethodIdDisplayedOnWeb)).getText();
        Assert.assertEquals(Literals.Verifier.URI_METHOD_IS_DISPLAYED_ON_WEB.label, pageHeader);
    }

    public File captureScreenOnWeb() {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        // Generate a unique filename based on timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timestamp + "_verifier.jpg";
        File destFile = new File("screenshots/" + filename);

        try {
            // Ensure folder exists
            destFile.getParentFile().mkdirs();

            By qrCanvas = By.cssSelector("qrcode canvas");

            // Wait for canvas to be visible
            WebElement canvas = wait.until(ExpectedConditions.visibilityOfElementLocated(qrCanvas));

            // Scroll into view (important on BrowserStack)
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", canvas
            );

            // Wait until canvas is actually rendered (non-zero size)
            wait.until(d -> {
                WebElement c = d.findElement(qrCanvas);
                Long w = (Long) ((JavascriptExecutor) d).executeScript("return arguments[0].width;", c);
                Long h = (Long) ((JavascriptExecutor) d).executeScript("return arguments[0].height;", c);
                return w != null && h != null && w > 0 && h > 0;
            });

            // Element screenshot (QR only)
            File srcFile = canvas.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile, destFile);

            System.out.println("Verifier QR (Web) screenshot saved at: " + destFile.getAbsolutePath());
            this.capturedScreenFile = destFile;
            return destFile;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to capture verifier QR (web) screenshot: " + e.getMessage());
        }
    }
}