package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverFactory {
    TestSetup test;
    EnvDataConfig envDataConfig;
    @Getter
    public WebDriver webDriver;
    @Getter
    private WebDriverWait webWait;

    public WebDriverFactory(TestSetup test) {
        this.test = test;
    }

    public void startWebDriverSession() {
        envDataConfig = new EnvDataConfig();
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--headless=new"); // or "--headless" for older Chrome
            options.addArguments("--window-size=1920,1080"); // ensure consistent layout
            options.addArguments("--user-data-dir=/tmp/chrome-" + System.currentTimeMillis());
            options.addArguments("--disable-gpu");
            webDriver = new ChromeDriver(options);
            webWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriver.manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
            webDriver = null;
        }
    }

    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}