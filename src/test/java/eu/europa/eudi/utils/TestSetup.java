package eu.europa.eudi.utils;

import eu.europa.eudi.utils.config.EnvDataConfig;

import eu.europa.eudi.utils.factory.MobilePageObjectFactory;
import eu.europa.eudi.utils.factory.MobileWebDriverFactory;
import eu.europa.eudi.utils.factory.WebPageObjectFactory;
import eu.europa.eudi.utils.factory.WebWebDriverFactory;
import io.cucumber.java.Scenario;

import java.net.MalformedURLException;

public class TestSetup {
    private String transactionCode;
    EnvDataConfig envDataConfig;
    MobileWebDriverFactory mobileWebDriverFactory;
    WebWebDriverFactory webWebDriverFactory;
    MobilePageObjectFactory mobilePageObjectFactory;
    WebPageObjectFactory webPageObjectFactory;
    String systemOperation;
    Scenario scenario;

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

    // Methods to start and stop logging
    public void startLogging() {
        if (!scenario.getSourceTagNames().contains("@automated")) {
            // If the @automated tag is not present, do not start logging
            return;
        }

        // Extract the full path from the URI
        String fullPath = scenario.getUri().getPath();

        // Extract the directory of the feature file
        String featureDirPath = fullPath.substring(0, fullPath.lastIndexOf('/'));

        // Extract the feature name from the path
        String featureName = fullPath.substring(fullPath.lastIndexOf('/') + 1).replace(".feature", "");
        // Extract the scenario name
        String scenarioName = scenario.getName();

        // Start logging with the determined parameters
        mobileWebDriverFactory.startLogging(featureDirPath, featureName, scenarioName, systemOperation);
    }


    public void stopLogging() {
        mobileWebDriverFactory.stopLogging();
    }

    public void setTransactionCode(String code) {
        this.transactionCode = code;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public WebPageObjectFactory web() {
        return webPageObjectFactory;
    }

    public WebWebDriverFactory webWebDriverFactory() {
        return webWebDriverFactory;
    }
}