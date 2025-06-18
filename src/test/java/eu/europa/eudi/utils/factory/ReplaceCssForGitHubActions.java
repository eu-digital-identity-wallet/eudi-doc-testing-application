package eu.europa.eudi.utils.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ReplaceCssForGitHubActions {
    public static void main(String[] args) {
        try {
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
}
