package eu.europa.eudi.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
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



    public static WebElement waitAndClick(By locator,
                                          AppiumDriver driver,
                                          long timeoutSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        WebElement target = null;

        // Try up to 3 times (soft refresh between attempts)
        for (int attempt = 1; attempt <= 3; attempt++) {

            try {
                // Wait only for existence (visibility is unreliable on iOS)
                target = wait.until(d -> {
                    try {
                        return d.findElement(locator);
                    } catch (Exception ignored) {
                        return null;
                    }
                });

                // Try native click
                target.click();
                return target; // SUCCESS

            } catch (Exception clickError) {
                // --- REFRESH LOGIC ---
                try {
                    Thread.sleep(500);  // allow UI to stabilize
                } catch (InterruptedException ignored) {}

                // Try to re-find element after small refresh
                try {
                    target = driver.findElement(locator);
                } catch (Exception ignored) {
                    // ignore, next retry will handle it
                }
            }
        }

        // Final fallback: tap the center of the element
        if (target != null) {
            tapCenter(driver, target);
            return target;
        }

        throw new TimeoutException("Element not clickable after retries: " + locator);
    }

    private static void tapCenter(AppiumDriver driver, WebElement target) {
        Point p = target.getLocation();
        Dimension s = target.getSize();

        int x = p.getX() + s.getWidth() / 2;
        int y = p.getY() + s.getHeight() / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.ordinal()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.ordinal()));

        driver.perform(Collections.singletonList(tap));
    }

}
