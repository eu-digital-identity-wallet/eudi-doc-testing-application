package eu.europa.eudi.utils.factory;

import java.io.IOException;
import java.nio.file.*;
    public class ReadmeManager {
<<<<<<< HEAD

=======
>>>>>>> milestone/2025.Q2
        public static final String SHELL_SCRIPT = "./local-execution.cmd";

        public static void main(String[] args) {
            try {
                runShellScript();
                replaceCoreCssWithCustomStyle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void replaceCoreCssWithCustomStyle() throws IOException {
            Path coreCssPath = Paths.get("target/site/reports/EUDI_Wallet_Version_2025.02.22-Demo/css/core.css"); // Replace with the actual path to core.css
            Path customStyleCssPath = Paths.get("C:/Users/ftheofil/Projects/eu-digital-identity-walleteudi-doc-testing-application-internal/src/test/resources/custom-style.css"); // Replace with the actual path to custom-style.css

            if (Files.exists(coreCssPath) && Files.exists(customStyleCssPath)) {
                // Replace core.css with custom-style.css
                Files.copy(customStyleCssPath, coreCssPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Replaced core.css with custom-style.css");
            } else {
                System.err.println("Either core.css or custom-style.css doesn't exist.");
            }
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
<<<<<<< HEAD
=======

>>>>>>> milestone/2025.Q2
    }
