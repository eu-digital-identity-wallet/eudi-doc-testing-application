import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ReportModifier {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide the report directory as an argument.");
            System.exit(1);
        }

        String reportDir = args[0];
        try (Stream<Path> paths = Files.walk(Paths.get(reportDir))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".html"))
                    .forEach(ReportModifier::modifyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifyFile(Path path) {
        try {
            String content = new String(Files.readAllBytes(path));
            // Example: Replace "Old Text" with "New Text"
            content = content.replaceAll("Test Outcomes for Platforms ", "Test Outcomes per Platform");
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
