package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebWebDriverFactory {
    private WebDriver webDriver;
    private WebDriverWait wait;

    public WebWebDriverFactory(TestSetup test) {
    }

//    public void startWebDriverSession() {
//        ChromeOptions options = new ChromeOptions();
//
//        webDriver = new ChromeDriver(options);
//        wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
//    }

    public void startWebDriverSession() {
        ChromeOptions options = new ChromeOptions();

        options.setBinary(
                "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
        );

        webDriver = new ChromeDriver(options);

        wait = new WebDriverWait(
                webDriver,
                Duration.ofSeconds(30)
        );

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
