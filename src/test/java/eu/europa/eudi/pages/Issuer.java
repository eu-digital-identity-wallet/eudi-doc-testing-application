package eu.europa.eudi.pages;

import com.google.common.collect.ImmutableMap;
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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.HasContext;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class Issuer {
    private String issuerType;
    private String credential;
    private String issuanceMethod;
    TestSetup test;
    EnvDataConfig envDataConfig;

    public Issuer(TestSetup test) {
        this.test = test;
    }

    public void issuerService() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));
            test.envDataConfig();
            String url = test.envDataConfig().getPythonUrl();
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
            String url = test.envDataConfig().getPythonUrl();
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

            test.envDataConfig();
            String url = test.envDataConfig().getPythonUrl();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            try {
                try {
                    driver.terminateApp("eu.europa.ec.euidi");
                } catch (Exception e) {
                }

                driver.activateApp("com.apple.mobilesafari");

                wait.until(d -> d.getWindowHandles().size() > 0);

                driver.get(url);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body")));

                driver.context("NATIVE_APP");

                if (!driver.getContext().equals("NATIVE_APP")) {
                    throw new RuntimeException("Failed to switch to NATIVE_APP context. Current: " + driver.getContext());
                }

            } catch (Exception e) {
                throw new RuntimeException("Failed to launch Safari or navigate to URL: " + e.getMessage(), e);
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

    public void qrCodeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait waitNativeAppTransition = new WebDriverWait(driver, Duration.ofSeconds(2000));
            waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));

            boolean found = false;

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

            if (!found) {
                for (String context : driver.getContextHandles()) {
                    System.out.println("Context: " + context);
                    if (context.contains("WEBVIEW")) {
                        driver.context(context);
                        break;
                    }
                }

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

                WebElement webQr = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//img | //canvas | //*[contains(@class,'qr')]")
                ));

                Assert.assertTrue(webQr.isDisplayed());

                System.out.println("QR found in WEBVIEW");

                driver.context("NATIVE_APP");
            }

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.qrCodeIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.QR_CODE.label, pageHeader);
        }
    }

    public void qrCodeIsDisplayedKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            int maxAttempts = 5;
            int attempt = 0;
            boolean found = false;

            while (attempt < maxAttempts && !found) {
                attempt++;
                System.out.println("Attempt " + attempt + " to find QR. Refreshing...");

                // ADDED: Refresh functionality
                pullToRefresh(driver);

                // --- YOUR ORIGINAL CODE STARTS HERE ---
                new WebDriverWait(driver, Duration.ofSeconds(2000))
                        .until(d -> driver.getContextHandles().contains("NATIVE_APP"));

                try {
                    driver.context("NATIVE_APP");
                    WebElement nativeQr = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    eu.europa.eudi.elements.android.IssuerElements.qrCodeIsDisplayedKotlin
                            ));
                    Assert.assertTrue(nativeQr.isDisplayed());
                    found = true;
                    System.out.println("Kotlin QR found in NATIVE");
                } catch (Exception e) {
                    System.out.println("Kotlin QR not found in NATIVE");
                }

                if (!found) {
                    for (String context : driver.getContextHandles()) {
                        if (context.contains("WEBVIEW")) {
                            driver.context(context);
                            break;
                        }
                    }
                    try {
                        WebElement webQr = new WebDriverWait(driver, Duration.ofSeconds(25))
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                        By.xpath("//img | //canvas | //*[contains(@class,'qr')]")
                                ));
                        Assert.assertTrue(webQr.isDisplayed());
                        System.out.println("Kotlin QR found in WEBVIEW");
                        found = true; // Added this so the loop knows to stop
                    } catch (Exception e) {
                        System.out.println("Kotlin QR not found in WEBVIEW");
                    }
                    driver.context("NATIVE_APP");
                }
                // --- YOUR ORIGINAL CODE ENDS HERE ---
            }

            if (!found) {
                throw new AssertionError("QR Code not found after " + maxAttempts + " refreshes.");
            }
        } else {
            WebElement el = test.mobileWebDriverFactory().getWait().until(
                    ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.qrCodeIsDisplayedKotlin)
            );
            Assert.assertTrue(el.isDisplayed());
        }
    }

    public void clickUseEudiw() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            String deepLink = "haip-vci://credential_offer?credential_offer=%7B%22credential_issuer%22:%20%22https://ec.dev.issuer.eudiw.dev%22%2C%20%22credential_configuration_ids%22:%20%5B%22eu.europa.ec.eudi.mdl_mdoc%22%5D%2C%20%22grants%22:%20%7B%22authorization_code%22:%20%7B%22issuer_state%22:%20%22ced958d4-c8c6-4763-9e7d-dd8c8b27b256%22%7D%7D%7D";

            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

                driver.executeScript("mobile: deepLink", ImmutableMap.of(
                        "url", deepLink,
                        "package", test.envDataConfig().getAppiumAndroidAppPackage()
                ));

                System.out.println("Deep link executed on Android");

            }
        }else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.context("NATIVE_APP");
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

        int viewportTop = 75;
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

    public void clickFormEu() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

// Locators for YOUR specific elements
            By[] locators = {
                    AppiumBy.androidUIAutomator("resourceId(\"FC\")"),
                    AppiumBy.androidUIAutomator("className(\"android.widget.RadioButton\").resourceId(\"FC\")"),
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"FormEU\")"),
                    AppiumBy.androidUIAutomator("className(\"android.widget.TextView\").text(\"FormEU\")")
            };

            int maxAttempts = 5; // Max number of refreshes to try
            int attempt = 0;
            boolean elementClicked = false;

            while (attempt < maxAttempts && !elementClicked) {
                attempt++;
                System.out.println("Attempt " + attempt + " to find FormEU. Refreshing page...");

                // 1. Perform the refresh gesture
                pullToRefresh(driver);

                // 2. Pause to allow the app to process the refresh and reload data


                try {
                    // 3. Ensure we are in NATIVE_APP context
                    if (!"NATIVE_APP".equals(driver.getContext())) {
                        driver.context("NATIVE_APP");
                    }

                    // 4. Try all locators within a reasonable window for THIS attempt
                    // We use a shorter timeout (e.g., 15s) so we can refresh again if it's not there
                    WebElement element = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .pollingEvery(Duration.ofMillis(500))
                            .ignoring(NoSuchElementException.class)
                            .ignoring(StaleElementReferenceException.class)
                            .until(d -> {
                                for (By locator : locators) {
                                    try {
                                        WebElement e = d.findElement(locator);
                                        if (e.isDisplayed() && e.isEnabled()) {
                                            System.out.println("SUCCESS: Found and ready to click using: " + locator);
                                            return e;
                                        }
                                    } catch (Exception ignored) {
                                        // Try the next locator in the list
                                    }
                                }
                                return null; // None of the locators found in this poll
                            });

                    if (element != null) {
                        element.click();
                        elementClicked = true;
                        System.out.println("Successfully clicked the element on attempt " + attempt);
                    }

                } catch (Exception e) {
                    System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
                }
            }

// Final check: If the loop finished and we never clicked the element, throw the error
            if (!elementClicked) {
                throw new AssertionError("Click FormEu not found after " + maxAttempts + " refreshes and attempts.");
            }
        } else {
           IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();

            boolean found = false;
            int maxAttempts = 5;
            int waitSeconds = 90;

            By locator = eu.europa.eudi.elements.ios.IssuerElements.clickFormEu;

            for (int attempt = 1; attempt <= maxAttempts && !found; attempt++) {
                try {
                    WebDriverWait waitNativeAppTransition = new WebDriverWait(driver, Duration.ofSeconds(3000));
                    waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));
                    driver.context("NATIVE_APP");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

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
                    System.out.println("FormEU not found in IOS on attempt " + attempt);
                }
            }

            if (!found) {
                throw new RuntimeException("FormEU element not found in IOS after retries.");
            }
        }
    }

    public void enterGivenName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String givenName = getValueFromYml("testdata/PID/py_issuer_form.yml", "Given Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickGivenName)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.clickGivenName);
            searchBar.clear();
            searchBar.sendKeys(givenName);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.closeKeyboardForm)).click();
        } else {
            String givenNameText = getValueFromYml("testdata/PID/py_issuer_form.yml", "Given Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickGivenName)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.givenNameField);
            givenName.clear();
            givenName.sendKeys(givenNameText);
        }
    }

    public void enterFamilyName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String familyName = getValueFromYml("testdata/PID/py_issuer_form.yml", "Family Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.clickFamilyName);
            searchBar.clear();
            searchBar.sendKeys(familyName);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.closeKeyboardForm)).click();
        } else {
            String familyName = getValueFromYml("testdata/PID/py_issuer_form.yml", "Family Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickFamilyName)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenFamily = (WebElement) driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.familyNameField);
            givenFamily.clear();
            givenFamily.sendKeys(familyName);
        }
    }

    private String getValueFromYml(String ymlPath, String fieldName) {
        // Load the YAML file based on the path passed to the method
        FormYml yml = YmlLoader.load(ymlPath, FormYml.class);

        if (!yml.fields.containsKey(fieldName)) {
            throw new RuntimeException("Field '" + fieldName + "' not found in YAML file: " + ymlPath);
        }

        return yml.fields.get(fieldName).value;
    }

    public void chooseBirthDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            FormYml yml = YmlLoader.load("testdata/PID/py_issuer_form.yml", FormYml.class);
            String birthDate = yml.fields.get("Birth Date").value;

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.clickBirthDate))
                    .click();

            String[] p = birthDate.split("-");
            String year = p[0];
            int targetMonth = Integer.parseInt(p[1]);
            String day = String.valueOf(Integer.parseInt(p[2]));

            try {
                driver.findElement(By.id("android:id/date_picker_header_year")).click();
            } catch (Exception e) {
                driver.findElement(By.xpath("//*[contains(@resource-id,'date_picker_header_year')]")).click();
            }

            selectYearScrollUp(driver, year);

            By nextBtn = By.id("android:id/next");
            By prevBtn = By.id("android:id/prev");

            int currentMonth = LocalDate.now().getMonthValue();

            int diff = 3 - currentMonth;

            WebDriverWait calendarWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < Math.abs(diff); i++) {
                if (diff > 0) {
                    driver.findElement(nextBtn).click();
                    calendarWait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                } else {
                    driver.findElement(prevBtn).click();
                    calendarWait.until(ExpectedConditions.elementToBeClickable(prevBtn));
                }
            }
            driver.findElement(By.xpath("//android.view.View[@text='" + day + "']")).click();

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.chooseSet))
                    .click();

        } else {
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            wait.until(ExpectedConditions.elementToBeClickable(
                    eu.europa.eudi.elements.ios.IssuerElements.clickBirthDate)).click();

            String day = "10";

            String dayXpath = String.format("//XCUIElementTypeStaticText[@name='%s']", day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

            By showYearPicker = AppiumBy.accessibilityId("Show year picker");
            wait.until(ExpectedConditions.elementToBeClickable(showYearPicker)).click();

            List<WebElement> wheels = wait.until(driver ->
                    driver.findElements(By.className("XCUIElementTypePickerWheel"))
            );

            if (wheels.size() < 2) {
                throw new RuntimeException("Expected at least 2 picker wheels (month & year)");
            }

            WebElement monthWheel = wheels.get(0);
            WebElement yearWheel = wheels.get(1);

            String targetYear = "2020";
            String targetMonth = "March";


            monthWheel.sendKeys(targetMonth);
            yearWheel.sendKeys(targetYear);

            By doneBtn = AppiumBy.accessibilityId("Done");
            wait.until(ExpectedConditions.elementToBeClickable(doneBtn)).click();


        }
    }

    private void selectYearScrollUp(AndroidDriver driver, String year) {
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

            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);

            String issueDate = yml.fields.get("Issue Date").value;

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.android.IssuerElements.clickIssueDate))
                    .click();

            String[] p = issueDate.split("-");
            String year = p[0];
            String day = String.valueOf(Integer.parseInt(p[2]));

            try {
                driver.findElement(By.id("android:id/date_picker_header_year")).click();
            } catch (Exception e) {
                driver.findElement(By.xpath("//*[contains(@resource-id,'date_picker_header_year')]")).click();
            }

            selectYearScrollUp(driver, year);

            By nextBtn = By.id("android:id/next");
            By prevBtn = By.id("android:id/prev");

            int currentMonth = LocalDate.now().getMonthValue();

            int diff = 3 - currentMonth;

            WebDriverWait calendarWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < Math.abs(diff); i++) {
                if (diff > 0) {
                    driver.findElement(nextBtn).click();
                    calendarWait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                } else {
                    driver.findElement(prevBtn).click();
                    calendarWait.until(ExpectedConditions.elementToBeClickable(prevBtn));
                }
            }
            driver.findElement(By.xpath("//android.view.View[@text='" + day + "']")).click();

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.chooseSet))
                    .click();

        } else {
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);
            wait.until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.ios.IssuerElements.clickIssueDate))
                    .click();
            String day = "10";

            String dayXpath = String.format("//XCUIElementTypeStaticText[@name='%s']", day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

            By showYearPicker = AppiumBy.accessibilityId("Show year picker");
            wait.until(ExpectedConditions.elementToBeClickable(showYearPicker)).click();

            List<WebElement> wheels = wait.until(driver ->
                    driver.findElements(By.className("XCUIElementTypePickerWheel"))
            );

            if (wheels.size() < 2) {
                throw new RuntimeException("Expected at least 2 picker wheels (month & year)");
            }

            WebElement monthWheel = wheels.get(0);
            WebElement yearWheel = wheels.get(1);

            String targetYear = "2020";
            String targetMonth = "March";

            monthWheel.sendKeys(targetMonth);
            yearWheel.sendKeys(targetYear);

            By doneBtn = AppiumBy.accessibilityId("Done");
            wait.until(ExpectedConditions.elementToBeClickable(doneBtn)).click();
        }
    }

    public void chooseExpiryDate() {

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            FormYml yml = YmlLoader.load("testdata/mDL/py_issuer_authorization.yml", FormYml.class);
            String expiryDate = yml.fields.get("Expiry Date").value;
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.android.IssuerElements.clickExpiryDate))
                    .click();
            String[] p = expiryDate.split("-");
            String year = p[0];
            String day = String.valueOf(Integer.parseInt(p[2]));
            try {
                driver.findElement(By.id("android:id/date_picker_header_year")).click();
            } catch (Exception e) {
                driver.findElement(By.xpath("//*[contains(@resource-id,'date_picker_header_year')]")).click();
            }
            selectYearScrollUp(driver, year);

            By nextBtn = By.id("android:id/next");
            By prevBtn = By.id("android:id/prev");
            int currentMonth = LocalDate.now().getMonthValue();
            int diff = 3 - currentMonth;
            WebDriverWait calendarWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            for (int i = 0; i < Math.abs(diff); i++) {
                if (diff > 0) {
                    driver.findElement(nextBtn).click();
                    calendarWait.until(ExpectedConditions.elementToBeClickable(nextBtn));
                } else {
                    driver.findElement(prevBtn).click();
                    calendarWait.until(ExpectedConditions.elementToBeClickable(prevBtn));
                }
            }
            driver.findElement(By.xpath("//android.view.View[@text='" + day + "']")).click();
            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(IssuerElements.chooseSet))
                    .click();
        } else {
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();
            wait.until(ExpectedConditions.elementToBeClickable(
                    eu.europa.eudi.elements.ios.IssuerElements.clickExpiryDate)).click();
            String day = "10";
            String dayXpath = String.format("//XCUIElementTypeStaticText[@name='%s']", day);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dayXpath))).click();

            By showYearPicker = AppiumBy.accessibilityId("Show year picker");
            wait.until(ExpectedConditions.elementToBeClickable(showYearPicker)).click();

            List<WebElement> wheels = wait.until(driver ->
                    driver.findElements(By.className("XCUIElementTypePickerWheel"))
            );

            if (wheels.size() < 2) {
                throw new RuntimeException("Expected at least 2 picker wheels (month & year)");
            }

            WebElement monthWheel = wheels.get(0);
            WebElement yearWheel = wheels.get(1);

            String targetYear = "2020";
            String targetMonth = "March";


            monthWheel.sendKeys(targetMonth);
            yearWheel.sendKeys(targetYear);

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

    public void scrollUntilFindDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 3; i++) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.6);
                int endY = (int) (size.height * 0.5);
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);

                swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
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
                    slowScroll();
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
                    slowScroll();
                }
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 6) {
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

    public void clickAuthorize() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            By theButtonToClick = By.xpath("//android.widget.Button[@text=\"Authorize\"]");
            wait.until(ExpectedConditions.elementToBeClickable(theButtonToClick)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            By theButtonToClick = By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]");
            wait.until(ExpectedConditions.elementToBeClickable(theButtonToClick)).click();
        }
    }

    public void formIsDisplayed() {
        // 1. Check if we are running on Android
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            // Locators
            By[] locators = {
                    AppiumBy.androidUIAutomator("resourceId(\"content\")"),
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"Mandatory Information\")")
            };

            int maxAttempts = 5; // Maximum number of times to refresh
            int attempt = 0;
            WebElement element = null;

            while (attempt < maxAttempts) {
                attempt++;
                System.out.println("Attempt " + attempt + " to find element. Refreshing page...");

                // 1. Perform the refresh
                pullToRefresh(driver);

                // 2. Small pause to let the refresh action register

                try {
                    // 3. Ensure we are in NATIVE_APP
                    if (!"NATIVE_APP".equals(driver.getContext())) {
                        driver.context("NATIVE_APP");
                    }

                    // 4. Short wait to see if the element appeared after this specific refresh
                    // We use a shorter timeout here (e.g., 15 seconds) because we want to
                    // refresh again if it doesn't appear quickly.
                    element = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .pollingEvery(Duration.ofMillis(500))
                            .ignoring(NoSuchElementException.class)
                            .ignoring(StaleElementReferenceException.class)
                            .until(d -> {
                                for (By locator : locators) {
                                    try {
                                        WebElement e = d.findElement(locator);
                                        if (e.isDisplayed()) {
                                            System.out.println("SUCCESS: Found using " + locator);
                                            return e;
                                        }
                                    } catch (Exception ignored) {}
                                }
                                return null;
                            });

                    if (element != null) {
                        break; // Exit the while loop, we found it!
                    }

                } catch (Exception e) {
                    System.out.println("Attempt " + attempt + " failed to find element: " + e.getMessage());
                }
            }

            // Final Action
            if (element != null) {
                element.click();
            } else {
                throw new AssertionError("Element was not displayed even after " + maxAttempts + " refreshes.");
        }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            By locator = eu.europa.eudi.elements.ios.IssuerElements.formIsDisplayed;
            boolean found = false;
            int maxAttempts = 8;
            int waitSeconds = 90;

            for (int attempt = 1; attempt <= maxAttempts && !found; attempt++) {
                try {
                    WebDriverWait waitNativeAppTransition = new WebDriverWait(driver, Duration.ofSeconds(3000));
                    waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));
                    driver.context("NATIVE_APP");
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

                    WebElement element = wait.until(
                            ExpectedConditions.refreshed(
                                    ExpectedConditions.visibilityOfElementLocated(locator)
                            )
                    );

                    String text = element.getText().trim();

                    if (text.contains(Literals.Issuer.FORM_IOS.label)) {
                        System.out.println("Element visible on attempt " + attempt);
                        found = true;
                    } else {
                        throw new RuntimeException("Text mismatch: " + text);
                    }

                } catch (Exception e) {
                    System.out.println("Attempt " + attempt + " failed - retrying...");

                    if (attempt == maxAttempts) {
                        throw new RuntimeException("Form iOS element not found after retries", e);
                    }
                }
            }

            Assert.assertTrue("Form not displayed correctly", found);
        }
    }

    public void pullToRefresh(AndroidDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int centerX = size.width / 2;

        // START: Lower the start point to avoid the system status bar
        int top = 400;    // Changed from 200 to 400

        // END: Stop the swipe before the bottom of the screen
        int bottom = 700; // Changed from 800 to 700 (shorter, more controlled pull)

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, top));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        // Increase duration slightly to make it look like a human "pull" rather than a "flick"
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), centerX, bottom));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
        System.out.println("Performed a safe Pull-to-Refresh gesture.");
    }

    public void issuePID() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
        clickFormEu();
        scrollUntilFindSubmit();
        clickSubmit();
        formIsDisplayed();
        chooseBirthDate();
        enterFamilyName();
        enterGivenName();
        scrollUntilCountryCodePid();
        enterCountryCode();
        scrollUntilCountry();
        enterCountry();
        scrollUntilFindSubmit();
        clickConfirm();
        authorizeIsDisplayed();
        verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_issuer_authorization.yml");
        scrollUntilAuthorize();
        clickAuthorize();
    }

    private void verifyMandatoryInfoLabelsPresentInAuthorizePage(String yamlPath) {
            FormYml yml = YmlLoader.load(yamlPath, FormYml.class);

            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

                List<String> mandatoryLabels = yml.fields.entrySet().stream()
                        .filter(entry -> entry.getValue().required)
                        .flatMap(entry -> Arrays.stream(entry.getKey().split("\\.")))
                        .distinct()
                        .collect(Collectors.toList());

                Set<String> foundLabels = new HashSet<>();
                int maxScrolls = 5;

                for (int scroll = 0; scroll < maxScrolls && foundLabels.size() < mandatoryLabels.size(); scroll++) {

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

    public void clickConfirm() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickConfirm)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickConfirm)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        }
    }

    public void issueMDL() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
        clickFormEu();
        clickSubmit();
        formIsDisplayed();
        chooseBirthDate();
        enterDocumentNumber();
        scrollUntilFindSign();
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

    public void enterGivenNameOnMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
            String givenNameText = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Given Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickGivenName)).click();
            WebElement givenName = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickGivenName));
            givenName.clear();
            givenName.sendKeys(givenNameText);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardForm)).click();
        } else {
            String givenNameText = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Given Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickGivenNameOnMdl)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement givenName = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickGivenNameOnMdl);
            givenName.clear();
            givenName.sendKeys(givenNameText);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickGivenNameText)).click();
        }
    }

    public void enterFamilyNameOnMdl() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String familyName = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Family Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName)).click();
            WebElement givenFamily = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName));
            givenFamily.clear();
            givenFamily.sendKeys(familyName);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardForm)).click();
        } else {
            String familyName = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Family Name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickFamilyNameOnMdl)).click();
            WebElement givenFamily = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.clickFamilyNameOnMdl));
            givenFamily.clear();
            givenFamily.sendKeys(familyName);
        }
    }

    public void enterCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String code = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Licence number");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.enterCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.enterCode);
            searchBar.clear();
            searchBar.sendKeys(code);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.clickCode)).click();
        } else {
            String code = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Licence number");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.enterCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement searchBar = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.enterCode);
            searchBar.clear();
            searchBar.sendKeys(code);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCode)).click();
        }
    }

    public void scrollUntilFindSign() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 3; i++) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.6);
                int endY = (int) (size.height * 0.5);
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);

                swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver.perform(Collections.singletonList(swipe));
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 1; i++) {
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

    public void enterCountry() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String country = getValueFromYml("testdata/PID/py_issuer_form.yml", "Place Of Birth.Country");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountry)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.clickCountry);
            searchBar.clear();
            searchBar.sendKeys(country);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.clickPlaceOfBirth)).click();
        } else {
            String countryText = getValueFromYml("testdata/PID/py_issuer_form.yml", "Place Of Birth.Country");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCountry)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement country = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountry);
            country.clear();
            country.sendKeys(countryText);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickPlaceOfBirth)).click();
        }
    }

    public void scrollUntilCountryCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
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
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 1; i++) {
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

    public void scrollUntilCountryCodePid() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 4; i++) {
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
            for (int i = 0; i < 1; i++) {
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

    public void scrollUntilCountry() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 4; i++) {
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
            for (int i = 0; i < 1; i++) {
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

    public void authorizeIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            int maxAttempts = 5;
            int attempt = 0;
            boolean success = false;

            while (attempt < maxAttempts && !success) {
                attempt++;
                System.out.println("Attempt " + attempt + " to verify Authorize page. Refreshing...");

                // 1. Perform the refresh gesture
                pullToRefresh(driver);

                try {
                    // 2. Verify the Page Header
                    // We wrap this in a try-catch so if the header is missing, we refresh and try again
                    String pageHeader = test.mobileWebDriverFactory().getWait().until(
                            ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.authorizePageIsDisplayed)
                    ).getText();

                    Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
                    System.out.println("Header verified correctly.");

                    // 3. Switch to NATIVE_APP
                    if (!"NATIVE_APP".equals(driver.getContext())) {
                        driver.context("NATIVE_APP");
                    }

                    // 4. Search for the form elements
                    By[] locators = {
                            AppiumBy.androidUIAutomator("resourceId(\"//android.view.View[@resource-id=\"authForm\"]\")"),
                            AppiumBy.androidUIAutomator("new UiSelector().text(\"Review & Send\")")
                    };

                    // We look for all locators within a shared timeout for this attempt
                    WebElement element = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .pollingEvery(Duration.ofMillis(500))
                            .ignoring(StaleElementReferenceException.class)
                            .ignoring(NoSuchElementException.class)
                            .until(d -> {
                                for (By locator : locators) {
                                    try {
                                        WebElement e = d.findElement(locator);
                                        if (e.isDisplayed()) return e;
                                    } catch (Exception ignored) {}
                                }
                                return null;
                            });

                    if (element != null) {
                        element.click();
                        System.out.println("SUCCESS: Element found and clicked.");
                        success = true; // Mark as successful to break the loop
                    }

                } catch (Exception e) {
                    System.out.println("Attempt " + attempt + " failed: " + e.getMessage());
                    // The loop will now continue to the next attempt (refresh)
                }
            }

            if (!success) {
                throw new AssertionError("Authorize page or form was not found/clickable after " + maxAttempts + " refreshes.");
            }

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authorizePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
        }
    }

    public void successfullySharedMessage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.successfullyShared)).getText();
            Assert.assertEquals(Literals.Issuer.SUCCESSFULLY_SHARED_IOS.label, pageHeader);
        }
    }

    public void enterCountryCode() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String countryCode = getValueFromYml("testdata/PID/py_issuer_form.yml", "Nationality.Country Code");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickCountryCode)).click();
            AppiumDriver driver = (AppiumDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement searchBar = driver.findElement(IssuerElements.clickCountryCode);
            searchBar.clear();
            searchBar.sendKeys(countryCode);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.closeKeyboard)).click();
        } else {
            String countryCodeText = getValueFromYml("testdata/PID/py_issuer_form.yml", "Nationality.Country Code");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickCountryCode)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement countryCode = driver.findElement(eu.europa.eudi.elements.ios.IssuerElements.clickCountryCode);
            countryCode.clear();
            countryCode.sendKeys(countryCodeText);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.closeKeyboard)).click();
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

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                    wait.until(d -> ((HasContext) d).getContext().equals("NATIVE_APP"));
                }

                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.75);
                int endY = (int) (size.height * 0.35);

                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

                Sequence swipe = new Sequence(finger, 0);
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(new Pause(finger, Duration.ofMillis(100)));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                int maxRetries = 2;
                int attempts = 0;
                boolean success = false;

                while (attempts < maxRetries && !success) {
                    try {
                        driver.perform(Collections.singletonList(swipe));
                        success = true;
                    } catch (InvalidElementStateException e) {
                        attempts++;
                        if (attempts >= maxRetries) {
                            throw new RuntimeException("Swipe failed after " + maxRetries + " attempts", e);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to execute swipe sequence: " + e.getMessage(), e);
            }

        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            String originalContext = driver.getContext();

            try {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                }

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
                        startY
                ));

                swipe.addAction(finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));

                swipe.addAction(finger.createPointerMove(
                        Duration.ofMillis(350),
                        PointerInput.Origin.viewport(),
                        startX,
                        endY
                ));

                swipe.addAction(finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                ));

                driver.perform(Collections.singletonList(swipe));

            } finally {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context(originalContext);
                }
            }
        }
    }

    public void scrollUntilFindName() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            for (int i = 0; i < 3; i++) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = (int) (size.height * 0.6);
                int endY = (int) (size.height * 0.5);
                PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                Sequence swipe = new Sequence(finger, 1);

                swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(new Pause(finger, Duration.ofMillis(200)));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), startX, endY));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

                driver.perform(Collections.singletonList(swipe));
            }
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 5; i++) {
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

    public void kotlinIssuerService() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            envDataConfig = new EnvDataConfig();
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.runAppInBackground(Duration.ofSeconds(10));
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);

            test.envDataConfig();
            String url = test.envDataConfig().getKotlinUrl();
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
            String url = test.envDataConfig().getKotlinUrl();
            driver.get(url);
            Map<String, Object> args = new HashMap<>();
            args.put("bundleId", "com.apple.mobilesafari");
            driver.executeScript("mobile: launchApp", args);
        }
    }

    public void scrollUntilGenerate() {

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

    public void clickWalletLink() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(WalletElements.walletLink)).click();
            driver.context("NATIVE_APP");

        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.walletLink)).click();
        }
    }

    public void fillLoginForm() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement username;
            try {
                username = waitForVisibleAcrossContexts(driver,
                        IssuerElements.clickUsername, IssuerElements.usernameWeb,
                        Duration.ofSeconds(100));
            } catch (TimeoutException e) {
                System.out.println("Contexts at failure: " + driver.getContextHandles());
                System.out.println("Page source:\n" + driver.getPageSource());
                throw new AssertionError("Username field was not found within 100 seconds.", e);
            }
            username.click();
            username.sendKeys("tneal"); // or wherever the value comes from

            WebElement password = waitForVisibleAcrossContexts(driver,
                    IssuerElements.clickPassword, IssuerElements.passwordWeb,
                    Duration.ofSeconds(100));
            password.click();
            password.sendKeys("password");

            WebElement submit = waitForVisibleAcrossContexts(driver,
                    IssuerElements.loginSubmit, IssuerElements.loginSubmitWeb,
                    Duration.ofSeconds(100));
            submit.click();

            driver.context("NATIVE_APP"); // reset before continuing native steps
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait waitNativeAppTransition = new WebDriverWait(driver, Duration.ofSeconds(3000));
            waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));
            driver.context("NATIVE_APP");

            test.mobileWebDriverFactory().iosDriver.rotate(ScreenOrientation.PORTRAIT);

            By locator = eu.europa.eudi.elements.ios.IssuerElements.clickUsername;

            boolean found = false;
            int maxAttempts = 8;
            int waitSeconds = 90;

            for (int attempt = 1; attempt <= maxAttempts && !found; attempt++) {
                try {
                    waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));
                    driver.context("NATIVE_APP");

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));

                    WebElement element = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(locator)
                    );

                    System.out.println("Username field is visible on attempt " + attempt);
                    found = true;

                } catch (TimeoutException e) {
                    System.out.println("Attempt " + attempt + " failed - username not visible yet");

                    if (attempt == maxAttempts) {
                        throw e;
                    }
                }
            }

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .click();

            WebElement username = test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));

            username.clear();
            username.sendKeys("tneal");

            By passwordLocator = eu.europa.eudi.elements.ios.IssuerElements.clickPassword;

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(passwordLocator))
                    .click();

            WebElement password = test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));

            password.clear();
            password.sendKeys("password");

            test.mobileWebDriverFactory().getWait()
                    .until(ExpectedConditions.elementToBeClickable(
                            eu.europa.eudi.elements.ios.IssuerElements.clickSignIn))
                    .click();
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
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.pidMsoMdoc)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.pidMsoMdoc)).click();
        }
    }

    public void assertTextVisibleWithScroll(AndroidDriver driver, String text, int maxScrolls) throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            By locator = By.xpath("//*[@text=\"" + text.replace("\"", "\\\"") + "\"]");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
            int scrollCount = 0;

            while (scrollCount < maxScrolls) {
                if (!driver.findElements(locator).isEmpty()) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                    return;
                }

                fastSwipe(driver);
                scrollCount++;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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

    public void selectMDLKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.selectMDLKotlinCredential)).click();
        } else {
            WebDriverWait wait = test.mobileWebDriverFactory().getWait();

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    eu.europa.eudi.elements.ios.WalletElements.selectMDLKotlin));

            wait.until(driver -> {
                try {
                    driver.findElement(eu.europa.eudi.elements.ios.WalletElements.selectMDLKotlin).click();
                    return true;
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
        }
    }

    public void issueCredentialsPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            String expectedText = "Scan the generated QR Code to issue the requested Credentials:";
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

            WebElement header = wait.until((ExpectedCondition<WebElement>) d -> {
                AndroidDriver ad = (AndroidDriver) d;

                for (String ctx : ad.getContextHandles()) {
                    try {
                        ad.context(ctx);

                        By locator = ctx.equals("NATIVE_APP")
                                ? By.xpath("//android.widget.TextView[@text=\"" + expectedText + "\"]")
                                : By.xpath("//*[normalize-space(text())=\"" + expectedText + "\"]");

                        List<WebElement> found = ad.findElements(locator);
                        if (!found.isEmpty() && found.get(0).isDisplayed()) {
                            return found.get(0); // driver stays switched into whichever context matched
                        }
                    } catch (Exception ignored) {
                        // context may not be attached yet / transient error — try the next one
                    }
                }
                return null; // triggers another poll
            });

            Assert.assertTrue(header.isDisplayed());
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.issueCredentialPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.ISSUANCE_CREDENTIALS.label, pageHeader);
        }
    }

    public void signInUser() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

// wait until session has NATIVE_APP context available at all
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(d -> driver.getContextHandles().contains("NATIVE_APP"));
            driver.context("NATIVE_APP");

            safeScrollForwardAndBack(driver);

            By nativeLocator = eu.europa.eudi.elements.android.IssuerElements.signPageIsDisplayed; // //android.widget.TextView[@resource-id="kc-page-title"]
            By webLocator = By.cssSelector("#kc-page-title");

            WebElement header = waitForVisibleAcrossContexts(driver, nativeLocator, webLocator, Duration.ofSeconds(80));
            System.out.println("Header is visible: " + header.isDisplayed());
            Assert.assertTrue(header.isDisplayed());

            driver.context("NATIVE_APP"); // reset context before continuing with native steps

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebDriverWait waitNativeAppTransition = new WebDriverWait(driver, Duration.ofSeconds(3000));
            waitNativeAppTransition.until(d -> driver.getContextHandles().contains("NATIVE_APP"));
            driver.context("NATIVE_APP");

            WebElement header = new WebDriverWait(driver, Duration.ofSeconds(80))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            eu.europa.eudi.elements.ios.IssuerElements.signPageIsDisplayed
                    ));

            String headerText = header.getText().trim();

            Assert.assertEquals(
                    Literals.Issuer.SIGN_IN_USER_PAGE.label,
                    headerText
            );
        }
    }

    public WebElement waitForVisibleAcrossContexts(AndroidDriver driver,
                                                   By nativeLocator,
                                                   By webLocator,
                                                   Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(d -> {
            AndroidDriver ad = (AndroidDriver) d;
            for (String ctx : ad.getContextHandles()) {
                try {
                    ad.context(ctx);
                    By locator = ctx.equals("NATIVE_APP") ? nativeLocator : webLocator;
                    List<WebElement> found = ad.findElements(locator);
                    if (!found.isEmpty() && found.get(0).isDisplayed()) {
                        return found.get(0);
                    }
                } catch (Exception ignored) {
                    // context not attached yet / transient — try next context
                }
            }
            return null; // triggers another poll cycle
        });
    }

    public void safeScrollForwardAndBack(AndroidDriver driver) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollBackward()"));
        } catch (Exception e) {
            // no scrollable native element present — safe to ignore
        }
    }

    public void selectMdlPythonIssuer() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.selectMDLPythonCredential)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.selectMDLPython)).click();

        }
    }

    public void selectPidPythonIssuer() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.selectPIDPythonCredential)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.selectPIDPython)).click();

        }
    }

    public void scrollUntilMdlIssuer() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement element = null;

            for (int i = 0; i < 80; i++) {
                try {
                    element = driver.findElement(WalletElements.selectMDLPythonCredential);

                    if (element.isDisplayed() && element.isEnabled()) {
                        break;
                    }

                } catch (Exception ignored) {
                    slowScroll();
                }
            }
        } else {
            envDataConfig = new EnvDataConfig();
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                for (int i = 0; i < 9; i++) {
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

    public void scrollUntilPidIssuer() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement element = null;

            for (int i = 0; i < 80; i++) {
                try {
                    element = driver.findElement(WalletElements.selectPIDPythonCredential);

                    if (element.isDisplayed() && element.isEnabled()) {
                        break;
                    }

                } catch (Exception ignored) {
                    slowScroll();
                }
            }
        } else {
            envDataConfig = new EnvDataConfig();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            for (int i = 0; i < 9; i++) {
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

    public void clickUseEudiwPid() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        String deepLink = "haip-vci://credential_offer?credential_offer=%7B%22credential_issuer%22:%20%22https://ec.dev.issuer.eudiw.dev%22%2C%20%22credential_configuration_ids%22:%20%5B%22eu.europa.ec.eudi.pid_mdoc%22%5D%2C%20%22grants%22:%20%7B%22authorization_code%22:%20%7B%22issuer_state%22:%20%22ced958d4-c8c6-4763-9e7d-dd8c8b27b256%22%7D%7D%7D";

                        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

                            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

                            driver.executeScript("mobile: deepLink", ImmutableMap.of(
                                    "url", deepLink,
                                    "package", test.envDataConfig().getAppiumAndroidAppPackage()
                            ));

                            System.out.println("Deep link executed on Android");

                        }
                    } else {
                        IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                        driver.context("NATIVE_APP");
                        test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickEudiwButton)).click();
                    }
            }

    public void issuanceMethod(String issuanceMethod) throws InterruptedException {
        this.issuanceMethod = issuanceMethod;
        switch (issuanceMethod.toLowerCase()) {
            case "from list":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertPidFromListKotlin();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertMdlFromList();
                    }
                } else {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertPidFromList();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertMdlFromList();
                    }
                }
                break;
            case "credential offer":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                    test.mobile().issuer().kotlinIssuerService();
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().selectPIDKotlin();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().selectMDLKotlin();
                    }
                    test.mobile().issuer().scrollUntilGenerate();
                    test.mobile().issuer().clickGenerate();
                } else {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                    } else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                    }
                }
                break;
        }
    }

    public void performIssuance(String issueScenario, String credential) throws InterruptedException {
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            switch (issueScenario.toLowerCase()) {
                case "same device":
                    if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                        test.mobile().issuer().issueCredentialsPageIsDisplayed();
                        test.mobile().issuer().clickWalletLink();
                        test.mobile().wallet().viewDataPage();
                        test.mobile().wallet().clickAddButton();
                        test.mobile().issuer().signInUser();
                        test.mobile().issuer().fillLoginForm();
                    }
                    break;
                case "cross device":
                    test.mobile().issuer().qrCodeIsDisplayedKotlin();
                    test.mobile().verifier().captureScreen();
                    test.mobile().wallet().restartApp();
                    test.mobile().wallet().createAPin();
                    test.mobile().wallet().clickOnDocuments();
                    test.mobile().wallet().clickToAddDocument();
                    test.mobile().wallet().clickQROption();
                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        if (test.mobile().wallet().isQrVisible()) {
                            test.mobile().wallet().onlyThisTimeQR();
                        }
                    }
                    test.mobile().wallet().theQRScannerIsActivatedForIssuance();
                    test.mobile().wallet().mockQRInject(test.mobile().verifier().getCapturedScreenFile());
                    test.mobile().wallet().viewDataPage();
                    test.mobile().wallet().clickAddButton();
                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
                    }
                    test.mobile().issuer().signInUser();
                    test.mobile().issuer().fillLoginForm();
                    break;
            }
        } else {
            switch (issueScenario.toLowerCase()) {
                case "same device":
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                            test.mobile().issuer().issuerService();
                            test.mobile().issuer().requestCredentialsPageIsDisplayed();
                            test.mobile().issuer().scrollUntilPidIssuer();
                            test.mobile().issuer().selectPidPythonIssuer();
                            test.mobile().issuer().scrollUntilFindSubmitIssuer();
                            test.mobile().issuer().clickSubmitButton();
                            test.mobile().issuer().clickUseEudiwPid();
                            test.mobile().wallet().clickAddButton();
                            test.mobile().issuer().issuePID();
                        }
                    } else {
                        if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                            test.mobile().issuer().issuerService();
                            test.mobile().issuer().requestCredentialsPageIsDisplayed();
                            test.mobile().issuer().scrollUntilMdlIssuer();
                            test.mobile().issuer().selectMdlPythonIssuer();
                            test.mobile().issuer().scrollUntilFindSubmitIssuer();
                            test.mobile().issuer().clickSubmitButton();
                            test.mobile().issuer().clickUseEudiw();
                            test.mobile().wallet().clickAddButton();
                            test.mobile().issuer().issueMDL();

                        }
                    }
                    break;
                case "cross device":
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().issuerService();
                        test.mobile().issuer().requestCredentialsPageIsDisplayed();
                        test.mobile().issuer().scrollUntilPidIssuer();
                        test.mobile().issuer().selectPidPythonIssuer();
                        test.mobile().issuer().scrollUntilFindSubmitIssuer();
                        test.mobile().issuer().clickSubmitButton();
                        test.mobile().issuer().qrCodeIsDisplayed();
                        test.mobile().verifier().captureScreen();
                        test.mobile().wallet().restartApp();
                        test.mobile().wallet().createAPin();
                        test.mobile().wallet().clickOnDocuments();
                        test.mobile().wallet().clickToAddDocument();
                        test.mobile().wallet().clickQROption();
                        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                            if (test.mobile().wallet().isQrVisible()) {
                                test.mobile().wallet().onlyThisTimeQR();
                            }
                        }
                        test.mobile().wallet().theQRScannerIsActivatedForIssuance();
                        test.mobile().wallet().mockQRInject(test.mobile().verifier().getCapturedScreenFile());
                        test.mobile().wallet().viewDataPage();
                        test.mobile().wallet().clickAddButton();
                        test.mobile().issuer().issuePID();
                    } else {
                        test.mobile().issuer().issuerService();
                        test.mobile().issuer().requestCredentialsPageIsDisplayed();
                        test.mobile().issuer().scrollUntilMdlIssuer();
                        test.mobile().issuer().selectMdlPythonIssuer();
                        test.mobile().issuer().scrollUntilFindSubmitIssuer();
                        test.mobile().issuer().clickSubmitButton();
                        test.mobile().issuer().qrCodeIsDisplayed();
                        test.mobile().verifier().captureScreen();
                        test.mobile().wallet().restartApp();
                        test.mobile().wallet().createAPin();
                        test.mobile().wallet().clickOnDocuments();
                        test.mobile().wallet().clickToAddDocument();
                        test.mobile().wallet().clickQROption();
                        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                            if (test.mobile().wallet().isQrVisible()) {
                                test.mobile().wallet().onlyThisTimeQR();
                            }
                        }
                        test.mobile().wallet().theQRScannerIsActivatedForIssuance();
                        test.mobile().wallet().mockQRInject(test.mobile().verifier().getCapturedScreenFile());
                        test.mobile().wallet().viewDataPage();
                        test.mobile().wallet().clickAddButton();
                        test.mobile().issuer().issueMDL();
                        break;
                    }
            }
        }
    }

    public void completedIsuuanceFlow() {
        if ("Python".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().successMessageIsDisplayedForIssuer();
        }
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().successMessageIsDisplayedForIssuer();
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                test.mobile().wallet().clickExpandVerification();
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUntilPlaceOfBirth();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_wallet.yml");
                } else {
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_wallet.yml");
                }
            } else {
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().clickExpandVerificationMSODocIOS();
                    test.mobile().wallet().clickExpandNationalityIOS();
                    test.mobile().wallet().clickExpandPlaceOfBirthIOS();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/ios_kotlin_data_on_wallet.yml");
                } else {
                    test.mobile().wallet().clickExpandVerification();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/ios_kotlin_data_on_wallet.yml");
                }
            }
        }
    }
}
