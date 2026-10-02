package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

public class WebWebDriverFactory {
    private WebDriver webDriver;
    private WebDriverWait wait;

    public WebWebDriverFactory(TestSetup test) {
    }

    public void startWebDriverSession() {

        ChromeOptions options = new ChromeOptions();

        if ("githubactions".equalsIgnoreCase(
                System.getProperty("ci.environment"))) {

            String chromePath =
                    "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";

            options.setBinary(chromePath);

            System.out.println("=== GitHub Actions Chrome ===");
            System.out.println("Chrome binary: " + chromePath);
        }

        /*
         * Do NOT configure ChromeDriver manually.
         * Selenium Manager will resolve the compatible driver.
         */
        webDriver = new ChromeDriver(options);

        wait = new WebDriverWait(
                webDriver,
                Duration.ofSeconds(30)
        );

        System.out.println("Chrome WebDriver session started successfully.");
    }

    public WebDriver getDriverWeb() {
        return webDriver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void quitDriverWeb() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
