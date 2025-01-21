package eu.europa.eudi.utils.config;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.apache.http.HttpHeaders.TIMEOUT;

/**
 * The {@link EnvDataConfig} exposes all properties contained in env.properties derived from {@link ResourcesConfig} to the tests.
 */
public class EnvDataConfig {
    public static final String VERIFIER_URL = "verifier.url";
    public static final String CREDENTIAL_URL = "credential.url";
    public static final String OPENID_URL = "openid.url";
    public static final String URL = "app.url";
    public static final String HEADLESS_MODE = "HEADLESS.MODE";
    public static final String APPIUM_ANDROID_DEVICE_NAME = "appium.android.deviceName";
    public static final String APPIUM_ANDROID_PLATFORM_NAME = "appium.android.platformName";
    public static final String APPIUM_ANDROID_PLATFORM_VERSION = "appium.android.platformVersion";
    public static final String APPIUM_ANDROID_UDID = "appium.android.udid";
    public static final String APPIUM_ANDROID_AUTOMATION_NAME = "appium.android.automationName";
    public static final String APPIUM_ANDROID_APP_PACKAGE = "appium.android.appPackage";
    public static final String APPIUM_ANDROID_APP_ACTIVITY = "appium.android.appActivity";
    public static final String APPIUM_IOS_DEVICE_NAME = "appium.ios.deviceName";
    public static final String APPIUM_IOS_PLATFORM_NAME = "appium.ios.platformName";
    public static final String APPIUM_IOS_PLATFORM_VERSION = "appium.ios.platformVersion";
    public static final String APPIUM_IOS_UDID = "appium.ios.udid";
    public static final String APPIUM_IOS_AUTOMATION_NAME = "appium.ios.automationName";
    public static final String APPIUM_IOS_BUNDLE_ID = "appium.ios.bundleId";
    public static final String APPIUM_RECORDING = "appium.recording";
    public static final String APPIUM_SCREENSHOT = "appium.screenshot";
    public static final String APPIUM_URL_ANDROID = "appium.url.android";
    public static final String APPIUM_URL_IOS = "appium.url.ios";
    public static final String APPIUM_SHORT_WAIT_IN_MILLISECONDS = "appium.shortWaitInMilliseconds";
    public static final String APPIUM_MEDIUM_WAIT_IN_SECONDS = "appium.mediumWaitInSeconds";
    public static final String APPIUM_LONG_WAIT_IN_SECONDS = "appium.longWaitInSeconds";
    public static final String APP_ACTIVATION_CODE = "app.activationCode";
    public static final String BROWSER = "browser";
    public static final String DRIVER_REMOTE_URL = "driver.remote.url";
    public static final String WALLET_PIN = "wallet.pin";
    public static final String DRIVER_MODE = "driver.mode";
    private static final String TRANSACTION_CODE = "transaction.code";
    ResourcesConfig resourcesConfig;

    public EnvDataConfig() {
        resourcesConfig = new ResourcesConfig();
    }

    public String getAppiumIosDeviceName() {
        return getEnvProperties().getProperty(APPIUM_IOS_DEVICE_NAME);
    }

    public String getAppiumIosPlatformName() {
        return getEnvProperties().getProperty(APPIUM_IOS_PLATFORM_NAME);
    }
    public String getAppiumIosPlatformVersion() {
        return getEnvProperties().getProperty(APPIUM_IOS_PLATFORM_VERSION);
    }

    public String getAppiumIosUdid() {
        return getEnvProperties().getProperty(APPIUM_IOS_UDID);
    }

    public String getAppiumIosAutomationName() {
        return getEnvProperties().getProperty(APPIUM_IOS_AUTOMATION_NAME);
    }
    public String getAppiumIosBundleId() {
        return getEnvProperties().getProperty(APPIUM_IOS_BUNDLE_ID);
    }

    public String getAppiumAndroidDeviceName() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_DEVICE_NAME);
    }

    public String getAppiumAndroidPlatformName() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_PLATFORM_NAME);
    }
    public String getAppiumAndroidPlatformVersion() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_PLATFORM_VERSION);
    }

    public String getAppiumAndroidUdid() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_UDID);
    }

    public String getAppiumAndroidAutomationName() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_AUTOMATION_NAME);
    }
    public String getAppiumAndroidAppPackage() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_APP_PACKAGE);
    }
    public int getAppiumShortWaitInMilliseconds() {
        return Integer.parseInt(getEnvProperties().getProperty(APPIUM_SHORT_WAIT_IN_MILLISECONDS));
    }

    public int getAppiumLongWaitInSeconds() {
        return Integer.parseInt(getEnvProperties().getProperty(APPIUM_MEDIUM_WAIT_IN_SECONDS));
    }

    public Boolean getAppiumRecording() {
        return Boolean.parseBoolean(getEnvProperties().getProperty(APPIUM_RECORDING));
    }

    public Boolean getAppiumScreenshot() {
        return Boolean.parseBoolean(getEnvProperties().getProperty(APPIUM_SCREENSHOT));
    }

    public String getAppiumUrlAndroid() {
        return getEnvProperties().getProperty(APPIUM_URL_ANDROID);
    }

    public String getAppiumUrlIos() {
        return getEnvProperties().getProperty(APPIUM_URL_IOS);
    }

    public String getAppiumAndroidAppActivity() {
        return getEnvProperties().getProperty(APPIUM_ANDROID_APP_ACTIVITY);
    }
    private Properties getEnvProperties() {
        return readProperties(resourcesConfig.getEnvironmentPropertiesPath());
    }

    private static Properties readProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static String removeTrailingSlash(String url) {
        return StringUtils.removeEnd(url, "/");
    }
    public String getBrowser() {
        return getEnvProperties().getProperty(BROWSER);
    }

    public String getDriverRemoteURL() {
        return removeTrailingSlash(getEnvProperties().getProperty(DRIVER_REMOTE_URL));
    }

    public String getURL() {
        return getEnvProperties().getProperty(URL);
    }

    public String getURLVerifier() {
        return getEnvProperties().getProperty(VERIFIER_URL);
    }
    public String getURLCredential() {
        return getEnvProperties().getProperty(CREDENTIAL_URL);
    }
    public String getURLOpenId() {
        return getEnvProperties().getProperty(OPENID_URL);
    }

    public String getDriverMode() {
        return getEnvProperties().getProperty(DRIVER_MODE);
    }

    public Boolean getHeadlessMode() {
        return Boolean.parseBoolean(getEnvProperties().getProperty(HEADLESS_MODE));
    }

    public String getTimeout() {
        return getEnvProperties().getProperty(TIMEOUT);
    }

    public String getRemoteURL() {
        return removeTrailingSlash(getEnvProperties().getProperty(DRIVER_REMOTE_URL));
    }

    public String getPin() {
        return getEnvProperties().getProperty(WALLET_PIN);
    }
    public String getTransactionCode() {
        return getEnvProperties().getProperty(TRANSACTION_CODE);
    }

}
