package eu.europa.eudi.stepdefs;

import browserstack.shaded.org.json.JSONObject;
import eu.europa.eudi.data.Literals;
import eu.europa.eudi.utils.TestSetup;
import eu.europa.eudi.utils.config.EnvDataConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.AssumptionViolatedException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TestHooks {
    EnvDataConfig envDataConfig;
    private static TestSetup test;

    @Before
    public void setup(Scenario scenario) throws Exception {

        envDataConfig = new EnvDataConfig();

        boolean android = scenario.getSourceTagNames().contains("@ANDROID");
        boolean ios = scenario.getSourceTagNames().contains("@IOS");

        String env = envDataConfig.getExecutionEnvironment();

        if (android) {
            test = new TestSetup(false, Literals.General.ANDROID.label, scenario);
            test.startAndroidDriverSession();
            test.startLogging();

            if ("browserstack".equalsIgnoreCase(env)) {
                waitForDriver(test.mobileWebDriverFactory().getDriverAndroid());
            }
        }

        if (ios) {
            test = new TestSetup(false, Literals.General.IOS.label, scenario);
            test.startIosDriverSession();
            test.startLogging();

            if ("browserstack".equalsIgnoreCase(env)) {
                waitForDriver(test.mobileWebDriverFactory().getDriverIos());
            }
        }

        if (scenario.getSourceTagNames().contains("@Ignored")) {
            throw new AssumptionViolatedException("Ignored scenario");
        }
    }

    private void waitForDriver(org.openqa.selenium.WebDriver driver) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            try {
                driver.getPageSource();
                return;
            } catch (Exception e) {
                Thread.sleep(1500);
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {

        try {
            String env = envDataConfig.getExecutionEnvironment();
            if (!"browserstack".equalsIgnoreCase(env)) {
                stopDrivers();
                return;
            }

            boolean android = scenario.getSourceTagNames().contains("@ANDROID");
            boolean ios = scenario.getSourceTagNames().contains("@IOS");

            String sessionId = null;

            if (android) {
                AndroidDriver driver = (AndroidDriver) test.mobileWebDriverFactory().getDriverAndroid();
                sessionId = driver.getSessionId().toString();

                test.stopAndroidDriverSession();
            }

            if (ios) {
                IOSDriver driver = (IOSDriver) test.mobileWebDriverFactory().getDriverIos();
                sessionId = driver.getSessionId().toString();

                test.stopIosDriverSession();
            }

            if (sessionId != null) {
                BrowserStackService.downloadDeviceLogs(sessionId, test);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        cleanup();
    }

    private void stopDrivers() {
        test.stopAndroidDriverSession();
        test.stopIosDriverSession();
    }

    private void cleanup() {
        cleanupScreenshotsFolder();
        test.stopLogging();
    }

    public static class BrowserStackService {

        static void downloadDeviceLogs(String sessionId, TestSetup test) throws Exception {

            EnvDataConfig config = new EnvDataConfig();

            String auth = config.getAppiumBrowserstackGeneralUsername()
                    + ":" + config.getAppiumBrowserstackGeneralAccesskey();

            String encodedAuth = Base64.getEncoder()
                    .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

            String sessionUrl =
                    "https://api.browserstack.com/app-automate/sessions/"
                            + sessionId + ".json";

            JSONObject sessionJson = httpGetJson(sessionUrl, encodedAuth);
            String buildId = sessionJson
                    .getJSONObject("automation_session")
                    .optString("build_hashed_id");

            if (buildId == null || buildId.isEmpty()) {
                throw new RuntimeException("Missing build_id for session " + sessionId);
            }

            String logUrl =
                    "https://api-cloud.browserstack.com/app-automate/builds/"
                            + buildId
                            + "/sessions/"
                            + sessionId
                            + "/devicelogs";

            fetchAndAppendLogs(logUrl, encodedAuth, test);
        }

        public static JSONObject httpGetJson(String url, String auth) throws Exception {

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Authorization", "Basic " + auth);
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();

            if (code != 200) {
                throw new RuntimeException("API failed: HTTP " + code);
            }

            try (BufferedReader br =
                         new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                return new JSONObject(sb.toString());
            }
        }

        public static void fetchAndAppendLogs(
                String url,
                String auth,
                TestSetup test
        ) throws Exception {

            for (int i = 0; i < 10; i++) {

                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestProperty("Authorization", "Basic " + auth);

                if (conn.getResponseCode() == 200) {

                    File file = test.mobileWebDriverFactory().getCurrentLogFile();

                    if (file == null) {
                        file = new File("logs/ui/fallback.log");
                    }

                    file.getParentFile().mkdirs();

                    try (BufferedReader reader =
                                 new BufferedReader(new InputStreamReader(conn.getInputStream()));
                         BufferedWriter writer =
                                 new BufferedWriter(new FileWriter(file, true))) {

                        writer.newLine();
                        writer.write("===== BROWSERSTACK DEVICE LOGS =====");
                        writer.newLine();

                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                    }

                    return;
                }

                Thread.sleep(4000);
            }

            throw new RuntimeException("Failed to download BrowserStack logs");
        }
    }

    private void cleanupScreenshotsFolder() {
        File dir = new File("screenshots");
        if (!dir.exists()) return;

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) {
            f.delete();
        }
    }

    public static TestSetup getTest() {
        return test;
    }
}
