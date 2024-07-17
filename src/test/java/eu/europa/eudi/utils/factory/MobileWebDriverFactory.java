package eu.europa.eudi.utils.factory;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
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
        caps2.setCapability("deviceName", "Pixel_6_API_33_1");
        caps2.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps2.setCapability("platformName", "Android");
        caps2.setCapability("platformVersion", "13.0");
        caps2.setCapability("automationName", "UiAutomator2");
        caps2.setCapability("appium:uiautomator2ServerLaunchTimeout", 200000);
        caps2.setCapability("appium:uiautomator2ServerInstallTimeout", 200000);
        caps2.setCapability("appium:androidInstallTimeout", 200000);
        caps2.setCapability("appium:adbExecTimeout", 200000);
        caps2.setCapability("enforceXPath1", true);
//        caps2.setCapability("deviceName", test.envDataConfig().getAppiumAndroidDeviceName());
//        caps2.setCapability("udid", test.envDataConfig().getAppiumAndroidUdid());
//        caps2.setCapability("platformName", test.envDataConfig().getAppiumAndroidPlatformName());
//        caps2.setCapability("platformVersion", test.envDataConfig().getAppiumAndroidPlatformVersion());
//        caps2.setCapability("automationName", test.envDataConfig().getAppiumAndroidAutomationName());
        caps2.setCapability("skipUnlock", "true");
        caps2.setCapability("appPackage", test.envDataConfig().getAppiumAndroidAppPackage());
        caps2.setCapability("appActivity", test.envDataConfig().getAppiumAndroidAppActivity());
        caps2.setCapability("noReset", noReset);
        caps2.setCapability("fullReset", "false");
        caps2.setCapability("app", apkPath2.getAbsolutePath());
        caps2.setCapability("allowInsecure", "adb_shell");

        try {
            androidDriver = new AndroidDriver(new URL(test.envDataConfig().getAppiumUrl()), caps2);
            wait = new WebDriverWait(androidDriver, Duration.ofSeconds(80));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        if(test.envDataConfig().getAppiumRecording()) {
            androidDriver.startRecordingScreen();}
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
            iosDriver = new IOSDriver(new URL(test.envDataConfig().getAppiumUrl()), caps1);
            wait = new WebDriverWait(iosDriver,     Duration.ofSeconds(80));
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

    public void quitDriverAndroid(){
        if (androidDriver != null) {
            if(test.envDataConfig().getAppiumRecording()) {
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
                Path path = Paths.get(System.getProperty("user.dir"), "target/recordings/"+ test.getSystemOperation() + "_" +test.getScenario().getName() + "_" +
                        formattedTimestamp +  ".mp4");
                try {
                    Files.write(path, data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            androidDriver.quit();
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