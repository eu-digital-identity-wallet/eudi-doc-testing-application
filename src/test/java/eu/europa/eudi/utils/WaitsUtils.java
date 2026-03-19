package eu.europa.eudi.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class WaitsUtils {
    public static WebElement waitForExactText(By locator,
                                              String expectedText,
                                              AppiumDriver driver,
                                              int timeoutSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        // Define a custom ExpectedCondition that returns a WebElement
        ExpectedCondition<WebElement> textMatches = new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    // Find the element on each check to avoid staleness
                    WebElement element = driver.findElement(locator);
                    String actualText = element.getText().trim();

                    // If text matches, return the element. WebDriverWait will stop waiting.
                    if (actualText.equalsIgnoreCase(expectedText)) {
                        return element;
                    }
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    // Element not found or stale, wait will continue polling.
                }
                // If text does not match or an exception occurred, return null.
                // WebDriverWait will continue polling until timeout.
                return null;
            }

            @Override
            public String toString() {
                return "element with locator '" + locator + "' to have exact text '" + expectedText + "'";
            }
        };

        // Use the custom condition with wait.until()
        try {
            return wait.until(textMatches);
        } catch (TimeoutException e) {
            // Throw a more informative exception
            throw new TimeoutException("Timeout: Text '" + expectedText + "' not found for locator '" + locator + "' within " + timeoutSeconds + " seconds.", e);
        }
    }

    public static WebElement waitVisibleThenClickable(By locator,
                                                      AndroidDriver driver,
                                                      int timeoutSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        // 1️⃣ Wait for visibility
        WebElement visibleElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        // 2️⃣ Wait for clickability
        WebElement clickableElement = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );

        return clickableElement;
    }
}
