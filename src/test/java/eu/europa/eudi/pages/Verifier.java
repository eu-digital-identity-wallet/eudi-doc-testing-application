package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.ios.WalletElements;
import eu.europa.eudi.utils.TestSetup;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Verifier {
    TestSetup test;
    public Verifier(TestSetup test) {
        this.test = test;
    }

    public void selectShareAttributes() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickShareData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickShareData)).click();
        }
    }

    public void clickNext() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.clickNext)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.clickNext)).click();
        }
    }

    public void chooseData() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseData)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseData)).click();
        }
    }

    public void chooseWallet() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(eu.europa.eudi.elements.android.WalletElements.chooseWallet)).click();
        } else {
            test.mobileWebDriverFactory().getWait().until(ExpectedConditions.elementToBeClickable(WalletElements.chooseWallet)).click();
        }
    }

    public void viewDataPage() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.viewDataPage)).getText();
            Assert.assertEquals(Literals.Verifier.VIEW_DATA_PAGE_IOS.label, pageHeader);
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
        }else{
            IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
            driver.findElement(WalletElements.pinTexfield1).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield2).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield3).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield4).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield5).sendKeys("1");
            driver.findElement(WalletElements.pinTexfield6).sendKeys("1");        }
    }

    public void appOpensSuccefully() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.appOpensSuccessfully)).getText();
            Assert.assertEquals(Literals.Verifier.APP_OPEN_SUCCESSFULLY.label, pageHeader);
        }
    }

    public void AuthenticationPageIsDisplayed() {
        if (test.getSystemOperation().equals(Literals.General.ANDROID.label)) {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(eu.europa.eudi.elements.android.WalletElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.AUTHENTICATION_PAGE.label, pageHeader);
        } else {
            String pageHeader = test.mobileWebDriverFactory().getWait().until(ExpectedConditions.visibilityOfElementLocated(WalletElements.authenticationPageIsDisplayed)).getText();
            Assert.assertEquals(Literals.Verifier.AUTHENTICATION_PAGE.label, pageHeader);
        }
    }
}