package eu.europa.eudi.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitsUtils {
    public static WebElement waitForExactText(By locator,
                                              String expectedText,
                                              AppiumDriver driver,
                                              int timeoutSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        ExpectedCondition<WebElement> textMatches = new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    WebElement element = driver.findElement(locator);
                    String actualText = element.getText().trim();

                    if (actualText.toLowerCase().contains(expectedText.toLowerCase())) {
                        return element;
                    }
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                }
                return null;
            }

            @Override
            public String toString() {
                return "element with locator '" + locator + "' to have exact text '" + expectedText + "'";
            }
        };

        try {
            return wait.until(textMatches);
        } catch (TimeoutException e) {
            throw new TimeoutException("Timeout: Text '" + expectedText + "' not found for locator '" + locator + "' within " + timeoutSeconds + " seconds.", e);
        }
    }

}
