package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

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

    public MobileWebDriverFactory(TestSetup test, boolean noReset) {
        this.test = test;
        this.noReset = noReset;
    }

    public void startAndroidDriverSession() {
        envDataConfig = new EnvDataConfig();
        File apkPath2 = new File("src/test/resources/app/androidAppDebug.apk");
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
        caps2.setCapability("noReset", noReset);
        caps2.setCapability("fullReset", "false");
        caps2.setCapability("app", apkPath2.getAbsolutePath());
        caps2.setCapability("allowInsecure", "adb_shell");
        caps2.setCapability("enableLogcatLogging", true);

        try {
            androidDriver = new AndroidDriver(new URL(test.envDataConfig().getAppiumUrlAndroid()), caps2);
            wait = new WebDriverWait(androidDriver, Duration.ofSeconds(test.envDataConfig().getAppiumLongWaitInSeconds()));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    public void startLogging(String featureDirPath, String featureName, String scenarioName) {
        try {
            // Stop any previous logging
            stopLogging();

//             Create a directory for the feature if it doesn't exist
            File featureDir = new File( featureDirPath + "/logs");
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

            // Start logcat process
            logcatProcess = Runtime.getRuntime().exec("adb logcat");

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
        try {
            iosDriver = new IOSDriver(new URL(test.envDataConfig().getAppiumUrlIos()), caps1);
            wait = new WebDriverWait(iosDriver, Duration.ofSeconds(80));
            Process syslogProcess = Runtime.getRuntime().exec("idevicesyslog");
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(syslogProcess.getInputStream()));
                     PrintWriter logWriter = new PrintWriter(new FileWriter("ios_logs.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        logWriter.println(line);  // Write syslog output to file
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
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
            stopMethodTracing(test.envDataConfig().getAppiumAndroidAppPackage());
            String remoteFilePath = "/data/local/tmp/trace_file.trace";
            String localFilePath = "C:/Users/ftheofil/Projects/eu-digital-identity-walleteudi-doc-testing-application-internal/trace_file.trace";
            pullTraceFile(remoteFilePath, localFilePath);
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
}
