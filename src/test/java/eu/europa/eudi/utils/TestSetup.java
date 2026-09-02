package eu.europa.eudi.utils;

import eu.europa.eudi.utils.config.EnvDataConfig;

import eu.europa.eudi.utils.factory.*;
import io.cucumber.java.Scenario;
import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestSetup {
    EnvDataConfig envDataConfig;
    MobileWebDriverFactory mobileWebDriverFactory;
    WebWebDriverFactory webWebDriverFactory;
    MobilePageObjectFactory mobilePageObjectFactory;
    WebPageObjectFactory webPageObjectFactory;
    String systemOperation;
    Scenario scenario;
    private String transactionCode;

    public TestSetup(boolean noReset, String systemOperation, Scenario scenario) {
        this.systemOperation = systemOperation;
        this.scenario = scenario;
        mobileWebDriverFactory = new MobileWebDriverFactory(TestSetup.this, noReset);
        mobilePageObjectFactory = new MobilePageObjectFactory(TestSetup.this);
        webWebDriverFactory = new WebWebDriverFactory(TestSetup.this);
        webPageObjectFactory = new WebPageObjectFactory(TestSetup.this);
    }

    public MobilePageObjectFactory mobile() {
        return mobilePageObjectFactory;
    }

    public MobileWebDriverFactory mobileWebDriverFactory() {
        return mobileWebDriverFactory;
    }

    public EnvDataConfig envDataConfig() {
        return (envDataConfig == null) ? envDataConfig = new EnvDataConfig() : envDataConfig;
    }

    public void startAndroidDriverSession() throws MalformedURLException {
        mobileWebDriverFactory.startAndroidDriverSession();
    }

    public void startIosDriverSession() throws MalformedURLException {
        mobileWebDriverFactory.startIosDriverSession();
    }

    public void stopAndroidDriverSession() {
        mobileWebDriverFactory.quitDriverAndroid();
    }

    public void stopIosDriverSession() {
        mobileWebDriverFactory.quitDriverIos();
    }

    public String getSystemOperation() {
        return systemOperation;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void startLogging() {

        if (!scenario.getSourceTagNames().contains("@automated")) {
            return;
        }

        String fullPath = scenario.getUri().getPath();

        String featureDirPath =
                fullPath.substring(0, fullPath.lastIndexOf('/'));

        String featureName =
                fullPath.substring(fullPath.lastIndexOf('/') + 1)
                        .replace(".feature", "")
                        .replace(" ", "_");

        int scenarioNumber = getNextScenarioNumber(featureName);

        MobileDeviceLogger.startLogging(
                featureDirPath,
                featureName,
                String.valueOf(scenarioNumber),
                systemOperation
        );
    }

    private static final ConcurrentHashMap<String, AtomicInteger> SCENARIO_COUNTERS =
            new ConcurrentHashMap<>();

    private static int getNextScenarioNumber(String featureName) {
        return SCENARIO_COUNTERS
                .computeIfAbsent(featureName, k -> new AtomicInteger(0))
                .incrementAndGet();
    }

    public void stopLogging() {
        MobileDeviceLogger.stopLogging();
    }

    public WebPageObjectFactory web() {
        return webPageObjectFactory;
    }

    public WebWebDriverFactory webWebDriverFactory() {
        return webWebDriverFactory;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }
}