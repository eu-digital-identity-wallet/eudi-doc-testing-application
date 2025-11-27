package eu.europa.eudi.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class WaitsUtils {
    public static WebElement waitForExactText(By locator,
                                              String expectedText,
                                              AndroidDriver driver,
                                              int timeoutSeconds) {

        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L * 5);

        while (System.currentTimeMillis() < endTime) {

            try {
                // Always get a fresh element
                WebElement el = driver.findElement(locator);
                String actual = el.getText().trim();

                System.out.println("DEBUG TEXT: [" + actual + "] EXPECTED: [" + expectedText + "]");

                if (actual.equalsIgnoreCase(expectedText)) {
                    // Return a fresh element reference
                    return driver.findElement(locator);
                }

            } catch (StaleElementReferenceException stale) {
                System.out.println("⚠️ Stale element detected — retrying...");
            } catch (Exception ignored) {
                // Other errors during wait are ignored and retried
            }

            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }

        throw new TimeoutException("Text '" + expectedText + "' not found within timeout");
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
