package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class WebWebDriverFactory {
    private WebDriver webDriver;
    private WebDriverWait wait;

    public WebWebDriverFactory(TestSetup test) {
    }

    public void startWebDriverSession() {
        if (Objects.equals(System.getProperty("ci.environment"), "githubactions")){
        ChromeOptions options = new ChromeOptions();
        // Let Selenium Manager resolve the matching ChromeDriver for the installed Chrome version.
        // (WebDriverManager pre-downloaded a driver that could mismatch the runner's Chrome,
        //  causing "This version of ChromeDriver only supports Chrome version NNN".)
        options.setBrowserVersion("stable");

        webDriver = new ChromeDriver(options);

        wait = new WebDriverWait(
                webDriver,
                Duration.ofSeconds(30)
        );
    }else{
        ChromeOptions options = new ChromeOptions();

        webDriver = new ChromeDriver(options);
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

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
