package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MobileWebDriverFactory {
    TestSetup test;
    boolean noReset;
    WebDriverWait wait;
    EnvDataConfig envDataConfig;
    public AndroidDriver androidDriver;
    public IOSDriver iosDriver;
    public UiAutomator2Options options;

    private String sessionId;

    public MobileWebDriverFactory(TestSetup test, boolean noReset) {
        this.test = test;
        this.noReset = noReset;
        this.envDataConfig = new EnvDataConfig();
    }

    public void startAndroidDriverSession() throws MalformedURLException {
        String env = envDataConfig.getExecutionEnvironment();
        String envCI = envDataConfig.getExecutionCIEnvironment();
        System.out.println("Running environment: " + env);
        try {
            if (env.equalsIgnoreCase("browserstack")) {
                String appUrl = System.getenv("BROWSERSTACK_APP_URL");
                // --- BrowserStack setup ---
                UiAutomator2Options options = new UiAutomator2Options();
//                if (envCI.equalsIgnoreCase("githubactions")) {
//                    options.setCapability("appium:app", appUrl);
//                }else{
                options.setCapability("appium:app", envDataConfig.getAppiumBrowserstackAndroidAppUrl());
                //  }
                options.setCapability("appium:deviceName", envDataConfig.getAppiumBrowserstackAndroidDeviceName());
                options.setCapability("appium:platformVersion", envDataConfig.getAppiumBrowserstackAndroidPlatformVersion());
                options.setCapability("browserstack.interactiveDebugging", envDataConfig.getAppiumBrowserstackInteractiveDebugging());
                options.setCapability("automationName", envDataConfig.getAppiumAndroidAutomationName());
                options.setCapability("browserstack.debug", true);
                options.setCapability("browserstack.deviceLogs", true);
                options.setCapability("autoRotate", true);
                options.setCapability("browserstack.video", true);
                options.setCapability("browserstack.appiumLogs", false);
                options.setCapability("orientation", "PORTRAIT");
                options.setCapability("appium:disableIdLocatorAutocompletion", true);
                options.setCapability("appium:disableSuppressAccessibilityService", false);
                options.setCapability("autoGrantPermissions", true);
                options.setCapability("disableWindowAnimation", true);
                options.setCapability("newCommandTimeout", 300);
                Map<String, Object> appiumSettings = new HashMap<>();
                appiumSettings.put("allowInvisibleElements", true);
                options.setCapability("appium:settings", appiumSettings);
                String featureName = test.getScenario().getUri().getPath()
                        .substring(test.getScenario().getUri().getPath().lastIndexOf('/') + 1)
                        .replace(".feature", "");
                options.setCapability("name", featureName + " - Android Test");
                options.setCapability("feature_name", featureName); // used for logs mapping
                options.setCapability("sessionName", featureName);  // fallback key also recognized by BS
                try {
                    if (envCI.equalsIgnoreCase("githubactions")) {
                        String username = System.getenv("BROWSERSTACK_USERNAME");
                        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
                        androidDriver = new AndroidDriver(new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", username, accessKey)), options);
                    }else{
                        androidDriver = new AndroidDriver(
                                new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", envDataConfig.getAppiumBrowserstackGeneralUsername(), envDataConfig.getAppiumBrowserstackGeneralAccesskey())), options);
                    }

                    wait = new WebDriverWait(androidDriver, Duration.ofSeconds(envDataConfig.getAppiumLongWaitInSeconds()));
                    this.sessionId = ((RemoteWebDriver) androidDriver).getSessionId().toString();
                    System.out.println("Session ID: " + this.sessionId);

                } catch (Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            }

            else {
                // --- Real device setup ---
                File apkPath = new File("src/test/resources/app/androidApp.apk");

                UiAutomator2Options caps = new UiAutomator2Options();
                caps.setCapability("deviceName", envDataConfig.getAppiumAndroidDeviceName());
                caps.setCapability("udid", envDataConfig.getAppiumAndroidUdid());
                caps.setCapability("platformName", envDataConfig.getAppiumAndroidPlatformName());
                caps.setCapability("platformVersion", envDataConfig.getAppiumAndroidPlatformVersion());
                caps.setCapability("automationName", envDataConfig.getAppiumAndroidAutomationName());
                caps.setCapability("skipUnlock", true);
                caps.setCapability("appPackage", envDataConfig.getAppiumAndroidAppPackage());
                caps.setCapability("appActivity", envDataConfig.getAppiumAndroidAppActivity());
                caps.setCapability("noReset", noReset);
                caps.setCapability("fullReset", false);
                caps.setCapability("app", apkPath.getAbsolutePath());
                caps.setCapability("enableLogcatLogging", true);
                caps.setCapability("autoGrantPermissions", true);
                caps.setCapability("newCommandTimeout", 120);
                caps.setCapability("waitForIdleTimeout", 150);
                caps.setCapability("disableWindowAnimation", true);
                caps.setCapability("browserstack.interactiveDebugging", envDataConfig.getAppiumBrowserstackInteractiveDebugging());


                try {
                    androidDriver = new AndroidDriver(new URL(test.envDataConfig().getAppiumUrlAndroid()), caps);
                    wait = new WebDriverWait(androidDriver, Duration.ofSeconds(test.envDataConfig().getAppiumLongWaitInSeconds()));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void startIosDriverSession() throws MalformedURLException {
        String env = envDataConfig.getExecutionEnvironment();
        String envCI = envDataConfig.getExecutionCIEnvironment();
        System.out.println("Running environment: " + env);

        try {
            if (env.equalsIgnoreCase("browserstack")) {
                // --- BrowserStack setup ---
                XCUITestOptions options = new XCUITestOptions();
                options.setCapability("appium:app", envDataConfig.getAppiumBrowserstackIosAppUrl());
                options.setCapability("appium:deviceName", envDataConfig.getAppiumBrowserstackIosDeviceName());
                options.setCapability("appium:platformVersion", envDataConfig.getAppiumBrowserstackIosPlatformVersion());
                options.setCapability("browserstack.interactiveDebugging", envDataConfig.getAppiumBrowserstackInteractiveDebugging());
                options.setCapability("appium:automationName", envDataConfig.getAppiumBrowserstackIosAutomationName());
                options.setCapability("autoAcceptAlerts", true);
                options.setCapability("browserstack.debug", true);
                options.setCapability("browserstack.deviceLogs", true);
                options.setCapability("browserstack.video", true);
                options.setCapability("browserstack.appiumLogs", false);
                String featureName = test.getScenario().getUri().getPath()
                        .substring(test.getScenario().getUri().getPath().lastIndexOf('/') + 1)
                        .replace(".feature", "");
                options.setCapability("name", featureName + " - iOS Test");
                options.setCapability("feature_name", featureName);
                options.setCapability("sessionName", featureName);
                options.setCapability("includeSafariInWebviews", true);
                options.setCapability("waitForQuiescence", true);

                try {
                    if (envCI.equalsIgnoreCase("githubactions")) {
                        String username = System.getenv("BROWSERSTACK_USERNAME");
                        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
                        iosDriver = new IOSDriver(new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", username, accessKey)), options);
                    }else{
                        iosDriver = new IOSDriver(
                                new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", envDataConfig.getAppiumBrowserstackGeneralUsername(), envDataConfig.getAppiumBrowserstackGeneralAccesskey())), options);
                    }

                    wait = new WebDriverWait(iosDriver, Duration.ofSeconds(envDataConfig.getAppiumLongWaitInSeconds()));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
                this.sessionId = ((RemoteWebDriver) iosDriver).getSessionId().toString();
                System.out.println("Session ID: " + this.sessionId);

            } else {
                // --- Real device setup ---
                envDataConfig = new EnvDataConfig();
                File apkPath1 = new File("src/test/resources/app/iosApp.ipa");
                apkPath1.getAbsolutePath();
                XCUITestOptions caps1 = new XCUITestOptions();
                caps1.setCapability("deviceName", test.envDataConfig().getAppiumIosDeviceName());
                caps1.setCapability("platformName", test.envDataConfig().getAppiumIosPlatformName());
                caps1.setCapability("platformVersion", test.envDataConfig().getAppiumIosPlatformVersion()); // your iOS version
                caps1.setCapability("udid", test.envDataConfig().getAppiumIosUdid()); // your device udid
                caps1.setCapability("automationName", test.envDataConfig().getAppiumIosAutomationName());
                caps1.setCapability("bundleId", test.envDataConfig().getAppiumIosBundleId()); // your app's bundle id
                caps1.setCapability("noReset", noReset);
                caps1.setCapability("fullReset", false);
                caps1.setCapability("app", apkPath1.getAbsolutePath());
                caps1.setCapability("autoAcceptAlerts", true);
                caps1.setCapability("usePrebuiltWDA", true);
                caps1.setCapability("waitForIdleTimeout", 150);
                caps1.setCapability("autoDismissAlerts", true);
                caps1.setCapability("newCommandTimeout", 180);
                caps1.setCapability("includeNonModalElements", true);
                caps1.setCapability("connectHardwareKeyboard", false);

                try {
                    iosDriver = new IOSDriver(new URL(test.envDataConfig().getAppiumUrlIos()), caps1);
                    wait = new WebDriverWait(iosDriver, Duration.ofSeconds(80));
                } catch (Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriverAndroid() {
        return androidDriver;
    }

    public WebDriver getDriverIos() {
        return iosDriver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void quitDriverIos() {
        if (iosDriver != null) {
            iosDriver.quit();
            iosDriver = null; // Important: clear the reference
        }
    }

    public void quitDriverAndroid() {
        if (androidDriver != null) {
            androidDriver.quit();
            androidDriver = null; // Important: clear the reference
        }
    }


}