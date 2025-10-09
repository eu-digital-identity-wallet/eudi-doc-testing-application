package eu.europa.eudi.pages;

import eu.europa.eudi.api.EventsApiVerifier;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.ios.IssuerElements;
import eu.europa.eudi.elements.ios.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.MobileBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Verifier {
    TestSetup test;

    public Verifier(TestSetup test) {
        this.test = test;
    }

    public void selectShareAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String deviceName = test.envDataConfig().getAppiumAndroidDeviceName();

// Debugging: Print the device name to check its value
            System.out.println("Device Name: '" + deviceName + "'");
            if (test.envDataConfig().getAppiumAndroidDeviceName().equals("POCO X5 Pro")) {
//                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
//                driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickData)).click();
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAttributes)).click();
//                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAllAttributes)).click();
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickFormat)).click();
//                driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSpecificFormat)).click();
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickData)).click();
            } else {
                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
                driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickData)).click();
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAttributesEmulator)).click();
//                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickAllAttributes)).click();
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickFormatEmulator)).click();
//                driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSpecificFormatEmulator)).click();
            }
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickPersonIdentificationData)).click();
        }
    }

    public void clickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickNextForVerifier)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickNext)).click();
        }
    }
    public void assertAndClickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.presentationQueryTypeIsVisible)).getText();
            Assert.assertEquals(Literals.Verifier.PRESENTATION_QUERY_TYPE.label, pageHeader);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickNextForVerifier)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickNext)).click();
        }
    }

    public void chooseWallet() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseWallet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseWallet)).click();
        }
    }

    public void viewDataPage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        }
    }

    public void authorizeData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            WebDriver driver = test.mobileWebDriverFactory().getDriverAndroid();
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield1).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield2).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield3).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield4).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield5).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.android.WalletElements.pinTexfield6).sendKeys("1");
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield1).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield2).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield3).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield4).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield5).sendKeys("1");
            driver.findElement(eu.europa.eudi.elements.ios.WalletElements.pinTexfield6).sendKeys("1");
        }
    }

    public void appOpensSuccessfully() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
        } else {
//            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.appOpensSuccessfully)).getText();
//            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY_IOS.label, pageHeader);
        }
    }

    public void AuthenticationPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.AUTHENTICATION_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.AUTHENTICATION_PAGE.label, pageHeader);
        }
    }

    public void chooseData2() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseData2)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.chooseData2)).click();
        }
    }
    public void scrollUntilFindIssuanceDate() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {

            WebDriver driver = test.mobileWebDriverFactory().getDriverAndroid();
            By locator = By.xpath("//android.widget.CheckBox[@text=\"Issuance date\"]");
            WebElement element = null;
            int i = 1;
            while (i < 4) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = size.height / 2;  // Start from the middle of the screen
                int endY = (int) (size.height * 0.2);  // Adjust the endY as needed
                new TouchAction<>((PerformsTouchActions) driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                i++;
            }

        } else {
            WebDriver driver = test.mobileWebDriverFactory().getDriverIos();
            int i = 1;
            while (i < 4) {
                Dimension size = driver.manage().window().getSize();
                int startX = size.width / 2;
                int startY = size.height / 2;  // Start from the middle of the screen
                int endY = (int) (size.height * 0.2);  // Adjust the endY as needed
                new TouchAction<>((PerformsTouchActions) driver)
                        .press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                i++;
            }
        }
    }

    public void selectAllAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            if (test.envDataConfig().getAppiumAndroidDeviceName().equals("POCO X5 Pro")) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.selectAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.firstAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.msoMdoc)).click();
                clickMsoMdocRealDevice();
            }   else{
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickData)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.selectAttributesEmulator)).click();
                clickAllAttributes();
                //test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.firstAttribute)).click();
                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFormat)).click();
//                test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.msoMdoc)).click();
                clickMsoMdoc();
            }
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.presenceOfElementLocated(IssuerElements.clickPersonIdentificationData)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.selectAttributesBy)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.allAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.msoMdoc)).click();
        }
    }

    private void clickAllAttributes() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        int retries = 3;
        while (retries > 0) {
            try {
                WebElement dropdown = test.mobileWebDriverFactory().getWait().until(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ListView[@resource-id='mat-select-0-panel']"))
                );
                Point location = dropdown.getLocation();
                Dimension size = dropdown.getSize();
                int optionY = location.y + (size.height - 10);
                int centerX = location.x + (size.width / 2);
                new TouchAction(driver)
                        .tap(PointOption.point(centerX, optionY))
                        .perform();
                break;
            } catch (Exception e) {
                retries--;
                if (retries == 0) throw e;
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }
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
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.walletResponded)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED.label, pageHeader);
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.walletResponded)).getText();
            Assert.assertEquals(Literals.Verifier.WALLET_RESPONDED.label, pageHeader);
        }
    }


    public void selectSpecificAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.selectAttributesBy)).click();
            clickSpecificAttributes();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickFormat)).click();
            clickMsoMdoc();
            test.mobile().verifier().scrollUntilNext();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.selectAttributesBy)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.selectSpecificAtt)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.clickFormat)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(IssuerElements.msoMdoc)).click();
            test.mobile().verifier().scrollUntilNext();
        }
    }

    public void selectAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.clickShareAttributes)).click();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.familyNameAtt)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.givenNameAtt)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.birthDateAtt)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.ageOver18Att)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.IssuerElements.birthPlaceAtt)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickShareAttributes)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.firstAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.secondAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.thirdAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.fourthAttribute)).click();
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.fifthAttribute)).click();
        }
    }

    public void clickSelect() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickSelect)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.IssuerElements.clickSelect)).click();

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

    public void clickMsoMdoc() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        WebElement dropdown = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ListView[@resource-id='mat-select-1-panel']")));
        Point location = dropdown.getLocation();
        Dimension size = dropdown.getSize();
        int optionY = location.y + (size.height / 6);
        int centerX = location.x + (size.width / 2);
        new TouchAction(driver)
                .tap(PointOption.point(centerX, optionY))
                .perform();
    }

    public void clickSpecificAttributes() {
        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
        WebElement dropdown = driver.findElement(By.xpath("//android.widget.ListView[@resource-id=\"mat-select-0-panel\"]"));
        Point location = dropdown.getLocation();
        Dimension size = dropdown.getSize();

        int optionY = location.y + (size.height / 6);
        int centerX = location.x + (size.width / 2);

        new TouchAction(driver)
                .tap(PointOption.point(centerX, optionY))
                .perform();
    }

    public void clickTransactionsLogs() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickTransactionsLogs)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.ios.WalletElements.clickTransactionsLogs)).click();
        }
    }

    public void clickTransactionInitialized() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickTransactionInitialized)).click();
        } else {
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), 66, 220));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            ((RemoteWebDriver) driver).perform(Arrays.asList(tap));
        }
    }
    public void getTransactionId() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
            WebElement jsonElement = driver.findElement(By.className("android.widget.TextView"));
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
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
//            WebElement jsonElement = driver.findElement(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText"));
//            // Get the text
//            String rawText = jsonElement.getText();
//            System.out.println(rawText);
//            JSONObject jsonObject = new JSONObject(rawText);
//            JSONObject valueObject = jsonObject.getJSONObject("value");
//
//            // Step 4: Extract the transaction_id
//            String transactionId = valueObject.getString("transaction_id");
//
//            // Output the result
//            System.out.println("Transaction ID: " + transactionId);
//
//            EventsApiVerifier api = new EventsApiVerifier();
//            api.getPresentationEvents(transactionId);
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

    public void chooseWalletPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.chooseWalletPageDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.CHOOSE_WALLET_DISPLAYED.label, pageHeader);
        }else{
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.ios.WalletElements.chooseWalletPageDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.CHOOSE_WALLET_DISPLAYED.label, pageHeader);
        }
    }

    public void urlToWalletIsDisplayed() {
        test.mobile().verifier().chooseWallet();
    }
}