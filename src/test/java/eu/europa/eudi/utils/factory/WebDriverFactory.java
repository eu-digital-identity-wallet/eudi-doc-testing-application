package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import lombok.Getter;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Set;

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
//            options.addArguments("--remote-allow-origins=*");
//            options.addArguments("--disable-web-security");
//            options.addArguments("--no-sandbox");
//            options.addArguments("--disable-dev-shm-usage");
//            options.addArguments("--disable-blink-features=AutomationControlled");
//            options.addArguments("--window-size=1920,1080"); // ensure responsive layout
//            options.addArguments("--force-device-scale-factor=1"); // helps with visibility
//            options.addArguments("--user-data-dir=/tmp/chrome-" + System.currentTimeMillis());
//            options.addArguments("--disable-gpu");
//
//
//            webDriver = new ChromeDriver(options);
//            webWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-web-security");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-blink-features=AutomationControlled");
//            options.addArguments("--headless=new");           // headless required for CI
            options.addArguments("--window-size=1920,1080");  // ensures proper layout
            options.addArguments("--disable-gpu");           // required for headless
            options.addArguments("--disable-software-rasterizer");
            options.addArguments("--force-device-scale-factor=1");

// Optional: prevent websites from detecting automation
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            webDriver = new ChromeDriver(options);
            webWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
            webDriver.manage().window().maximize();

//            File cookieFile = new File("gmail_cookies.data");
//
//            // Step 1: Always navigate to Gmail first
//            webDriver.get("https://myaccount.google.com");
//
//            if (cookieFile.exists()) {
//                System.out.println("Loading saved Gmail cookies...");
//
//                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cookieFile));
//                Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
//                ois.close();
//
//                String currentDomain = "accounts.google.com";
//
//                // Step 2: Inject only cookies that match mail.google.com
//                for (Cookie cookie : cookies) {
//                    if (cookie.getDomain().contains(currentDomain)) {
//                        webDriver.manage().addCookie(cookie);
//                    } else {
//                        System.out.println("Skipping cookie for different domain: " + cookie.getDomain());
//                    }
//                }
//
//                // Step 3: Refresh Gmail to apply cookies
//                webDriver.navigate().refresh();
//                System.out.println("Logged into Gmail using saved cookies.");
//
//            } else {
//                System.out.println("No saved cookies found. Please log in manually.");
//
//                // Wait for manual login (2 minutes)
//                Thread.sleep(120000);
//
//                // After manual login, save cookies
//                Set<Cookie> cookies = webDriver.manage().getCookies();
//                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cookieFile));
//                oos.writeObject(cookies);
//                oos.close();
//
//                System.out.println("Cookies saved for future runs.");
//            }

        } catch (Exception e) {
            e.printStackTrace();
            webDriver = null;
        }
    }

    public void takeScreenshot(String filePath) {
        if (webDriver == null) return;

        try {
            File targetFile = new File(filePath);
            File parentDir = targetFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}