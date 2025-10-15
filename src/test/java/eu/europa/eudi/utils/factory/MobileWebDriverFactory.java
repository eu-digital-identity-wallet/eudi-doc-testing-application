package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.Map;


public class MobileWebDriverFactory {
    TestSetup test;
    boolean noReset;
    WebDriverWait wait;
    EnvDataConfig envDataConfig;
    public AndroidDriver androidDriver;
    public IOSDriver iosDriver;
    private Process logcatProcess;
    private Thread logcatThread;
    private String logFilePath;
 
    public UiAutomator2Options options;
    public String userName;
    public String accessKey;
    public static Map<String, Object> browserStackYamlMap;
    public static final String USER_DIR = "user.dir";


    public MobileWebDriverFactory(TestSetup test, boolean noReset) {
        this.test = test;
        this.noReset = noReset;
    }

    public void startAndroidDriverSession() {
        envDataConfig = new EnvDataConfig();
            options = new UiAutomator2Options();
        String appUrl = System.getenv("BROWSERSTACK_APP_URL");
//        String username = System.getenv("BROWSERSTACK_USERNAME");
//        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
            String username = "foteinitheofilat_OrT9j5";
            String accessKey = "abnr8yzxsnUcB7XtssWJ";
            System.out.println("Username: " + username);
            System.out.println("AccessKey: " + accessKey);
//            options.setCapability("appium:app", appUrl);
            options.setCapability("appium:app", "bs://41139b1f29e9a8d4194c076940c544a7f71edcda");
            options.setCapability("appium:deviceName", "Samsung Galaxy S22 Ultra");
            options.setCapability("appium:platformVersion", "12.0");
            options.setCapability("browserstack.interactiveDebugging", "true");
            try{
                androidDriver = new AndroidDriver(new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", username, accessKey)), options);
                wait = new WebDriverWait(androidDriver, Duration.ofSeconds(envDataConfig.getAppiumLongWaitInSeconds()));
            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }
    }



//    public void startAndroidDriverSession() {
//        envDataConfig = new EnvDataConfig();
//        File apkPath2 = new File("src/test/resources/app/androidApp.apk");
//        apkPath2.getAbsolutePath();
//        DesiredCapabilities caps2 = new DesiredCapabilities();
//        caps2.setCapability("deviceName", test.envDataConfig().getAppiumAndroidDeviceName());
//        caps2.setCapability("udid", test.envDataConfig().getAppiumAndroidUdid());
//        caps2.setCapability("platformName", test.envDataConfig().getAppiumAndroidPlatformName());
//        caps2.setCapability("platformVersion", test.envDataConfig().getAppiumAndroidPlatformVersion());
//        caps2.setCapability("automationName", test.envDataConfig().getAppiumAndroidAutomationName());
//        caps2.setCapability("skipUnlock", "true");
//        caps2.setCapability("appPackage", test.envDataConfig().getAppiumAndroidAppPackage());
//        caps2.setCapability("appActivity", test.envDataConfig().getAppiumAndroidAppActivity());
//        caps2.setCapability("noReset", noReset);
//        caps2.setCapability("fullReset", "false");
//        caps2.setCapability("app", apkPath2.getAbsolutePath());
//        caps2.setCapability("enableLogcatLogging", true);
//        caps2.setCapability("autoGrantPermissions", true); // Δίνει αυτόματα permissions που ζητάει το app
//        caps2.setCapability("newCommandTimeout", 120); // Για να μην σπάει το session αν αργήσει κάπου
//        caps2.setCapability("disableWindowAnimation", true); // Μπορεί να βοηθήσει σε κάποιους emulators
//
//        try {
//            androidDriver = new AndroidDriver(new URL(test.envDataConfig().getAppiumUrlAndroid()), caps2);
//            wait = new WebDriverWait(androidDriver, Duration.ofSeconds(test.envDataConfig().getAppiumLongWaitInSeconds()));
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            e.printStackTrace();
//        }
//    }

    public void startLogging(String featureDirPath, String featureName, String scenarioName, String platform) {
        try {
            // Stop any previous logging
            stopLogging();

//             Create a directory for the feature if it doesn't exist
            File featureDir = new File( featureDirPath + "/logs/ui");
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
            }

//            // Start logcat process
//            logcatProcess = Runtime.getRuntime().exec("adb logcat");

            if ("IOS".equalsIgnoreCase(platform)) {
                logcatProcess = Runtime.getRuntime().exec("idevicesyslog");
            } else if ("ANDROID".equalsIgnoreCase(platform)) {
                logcatProcess = Runtime.getRuntime().exec("adb logcat");
            } else {
                throw new IllegalArgumentException("Unsupported platform for logging: " + platform);
            }

            // Start a new thread to read logcat output and write to the log file
            logcatThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                     PrintWriter logWriter = new PrintWriter(new FileWriter(newFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("@IOS and @automated")) {
                            writeLog(line, "logs/IOS/" + featureName + "/" + scenarioName + ".log");
                        } else if (line.contains("@ANDROID and @automated")) {
                            writeLog(line, "logs/ANDROID/" + featureName + "/" + scenarioName + ".log");
                        } else {
                            writeLog(line, newFile.getPath());
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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
        if (logcatProcess != null) {
            logcatProcess.destroy();
            logcatProcess = null;
        }
        if (logcatThread != null) {
            logcatThread.interrupt();
            logcatThread = null;
        }
    }

    private static void logProcessOutput(Process process) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = errorReader.readLine()) != null) {
            System.err.println(line);
        }
    }

    public void startIosDriverSession() {
        envDataConfig = new EnvDataConfig();
        String appUrl = System.getenv("BROWSERSTACK_APP_URL");
//        String username = System.getenv("BROWSERSTACK_USERNAME");
//        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        String username = "foteinitheofilat_OrT9j5";
        String accessKey = "abnr8yzxsnUcB7XtssWJ";
        System.out.println("Username: " + username);
        System.out.println("AccessKey: " + accessKey);
        XCUITestOptions options = new XCUITestOptions();
        options.setCapability("appium:app", "bs://a0fa684e4954d5c9f5126449fdbb1f4b6a924a15");
        options.setCapability("appium:deviceName", "iPhone 15 Pro");
        options.setCapability("appium:platformVersion", "17");
        options.setCapability("browserstack.interactiveDebugging", "true");
        options.setCapability("platformName", "iOS");
        options.setCapability("appium:automationName", "XCUITest");
        try{
            iosDriver = new IOSDriver(new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", username, accessKey)), options);
            wait = new WebDriverWait(iosDriver, Duration.ofSeconds(envDataConfig.getAppiumLongWaitInSeconds()));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public WebDriver getDriverAndroid() {
        return androidDriver;
    }

    public WebDriver getDriverIos() {
        return iosDriver;
    }

    public void quitDriverAndroid() {
        if (androidDriver != null) {
            // Stop method tracing
            // stopMethodTracing(test.envDataConfig().getAppiumAndroidAppPackage());
            String remoteFilePath = "/data/local/tmp/trace_file.trace";
            String localFilePath = "/trace_file.trace";
            // pullTraceFile(remoteFilePath, localFilePath);
        }
    }

    public void quitDriverIos() {
        if (iosDriver != null) {

            iosDriver.quit();
        }
    }

    private void startMethodTracing(String packageName) {
        try {
            Process process = Runtime.getRuntime().exec("adb shell am profile start " + packageName + " /data/local/tmp/trace_file.trace");
            logProcessOutput(process);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMethodTracing(String packageName) {
        try {
            Process process = Runtime.getRuntime().exec("adb shell am profile stop " + packageName);
            logProcessOutput(process);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pullTraceFile(String remoteFilePath, String localFilePath) {
        try {
            Process process = Runtime.getRuntime().exec("adb pull " + remoteFilePath + " " + localFilePath);
            logProcessOutput(process);
            System.out.println("Trace file pulled to: " + localFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    /**
//     * Περιμένει αξιόπιστα μέχρι να βρεθεί ένα WebView context και κάνει switch σε αυτό.
//     * Αυτή η μέθοδος ΑΠΑΙΤΕΙ ένα AppiumDriver instance για να λειτουργήσει.
//     *
//     */
//    public static void switchToWebView(WebDriver driver) {
//        System.out.println("Attempting to switch to WebView context...");
//        try {
//            for (int i = 0; i < 30; i++) { // Περιμένουμε μέχρι 15 δευτερόλεπτα
//                if (getDriverContexts(driver).size() > 1) {
//                    break;
//                }
//                Thread.sleep(500);
//            }
//
//            for (String contextHandle : getDriverContexts(driver)) {
//                if (contextHandle.contains("WEBVIEW")) {
//                    setDriverContext(driver, contextHandle);
//                    System.out.println("Switched successfully to context: " + contextHandle);
//                    return;
//                }
//            }
//            throw new IllegalStateException("No WebView context was found after waiting.");
//
//        } catch (Exception e) {
//            System.err.println("Failed to find or switch to a WebView context.");
//            e.printStackTrace();
//            throw new IllegalStateException("Could not switch to WebView context.", e);
//        }
//    }
//
//    private static Set<String> getDriverContexts(WebDriver driver) {
//        if (driver instanceof AndroidDriver) {
//            return ((AndroidDriver) driver).getContextHandles();
//        } else if (driver instanceof IOSDriver) {
//            return ((IOSDriver) driver).getContextHandles();
//        }
//        return null;
//    }
//
//    public void switchToNativeView() {
//        System.out.println("Attempting to switch back to Native context...");
//        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
//        try {
//            setDriverContext(driver, "NATIVE_APP");
//            System.out.println("Switched successfully to context: NATIVE_APP");
//        } catch (Exception e) {
//            System.err.println("Failed to switch back to NATIVE_APP context.");
//            e.printStackTrace();
//            throw new IllegalStateException("Could not switch back to NATIVE_APP context.", e);
//        }
//    }
//
//    private static void setDriverContext(WebDriver driver, String context) {
//        if (driver instanceof AndroidDriver) {
//            ((AndroidDriver) driver).context(context);
//        } else if (driver instanceof IOSDriver) {
//            ((IOSDriver) driver).context(context);
//        }
//    }
}
