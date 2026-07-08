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
import java.util.concurrent.TimeUnit;

public class MobileWebDriverFactory {
    TestSetup test;
    boolean noReset;
    WebDriverWait wait;
    EnvDataConfig envDataConfig;
    public AndroidDriver androidDriver;
    public IOSDriver iosDriver;
    private File currentLogFile;
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
        if (androidDriver != null) {
            androidDriver.quit();
            androidDriver = null; // Important: clear the reference
        }
    }

    public void quitDriverAndroid() {
        if (androidDriver != null) {
            androidDriver.quit();
            androidDriver = null; // Important: clear the reference
        }
    }

    public File getCurrentLogFile() {
        return currentLogFile;
    }

    public void startLogging(String featureDirPath,
                             String featureName,
                             String scenarioNumber,
                             String platform) {

        envDataConfig = new EnvDataConfig();

        try {

            stopLogging();

            String env = envDataConfig.getExecutionEnvironment();

            // ----------------------------
            // 1. Create SAME LOG FILE ALWAYS
            // ----------------------------
            File logDir = new File(
                    featureDirPath
                            + File.separator
                            + "logs"
                            + File.separator
                            + "ui"
            );

            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            File logFile = new File(
                    logDir,
                    featureName + "_" + scenarioNumber + ".log"
            );

            if (logFile.exists() && !logFile.delete()) {
                throw new IOException("Cannot delete old log file: " + logFile);
            }

            if (!logFile.createNewFile()) {
                throw new IOException("Cannot create log file: " + logFile);
            }

            // 🔥 CRITICAL FIX: ALWAYS SET THIS
            currentLogFile = logFile;

            System.out.println("Log file ready: " + logFile.getAbsolutePath());

            // ----------------------------
            // 2. BrowserStack MODE
            // ----------------------------
            if ("browserstack".equalsIgnoreCase(env)) {
                System.out.println("BrowserStack mode → skipping local logcat, file created");
                return;
            }

            // ----------------------------
            // 3. REAL DEVICES ONLY
            // ----------------------------
            if ("ANDROID".equalsIgnoreCase(platform)) {

                try {
                    Runtime.getRuntime().exec("adb logcat -c").waitFor();
                } catch (Exception e) {
                    System.err.println("adb clear failed: " + e.getMessage());
                }

                logcatProcess = Runtime.getRuntime().exec("adb logcat");

            } else if ("IOS".equalsIgnoreCase(platform)) {

                logcatProcess = Runtime.getRuntime().exec("idevicesyslog");

            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

            // ----------------------------
            // 4. LOG THREAD (REAL DEVICES ONLY)
            // ----------------------------
            logcatThread = new Thread(() -> {

                try (BufferedReader reader =
                             new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                     BufferedWriter writer =
                             new BufferedWriter(new FileWriter(currentLogFile, true))) {

                    String line;

                    while (!Thread.currentThread().isInterrupted()
                            && (line = reader.readLine()) != null) {

                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                    }

                } catch (Exception e) {
                    if (!Thread.currentThread().isInterrupted()) {
                        e.printStackTrace();
                    }
                }
            });

            logcatThread.setDaemon(true);
            logcatThread.start();

        } catch (Exception e) {
            e.printStackTrace();
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