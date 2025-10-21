package eu.europa.eudi.utils.config;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.SKIP_UNLOCK;
import static org.apache.http.HttpHeaders.TIMEOUT;

/**
 * The {@link EnvDataConfig} exposes all properties contained in env.properties derived from {@link ResourcesConfig} to the tests.
 */
public class EnvDataConfig {
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
    public static final String APPIUM_URL_ANDROID = "appium.url.android";
    public static final String APPIUM_URL_IOS = "appium.url.ios";
    public static final String APPIUM_MEDIUM_WAIT_IN_SECONDS = "appium.mediumWaitInSeconds";
    public static final String WALLET_PIN = "wallet.pin";

    public static final String BROWSERSTACK_ANDROID_APP_URL = "browserstack.android.appUrl";
    public static final String BROWSERSTACK_ANDROID_DEVICE_NAME = "browserstack.android.deviceName";
    public static final String BROWSERSTACK_ANDROID_PLATFORM_VERSION = "browserstack.android.platformVersion";
    public static final String BROWSERSTACK_INTERACTIVE_DEBUGGING = "browserstack.interactiveDebugging";
    public static final String BROWSERSTACK_GENERAL_USERNAME = "browserstack.general.username";
    public static final String BROWSERSTACK_GENERAL_ACCESS_KEY = "browserstack.general.accessKey";
    public static final String GET_EXECUTION_ENVIRONMENT = "execution.environment";
    public static final String BROWSERSTACK_IOS_APP_URL = "browserstack.ios.appUrl";
    public static final String BROWSERSTACK_IOS_DEVICE_NAME = "browserstack.ios.deviceName";
    public static final String BROWSERSTACK_IOS_PLATFORM_VERSION = "browserstack.ios.platformVersion";
    public static final String BROWSERSTACK_IOS_AUTOMATION_NAME = "browserstack.ios.automationName";
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

    public int getAppiumLongWaitInSeconds() {
        return Integer.parseInt(getEnvProperties().getProperty(APPIUM_MEDIUM_WAIT_IN_SECONDS));
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

    public String getPin() {
        return getEnvProperties().getProperty(WALLET_PIN);
    }

    public String getAppiumBrowserstackAndroidAppUrl() {
        return getEnvProperties().getProperty(BROWSERSTACK_ANDROID_APP_URL);
    }

    public String getAppiumBrowserstackAndroidDeviceName() {
        return getEnvProperties().getProperty(BROWSERSTACK_ANDROID_DEVICE_NAME);
    }

    public String getAppiumBrowserstackAndroidPlatformVersion() {
        return getEnvProperties().getProperty(BROWSERSTACK_ANDROID_PLATFORM_VERSION);
    }

    public String getAppiumBrowserstackInteractiveDebugging() {
        return getEnvProperties().getProperty(BROWSERSTACK_INTERACTIVE_DEBUGGING);
    }
    public String getAppiumBrowserstackGeneralUsername() {
        return getEnvProperties().getProperty(BROWSERSTACK_GENERAL_USERNAME);
    }

    public String getAppiumBrowserstackGeneralAccesskey() {
        return getEnvProperties().getProperty(BROWSERSTACK_GENERAL_ACCESS_KEY);
    }

    public String getExecutionEnvironment() {
        return getEnvProperties().getProperty(GET_EXECUTION_ENVIRONMENT);
    }

    public String getAppiumBrowserstackIosAppUrl() {
        return getEnvProperties().getProperty(BROWSERSTACK_IOS_APP_URL);
    }

    public String getAppiumBrowserstackIosDeviceName() {
        return getEnvProperties().getProperty(BROWSERSTACK_IOS_DEVICE_NAME);
    }

    public String getAppiumBrowserstackIosPlatformVersion() {
        return getEnvProperties().getProperty(BROWSERSTACK_IOS_PLATFORM_VERSION);
    }

    public String getAppiumBrowserstackIosAutomationName() {
        return getEnvProperties().getProperty(BROWSERSTACK_IOS_AUTOMATION_NAME);

    }
}
