package eu.europa.eudi.pages;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.elements.conformance.ConformanceElements;
import eu.europa.eudi.utils.TestSetup;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class Conformance {
    TestSetup test;

    public Conformance(TestSetup test) {
        this.test = test;
    }

    public void openConformanceSuite() {
        WebDriver driver = test.webDriverFactory().getWebDriver();
        driver.get("https://www.certification.openid.net/schedule-test.html");
    }

    public void waitForPageToLoad() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.BODY_CONTENT));
    }

    public void verifyLoginPageDisplayed() {
        Assert.assertTrue("Login page title should be displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.LOGIN_PAGE_TITLE)).isDisplayed());
    }
    
    public void clickProceedWithGoogle() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.elementToBeClickable(ConformanceElements.PROCEED_WITH_GOOGLE_BUTTON)).click();
    }
    
    public void enterGoogleEmail() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.EMAIL_INPUT)).sendKeys(test.envDataConfig().getWebAppEmail());
    }
    
    public void clickGoogleNext() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.elementToBeClickable(ConformanceElements.EMAIL_NEXT_BUTTON)).click();
    }
    
    public void enterGooglePassword() {
        waitForPageToLoad();
        waitForPageToLoad();
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.PASSWORD_INPUT)).sendKeys(test.envDataConfig().getWebAppPassword());
    }
    
    public void clickGooglePasswordNext() {
        waitForPageToLoad();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.elementToBeClickable(ConformanceElements.PASSWORD_NEXT_BTN)).click();
    }

    public void verifyTheUserIsLoggedIn() {
        Assert.assertTrue("Logged in <email>, should be displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.USER_LOGGED_IN)).isDisplayed());
    }

    public void verifyTestPlanPageDisplayed() {
        Assert.assertTrue("''Please see OpenID Foundation Certification Instructions.'' should be displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.TEST_PLAN_PAGE)).isDisplayed());
    }

    public void checkEarlyVersionTestsCheckbox() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.invisibilityOfElementLocated(ConformanceElements.LOADING_MODAL));
        test.webDriverFactory().getWebWait().until(ExpectedConditions.elementToBeClickable(ConformanceElements.SHOW_EARLY_VERSION_TESTS_CHECKBOX)).click();
    }

    public void selectTestPlan() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.TEST_PLAN_DROPDOWN)).selectByVisibleText(Literals.WebApp.TEST_PLAN.label);
    }

    public void verifySenderConstrainingFieldDisplayed() {
        Assert.assertTrue("Sender Constraining field should be displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.SENDER_CONSTRAINING)).isDisplayed());
    }

    public void verifyAuthRequestTypeDisplayed() {
        Assert.assertTrue("Authorization Request Type field should be displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.AUTH_REQUEST_TYPE)).isDisplayed());
    }

    public void verifyGrantTypeDisplayed() {
        Assert.assertTrue("Grant Type field should be displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.GRANT_TYPE)).isDisplayed());
    }

    public void selectSenderConstraining() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.SENDER_CONSTRAINING_DROPDOWN)).selectByVisibleText(Literals.WebApp.SENDER_CONSTRAINT.label);
    }

    public void selectClientAuthType() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.CLIENT_AUTH_DROPDOWN)).selectByVisibleText(Literals.WebApp.CLIENT_AUTH_TYPE.label);
    }

    public void selectAuthCodeFlow() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.AUTH_CODE_FLOW_DROPDOWN)).selectByVisibleText(Literals.WebApp.AUTH_CODE_FLOW.label);
    }

    public void selectAuthRequestType() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.AUTH_REQUEST_DROPDOWN)).selectByVisibleText(Literals.WebApp.AUTH_REQUEST_TYPE.label);
    }

    public void selectRequestMethod() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.REQUEST_METHOD_DROPDOWN)).selectByVisibleText(Literals.WebApp.REQUEST_METHOD.label);
    }

    public void selectGrantType() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.GRANT_TYPE_DROPDOWN)).selectByVisibleText(Literals.WebApp.GRANT_TYPE.label);
    }

    public void selectMetadataDiscovery() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.METADATA_DISCOVERY_DROPDOWN)).selectByVisibleText(Literals.WebApp.METADATA_DISCOVERY.label);
    }

    public void selectFAPIProfile() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.FAPI_PROFILE_DROPDOWN)).selectByVisibleText(Literals.WebApp.FAPI_PROFILE.label);
    }

    public void selectFAPIResponseMode() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.FAPI_RESPONSE_DROPDOWN)).selectByVisibleText(Literals.WebApp.FAPI_RESPONSE_MODE.label);
    }

    public void verifyConfigureTestDisplayed() {
        Assert.assertTrue("Configure Test section is not displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CONFIGURE_TEST)).isDisplayed());
    }

    public void enterAlias() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.ALIAS_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.ALIAS_INPUT)).sendKeys(Literals.WebApp.ALIAS.label);
    }

    public void selectPublish() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.PUBLISH_DROPDOWN)).selectByVisibleText(Literals.WebApp.PUBLISH.label);
    }

    public void enterCredentialIssuerURL() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CREDENTIAL_ISSUER_URL_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CREDENTIAL_ISSUER_URL_INPUT)).sendKeys(Literals.WebApp.CREDENTIAL_ISSUER_URL.label);
    }

    public void enterCredentialConfID() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CREDENTIAL_CONF_ID_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CREDENTIAL_CONF_ID_INPUT)).sendKeys(Literals.WebApp.CREDENTIAL_CONF_ID.label);
    }

    public void enterCredentialIssuerMetadataURL() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CREDENTIAL_ISSUER_METADATA_URL_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CREDENTIAL_ISSUER_METADATA_URL_INPUT)).sendKeys(Literals.WebApp.CREDENTIAL_ISSUER_METADATA_URL.label);
    }

    public void enterAuthServerURLOverride() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.AUTH_SERVER_URL_OVERRIDE_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.AUTH_SERVER_URL_OVERRIDE_INPUT)).sendKeys(Literals.WebApp.AUTH_SERVER_URL_OVERRIDE.label);
    }

    public void enterClientID() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CLIENT_ID_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.CLIENT_ID_INPUT)).sendKeys(Literals.WebApp.CLIENT_ID.label);
    }

    public void selectDPoPSigningAlg() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.DPOP_SIGNING_ALG_DROPDOWN)).selectByVisibleText(Literals.WebApp.DPOP_SIGNING_ALG.label);
    }

    public void executeTestPlan() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.EXECUTE_TEST_PLAN_BTN)).click();
    }

    public void scrollUntilEndOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) test.webDriverFactory().getWebDriver();

        while (true) {
            long initialHeight = (Long) js.executeScript("return document.body.scrollHeight");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            long newHeight = (Long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == initialHeight) {
                break;
            }
        }
    }

    public void selectCredentialOfferParameter() {
        new Select(test.webDriverFactory().getWebDriver().findElement(ConformanceElements.CREDENTIAL_OFFER_PARAMETER_DROPDOWN)).selectByVisibleText(Literals.WebApp.CREDENTIAL_OFFER_PARAMETER.label);
    }

    public void enterJwks() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.JWKS_INPUT)).clear();
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.JWKS_INPUT)).sendKeys(Literals.WebApp.JWKS.label);
    }

    public void verifyTestIsPassed() {
        Assert.assertTrue("Test is not passed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.PASSED_TEST)).isDisplayed());

    }

    public void theUserClicksRunTest() {
        test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.RUN_TEST_BTN)).click();
    }

    public void verifyTestPlanResultsPageDisplayed() {
        Assert.assertTrue("Test plan results page is not displayed!", test.webDriverFactory().getWebWait().until(ExpectedConditions.presenceOfElementLocated(ConformanceElements.TEST_PLAN_RESULTS_PAGE)).isDisplayed());
    }
}