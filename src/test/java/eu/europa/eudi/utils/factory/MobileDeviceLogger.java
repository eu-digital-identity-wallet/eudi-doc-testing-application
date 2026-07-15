package eu.europa.eudi.utils.factory;

import eu.europa.eudi.utils.config.EnvDataConfig;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class MobileDeviceLogger {
    static EnvDataConfig envDataConfig;
    private static File currentLogFile;
    private static Process logcatProcess;
    private static Thread logcatThread;

    public static void startLogging(String featureDirPath,
                                    String featureName,
                                    String scenarioNumber,
                                    String platform) {
        envDataConfig = new EnvDataConfig();

        try {

            stopLogging();

            String env = envDataConfig.getExecutionEnvironment();

            // ----------------------------
            // 1. Create SAME LOG FILE ALWAYS
            // ----------------------------
            File logDir = new File(
                    featureDirPath
                            + File.separator
                            + "logs"
                            + File.separator
                            + "ui"
            );

            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            File logFile = new File(
                    logDir,
                    featureName + "_" + scenarioNumber + ".log"
            );

            if (logFile.exists() && !logFile.delete()) {
                throw new IOException("Cannot delete old log file: " + logFile);
            }

            if (!logFile.createNewFile()) {
                throw new IOException("Cannot create log file: " + logFile);
            }

            // 🔥 CRITICAL FIX: ALWAYS SET THIS
            currentLogFile = logFile;

            System.out.println("Log file ready: " + logFile.getAbsolutePath());

            // ----------------------------
            // 2. BrowserStack MODE
            // ----------------------------
            if ("browserstack".equalsIgnoreCase(env)) {
                System.out.println("BrowserStack mode → skipping local logcat, file created");
                return;
            }

            // ----------------------------
            // 3. REAL DEVICES ONLY
            // ----------------------------
            if ("ANDROID".equalsIgnoreCase(platform)) {

                try {
                    Runtime.getRuntime().exec("adb logcat -c").waitFor();
                } catch (Exception e) {
                    System.err.println("adb clear failed: " + e.getMessage());
                }

                logcatProcess = Runtime.getRuntime().exec("adb logcat");

            } else if ("IOS".equalsIgnoreCase(platform)) {

                logcatProcess = Runtime.getRuntime().exec("idevicesyslog");

            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

            // ----------------------------
            // 4. LOG THREAD (REAL DEVICES ONLY)
            // ----------------------------
            logcatThread = new Thread(() -> {

                try (BufferedReader reader =
                             new BufferedReader(new InputStreamReader(logcatProcess.getInputStream()));
                     BufferedWriter writer =
                             new BufferedWriter(new FileWriter(currentLogFile, true))) {

                    String line;

                    while (!Thread.currentThread().isInterrupted()
                            && (line = reader.readLine()) != null) {

                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                    }

                } catch (Exception e) {
                    if (!Thread.currentThread().isInterrupted()) {
                        e.printStackTrace();
                    }
                }
            });

            logcatThread.setDaemon(true);
            logcatThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLogging() {
        if (logcatProcess != null)
        {
            try {
                // Destroy the process
                logcatProcess.destroy();
                // Wait for the process to terminate gracefully (with a timeout)
                boolean terminated = logcatProcess.waitFor(2, TimeUnit.SECONDS);
                // If it didn't terminate, force it
                if (!terminated) {
                    logcatProcess.destroyForcibly();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while waiting for logcat process to terminate");
            } catch (Exception e)
            {
                System.err.println("Error stopping logcat process: " + e.getMessage());
            } finally {
                logcatProcess = null;
            }
        }// Then handle the thread
        if (logcatThread != null) {
            try {// Interrupt the thread
                logcatThread.interrupt();
                // Give the thread some time to finish its work
                logcatThread.join(3000);
                // Wait up to 3 seconds// If the thread is still alive after the timeout, log a warning
                if (logcatThread.isAlive()) {
                    System.err.println("Warning: Logging thread did not terminate within the timeout period");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // Preserve interrupt status
                System.err.println("Interrupted while waiting for logging thread to terminate");
            } catch (Exception e) {
                System.err.println("Error stopping logging thread: " + e.getMessage());
            } finally {
                logcatThread = null;
            }
        }
    }
    public static File getCurrentLogFile() {
        return currentLogFile;
    }
}
