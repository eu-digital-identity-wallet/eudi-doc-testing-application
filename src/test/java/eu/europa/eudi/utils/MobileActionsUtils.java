package eu.europa.eudi.utils;

import eu.europa.eudi.data.Literals;
import eu.europa.eudi.stepdefs.TestHooks;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.HasContext;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class MobileActionsUtils {
    private static TestSetup getTest() {
        TestSetup currentTest = TestHooks.getTest();
        if (currentTest == null) {
            throw new RuntimeException("CRITICAL: TestSetup is null. TestHooks.getTest() failed to provide a session.");
        }
        return currentTest;
    }
    public static void tapAction(WebElement myDigitalIDButton, boolean clickLeft) {
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

        ((AppiumDriver) getTest().mobileWebDriverFactory().getDriverAndroid())
                .perform(Collections.singletonList(tap));
    }

    public static void tapActionOffSet(WebElement element, int xOffset, int yOffset) {
        AppiumDriver driver;

        if (getTest().getSystemOperation().equals(Literals.General.ANDROID.label)) {
            driver = (AppiumDriver) getTest().mobileWebDriverFactory().getDriverAndroid();
        } else {
            driver = (AppiumDriver) getTest().mobileWebDriverFactory().getDriverIos();
        }

        Point location = element.getLocation();
        int x = location.getX() + xOffset;
        int y = location.getY() + yOffset;

        if (getTest().getSystemOperation().equals(Literals.General.ANDROID.label)) {
            int viewportTop = 75;
            y = Math.max(y, viewportTop + 1);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), x, y));

        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));  // FIXED

        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));    // FIXED

        driver.perform(Collections.singletonList(tap));
    }

    public static void tapActionWallet(WebElement element, boolean clickLeft) {
        Dimension size = element.getSize();
        if (clickLeft) {
            MobileActionsUtils.tapActionOffSet(element, 10, size.getHeight() / 2);
        } else {
            MobileActionsUtils.tapActionOffSet(element, size.getWidth() / 2, size.getHeight() / 2);
        }
    }

    public static void slowScroll() throws InterruptedException {
        if (getTest().getSystemOperation().equals(Literals.General.ANDROID.label)) {
            AndroidDriver driver = (AndroidDriver) getTest().mobileWebDriverFactory().getDriverAndroid();
            String originalContext = driver.getContext();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                if (!"NATIVE_APP".equals(originalContext)) {
                    driver.context("NATIVE_APP");
                    wait.until(d -> {
                        ((HasContext) d).getContext();
                        return false;
                    });
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

                while (!success) {
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
            IOSDriver driver = (IOSDriver) getTest().mobileWebDriverFactory().getDriverIos();
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

    public static void slowScrollUp() {
        if (getTest().getSystemOperation().equals(Literals.General.ANDROID.label)) {

            AndroidDriver driver = (AndroidDriver) getTest().mobileWebDriverFactory().getDriverAndroid();
            Dimension size = driver.manage().window().getSize();
            int width = size.width;
            int height = size.height;

            int x = width / 2;

            int startY = (int) (height * 0.25);
            int endY = (int) (height * 0.80);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(List.of(swipe));
        } else {
            IOSDriver driver = (IOSDriver) getTest().mobileWebDriverFactory().getDriverIos();

            Dimension size = driver.manage().window().getSize();
            int width = size.width;
            int height = size.height;

            int x = width / 2;

            int startY = (int) (height * 0.25); // Start near top
            int endY = (int) (height * 0.80);   // Move to bottom

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(List.of(swipe));
        }
    }

    public static void fastSwipe(AndroidDriver driver) {
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

    public static void scrollFast(AppiumDriver driver, int startX, int startY, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO,
                PointerInput.Origin.viewport(), startX, startY));

        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(180),
                PointerInput.Origin.viewport(), startX, endY));

        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}
