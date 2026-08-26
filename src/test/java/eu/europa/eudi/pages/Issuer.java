package eu.europa.eudi.pages;

import com.google.common.collect.ImmutableMap;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.data.yml.FormYml;
import eu.europa.eudi.elements.android.IssuerElements;
import eu.europa.eudi.elements.android.WalletElements;
import eu.europa.eudi.utils.MobileActionsUtils;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.WaitsActionsUtils;
import eu.europa.eudi.utils.yaml.YmlLoader;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en_scouse.An;
import org.junit.Assert;
import org.openqa.selenium.*;
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

public class Issuer {
    public String issuerType;
    public String credential;
    public String issuanceMethod;
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

            String deepLink = "haip-vci://credential_offer?credential_offer=%7B%22credential_issuer%22:%20%22https://issuer.eudiw.dev%22%2C%20%22credential_configuration_ids%22:%20%5B%22eu.europa.ec.eudi.mdl_mdoc%22%5D%2C%20%22grants%22:%20%7B%22authorization_code%22:%20%7B%22issuer_state%22:%20%22ced958d4-c8c6-4763-9e7d-dd8c8b27b256%22%7D%7D%7D";

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
            MobileActionsUtils.tapAction(button, false);
        }
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

            By showYearPicker = AppiumBy.accessibilityId("DatePicker.Show");
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

            By showYearPicker = AppiumBy.accessibilityId("DatePicker.Show");
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

            By showYearPicker = AppiumBy.accessibilityId("DatePicker.Show");
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
                    MobileActionsUtils.slowScroll();
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
                    MobileActionsUtils.slowScroll();
                }
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));

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

    public void clickAuthorize() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            // Switch to WEBVIEW
            for (String context : driver.getContextHandles()) {
                if (context.contains("WEBVIEW")) {
                    driver.context(context);
                    break;
                }
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

            wait.until(d ->
                    ((JavascriptExecutor)d)
                            .executeScript("return document.readyState")
                            .equals("complete"));

// Switch back if you need to click a native element
            driver.context("NATIVE_APP");


            By authorizeButton = By.xpath("//android.widget.Button[@text='Authorize']");

// Wait until visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(authorizeButton));

// Wait until clickable
            wait.until(ExpectedConditions.elementToBeClickable(authorizeButton));

// Click
            driver.findElement(authorizeButton).click();
            Thread.sleep(5000); // 3-second delay

        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.context("NATIVE_APP");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(350));
            By theButtonToClick = By.xpath("//XCUIElementTypeButton[@name=\"Authorize\"]");
            wait.until(ExpectedConditions.elementToBeClickable(theButtonToClick)).click();
            driver.context("NATIVE_APP");
        }
    }

    public void formIsDisplayed() {

        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            By[] locators = {
                    AppiumBy.androidUIAutomator("resourceId(\"content\")"),
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"Mandatory Information\")")
            };

            int maxAttempts = 5;
            int attempt = 0;
            WebElement element = null;

            while (attempt < maxAttempts) {

                attempt++;
                System.out.println("Attempt " + attempt + " to find element.");

                try {
                    // Ensure we are in NATIVE_APP
                    if (!"NATIVE_APP".equals(driver.getContext())) {
                        driver.context("NATIVE_APP");
                    }

                    // First try WITHOUT refresh
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

                                    } catch (Exception ignored) {
                                    }
                                }

                                return null;
                            });

                    if (element != null) {
                        break;
                    }

                } catch (Exception e) {
                    System.out.println("Element not found on attempt " + attempt);

                    // Refresh only after the first failed attempt
                    if (attempt < maxAttempts) {
                        System.out.println("Refreshing page...");
                        pullToRefresh(driver);
                    }
                }
            }

            if (element != null) {
                element.click();
            } else {
                throw new AssertionError(
                        "Element was not displayed even after " + maxAttempts + " attempts."
                );
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

        int startY = (int) (size.height * 0.25);
        int endY = (int) (size.height * 0.70);

        PointerInput finger = new PointerInput(
                PointerInput.Kind.TOUCH,
                "finger"
        );

        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ZERO,
                        PointerInput.Origin.viewport(),
                        centerX,
                        startY
                )
        );

        swipe.addAction(
                finger.createPointerDown(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        swipe.addAction(
                finger.createPointerMove(
                        Duration.ofMillis(800),
                        PointerInput.Origin.viewport(),
                        centerX,
                        endY
                )
        );

        swipe.addAction(
                finger.createPointerUp(
                        PointerInput.MouseButton.LEFT.asArg()
                )
        );

        driver.perform(Collections.singletonList(swipe));

        System.out.println("Pull-to-refresh executed.");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            WebElement continueButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            AppiumBy.id("com.android.chrome:id/positive_button")
                    )
            );

            continueButton.click();
            System.out.println("Clicked Continue.");
        } catch (TimeoutException e) {
            System.out.println("Confirm Form Resubmission popup did not appear.");
        }
    }

    public void issuePID(String credential) throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
        selectCountryOfOrigin();
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
        if ("PID (SD-JWT)".equalsIgnoreCase(credential)) {
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_issuer_authorization_sd_jwt_android.yml");
            }else{
                verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_issuer_authorization_sd_jwt.yml");
            }
        } else {
            verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_issuer_authorization.yml");
        }
        scrollUntilAuthorize();
        clickAuthorize();
    }

    private void selectCountryOfOrigin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement header = null;
            String headerText = null;

            try {
                // First attempt
                header = WaitsActionsUtils.waitForExactText(
                        eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed,
                        Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label,
                        driver,
                        30
                );

                headerText = driver.findElement(
                        eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed
                ).getText().trim();

            } catch (Exception e) {
                // If not found, refresh and check again
                pullToRefresh(driver);

                header = WaitsActionsUtils.waitForExactText(
                        eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed,
                        Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label,
                        driver,
                        30
                );

                headerText = driver.findElement(
                        eu.europa.eudi.elements.android.IssuerElements.selectCountryOfOriginIsDisplayed
                ).getText().trim();
            }

            Assert.assertEquals(
                    Literals.Issuer.SELECT_COUNTRY_IS_DISPLAYED.label,
                    headerText
            );

            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            WebElement header = WaitsActionsUtils.waitForExactText(
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
                            MobileActionsUtils.slowScroll();
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
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickConfirm)).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(250));
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickConfirm)).click();
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(250));

        }
    }

    public void issueMDL() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
        }
        selectCountryOfOrigin();
        clickFormEu();
        scrollUntilFindSubmit();
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
            String givenNameText = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Given name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickGivenName)).click();
            WebElement givenName = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickGivenName));
            givenName.clear();
            givenName.sendKeys(givenNameText);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardForm)).click();
        } else {
            String givenNameText = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Given name");
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
            String familyName = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Family name");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName)).click();
            WebElement givenFamily = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.IssuerElements.clickFamilyName));
            givenFamily.clear();
            givenFamily.sendKeys(familyName);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.closeKeyboardForm)).click();
        } else {
            String familyName = getValueFromYml("testdata/mDL/py_data_on_wallet.yml", "Family name");
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

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {

                try {
                    System.out.println("Attempt " + attempt + " checking Authorize page...");

                    // Check session before doing anything
                    verifyDriverSession(driver);

                    // 1. Verify header exists
                    String pageHeader = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .until(ExpectedConditions.visibilityOfElementLocated(
                                    eu.europa.eudi.elements.android.IssuerElements.authorizePageIsDisplayed))
                            .getText();

                    Assert.assertEquals(
                            Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label,
                            pageHeader
                    );

                    System.out.println("Authorize header verified.");

                    // 2. Switch context
                    if (!"NATIVE_APP".equals(driver.getContext())) {
                        driver.context("NATIVE_APP");
                    }

                    // 3. Find Review & Send button
                    WebElement element = new WebDriverWait(driver, Duration.ofSeconds(15))
                            .pollingEvery(Duration.ofMillis(500))
                            .ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.elementToBeClickable(
                                    AppiumBy.androidUIAutomator(
                                            "new UiSelector().text(\"Review & Send\")"
                                    )
                            ));

                    // 4. Click/tap
                    element.click();

                    System.out.println("SUCCESS: Review & Send clicked.");
                    return;

                } catch (TimeoutException e) {

                    System.out.println(
                            "Authorize page not ready. Attempt "
                                    + attempt + "/" + maxAttempts
                    );

                    if (attempt < maxAttempts) {
                        pullToRefresh(driver);
                    }

                } catch (WebDriverException e) {

                    // Session is dead - do not retry
                    throw new RuntimeException(
                            "Appium session terminated during Authorize verification",
                            e
                    );
                }
            }

            throw new AssertionError(
                    "Authorize page was not available after " + maxAttempts + " attempts."
            );
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.authorizePageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.AUTHORIZE_IS_DISPLAYED.label, pageHeader);
        }
    }

    private void verifyDriverSession(AndroidDriver driver) {

        try {
            driver.getPageSource();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "BrowserStack/Appium session is no longer active",
                    e
            );
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

            WebElement header = WaitsActionsUtils.waitForExactText(
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

            try {
                test.mobileWebDriverFactory().getWait()
                        .until(ExpectedConditions.presenceOfElementLocated(WalletElements.walletLink))
                        .click();

            } catch (Exception e) {
                // Element not found -> refresh and try again
                pullToRefresh(driver);

                test.mobileWebDriverFactory().getWait()
                        .until(ExpectedConditions.presenceOfElementLocated(WalletElements.walletLink))
                        .click();
            }

            driver.context("NATIVE_APP");

        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.walletLink)).click();
        }
    }

    public void fillLoginForm() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().androidDriver.rotate(ScreenOrientation.PORTRAIT);
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

            WebElement username;
            try {
                username = waitForVisibleAcrossContexts(driver,
                        IssuerElements.clickUsername, IssuerElements.usernameWeb,
                        Duration.ofSeconds(2000));
            } catch (TimeoutException e) {
                System.out.println("Contexts at failure: " + driver.getContextHandles());
                System.out.println("Page source:\n" + driver.getPageSource());
                throw new AssertionError("Username field was not found within 100 seconds.", e);
            }
            username.click();
            username.sendKeys("tneal"); // or wherever the value comes from

            WebElement password = waitForVisibleAcrossContexts(driver,
                    IssuerElements.clickPassword, IssuerElements.passwordWeb,
                    Duration.ofSeconds(2000));
            password.click();
            password.sendKeys("password");

            WebElement submit = waitForVisibleAcrossContexts(driver,
                    IssuerElements.loginSubmit, IssuerElements.loginSubmitWeb,
                    Duration.ofSeconds(2000));
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

            By[] locators = {
                    By.xpath("//android.widget.TextView[@text=\"" + expectedText + "\"]"),
                    By.xpath("//*[normalize-space(text())=\"" + expectedText + "\"]"),
                    By.xpath("//*[contains(@text,\"Scan the generated QR Code\")]"),
                    By.xpath("//*[contains(text(),\"Scan the generated QR Code\")]")
            };

            int maxAttempts = 5;
            int attempt = 0;
            boolean headerFound = false;

            while (attempt < maxAttempts && !headerFound) {
                attempt++;

                System.out.println("Attempt " + attempt + " to find QR header. Refreshing page...");

                // Refresh if needed
                pullToRefresh(driver);

                try {
                    WebElement header = new WebDriverWait(driver, Duration.ofSeconds(20))
                            .pollingEvery(Duration.ofMillis(500))
                            .ignoring(NoSuchElementException.class)
                            .ignoring(StaleElementReferenceException.class)
                            .until(d -> {

                                AndroidDriver ad = (AndroidDriver) d;

                                // Try all available contexts
                                for (String ctx : ad.getContextHandles()) {
                                    try {
                                        System.out.println("Checking context: " + ctx);

                                        ad.context(ctx);

                                        for (By locator : locators) {
                                            try {
                                                List<WebElement> elements = ad.findElements(locator);

                                                if (!elements.isEmpty()) {
                                                    WebElement element = elements.get(0);

                                                    if (element.isDisplayed()) {
                                                        System.out.println(
                                                                "SUCCESS: Found QR header using "
                                                                        + locator
                                                                        + " in context "
                                                                        + ctx
                                                        );

                                                        return element;
                                                    }
                                                }

                                            } catch (Exception ignored) {
                                                // Try next locator
                                            }
                                        }

                                    } catch (Exception ignored) {
                                        // Context unavailable, continue
                                    }
                                }

                                return null;
                            });


                    if (header != null) {
                        Assert.assertTrue(header.isDisplayed());
                        headerFound = true;

                        System.out.println(
                                "Successfully found QR header on attempt " + attempt
                        );
                    }


                } catch (Exception e) {
                    System.out.println(
                            "Attempt " + attempt + " failed: " + e.getMessage()
                    );
                }
            }


            if (!headerFound) {
                throw new AssertionError(
                        "QR header not found after " + maxAttempts + " refresh attempts."
                );
            }
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.issueCredentialPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Issuer.ISSUANCE_CREDENTIALS.label, pageHeader);
        }
    }

    public void signInUser() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();

// Wait until the NATIVE_APP context exists
            new WebDriverWait(driver, Duration.ofSeconds(3000))
                    .until(d -> driver.getContextHandles().contains("NATIVE_APP"));

            driver.context("NATIVE_APP");

// Perform your native actions
            safeScrollForwardAndBack(driver);

// Define locators for both contexts
            By nativeLocator = eu.europa.eudi.elements.android.IssuerElements.signPageIsDisplayed;
            By webLocator = By.cssSelector("#kc-page-title");

            WebElement header;
            try {
                header = waitForVisibleAcrossContexts(
                        driver,
                        nativeLocator,
                        webLocator,
                        Duration.ofSeconds(300));
            } catch (TimeoutException e) {
                System.out.println("Contexts at failure: " + driver.getContextHandles());
                System.out.println("Current context: " + driver.getContext());
                System.out.println("Page source:\n" + driver.getPageSource());
                throw new AssertionError("Sign page header was not found.", e);
            }

            Assert.assertTrue(header.isDisplayed());

            driver.context("NATIVE_APP"); // Reset before continuing native steps

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

    public void selectMdlPythonIssuer() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.selectMDLPythonCredential)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.selectMDLPython)).click();

        }
    }

    public void selectPidPythonIssuer() throws InterruptedException {
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
                    MobileActionsUtils.slowScroll();
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

    public void scrollUntilPidSDJWTIssuer() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement element = null;

            for (int i = 0; i < 80; i++) {
                try {
                    element = driver.findElement(WalletElements.selectPIDSDJWTPythonCredential);

                    if (element.isDisplayed() && element.isEnabled()) {
                        break;
                    }

                } catch (Exception ignored) {
                    MobileActionsUtils.slowScroll();
                }
            }
        } else {
            envDataConfig = new EnvDataConfig();
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
                    MobileActionsUtils.slowScroll();
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
                        String deepLink = "haip-vci://credential_offer?credential_offer=%7B%22credential_issuer%22:%20%22https://issuer.eudiw.dev%22%2C%20%22credential_configuration_ids%22:%20%5B%22eu.europa.ec.eudi.pid_mdoc%22%5D%2C%20%22grants%22:%20%7B%22authorization_code%22:%20%7B%22issuer_state%22:%20%22ced958d4-c8c6-4763-9e7d-dd8c8b27b256%22%7D%7D%7D";

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

    public void clickUseEudiwPidSDJWT() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String deepLink =
                    "haip-vci://credential_offer?credential_offer=%7B%22credential_issuer%22:%20%22https://issuer.eudiw.dev%22%2C%20%22credential_configuration_ids%22:%20%5B%22eu.europa.ec.eudi.pid_vc_sd_jwt%22%5D%2C%20%22grants%22:%20%7B%22authorization_code%22:%20%7B%22issuer_state%22:%20%2256c01aa5-3d6f-4983-b376-5e759bbeded6%22%7D%7D%7D";
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

    public void issuanceMethodIs(String issuanceMethod, String credential, String issuerType) throws InterruptedException {
        this.issuanceMethod = issuanceMethod;
        this.issuerType = issuerType;
        this.credential = credential;
        switch (issuanceMethod.toLowerCase()) {
            case "from list":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential) || "PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertPidFromListKotlin();
                    }  else if ("mDL (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                        test.mobile().wallet().insertMdlFromListKotlin();
                    }
                } else {
                    if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential) || "PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
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
                    }else if ("PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
                        test.mobile().issuer().selectPIDSDJWTKotlin();
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

    private void selectPIDSDJWTKotlin() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(IssuerElements.pidSDJWT)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.IssuerElements.pidSDJWT)).click();
        }
    }

    public void viewDataPage() throws InterruptedException {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.context("NATIVE_APP");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

            wait.until(ExpectedConditions.textToBePresentInElementLocated(eu.europa.eudi.elements.android.VerifierElements.viewDataPage, Literals.Verifier.VIEW_DATA_PAGE.label));

            String headerText = driver.findElement(eu.europa.eudi.elements.android.VerifierElements.viewDataPage).getText().trim();

            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, headerText);
            Thread.sleep(5000);

        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.VerifierElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        }
    }

    public void performIssuance(String issueScenario, String credential, String issuanceMethod, String issuerType) throws InterruptedException {
       this.issuerType = issuerType;
        switch (issuanceMethod.toLowerCase()) {
            case "credential offer":
                if ("kotlin".equalsIgnoreCase(this.issuerType)) {
                        switch (issueScenario.toLowerCase()) {
                            case "same device":
                                if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                                    test.mobile().issuer().issueCredentialsPageIsDisplayed();
                                    test.mobile().issuer().clickWalletLink();
                                    test.mobile().issuer().viewDataPage();
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
                                test.mobile().issuer().viewDataPage();
                                test.mobile().wallet().clickAddButton();
                                test.mobile().issuer().signInUser();
                                test.mobile().issuer().fillLoginForm();
                                break;
                        }
                } else {
                    switch (issueScenario.toLowerCase()) {
                        case "same device":
                            if ("PID (MSO Mdoc)".equalsIgnoreCase(credential) || "PID (SD-JWT)".equalsIgnoreCase(credential)) {
                                test.mobile().issuer().issuerService();
                                test.mobile().issuer().clickissuerService();
                                test.mobile().issuer().requestCredentialsPageIsDisplayed();
                                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                                    test.mobile().issuer().scrollUntilPidIssuer();
                                    test.mobile().issuer().selectPidPythonIssuer();
                                    test.mobile().issuer().scrollUntilFindSubmitIssuer();
                                    test.mobile().issuer().clickSubmitButton();
                                    test.mobile().issuer().clickUseEudiwPid();
                                }else{
                                    test.mobile().issuer().scrollUntilPidSDJWTIssuer();
                                    test.mobile().issuer().selectPidSDJWTPythonIssuer();
                                    test.mobile().issuer().scrollUntilFindSubmitIssuer();
                                    test.mobile().issuer().clickSubmitButton();
                                    test.mobile().issuer().clickUseEudiwPidSDJWT();
                                }
                                test.mobile().wallet().clickAddButton();
                                test.mobile().issuer().issuePID(this.credential);
                            } else {
                                if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                                    test.mobile().issuer().issuerService();
                                    test.mobile().issuer().clickissuerService();
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
                            if ("PID (MSO Mdoc)".equalsIgnoreCase(credential) || "PID (SD-JWT)".equalsIgnoreCase(credential)) {
                                test.mobile().issuer().issuerService();
                                test.mobile().issuer().clickissuerService();
                                test.mobile().issuer().requestCredentialsPageIsDisplayed();
                            if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                                test.mobile().issuer().scrollUntilPidIssuer();
                                test.mobile().issuer().selectPidPythonIssuer();
                            }else{
                                test.mobile().issuer().scrollUntilPidSDJWTIssuer();
                                test.mobile().issuer().selectPidSDJWTPythonIssuer();
                            }
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
                                test.mobile().issuer().viewDataPage();
                                test.mobile().wallet().clickAddButton();
                                test.mobile().issuer().issuePID(this.credential);
                            } else {
                                test.mobile().issuer().issuerService();
                                test.mobile().issuer().clickissuerService();
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
                                test.mobile().issuer().viewDataPage();
                                test.mobile().wallet().clickAddButton();
                                test.mobile().issuer().issueMDL();
                                break;
                            }
                    }
                }
                break;
        }
    }

    private void selectPidSDJWTPythonIssuer() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.selectPIDSDJWTPythonCredential)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.selectPIDSDJWTPython)).click();

        }
    }

    private void clickissuerService() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickIssuerCredentialOffer)).click();
        }else{
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickIssuerCredentialOffer)).click();
        }
    }

    public void completedIsuuanceFlow(String issuerType, String credential, String issuanceMethod) {
        this.issuerType = issuerType;
        this.credential = credential;
        this.issuanceMethod = issuanceMethod;
        if ("Python".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().successMessageIsDisplayedForIssuer();
            if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                    test.mobile().wallet().clickExpandVerification();
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUntilPlaceOfBirth();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_data_on_wallet.yml");
                } else {
                    test.mobile().wallet().clickExpandVerificationMSODocIOS(issuerType);
                    test.mobile().wallet().clickExpandPlaceOfBirthIOS();
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandNationalityIOS();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_data_on_wallet.yml");

                }
            } else if ("PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
                if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                    test.mobile().wallet().clickExpandVerificationForSDJWT();
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUntilPlaceOfBirth();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_data_on_wallet_sdjwt.yml");
                } else {
                    test.mobile().wallet().clickExpandVerificationForSDJWT();
                    test.mobile().wallet().clickExpandPlaceOfBirthIOS();
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandNationalityIOS();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/py_data_on_wallet_sdjwt.yml");
                }
            }
        }
        if ("kotlin".equalsIgnoreCase(this.issuerType)) {
            test.mobile().wallet().successMessageIsDisplayedForIssuer();
            if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().clickExpandVerification();
                    test.mobile().wallet().scrollUntilNationality();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUntilPlaceOfBirth();
                    test.mobile().wallet().clickExpandVerificationDown();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_wallet.yml");
                } else if ("PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
                    if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                        test.mobile().wallet().clickExpandVerificationForSDJWT();
                        test.mobile().wallet().scrollUntilNationality();
                        test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUntilPlaceOfBirth();
//                    test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUpForBirthDateOnPID();
                    }else{
                        test.mobile().wallet().clickExpandVerificationForSDJWT();
                        test.mobile().wallet().scrollUntilNationality();
                        test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUntilPlaceOfBirth();
//                    test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUpForBirthDateOnPID();
                    }
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_wallet_sdjwt.yml");
                }else if ("mDL (MSO Mdoc)".equalsIgnoreCase(credential)){
                    test.mobile().wallet().clickExpandVerification();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/kotlin_data_on_wallet.yml");
                }
            } else {
                if ("PID (MSO Mdoc)".equalsIgnoreCase(this.credential)) {
                    test.mobile().wallet().clickExpandVerificationMSODocIOS(issuerType);
                    test.mobile().wallet().clickExpandNationalityIOS();
                    test.mobile().wallet().clickExpandPlaceOfBirthIOS();
                    test.mobile().wallet().scrollUpForBirthDateOnPID();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/ios_kotlin_data_on_wallet.yml");
                } else if ("PID (SD-JWT)".equalsIgnoreCase(this.credential)) {
                    if ("credential offer".equalsIgnoreCase(this.issuanceMethod)) {
                        test.mobile().wallet().clickExpandVerificationForSDJWT();
                        test.mobile().wallet().scrollUntilNationality();
                        test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUntilPlaceOfBirth();
//                    test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUpForBirthDateOnPID();
                    }else{
                        test.mobile().wallet().clickExpandVerification();
                        test.mobile().wallet().scrollUntilNationality();
                        test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUntilPlaceOfBirth();
//                    test.mobile().wallet().clickExpandVerificationDown();
                        test.mobile().wallet().scrollUpForBirthDateOnPID();
                    }
                    if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
                        test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_wallet_sdjwt.yml");
                    }else{
                        test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/PID/kotlin_data_on_wallet_sdjwt_ios.yml");
                    }
                }else{
                    test.mobile().wallet().clickExpandVerification();
                    test.mobile().wallet().verifyMandatoryInfoLabelsPresentInAuthorizePage("testdata/mDL/ios_kotlin_data_on_wallet.yml");
                }
            }
        }
    }
}
