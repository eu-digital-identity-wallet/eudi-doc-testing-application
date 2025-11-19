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

        long endTime = System.currentTimeMillis() + timeoutSeconds * 1000L;

        while (System.currentTimeMillis() < endTime) {
            try {
                // Try finding text
                WebElement el = driver.findElement(locator);
                String actual = el.getText().trim();

                System.out.println("DEBUG TEXT: [" + actual + "] EXPECTED: [" + expectedText + "]");

                if (expectedText.equals(actual)) {
                    return el;  // SUCCESS 🎉
                }

            } catch (Exception ignored) {}

            // --- If text not found, refresh WebView ---
            try {
                System.out.println("🔄 Refreshing WebView...");
                driver.navigate().refresh();
            } catch (Exception ignored) {}

            // wait a bit before trying again
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        }

        throw new TimeoutException("Text '" + expectedText + "' not found after refresh attempts");
    }



    public static WebElement waitAndClick(By locator,
                                          AppiumDriver driver,
                                          long timeoutSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        WebElement target = wait.until(d -> {
            try {
                WebElement el = d.findElement(locator);

                if (!el.isDisplayed()) return null;   // not visible yet

                // Get element bounds (Compose sometimes returns temporary 0,0)
                Point p = el.getLocation();
                Dimension s = el.getSize();

                if (s.getHeight() == 0 || s.getWidth() == 0) return null; // still rendering

                return el;
            } catch (Exception ignored) {
                return null;
            }
        });

        // Now actually click (more reliable than expectedConditions)
        try {
            target.click();   // use native click first
        } catch (Exception e) {
            // Fallback to your tapAction
            tapCenter(driver, target);
        }

        return target;
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
