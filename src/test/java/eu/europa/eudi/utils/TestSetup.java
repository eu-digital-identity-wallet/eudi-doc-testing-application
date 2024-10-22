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
    public String getSystemOperation(){
        return systemOperation;
    }
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
    public Scenario getScenario() {
        return scenario;
    }

}

