package eu.europa.eudi.utils;

import eu.europa.eudi.stepdefs.TestHooks;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WalletActionsUtils {

    private static TestSetup getTest() {
        TestSetup currentTest = TestHooks.getTest();
        if (currentTest == null) {
            throw new RuntimeException("CRITICAL: TestSetup is null. TestHooks.getTest() failed to provide a session.");
        }
        return currentTest;
    }

    public static void safeClick(By locator) {
        for (int i = 0; i < 3; i++) {
            try {
                getTest().mobileWebDriverFactory().getWait()
                        .until(ExpectedConditions.elementToBeClickable(locator))
                        .click();
                return;
            } catch (StaleElementReferenceException e) {
                // retry
            }
        }

        throw new RuntimeException("Could not click element: " + locator);
    }

    public static Set<String> collectAllTexts(AppiumDriver driver, boolean isAndroid, int maxScrolls) {

        Set<String> allTexts = new HashSet<>();

        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.65);
        int endY = (int) (size.height * 0.35);

        String lastPageSource = "";
        int noChangeCounter = 0;

        for (int i = 0; i < maxScrolls; i++) {

            String pageSource = driver.getPageSource();

            assert pageSource != null;
            if (pageSource.equals(lastPageSource)) {
                noChangeCounter++;
            } else {
                noChangeCounter = 0;
            }

            lastPageSource = pageSource;

            extractTexts(pageSource, allTexts, isAndroid);

            if (noChangeCounter >= 2) break;

            MobileActionsUtils.scrollFast(driver, startX, startY, endY);
        }

        return allTexts;
    }

    public static void extractTexts(String pageSource, Set<String> allTexts, boolean isAndroid) {

        Pattern pattern;

        if (isAndroid) {
            pattern = Pattern.compile("text=\"(.*?)\"");
        } else {
            pattern = Pattern.compile("label=\"(.*?)\"");
        }

        Matcher matcher = pattern.matcher(pageSource);

        while (matcher.find()) {
            String txt = matcher.group(1).trim();
            if (!txt.isEmpty()) {
                allTexts.add(txt);
            }
        }
    }
}
