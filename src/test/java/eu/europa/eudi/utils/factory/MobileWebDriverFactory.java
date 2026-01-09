package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MobileWebDriverFactory {
    TestSetup test;
    boolean noReset;
    WebDriverWait wait;
    EnvDataConfig envDataConfig;
    public AndroidDriver androidDriver;
    public IOSDriver iosDriver;

    public UiAutomator2Options options;
    private Process logcatProcess;
    private Thread logcatThread;
    private String sessionId;


    public MobileWebDriverFactory(TestSetup test, boolean noReset) {
        this.test = test;
        this.noReset = noReset;
    }

    public void startAndroidDriverSession() throws MalformedURLException {
        envDataConfig = new EnvDataConfig();
        String env = envDataConfig.getExecutionEnvironment();
        String envCI = envDataConfig.getExecutionCIEnvironment();
        System.out.println("Running environment: " + env);
        try {
            if (env.equalsIgnoreCase("browserstack")) {
                String appUrl = System.getenv("BROWSERSTACK_APP_URL");
                // --- BrowserStack setup ---
                DesiredCapabilities options = new DesiredCapabilities();
                if (envCI.equalsIgnoreCase("githubactions")) {
                options.setCapability("appium:app", appUrl);
                }else{
                    options.setCapability("appium:app", envDataConfig.getAppiumBrowserstackAndroidAppUrl());
                }
                options.setCapability("appium:deviceName", envDataConfig.getAppiumBrowserstackAndroidDeviceName());
                options.setCapability("appium:platformVersion", envDataConfig.getAppiumBrowserstackAndroidPlatformVersion());
                options.setCapability("browserstack.interactiveDebugging", envDataConfig.getAppiumBrowserstackInteractiveDebugging());
                options.setCapability("automationName", envDataConfig.getAppiumAndroidAutomationName());
                options.setCapability("browserstack.debug", false);
                options.setCapability("browserstack.deviceLogs", true);
                options.setCapability("autoRotate", false);
                options.setCapability("orientation", "PORTRAIT");
                options.setCapability("newCommandTimeout", 320); // or longer for stability
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

                DesiredCapabilities caps = new DesiredCapabilities();
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
        envDataConfig = new EnvDataConfig();
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
                options.setCapability("browserstack.debug", false);
                options.setCapability("browserstack.deviceLogs", true);
                String featureName = test.getScenario().getUri().getPath()
                        .substring(test.getScenario().getUri().getPath().lastIndexOf('/') + 1)
                        .replace(".feature", "");
                options.setCapability("name", featureName + " - iOS Test");
                options.setCapability("feature_name", featureName);
                options.setCapability("sessionName", featureName);


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
                DesiredCapabilities caps1 = new DesiredCapabilities();
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
            try {
                Thread.sleep(1500); // wait 1.5s before next scenario starts
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public void quitDriverAndroid() {
        if (androidDriver != null) {
            androidDriver.quit();
            try {
                Thread.sleep(1500); // wait 1.5s before next scenario starts
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void startLogging(String featureDirPath, String featureName, String scenarioName, String platform) {
        envDataConfig = new EnvDataConfig();
        String env = envDataConfig.getExecutionEnvironment();
        if (env.equalsIgnoreCase("real")) {
            try {
                // Stop any previous logging
                stopLogging();
                //  Create a directory for the feature if it doesn't exist
                File featureDir = new File(featureDirPath + "/logs/ui");
                if (!featureDir.exists()) {
                    featureDir.mkdirs();
                }

                File newFile = new File(featureDir, featureName + ".txt");
                try {
                    if (newFile.createNewFile()) {
                        System.out.println("File created: " + newFile.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                    return;
                }

                if ("IOS".equalsIgnoreCase(platform)) {
                    logcatProcess = Runtime.getRuntime().exec("idevicesyslog");
                } else if ("ANDROID".equalsIgnoreCase(platform)) {
                    logcatProcess = Runtime.getRuntime().exec("adb logcat");
                } else if ("WEB".equalsIgnoreCase(platform)) {
                    // For web testing, we don't need device logging, just skip the process creation
                    logcatProcess = null;
                } else {
                    throw new IllegalArgumentException("Unsupported platform for logging: " + platform);
                }

                // Start a new thread to read logcat output and write to the log file
                if (logcatProcess != null) {
                    logcatThread = new Thread(() -> {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                             PrintWriter logWriter = new PrintWriter(new FileWriter(newFile))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                try {
                                    if (line.contains("@IOS and @automated")) {
                                        writeLog(line, "logs/ui" + featureName + "/" + scenarioName + ".txt");
                                    } else if (line.contains("@ANDROID and @automated")) {
                                        writeLog(line, "logs/ui" + featureName + "/" + scenarioName + ".txt");
                                    } else {
                                        writeLog(line, newFile.getPath());
                                    }
                                } catch (Exception e) {
                                    System.err.println("Error writing log line: " + e.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    logcatThread.start();
                } else {
                    // For web platform, just create an empty log file
                    try (PrintWriter logWriter = new PrintWriter(new FileWriter(newFile))) {
                        logWriter.println("Web test logging started for: " + scenarioName);
                    } catch (IOException e) {
                        System.err.println("Error creating web log file: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void writeLog(String line, String filePath) {
        try {
            File logFile = new File(filePath);
            logFile.getParentFile().mkdirs(); // Create directories if they don't exist
            try (PrintWriter logWriter = new PrintWriter(new FileWriter(logFile, true))) {
                logWriter.println(line); // Write logcat output to file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopLogging() {
        if (logcatProcess != null)
        {
            try {
                // Destroy the process
                logcatProcess.destroy();
                // Wait for the process to terminate gracefully (with a timeout)
                boolean terminated = logcatProcess.waitFor(2, TimeUnit.SECONDS);
                // If it didn't terminate, force it
                if (!terminated) {
                    logcatProcess.destroyForcibly();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while waiting for logcat process to terminate");
            } catch (Exception e)
            {
                System.err.println("Error stopping logcat process: " + e.getMessage());
            } finally {
                logcatProcess = null;
            }
        }// Then handle the thread
        if (logcatThread != null) {
            try {// Interrupt the thread
                logcatThread.interrupt();
                // Give the thread some time to finish its work
                logcatThread.join(3000);
                // Wait up to 3 seconds// If the thread is still alive after the timeout, log a warning
                if (logcatThread.isAlive()) {
                    System.err.println("Warning: Logging thread did not terminate within the timeout period");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // Preserve interrupt status
                System.err.println("Interrupted while waiting for logging thread to terminate");
            } catch (Exception e) {
                System.err.println("Error stopping logging thread: " + e.getMessage());
            } finally {
                logcatThread = null;
            }
        }
    }

}