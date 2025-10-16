package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

//    public void startAndroidDriverSession() {
//        envDataConfig = new EnvDataConfig();
//            options = new UiAutomator2Options();
////        String appUrl = System.getenv("BROWSERSTACK_APP_URL");
////        String username = System.getenv("BROWSERSTACK_USERNAME");
////        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
//            String username = "foteinitheofilat_OrT9j5";
//            String accessKey = "abnr8yzxsnUcB7XtssWJ";
//            System.out.println("Username: " + username);
//            System.out.println("AccessKey: " + accessKey);
//            options.setCapability("appium:app", "bs://34d000d023218a158ae1030883d84f8cbe0abbf4");
//            options.setCapability("appium:deviceName", "Samsung Galaxy S22 Ultra");
//            options.setCapability("appium:platformVersion", "12.0");
//            options.setCapability("browserstack.interactiveDebugging", "true");
//            options.setCapability("browserName", "Chrome");
//              options.setCapability("browserstack.debug", "true");
//              options.setCapability("browserstack.networkLogs", "true");
//              options.setCapability("browserstack.deviceLogs", "true");
//            try{
//                androidDriver = new AndroidDriver(new URL(String.format("https://%s:%s@hub.browserstack.com/wd/hub", username, accessKey)), options);
//                wait = new WebDriverWait(androidDriver, Duration.ofSeconds(envDataConfig.getAppiumLongWaitInSeconds()));
//            } catch (Exception e) {
//                System.out.println(e.toString());
//                e.printStackTrace();
//            }
//    }


    public void startAndroidDriverSession() {
        envDataConfig = new EnvDataConfig();
        File apkPath2 = new File("src/test/resources/app/androidApp.apk");
        apkPath2.getAbsolutePath();
        DesiredCapabilities caps2 = new DesiredCapabilities();
        caps2.setCapability("deviceName", test.envDataConfig().getAppiumAndroidDeviceName());
        caps2.setCapability("udid", test.envDataConfig().getAppiumAndroidUdid());
        caps2.setCapability("platformName", test.envDataConfig().getAppiumAndroidPlatformName());
        caps2.setCapability("platformVersion", test.envDataConfig().getAppiumAndroidPlatformVersion());
        caps2.setCapability("automationName", test.envDataConfig().getAppiumAndroidAutomationName());
        caps2.setCapability("skipUnlock", "true");
        caps2.setCapability("appPackage", test.envDataConfig().getAppiumAndroidAppPackage());
        caps2.setCapability("appActivity", test.envDataConfig().getAppiumAndroidAppActivity());
        caps2.setCapability("noReset", false);
        caps2.setCapability("fullReset", false);
        caps2.setCapability("app", apkPath2.getAbsolutePath());
        caps2.setCapability("enableLogcatLogging", true);
        caps2.setCapability("autoGrantPermissions", true);
        caps2.setCapability("newCommandTimeout", 120);
        caps2.setCapability("disableWindowAnimation", true);
        caps2.setCapability("waitForIdleTimeout", 900);      //Prevents UI hang during transitions
        caps2.setCapability("ignoreUnimportantViews", false); // Optional: makes hierarchy cleaner/faster


        try {
            androidDriver = new AndroidDriver(new URL(test.envDataConfig().getAppiumUrlAndroid()), caps2);
            wait = new WebDriverWait(androidDriver, Duration.ofSeconds(test.envDataConfig().getAppiumLongWaitInSeconds()));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

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
                return;
            }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void startLogging(String featureDirPath, String featureName, String scenarioName, String platform) throws IOException {
//        AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
//        String sessionId = driver.getSessionId().toString();
//
//        String logUrl = "https://api.browserstack.com/app-automate/sessions/" + sessionId + ".json";
//        String auth = "foteinitheofilat_OrT9j5:abnr8yzxsnUcB7XtssWJ"; // replace with actual credentials
//        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
//
//        HttpURLConnection connection = (HttpURLConnection) new URL(logUrl).openConnection();
//        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        StringBuilder response = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            response.append(line).append("\n");
//        }
//        reader.close();
//
//        // Write logs to file named after feature
//        Files.write(Paths.get("logs/" + featureName + ".txt"), response.toString().getBytes());
//
//    }



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

    public void startIosDriverSession() {
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
        caps1.setCapability("waitForIdleTimeout", 100);
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
            androidDriver.quit();
            try {

                Thread.sleep(1500); // wait 1.5s before next scenario starts

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

            }

        }
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
}