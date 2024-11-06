package eu.europa.eudi.utils.factory;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.logging.LogEntry;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class MobileWebDriverFactory {
    TestSetup test;
    boolean noReset;
    WebDriverWait wait;
    EnvDataConfig envDataConfig;
    public AndroidDriver androidDriver;
    public IOSDriver iosDriver;

    public MobileWebDriverFactory(TestSetup test, boolean noReset) {
        this.test = test;
        this.noReset = noReset;
    }
    public void startAndroidDriverSession() {
        envDataConfig = new EnvDataConfig();
        File apkPath2 = new File("src/test/resources/app/androidApp.apk");
        apkPath2.getAbsolutePath();
        DesiredCapabilities caps2 = new DesiredCapabilities();
        caps2.setCapability("deviceName", test.envDataConfig().getAppiumAndroidDeviceName());
        caps2.setCapability("udid", test.envDataConfig().getAppiumAndroidUdid());
        caps2.setCapability("platformName", test.envDataConfig().getAppiumAndroidPlatformName());
        // caps2.setCapability("platformVersion", test.envDataConfig().getAppiumAndroidPlatformVersion());
        caps2.setCapability("automationName", test.envDataConfig().getAppiumAndroidAutomationName());
        caps2.setCapability("skipUnlock", "true");
        caps2.setCapability("appPackage", test.envDataConfig().getAppiumAndroidAppPackage());
        caps2.setCapability("appActivity", test.envDataConfig().getAppiumAndroidAppActivity());
        caps2.setCapability("noReset", noReset);
        caps2.setCapability("fullReset", "false");
        caps2.setCapability("app", apkPath2.getAbsolutePath());
        caps2.setCapability("allowInsecure", "adb_shell");
        caps2.setCapability("enableLogcatLogging", true);
        caps2.setCapability("adbExecTimeout", 2400000);
        caps2.setCapability("androidInstallTimeout", 2400000);
        caps2.setCapability("uiautomator2ServerInstallTimeout", 2400000);

        try {
            androidDriver = new AndroidDriver(new URL(test.envDataConfig().getAppiumUrlAndroid()), caps2);
            wait = new WebDriverWait(androidDriver, Duration.ofSeconds(test.envDataConfig().getAppiumLongWaitInSeconds()));
            Process logcatProcess = Runtime.getRuntime().exec("adb logcat");
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                     PrintWriter logWriter = new PrintWriter(new FileWriter("android_logs.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        logWriter.println(line);  // Write logcat output to file
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        if (test.envDataConfig().getAppiumRecording()) {
            androidDriver.startRecordingScreen();
        }

        if (test.envDataConfig().getAppiumScreenshot()) {
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String filename = timestamp + ".png";

            // Capture the screenshot and store it in a file
            File srcFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
            // Define the destination file path
            File destFile = new File("screenshots/" + filename);

            // Create the destination directory if it does not exist
            destFile.getParentFile().mkdirs();
            try {
                // Copy the screenshot to the destination file
                FileHandler.copy(srcFile, destFile);
                System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
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
        caps1.setCapability("autoAcceptAlerts",true);
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
            try {
                if (test.envDataConfig().getAppiumRecording()) {
                    String base64String = androidDriver.stopRecordingScreen();
                    byte[] data = Base64.getMimeDecoder().decode(base64String);

                    Path recordingsPath = Paths.get("target", "recordings");
                    try {
                        Files.createDirectories(recordingsPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    LocalDateTime timestamp = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                    String formattedTimestamp = timestamp.format(formatter);
                    Path path = Paths.get(System.getProperty("user.dir"), "target", "recordings",
                            test.getSystemOperation() + "_" + test.getScenario().getName() + "_" + formattedTimestamp + ".mp4");

                    try {
                        Files.write(path, data);
                        System.out.println("Recording saved: " + path.toAbsolutePath());
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save recording: " + e.getMessage(), e);
                    }
                }
            } catch (NoSuchSessionException e) {
                System.err.println("No session found when trying to stop recording: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("An error occurred while stopping the recording: " + e.getMessage());
            } finally {
                try {
                    androidDriver.quit();
                    System.out.println("Driver quit successfully.");
                } catch (NoSuchSessionException e) {
                    System.err.println("Session already closed: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("An error occurred while quitting the driver: " + e.getMessage());
                }
            }
        }
    }
    public void quitDriverIos() {
        if (iosDriver != null) {
            if(test.envDataConfig().getAppiumRecording()) {
                String base64String = iosDriver.stopRecordingScreen();
                byte[] data = Base64.getMimeDecoder().decode(base64String);
                Path recordingsPath = Paths.get("target", "recordings");
                try {
                    Files.createDirectories(recordingsPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LocalDateTime timestamp = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                String formattedTimestamp = timestamp.format(formatter);
                Path path = Paths.get(System.getProperty("user.dir"), "target/recordings/"+ test.getSystemOperation() + "_" +test.getScenario().getName() + "_" +
                        formattedTimestamp +  ".mp4");
                try {
                    Files.write(path, data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            iosDriver.quit();
        }
    }


}