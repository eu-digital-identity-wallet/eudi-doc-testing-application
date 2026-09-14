package eu.europa.eudi.utils;

import eu.europa.eudi.utils.config.EnvDataConfig;

import eu.europa.eudi.utils.factory.*;
import io.cucumber.java.Scenario;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TestSetup {
    EnvDataConfig envDataConfig;
    MobileWebDriverFactory mobileWebDriverFactory;
    WebWebDriverFactory webWebDriverFactory;
    MobilePageObjectFactory mobilePageObjectFactory;
    WebPageObjectFactory webPageObjectFactory;
    String systemOperation;
    Scenario scenario;
    private String transactionCode;
    private File currentLogFile;

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

        String scenarioNumber = buildScenarioSuffix(featureDirPath, featureName);

        this.currentLogFile = MobileDeviceLogger.startLogging(
                featureDirPath,
                featureName,
                scenarioNumber,
                systemOperation
        );
    }

    /**
     * Builds a human-readable suffix for the log file name from the values of
     * the current Scenario Outline example row.
     * <p>
     * For example, for the example row:
     * {@code | PID (SD-JWT) | Python | from list | same device | Web verifier | same device | specific attributes |}
     * this will produce something like
     * {@code PID_(SD-JWT)_Python_from_list_same_device_Web_verifier_same_device_specific_attributes}.
     * <p>
     * If the scenario is not an outline (no matching Examples row is found), it returns
     * an empty string so the log file is named after the feature file only
     * (e.g. {@code preAuthorizationCodeSameDevice.log}).
     */
    private String buildScenarioSuffix(String featureDirPath, String featureName) {
        try {
            File featureFile = new File(featureDirPath, featureName + ".feature");
            if (!featureFile.exists()) {
                return "";
            }

            List<String> lines = Files.readAllLines(featureFile.toPath());

            // scenario.getLine() returns the line of the example row (1-based)
            int currentRow = scenario.getLine() - 1;
            if (currentRow < 0 || currentRow >= lines.size()) {
                return "";
            }

            String row = lines.get(currentRow).trim();
            if (!row.startsWith("|")) {
                return "";
            }

            List<String> values = parseTableRow(row);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.size(); i++) {
                if (i > 0) {
                    sb.append("_");
                }
                sb.append(sanitize(values.get(i)));
            }
            return sb.toString();

        } catch (Exception e) {
            return "";
        }
    }

    private List<String> parseTableRow(String row) {
        List<String> result = new ArrayList<>();
        String trimmed = row.trim();
        if (trimmed.startsWith("|")) {
            trimmed = trimmed.substring(1);
        }
        if (trimmed.endsWith("|")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }
        for (String cell : trimmed.split("\\|")) {
            result.add(cell.trim());
        }
        return result;
    }

    private String sanitize(String value) {
        return value.replace(" ", "_");
    }

    public File getCurrentLogFile() {
        return currentLogFile;
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