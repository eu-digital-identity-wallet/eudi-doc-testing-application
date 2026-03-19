package eu.europa.eudi.utils.factory;

import java.io.IOException;

public class locallyTestExecution {
    public static final String SHELL_SCRIPT = "./local-execution-mac.sh";

    public static void main(String[] args) {
        runShellScript();
    }
    public static void runShellScript() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(SHELL_SCRIPT);
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Shell script execution failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
