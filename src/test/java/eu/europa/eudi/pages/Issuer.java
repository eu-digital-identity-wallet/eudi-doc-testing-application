package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.data.yml.FormYml;
import eu.europa.eudi.elements.android.IssuerElements;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WaitsUtils;
import eu.europa.eudi.utils.YmlLoader;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidBatteryInfo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en_scouse.An;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;

public class Issuer {
    TestSetup test;
    EnvDataConfig envDataConfig;

    public Issuer(TestSetup test) {
        this.test = test;
    }

    public void issuerService() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));

            String url = "https://ec.dev.issuer.eudiw.dev/credential_offer";
            String env = test.envDataConfig().getExecutionEnvironment();

            if ("browserstack".equalsIgnoreCase(env)) {
                // Safe for BrowserStack
                Map<String, Object> deepLinkArgs = new HashMap<>();
                deepLinkArgs.put("url", "https://ec.dev.issuer.eudiw.dev/credential_offer");
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
            String url = "https://ec.dev.issuer.eudiw.dev/credential_offer";
            driver.get(url);
            Map<String, Object> args = new HashMap<>();
            args.put("bundleId", "com.apple.mobilesafari");
            driver.executeScript("mobile: launchApp", args);
        }
    }

    public void launchSafari() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            String url = "https://ec.dev.issuer.eudiw.dev/credential_offer";

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

    public void requestCredentialsPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.requestCredentialsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE.label, pageHeader);
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.requestCredentialsPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE_IOS.label, pageHeader);
        }
    }

    public void clickPersonalIdentificationData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.checkPID)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickPID)).click();
        }
    }

    public void clickSubmitButton() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickSubmitButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickSubmitButton)).click();
        }
    }

    public void qrCodeIsDisplayed() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            Thread.sleep(2000); // allow UI to settle

            boolean found = false;

// =========================
// 1TRY NATIVE FIRST
// =========================
            try {
                driver.context("NATIVE_APP");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

                WebElement nativeQr = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        eu.europa.eudi.elements.android.IssuerElements.qrCodeIsDisplayed
                ));

                Assert.assertTrue(nativeQr.getText().trim()
                        .contains(Literals.Issuer.QR_CODE.label));

                found = true;

                System.out.println("QR found in NATIVE");

            } catch (Exception e) {
                System.out.println("QR not found in NATIVE");
            }

// =========================
// FALLBACK TO WEBVIEW
// =========================
            if (!found) {
                for (String context : driver.getContextHandles()) {
                    System.out.println("Context: " + context);
                    if (context.contains("WEBVIEW")) {
                        driver.context(context);
                        break;
                    }
                }

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

                // IMPORTANT: QR in webview usually has NO text
                // so we look for container / canvas / image
                WebElement webQr = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//img | //canvas | //*[contains(@class,'qr')]")
                ));

                Assert.assertTrue(webQr.isDisplayed());

                System.out.println("QR found in WEBVIEW");

                // ALWAYS go back
                driver.context("NATIVE_APP");
            }

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.qrCodeIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, pageHeader);
        }
    }

    public void clickUseEudiw() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

//            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
//            driver.runAppInBackground(Duration.ofSeconds(30));
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickEudiwButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickEudiwButton)).click();
        }
    }

    public void authenticationPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
        }
    }

    public void clickCountrySelection() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountrySelection)).click();
        } else {
            WebElement button = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.clickCountrySelection));
            tapAction(button, false);
        }
    }

    public void tapAction(WebElement myDigitalIDButton, boolean clickLeft) {
        Point location = myDigitalIDButton.getLocation();
        Dimension size = myDigitalIDButton.getSize();

        int x, y;
        if (clickLeft) {
            x = location.getX() + 10;
            y = location.getY() + size.getHeight() / 2;
        } else {
            x = location.getX() + size.getWidth() / 2;
            y = location.getY() + size.getHeight() / 2;
        }

// FIX 1: Respect viewportRect on BrowserStack (top offset = 75)
        int viewportTop = 75;   // from capabilities
        y = Math.max(y, viewportTop + 1);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));     // FIX 2
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ((AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid())
                .perform(Collections.singletonList(tap));
    }

    public void clickFormEu() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            boolean found = false;
            int maxAttempts = 5; // number of tries
            int waitSeconds = 5; // per try

            for (int attempt = 1; attempt <= maxAttempts && !found; attempt++) {
                try {
                    // switch to native
                    driver.context("NATIVE_APP");

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
                    WebElement element = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(
                                    eu.europa.eudi.elements.android.IssuerElements.clickFormEu
                            )
                    );

                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    element.click();
                    System.out.println("Clicked FormEU in NATIVE on attempt " + attempt);
                    found = true;

                } catch (Exception e) {
                    System.out.println("⚠FormEU not found in NATIVE on attempt " + attempt);
                    Thread.sleep(1000); // small wait before retry
                }
            }

            if (!found) {
                throw new RuntimeException("FormEU element not found in NATIVE after retries.");
            }
        } else {
//            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickFormEu)).click();
           IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            boolean found = false;
            int maxAttempts = 5;
            int waitSeconds = 5;

            By locator = eu.europa.eudi.elements.ios.IssuerElements.clickFormEu;

            for (int attempt = 1; attempt <= maxAttempts && !found; attempt++) {
                try {

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

                    // 🔥 IMPORTANT: refresh + re-locate every attempt (iOS fix)
                    WebElement element = wait.until(
                            ExpectedConditions.refreshed(
                                    ExpectedConditions.visibilityOfElementLocated(locator)
                            )
                    );

                    wait.until(ExpectedConditions.elementToBeClickable(locator));

                    element.click();

                    System.out.println("Clicked FormEU in IOS on attempt " + attempt);

                    found = true;

                } catch (Exception e) {
                    System.out.println("⚠ FormEU not found in IOS on attempt " + attempt);

                    try {
                        Thread.sleep(1000); // small stabilization wait
                    } catch (InterruptedException ignored) {}
                }
            }

            if (!found) {
                throw new RuntimeException("FormEU element not found in IOS after retries.");
            }
        }
    }

    public void enterGivenName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String givenName = getValueFromYml("Given Name");

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            IssuerElements.clickGivenName
                    ))
                    .click();

            WebElement givenFamily =
                    test.mobileWebDriverFactory().getWait()
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    IssuerElements.clickGivenName
                            ));

            givenFamily.clear();
            givenFamily.sendKeys(givenName);

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            IssuerElements.closeKeyboardForm
                    ))
                    .click();

        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickGivenName)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.givenNameField);
            givenName.clear();
            givenName.sendKeys("Foteini");
        }
    }

    public void enterFamilyName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String familyName = getValueFromYml("Family Name");

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            IssuerElements.clickFamilyName
                    ))
                    .click();

            WebElement givenFamily =
                    test.mobileWebDriverFactory().getWait()
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    IssuerElements.clickFamilyName
                            ));

            givenFamily.clear();
            givenFamily.sendKeys(familyName);

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            IssuerElements.closeKeyboardForm
                    ))
                    .click();

        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickFamilyName)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenFamily = (WebElement) driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.familyNameField);
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");
        }
    }

    private String getValueFromYml(String familyName) {

        FormYml yml = YmlLoader.load("testdata/PID/py_issuer_form.yml", FormYml.class);

        if (!yml.fields.containsKey(familyName)) {
            throw new RuntimeException("Field not found in YAML: " + familyName);
        }

        return yml.fields.get(familyName).value;
    }

    public void chooseBirthDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            FormYml yml = YmlLoader.load("testdata/PID/py_issuer_form.yml", FormYml.class);
            String birthDate = yml.fields.get("Birth Date").value;   // "1990-01-10"

// open date picker
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.clickBirthDate))
                    .click();

// parse yyyy-MM-dd
            String[] p = birthDate.split("-");
            String year = p[0];
            int targetMonth = Integer.parseInt(p[1]);
            String day = String.valueOf(Integer.parseInt(p[2]));

// open year selection
            try {
                driver.findElement(By.id("android:id/date_picker_header_year")).click();
            } catch (Exception e) {
                driver.findElement(By.xpath("//*[contains(@resource-id,'date_picker_header_year')]")).click();
            }

            selectYearScrollUp(driver, year);
            //month

            By nextBtn = By.id("android:id/next");
            By prevBtn = By.id("android:id/prev");

// Target month from birthDate

// System current month (your picker starts here)
            int currentMonth = LocalDate.now().getMonthValue();

// Calculate difference
            int diff = 3 - currentMonth;

// Navigate
            for (int i = 0; i < Math.abs(diff); i++) {
                if (diff > 0) {
                    driver.findElement(nextBtn).click();
                } else {
                    driver.findElement(prevBtn).click();
                }

                try {
                    Thread.sleep(200); // allow UI to update
                } catch (InterruptedException ignored) {
                }
            }
// select day
            driver.findElement(By.xpath("//android.view.View[@text='" + day + "']")).click();

// confirm
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.chooseSet))
                    .click();

        } else {
            // Driver & wait
            IOSDriver driverIos = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            wait.until(ExpectedConditions.elementToBeClickable(
                    eu.europa.eudi.elements.ios.IssuerElements.clickBirthDate)).click();

            String day = "10";

            String dayXpath = String.format("//XCUIElementTypeStaticText[@name='%s']", day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

// 🔹 Step 1: Open year/month picker
            By showYearPicker = AppiumBy.accessibilityId("Show year picker");
            wait.until(ExpectedConditions.elementToBeClickable(showYearPicker)).click();

// 🔹 Step 2: Get all picker wheels
            List<WebElement> wheels = wait.until(driver ->
                    driver.findElements(By.className("XCUIElementTypePickerWheel"))
            );

// Safety check
            if (wheels.size() < 2) {
                throw new RuntimeException("Expected at least 2 picker wheels (month & year)");
            }

// Based on your inspector:
// index 0 → Month
// index 1 → Year
            WebElement monthWheel = wheels.get(0);
            WebElement yearWheel = wheels.get(1);

// 🔹 Step 3: Set values
            String targetYear = "2020";
            String targetMonth = "March";


            monthWheel.sendKeys(targetMonth);
            yearWheel.sendKeys(targetYear);

// 🔹 Step 4: Click Done
            By doneBtn = AppiumBy.accessibilityId("Done");
            wait.until(ExpectedConditions.elementToBeClickable(doneBtn)).click();


        }
    }

    private void selectYearScrollUp(AndroidDriver driver, String year) {
//        driver.findElement(AppiumBy.androidUIAutomator(
//                "new UiScrollable(new UiSelector().scrollable(true))" +
//                        ".scrollIntoView(new UiSelector().text(\"" + year + "\"))"
//        )).click();

        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollBackward()" +
                        ".scrollIntoView(new UiSelector().text(\"" + year + "\"))"
        )).click();

    }

    public void enterDocumentNumber() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.enterDocumentNumber)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.enterDocumentNumber);
            searchBar.clear();
            searchBar.sendKeys("1234");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardBefore03)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.enterDocumentNumber)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement searchBar = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.documentNumberField);
            searchBar.clear();
            searchBar.sendKeys("1234");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.closeKeyboardBefore03)).click();
        }
    }

    public void chooseIssueDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            // Load YAML
            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);

            // Get Issue Date value (example: 2024-03-01)
            String issueDate = yml.fields.get("Issue Date").value;

            // Open date picker
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.android.IssuerElements.clickIssueDate))
                    .click();


// parse yyyy-MM-dd
            String[] p = issueDate.split("-");
            String year = p[0];
            int targetMonth = Integer.parseInt(p[1]);
            String day = String.valueOf(Integer.parseInt(p[2]));

// open year selection
            try {
                driver.findElement(By.id("android:id/date_picker_header_year")).click();
            } catch (Exception e) {
                driver.findElement(By.xpath("//*[contains(@resource-id,'date_picker_header_year')]")).click();
            }

            selectYearScrollUp(driver, year);
            //month

            By nextBtn = By.id("android:id/next");
            By prevBtn = By.id("android:id/prev");

// Target month from birthDate

// System current month (your picker starts here)
            int currentMonth = LocalDate.now().getMonthValue();

// Calculate difference
            int diff = 3 - currentMonth;

// Navigate
            for (int i = 0; i < Math.abs(diff); i++) {
                if (diff > 0) {
                    driver.findElement(nextBtn).click();
                } else {
                    driver.findElement(prevBtn).click();
                }

                try {
                    Thread.sleep(200); // allow UI to update
                } catch (InterruptedException ignored) {}
            }
// select day
            driver.findElement(By.xpath("//android.view.View[@text='" + day + "']")).click();

// confirm
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.chooseSet))
                    .click();

        } else {

            IOSDriver driver1 = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

// Load YAML
            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);
            String issueDate = yml.fields.get("Issue Date").value; // "yyyy-MM-dd"

// Open date picker
            wait.until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.ios.IssuerElements.clickIssueDate))
                    .click();
            String day = "10";

            String dayXpath = String.format("//XCUIElementTypeStaticText[@name='%s']", day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

// Step 1: Open year/month picker
            By showYearPicker = AppiumBy.accessibilityId("Show year picker");
            wait.until(ExpectedConditions.elementToBeClickable(showYearPicker)).click();

// Step 2: Get all picker wheels
            List<WebElement> wheels = wait.until(driver ->
                    driver.findElements(By.className("XCUIElementTypePickerWheel"))
            );

// Safety check
            if (wheels.size() < 2) {
                throw new RuntimeException("Expected at least 2 picker wheels (month & year)");
            }

// Based on your inspector:
// index 0 → Month
// index 1 → Year
            WebElement monthWheel = wheels.get(0);
            WebElement yearWheel = wheels.get(1);

// Step 3: Set values
            String targetYear = "2020";
            String targetMonth = "March";


            monthWheel.sendKeys(targetMonth);
            yearWheel.sendKeys(targetYear);

// Step 4: Click Done
            By doneBtn = AppiumBy.accessibilityId("Done");
            wait.until(ExpectedConditions.elementToBeClickable(doneBtn)).click();
        }
    }

    public void chooseExpiryDate() {

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            // Load YAML
            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);

            // Get Expiry Date value (example: 2030-05-15)
            String expiryDate = yml.fields.get("Expiry Date").value;

            // Open date picker
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.android.IssuerElements.clickExpiryDate))
                    .click();


// parse yyyy-MM-dd
            String[] p = expiryDate.split("-");
            String year = p[0];
            int targetMonth = Integer.parseInt(p[1]);
            String day = String.valueOf(Integer.parseInt(p[2]));

// open year selection
            try {
                driver.findElement(By.id("android:id/date_picker_header_year")).click();
            } catch (Exception e) {
                driver.findElement(By.xpath("//*[contains(@resource-id,'date_picker_header_year')]")).click();
            }

            selectYearScrollUp(driver, year);
            //month

            By nextBtn = By.id("android:id/next");
            By prevBtn = By.id("android:id/prev");

// Target month from birthDate

// System current month (your picker starts here)
            int currentMonth = LocalDate.now().getMonthValue();

// Calculate difference
            int diff = 3 - currentMonth;

// Navigate
            for (int i = 0; i < Math.abs(diff); i++) {
                if (diff > 0) {
                    driver.findElement(nextBtn).click();
                } else {
                    driver.findElement(prevBtn).click();
                }

                try {
                    Thread.sleep(200); // allow UI to update
                } catch (InterruptedException ignored) {}
            }
// select day
            driver.findElement(By.xpath("//android.view.View[@text='" + day + "']")).click();

// confirm
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.chooseSet))
                    .click();

        } else {
            IOSDriver driver1 = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

// Load YAML
            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);
            String expiryDate = yml.fields.get("Expiry Date").value; // e.g., "2030-05-15"

// Open date picker
            wait.until(ExpectedConditions.elementToBeClickable(
                    eu.europa.eudi.elements.ios.IssuerElements.clickExpiryDate)).click();

            String day = "10";

            String dayXpath = String.format("//XCUIElementTypeStaticText[@name='%s']", day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

// Step 1: Open year/month picker
            By showYearPicker = AppiumBy.accessibilityId("Show year picker");
            wait.until(ExpectedConditions.elementToBeClickable(showYearPicker)).click();

// Step 2: Get all picker wheels
            List<WebElement> wheels = wait.until(driver ->
                    driver.findElements(By.className("XCUIElementTypePickerWheel"))
            );

// Safety check
            if (wheels.size() < 2) {
                throw new RuntimeException("Expected at least 2 picker wheels (month & year)");
            }

// Based on your inspector:
// index 0 → Month
// index 1 → Year
            WebElement monthWheel = wheels.get(0);
            WebElement yearWheel = wheels.get(1);

// Step 3: Set values
            String targetYear = "2020";
            String targetMonth = "March";


            monthWheel.sendKeys(targetMonth);
            yearWheel.sendKeys(targetYear);

// Step 4: Click Done
            By doneBtn = AppiumBy.accessibilityId("Done");
            wait.until(ExpectedConditions.elementToBeClickable(doneBtn)).click();

        }
    }

    public void clickSubmit() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickSubmit)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickSubmit)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
    }

    public void clickScreen() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickScreen)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickScreen)).click();
        }
    }

    public void authenticationMethodSelection() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authenticationMethodSelection)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHENTICATION_PAGE.label, pageHeader);
        }
    }

    public void scrollUntilFindDate() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 4; i++) {
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
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                // This replaces your waitAction
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
                // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                Thread.sleep(20);
            }
        } else {
            int i = 1;
            while (i < 1) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                Map<String, Object> params = new HashMap<>();
                params.put("direction", "up");
                driver.executeScript("mobile: swipe", params);
                i++;
            }

        }
    }

    public void scrollUntilFindSubmit() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            for (int i = 0; i < 2; i++) {
                try {
                    WebElement pidElement = driver.findElement(WalletElements.clickConfirm);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll();  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 5) {
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


    public void scrollUntilAuthorize() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 7; i++) {
                try {
                    WebElement pidElement = driver.findElement(IssuerElements.authorize);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll();  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 4) {
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

    public void clickAuthorize() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            By theButtonToClick = By.xpath("//android.widget.Button[@text=\"Authorize\"]"); // <-- IMPORTANT: Use the correct ID or selector for your button
            wait.until(ExpectedConditions.elementToBeClickable(theButtonToClick)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            By theButtonToClick = By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]"); // <-- IMPORTANT: Use the correct ID or selector for your button
            wait.until(ExpectedConditions.elementToBeClickable(theButtonToClick)).click();
//            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.authorize)).click();
        }
    }


    public void formIsDisplayed() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

// switch outside wait
            driver.context("NATIVE_APP");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(250));

            WebElement header = wait.until(d -> {
                try {
                    WebElement el = driver.findElement(
                            By.xpath("//android.widget.TextView[contains(@text,'Issue attributes for your EUDI Wallet demo application.')]")
                    );
                    return el.isDisplayed() ? el : null;
                } catch (Exception e) {
                    return null;
                }
            });


        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.ios.IssuerElements.formIsDisplayed,
                    Literals.Issuer.FORM_IOS.label,
                    driver,
                    80
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.ios.IssuerElements.formIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.FORM_IOS.label, headerText);
        }
    }

    public void issuePID() throws InterruptedException {
        selectCountryOfOrigin();
        clickFormEu();
        scrollUntilFindSubmit();
        clickSubmit();
        formIsDisplayed();
        verifyMandatoryInfoLabelsPresent("py_issuer_form.yml");
        chooseBirthDate();
        enterFamilyName();
        enterGivenName();
        scrollUntilCountryCode();
        enterCountryCode();
        scrollUntilCountry();
        enterCountry();
        scrollUntilFindSubmit();
        clickConfirm();
        authorizeIsDisplayed();
//        verifyMandatoryInfoLabelsPresentInAuthorizePage();
        scrollUntilAuthorize();
        clickAuthorize();
    }

    private void verifyMandatoryInfoLabelsPresentInAuthorizePage(String yamlPath) {
            FormYml yml = YmlLoader.load(yamlPath, FormYml.class);

            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

                // Collect all mandatory labels
                List<String> mandatoryLabels = yml.fields.entrySet().stream()
                        .filter(entry -> entry.getValue().required)
                        .flatMap(entry -> Arrays.stream(entry.getKey().split("\\.")))
                        .distinct()
                        .collect(Collectors.toList());

                Set<String> foundLabels = new HashSet<>();
                int maxScrolls = 5;

                for (int scroll = 0; scroll < maxScrolls && foundLabels.size() < mandatoryLabels.size(); scroll++) {

                    // Build one XPath that searches for all labels at once
                    String xpath = mandatoryLabels.stream()
                            .map(label -> "contains(@text, \"" + label + "\")")
                            .collect(Collectors.joining(" or "));

                    List<WebElement> elements = driver.findElements(By.xpath(
                            "//android.webkit.WebView//*[(@class='android.view.View' or @class='android.widget.TextView') and (" + xpath + ")]"
                    ));

                    for (WebElement el : elements) {
                        String text = el.getText();
                        for (String label : mandatoryLabels) {
                            if (text.contains(label)) {
                                foundLabels.add(label);
                            }
                        }
                    }

                    if (foundLabels.size() < mandatoryLabels.size()) {
                        try {
                            slowScroll();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                mandatoryLabels.stream()
                        .filter(label -> !foundLabels.contains(label))
                        .findFirst()
                        .ifPresent(label -> {
                            throw new AssertionError("Mandatory label not found: " + label);
                        });
        }
    }

    public void fastScroll(AndroidDriver driver) throws InterruptedException {

        String originalContext = driver.getContext();

        try {
            // Switch to NATIVE_APP context once
            if (!"NATIVE_APP".equals(originalContext)) {
                driver.context("NATIVE_APP");
                Thread.sleep(300); // shorter stabilization
            }

            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.75);
            int endY = (int) (size.height * 0.35); // slightly shorter swipe

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 0);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(new Pause(finger, Duration.ofMillis(100))); // short pause
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform swipe
            try {
                driver.perform(Collections.singletonList(swipe));
            } catch (InvalidElementStateException e) {
                // Retry once if swipe fails (common in hybrid apps)
                Thread.sleep(500);
                driver.perform(Collections.singletonList(swipe));
            }

        } finally {
            // Switch back to original context if needed
            if (!"NATIVE_APP".equals(originalContext)) {
                driver.context(originalContext);
            }
        }
    }

    private String escapeXpath(String s) {
        // minimal escape for quotes in your current style
        return s.replace("\"", "\\\"");
    }

    private void verifyMandatoryInfoLabelsPresent(String yamlPath) {

        FormYml yml = YmlLoader.load(yamlPath, FormYml.class);

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            // Collect all mandatory labels
            List<String> mandatoryLabels = yml.fields.entrySet().stream()
                    .filter(entry -> entry.getValue().required)
                    .flatMap(entry -> Arrays.stream(entry.getKey().split("\\.")))
                    .distinct()
                    .collect(Collectors.toList());

            Set<String> foundLabels = new HashSet<>();
            int maxScrolls = 5;

            for (int scroll = 0; scroll < maxScrolls && foundLabels.size() < mandatoryLabels.size(); scroll++) {

                // Build one XPath that searches for all labels at once
                String xpath = mandatoryLabels.stream()
                        .map(label -> "contains(@text, \"" + label + "\")")
                        .collect(Collectors.joining(" or "));

                List<WebElement> elements = driver.findElements(By.xpath(
                        "//android.webkit.WebView//*[(@class='android.view.View' or @class='android.widget.TextView') and (" + xpath + ")]"
                ));

                for (WebElement el : elements) {
                    String text = el.getText();
                    for (String label : mandatoryLabels) {
                        if (text.contains(label)) {
                            foundLabels.add(label);
                        }
                    }
                }

                if (foundLabels.size() < mandatoryLabels.size()) {
                    try {
                        slowScroll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            mandatoryLabels.stream()
                    .filter(label -> !foundLabels.contains(label))
                    .findFirst()
                    .ifPresent(label -> {
                        throw new AssertionError("Mandatory label not found: " + label);
                    });


            test.mobile().wallet().scrollUpForBirthDate();
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            yml.fields.forEach((fieldKey, cfg) -> {

                if (!cfg.required) return;

                String[] labels = fieldKey.split("\\.");

                for (String label : labels) {

                    boolean found = false;

                    for (int i = 0; i < 5; i++) {

                        By labelLocator = By.xpath(
                                "//XCUIElementTypeStaticText[contains(@name,'" + label + "') " +
                                        "or contains(@label,'" + label + "') " +
                                        "or contains(@value,'" + label + "')]"
                        );

                        if (!driver.findElements(labelLocator).isEmpty()) {
                            found = true;
                            break;
                        }

                        try {
                            slowScroll();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (!found) {
                        throw new AssertionError("Mandatory label not found: " + label);
                    }
                }
            });

            test.mobile().wallet().scrollUpForBirthDate();
        }
    }

    public void issuePIDDev() throws InterruptedException {
//        issuerServiceIsDisplayed();
        selectCountryOfOriginDev();
        clickFormEu();
        clickSubmit();
        formIsDisplayedDev();
        enterFamilyNameDev();
        enterGivenNameDev();
        chooseBirthDateDev();
        enterCountryDev();
        scrollUntilCountryCode();
        enterCountryCodeDev();
        scrollUntilFindSubmit();
        clickConfirm();
        authorizeIsDisplayedDev();
        scrollUntilAuthorize();
        clickAuthorize();
    }

    private void chooseBirthDateDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickBirthDate)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.chooseSet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickBirthDateDev)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.chooseSet)).click();
        }
    }

    private void authorizeIsDisplayedDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authorizePageIsDisplayedDev)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED_DEV.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authorizePageIsDisplayedDev)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED_DEV.label, pageHeader);
        }
    }

    public void clickConfirm() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickConfirm)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickConfirm)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        }
    }

    private void enterCountryCodeDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountryCodeDev)).click();
            WebElement countryCode = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickCountryCodeDev));
            countryCode.clear();
            countryCode.sendKeys("GR");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboard)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCountryCodeDev)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement countryCode = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountryCodeDev);
            countryCode.clear();
            countryCode.sendKeys("GR");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.closeKeyboard)).click();
        }
    }

    private void enterCountryDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebElement countryField = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountryDev));
            countryField.click();
            countryField = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickedCountryDev));
            countryField.clear();
            countryField.sendKeys("Greece");
            WebElement placeOfBirth = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickPlaceOfBirth));
            placeOfBirth.click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCountryDev)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement country = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountryDev);
            country.clear();
            country.sendKeys("Greece");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickPlaceOfBirth)).click();
        }
    }

    private void enterGivenNameDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.givenNameFieldDev)).click();
            WebElement givenName = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.givenNameFieldDev));
            givenName.clear();
            givenName.sendKeys("Foteini");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.givenNameFieldDev)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.givenNameFieldDev);
            givenName.clear();
            givenName.sendKeys("Foteini");
        }
    }


    private void enterFamilyNameDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFamilyNameDev)).click();
            WebElement givenFamily = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickFamilyNameDev));
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.familyNameFieldDev)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenFamily = (WebElement) driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.familyNameFieldDev);
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");
        }
    }

    private void formIsDisplayedDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.android.IssuerElements.formIsDisplayedDev,
                    Literals.Issuer.FORM_DEV.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.IssuerElements.formIsDisplayedDev
            ).getText().trim();

            Assert.assertEquals(Literals.Issuer.FORM_DEV.label, headerText);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.ios.IssuerElements.formIsDisplayedDev,
                    Literals.Issuer.FORM_DEV.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.ios.IssuerElements.formIsDisplayedDev
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.FORM_DEV.label, headerText);
        }
    }

    public void scrollUntilPIDIssuer() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 3; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.clickPID);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll();  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 9; i++) {
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
                for (int i = 0; i < 2; i++) {
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

    public void selectCountryOfOriginDev() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayedDev,
                    Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED_DEV.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayedDev
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED_DEV.label, headerText);
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.ios.IssuerElements.selectCountryOfOriginIsDisplayedDev,
                    Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED_DEV.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.ios.IssuerElements.selectCountryOfOriginIsDisplayedDev
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED_DEV.label, headerText);
        }
    }

    public void issueMDL() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
        clickFormEu();
        clickSubmit();
        formIsDisplayed();
//        verifyMandatoryInfoLabelsPresent("testdata/mDL/py_issuer_form.yml");
        chooseBirthDate();
        enterDocumentNumber();
        scrollUntilFindSign();
        codeIsVisible();
        enterCode();
        scrollUntilFindDate();
        clickScreen();
        chooseExpiryDate();
        chooseIssueDate();
        scrollUntilFindName();
        enterFamilyNameOnMdl();
        enterGivenNameOnMdl();
        scrollUntilFindSubmit();
        clickConfirm();
        authorizeIsDisplayed();
        verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/py_issuer_authorization.yml");
        scrollUntilAuthorize();
        clickAuthorize();
    }

    private void validateScreenFields() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        try {
            for (String context : driver.getContextHandles()) {
                if (context.contains("WEBVIEW")) {
                    driver.context(context);
                    break;
                }
            }

            String yamlPath = "src/test/resources/testdata/mDL/py_issuer_form.yml";
            List<String> yamlFields = loadYamlFields(yamlPath);

            int maxScrolls = 8;

            for (int i = 0; i < maxScrolls; i++) {

                Thread.sleep(1500);

                String dom = driver.getPageSource();

                List<String> missingFields = findMissingFields(dom, yamlFields);

                if (missingFields.isEmpty()) {
                    System.out.println("All fields found on screen");
                    return;
                }

                System.out.println("Scrolling... Missing fields: " + missingFields);

                swipeUp(driver);

            }

            throw new RuntimeException("Some fields not found after scrolling.");

        } catch (Exception e) {
            throw new RuntimeException("Validation failed: " + e.getMessage(), e);
        }
    }

    private void swipeUp(AppiumDriver driver) {

        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.75);
        int endY = (int) (size.height * 0.30);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);

        swipe.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX,
                startY));

        swipe.addAction(finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(new Pause(finger, Duration.ofMillis(200)));

        swipe.addAction(finger.createPointerMove(
                Duration.ofMillis(600),
                PointerInput.Origin.viewport(),
                startX,
                endY));

        swipe.addAction(finger.createPointerUp(
                PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    private List<String> findMissingFields(String dom, List<String> yamlFields) {

        List<String> missing = new ArrayList<>();

        for (String field : yamlFields) {

            if (!dom.contains(field)) {
                missing.add(field);
            }
        }

        return missing;
    }

    private List<String> loadYamlFields(String yamlPath) throws Exception {

        Yaml yaml = new Yaml();

        Map<String, Object> data =
                yaml.load(new FileInputStream(yamlPath));

        Map<String, Object> fields =
                (Map<String, Object>) data.get("fields");

        return new ArrayList<>(fields.keySet());
    }
    private void verifyMandatoryInfoLabelsPresentInAuthorizePageForMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            yml.fields.forEach((fieldKey, cfg) -> {
                if (!cfg.required) return;
                String[] labels = fieldKey.split("\\.");
                String lastLabel = labels[labels.length - 1];
                // 1) find labels (scroll)
                for (String label : labels) {
                    try {
                        assertTextVisibleWithScroll(driver, label, 10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 2) if expected value in yml -> verify it exists (scroll still at that area)
                if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                    try {
                        assertTextVisibleWithScroll(driver, cfg.value.trim(), 3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // if no expected value -> at least ensure some value exists near the label
                    // simplest: just ensure there is at least one TextView visible
                    By anyValue = By.xpath("//android.webkit.WebView//android.widget.TextView[@text!='']");
                    if (driver.findElements(anyValue).isEmpty()) {
                        throw new AssertionError("No values visible for label: " + lastLabel);
                    }
                }
            });
        } else {
            //nothing now for iOS
        }

    }

    private void verifyMandatoryInfoLabelsPresentForMdl() {
        FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_form.yml", FormYml.class);

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            // (Optional) make sure we're in a stable context
            // driver.context("NATIVE_APP");

            yml.fields.forEach((fieldKey, cfg) -> {
                if (!cfg.required) return;

                String[] labels = fieldKey.split("\\.");

                for (String label : labels) {

                    boolean found = false;

                    for (int i = 0; i < 5; i++) {

                        By labelLocator = By.xpath(
                                "//android.webkit.WebView//*[(@class='android.view.View' or @class='android.widget.TextView') " +
                                        "and contains(@text, \"" + label + "\")]"
                        );

                        if (!driver.findElements(labelLocator).isEmpty()) {
                            found = true;
                            break;
                        }

                        // scroll down a bit and try again
                        try {
                            slowScroll();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (!found) {
                        throw new AssertionError("Mandatory label not found: " + label);
                    }
                }
            });
            test.mobile().wallet().scrollUpForBirthDate();

        } else {
            //nothing now for iOS
        }
    }

    public void enterGivenNameOnMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickGivenName)).click();
            WebElement givenName = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickGivenName));
            givenName.clear();
            givenName.sendKeys("Foteini");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardForm)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickGivenNameOnMdl)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickGivenNameOnMdl);
            givenName.clear();
            givenName.sendKeys("Foteini");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickGivenNameText)).click();
        }
    }

    public void enterFamilyNameOnMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName)).click();
            WebElement givenFamily = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName));
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardForm)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickFamilyNameOnMdl)).click();
            WebElement givenFamily = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.clickFamilyNameOnMdl));
            givenFamily.clear();
            givenFamily.sendKeys("Theofilatou");
        }
    }

    private void codeIsVisible() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.android.IssuerElements.codeIsVisible,
                    Literals.Issuer.CODEISVISIBLE.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.IssuerElements.codeIsVisible
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.CODEISVISIBLE.label, headerText);
        }
    }

    public void enterCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.enterCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.enterCode);
            searchBar.clear();
            searchBar.sendKeys("1234");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.clickCode)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.enterCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement searchBar = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.enterCode);
            searchBar.clear();
            searchBar.sendKeys("1234");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCode)).click();
        }
    }

    public void scrollUntilFindSign() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 2; i++) {
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
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                // This replaces your waitAction
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
                // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                Thread.sleep(20);
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 1; i++) {
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

    public void enterCountry() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String country = getValueFromYml("Place Of Birth.Country");

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            IssuerElements.clickCountry
                    ))
                    .click();

            WebElement givenFamily =
                    test.mobileWebDriverFactory().getWait()
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    IssuerElements.clickCountry
                            ));

            givenFamily.clear();
            givenFamily.sendKeys(country);

            WebElement placeOfBirth = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickPlaceOfBirth));
            test.mobile().wallet().tapAction(placeOfBirth, false);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCountry)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement country = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountry);
            country.clear();
            country.sendKeys("Greece");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickPlaceOfBirth)).click();
        }
    }

    public void scrollUntilCountryCode() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 2; i++) {
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
            for (int i = 0; i < 1; i++) {
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

    public void scrollUntilCountry() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 3; i++) {
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
            for (int i = 0; i < 1; i++) {
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

    public void authorizeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authorizePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authorizePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
        }
    }

    public void successfullySharedMessage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED_IOS.label, pageHeader);
        }
    }

    public void enterCountryCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String countryCode = getValueFromYml("Nationality.Country Code");

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            IssuerElements.clickCountryCode
                    ))
                    .click();

            WebElement givenFamily =
                    test.mobileWebDriverFactory().getWait()
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    IssuerElements.clickCountryCode
                            ));

            givenFamily.clear();
            givenFamily.sendKeys(countryCode);

            WebElement element = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.closeKeyboard));
            test.mobile().wallet().tapAction(element, false);

        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCountryCode)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement countryCode = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountryCode);
            countryCode.clear();
            countryCode.sendKeys("GR");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.closeKeyboard)).click();
        }
    }

    public void selectCountryOfOrigin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed,
                    Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label, headerText);
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.ios.IssuerElements.selectCountryOfOriginIsDisplayed,
                    Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.ios.IssuerElements.selectCountryOfOriginIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label, headerText);
        }
    }

    public void sleepMethod() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void transactionCodeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement header = WaitsUtils.waitForExactText(
                    IssuerElements.qrCodeIsDisplayed,
                    Literals.Issuer.QR_CODE.label,
                    driver,
                    30
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.android.IssuerElements.qrCodeIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, headerText);
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.authorize)).click();
        }
    }

    public void scrollUntilFindSubmitIssuer() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 5; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.IssuerElements.clickSubmitButton);
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
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 8) {
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

    private void slowScroll() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            String originalContext = driver.getContext();

            try {
                // Switch to NATIVE once if needed
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                    Thread.sleep(500); // shorter wait
                }

                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.75);
                int endY = (int) (size.height * 0.35); // slightly shorter swipe

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

                Sequence swipe = new Sequence(finger, 0);
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(new Pause(finger, Duration.ofMillis(100))); // shorter pause
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                // Perform swipe
                try {
                    driver.perform(Collections.singletonList(swipe));
                } catch (InvalidElementStateException e) {
                    Thread.sleep(800); // retry delay if needed
                    driver.perform(Collections.singletonList(swipe));
                }

            } finally {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context(originalContext);
                }
            }

        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            String originalContext = driver.getContext();

            try {
                // Always scroll in NATIVE
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                }

                Dimension size = driver.manage().window().getSize();

                int startX = size.width / 2;
                int startY = (int) (size.height * 0.75);
                int endY = (int) (size.height * 0.30);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 0);

                // Move to start
                swipe.addAction(finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        startX,
                        startY
                ));

                // Touch down
                swipe.addAction(finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                // Pause (important for real devices / BrowserStack)
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));

                // Swipe
                swipe.addAction(finger.createPointerMove(
                        Duration.ofMillis(350),
                        PointerInput.Origin.viewport(),
                        startX,
                        endY
                ));

                // Release
                swipe.addAction(finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                driver.perform(Collections.singletonList(swipe));

            } finally {
                // Restore context
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context(originalContext);
                }
            }
        }
    }

    public void scrollUntilFindConfirm() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 10; i++) {
                try {
                    WebElement pidElement = driver.findElement(WalletElements.findConfirm);
                    if (pidElement.isDisplayed()) break;
                } catch (Exception e) {
                    slowScroll();  // ← slow scroll instead of UiScrollable
                }
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 5) {
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

    public void scrollUntilFindName() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 4; i++) {
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
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                // This replaces your waitAction
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
                // --- END: REPLACEMENT FOR TouchAction ---// Optional: Add a short pause between swipes
                Thread.sleep(20);
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 5; i++) {
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

    public void kotlinIssuerService() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);

            String url = "https://issuer-backend.eudiw.dev/issuer/credentialsOffer/generate";
            String env = test.envDataConfig().getExecutionEnvironment();

            if ("browserstack".equalsIgnoreCase(env)) {
                // Safe for BrowserStack
                Map<String, Object> deepLinkArgs = new HashMap<>();
                deepLinkArgs.put("url", "https://issuer-backend.eudiw.dev/issuer/credentialsOffer/generate");
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
            String url = "https://issuer-backend.eudiw.dev/issuer/credentialsOffer/generate";
            driver.get(url);
            Map<String, Object> args = new HashMap<>();
            args.put("bundleId", "com.apple.mobilesafari");
            driver.executeScript("mobile: launchApp", args);
        }
    }

    public void requestCredentialsKotlinIssuerPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.requestCredentialsKotlinIssuerPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE_KOTLIN.label, pageHeader);
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.requestCredentialsKotlinIssuerPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.CREDENTIAL_PAGE_KOTLIN.label, pageHeader);
        }
    }

    public void scrollUntilGenerate() throws InterruptedException {

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            for (int i = 0; i < 5; i++) {
                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.IssuerElements.clickGenerateButton);
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
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 5; i++) {
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

    public void clickWalletLink() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.walletLink)).click();
            driver.context("NATIVE_APP");

        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.walletLink)).click();
        }
    }

    public void fillLoginForm() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickUsername)).click();
            WebElement username = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickUsername));
            username.clear();
            username.sendKeys("tneal");
            test.mobileWebDriverFactory().androidDriver.hideKeyboard();

            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickPassword)).click();
            WebElement password = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickPassword));
            password.clear();
            password.sendKeys("password");

            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickSignIn)).click();

        } else {
            test.mobileWebDriverFactory().iosDriver.rotate(ScreenOrientation.PORTRAIT);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickUsername)).click();
            WebElement username = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.clickUsername));
            username.clear();
            username.sendKeys("tneal");

            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickPassword)).click();
            WebElement password = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.clickPassword));
            password.clear();
            password.sendKeys("password");

            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickSignIn)).click();
        }
    }

    public void clickGenerate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.clickGenerateButton)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.clickGenerateButton)).click();
        }
    }

    public void selectPIDKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.pidMsoMdoc)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.pidMsoMdoc)).click();
        }
    }

    public void ckeckFieldsOnWallet() {
        FormYml yml = YmlLoader.load("testdata/PID/py_issuer_authorization.yml", FormYml.class);
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        yml.fields.forEach((fieldKey, cfg) -> {
            if (!cfg.required) return;

            String[] labels = fieldKey.split("\\.");
            String lastLabel = labels[labels.length - 1];

            // 1) find labels (scroll)
            for (String label : labels) {
                try {
                    assertTextVisibleWithScroll(driver, label, 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // 2) if expected value in yml -> verify it exists (scroll still at that area)
            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                try {
                    assertTextVisibleWithScroll(driver, cfg.value.trim(), 3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // if no expected value -> at least ensure some value exists near the label
                // simplest: just ensure there is at least one TextView visible
                By anyValue = By.xpath("//android.webkit.WebView//android.widget.TextView[@text!='']");
                if (driver.findElements(anyValue).isEmpty()) {
                    throw new AssertionError("No values visible for label: " + lastLabel);
                }
            }
        });
    }

    private String readValueBelowLabel(AndroidDriver driver, String label) {
        By valueLocator = By.xpath(
                "//android.view.View[@text='" + label + "']/following::android.widget.TextView[1]"
        );
        return driver.findElement(valueLocator).getText();
    }

    public void assertTextVisibleWithScroll(AndroidDriver driver, String text, int maxScrolls) throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            By locator = By.xpath("//*[@text=\"" + text.replace("\"", "\\\"") + "\"]");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200)); // very short implicit wait
            int scrollCount = 0;

            while (scrollCount < maxScrolls) {
                if (!driver.findElements(locator).isEmpty()) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // reset to normal
                    return;
                }

                fastSwipe(driver); // perform faster swipe
                scrollCount++;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // reset to normal
            throw new AssertionError("Text not found: " + text);
        }
    }

    private void fastSwipe(AndroidDriver driver) {
        try {
            String originalContext = driver.getContext();
            if (!"NATIVE_APP".equals(originalContext)) {
                driver.context("NATIVE_APP");
            }

            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.7);
            int endY = (int) (size.height * 0.3);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 0);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY)); // faster swipe
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));

            if (!"NATIVE_APP".equals(originalContext)) {
                driver.context(originalContext);
            }
        } catch (Exception e) {
            throw new RuntimeException("Swipe failed", e);
        }
    }


    public void ckeckFieldsOnWalletFromPyIssuer() {
        FormYml yml = YmlLoader.load("testdata/PID/py_data_on_wallet.yml", FormYml.class);
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        yml.fields.forEach((fieldKey, cfg) -> {
            if (!cfg.required) return;

            String[] labels = fieldKey.split("\\.");
            String lastLabel = labels[labels.length - 1];

            // 1) find labels (scroll)
            for (String label : labels) {
                try {
                    assertTextVisibleWithScroll(driver, label, 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // 2) if expected value in yml -> verify it exists (scroll still at that area)
            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                try {
                    assertTextVisibleWithScroll(driver, cfg.value.trim(), 3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // if no expected value -> at least ensure some value exists near the label
                // simplest: just ensure there is at least one TextView visible
                By anyValue = By.xpath("//android.webkit.WebView//android.widget.TextView[@text!='']");
                if (driver.findElements(anyValue).isEmpty()) {
                    throw new AssertionError("No values visible for label: " + lastLabel);
                }
            }
        });
    }

    public void ckeckFieldsOnWalletFromKotlinIssuer() {
        FormYml yml = YmlLoader.load("testdata/PID/kotlin_data_on_wallet.yml", FormYml.class);
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

        yml.fields.forEach((fieldKey, cfg) -> {
            if (!cfg.required) return;

            String[] labels = fieldKey.split("\\.");
            String lastLabel = labels[labels.length - 1];

            // 1) find labels (scroll)
            for (String label : labels) {
                try {
                    assertTextVisibleWithScroll(driver, label, 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // 2) if expected value in yml -> verify it exists (scroll still at that area)
            if (cfg.value != null && !cfg.value.trim().isEmpty()) {
                try {
                    assertTextVisibleWithScroll(driver, cfg.value.trim(), 3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // if no expected value -> at least ensure some value exists near the label
                // simplest: just ensure there is at least one TextView visible
                By anyValue = By.xpath("//android.webkit.WebView//android.widget.TextView[@text!='']");
                if (driver.findElements(anyValue).isEmpty()) {
                    throw new AssertionError("No values visible for label: " + lastLabel);
                }
            }
        });
    }

    public void selectMDLKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.selectMDLKotlinCredential)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.selectMDLKotlin)).click();

        }
    }

    public void issueCredentialsPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            // 1. Get driver
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

// 2. Switch to WEBVIEW
            for (String context : driver.getContextHandles()) {
                if (context.contains("WEBVIEW_chrome")) {
                    driver.context(context);
                    break;
                }
            }

// 3. Locate element in DOM
            By locator = By.xpath("//h4[contains(text(),'Scan the generated QR Code')]");

// 4. Wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

// 5. Assert
            Assert.assertTrue(header.isDisplayed());

//            driver.context("NATIVE_APP");
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.issueCredentialPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.ISSUANCE_CREDENTIALS.label, pageHeader);
        }
    }

    public void signInUsser() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

// switch outside wait
            driver.context("NATIVE_APP");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));

            WebElement header = wait.until(d -> {
                try {
                    WebElement el = driver.findElement(eu.europa.eudi.elements.android.IssuerElements.signPageIsDisplayed);
                    return el.isDisplayed() ? el : null;
                } catch (Exception e) {
                    return null;
                }
            });
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsUtils.waitForExactText(
                    eu.europa.eudi.elements.ios.IssuerElements.signPageIsDisplayed,
                    Literals.Issuer.SIGN_IN_USER_PAGE.label,
                    driver,
                    80
            );
            String headerText = driver.findElement(
                    eu.europa.eudi.elements.ios.IssuerElements.signPageIsDisplayed
            ).getText().trim();
            Assert.assertEquals(Literals.Issuer.SIGN_IN_USER_PAGE.label, headerText);
        }



        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.signPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.SIGN_IN_USER_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.signPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.SIGN_IN_USER_PAGE.label, pageHeader);
        }
    }

    public void selectMdlPythonIssuer() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.selectMDLPythonCredential)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.selectMDLKotlin)).click();

        }
    }

    public void scrollUntilMdlIssuer() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                try {
                    WebElement pidElement = driver.findElement(eu.europa.eudi.elements.android.WalletElements.mdlIsDisplayed);
                } catch (Exception e) {
                    slowScroll(); // ← slow scroll instead of UiScrollable
                }


            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));


        } else {
            envDataConfig = new EnvDataConfig();
            String env = envDataConfig.getExecutionEnvironment();
            if (env.equalsIgnoreCase("browserstack")) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 9; i++) {
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
                for (int i = 0; i < 2; i++) {
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

    public void requestCredentialsPageIsDisplayedOnWeb() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.requestCredentialOnIssuer)).getText();
        Assert.assertEquals(Literals.Verifier.SELECT_CREDENTIALS.label, pageHeader);
    }

    public void scrollUntilMdlIssuerOnWeb() {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement mdlCheckbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.name("eu.europa.ec.eudi.mdl_mdoc")
                )
        );

// Scroll to element
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                mdlCheckbox
        );

// Wait until clickable
        wait.until(ExpectedConditions.elementToBeClickable(mdlCheckbox));

// Click it
        mdlCheckbox.click();
    }

    public void selectMdlPythonIssuerOnWeb() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickMdlOnWeb)).click();
    }

    public WebElement scrollUntilFindSubmitIssuerOnWeb() {
        WebDriver driver = test.webWebDriverFactory().getDriverWeb();
        WebDriverWait wait = test.webWebDriverFactory().getWait();

        By nextCandidates = By.id("btncheck");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Use a custom ExpectedCondition with a lambda expression
        return wait.until(d -> {
            try {
                // First, try to find the element without scrolling
                WebElement element = driver.findElement(nextCandidates);
                if (element.isDisplayed()) {
                    // If found and visible, scroll it into the center of the view for interaction
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                    return element;
                }
            } catch (Exception e) {
                // Element not found or not visible, so we scroll down and try again
                js.executeScript("window.scrollBy(0, 300);"); // Scroll down by 300 pixels
            }
            // Return null to tell the wait to continue polling
            return null;
        });
    }

    public void clickSubmitButtonOnWeb() {
        test.webWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.VerifierElements.clickSubmitButtonOnWeb)).click();
    }

    public void qrIsDisplayedOnIssuer() {
        String pageHeader = test.webWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.VerifierElements.requestCredentialOnIssuerForQR)).getText();
        Assert.assertEquals(Literals.Issuer.ISSUER_SERVICE_IS_DISPLAYED.label, pageHeader);
    }
}
