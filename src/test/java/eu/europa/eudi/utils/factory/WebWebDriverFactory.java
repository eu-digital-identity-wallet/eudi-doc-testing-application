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

        // macOS Chrome location
        if ("githubactions".equalsIgnoreCase(
                System.getProperty("ci.environment"))) {

            options.setBinary(
                    "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
            );

            System.out.println(
                    "Running ChromeDriver on GitHub Actions macOS"
            );

            System.out.println(
                    "Chrome binary: /Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
            );

        }
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
