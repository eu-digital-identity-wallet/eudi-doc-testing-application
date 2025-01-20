package eu.europa.eudi.utils;

import eu.europa.eudi.utils.config.EnvDataConfig;
import eu.europa.eudi.utils.factory.*;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.Scenario;
import org.openqa.selenium.logging.LogEntry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestSetup {
    EnvDataConfig envDataConfig;
    MobileWebDriverFactory mobileWebDriverFactory;
    MobilePageObjectFactory mobilePageObjectFactory;
    String systemOperation;
    Scenario scenario;
    private static final Logger logger = LoggerFactory.getLogger(TestSetup.class);

    public TestSetup(boolean noReset, String systemOperation, Scenario scenario) {
        this.systemOperation = systemOperation;
        this.scenario = scenario;
        mobileWebDriverFactory = new MobileWebDriverFactory(TestSetup.this, noReset);
        mobilePageObjectFactory = new MobilePageObjectFactory(TestSetup.this);
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

    public void startAndroidDriverSession() {
        mobileWebDriverFactory.startAndroidDriverSession();
    }

    public void reInitializeDriver() {
        mobileWebDriverFactory.startAndroidDriverSession();
    }

    public void startIosDriverSession() {
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
        String platformTag = "";

        // Determine the platform tag based on the scenario's tags
        if (scenario.getSourceTagNames().contains("@ANDROID")) {
            platformTag = "ANDROID";
        } else if (scenario.getSourceTagNames().contains("@IOS")) {
            platformTag = "IOS";
        } else {
            // Handle the case where no platform tag is found
            throw new IllegalArgumentException("Scenario does not have a valid platform tag (@ANDROID or @IOS)");
        }

        // Start logging with the determined parameters
        mobileWebDriverFactory.startLogging(featureDirPath, featureName, scenarioName, platformTag);
    }


    public void stopLogging() {
        mobileWebDriverFactory.stopLogging();
    }
}