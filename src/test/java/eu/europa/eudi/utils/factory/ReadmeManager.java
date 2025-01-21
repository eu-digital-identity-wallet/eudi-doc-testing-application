package eu.europa.eudi.utils.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
    public class ReadmeManager {

        private static final String FEATURE_FILES_DIR = "src/test/resources/features";
        private static final String BACKUP_DIR = "src/test/resources/backupreadme";
        private static final String SHELL_SCRIPT = "local-execution-manual.cmd";
        private static List<Path> readmePaths = new ArrayList<>();
        private static Map<Path, Path> backupPaths = new HashMap<>();

        public static void main(String[] args) {
            try {
                createBackupDirIfNotExists();
                removeReadmeFiles(Paths.get(FEATURE_FILES_DIR));
                runShellScript();
                restoreReadmeFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void createBackupDirIfNotExists() throws IOException {
            Path backupDir = Paths.get(BACKUP_DIR);
            if (Files.notExists(backupDir)) {
                Files.createDirectories(backupDir);
            }
        }

        private static void removeReadmeFiles(Path dir) throws IOException {
            Files.walk(dir)
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase("README.md"))
                    .forEach(path -> {
                        try {
                            // Store the original path
                            readmePaths.add(path);

                            // Create a unique backup path relative to the FEATURE_FILES_DIR
                            Path relativePath = dir.relativize(path);
                            Path backupPath = Paths.get(BACKUP_DIR, relativePath.toString());
                            backupPaths.put(path, backupPath);

                            // Ensure the backup directory structure exists
                            Files.createDirectories(backupPath.getParent());

                            // Copy the README file to the backup location
                            Files.copy(path, backupPath, StandardCopyOption.REPLACE_EXISTING);

                            // Delete the original README file
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        private static void runShellScript() {
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

        private static void restoreReadmeFiles() throws IOException {
            for (Path sourcePath : readmePaths) {
                Path backupPath = backupPaths.get(sourcePath);
                if (Files.exists(backupPath)) {
                    Files.copy(backupPath, sourcePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Restored README from " + backupPath + " to " + sourcePath);

                    // Delete the backup file after restoring it
                    Files.delete(backupPath);
                    System.out.println("Deleted backup README from " + backupPath);
                } else {
                    System.err.println("Backup file does not exist: " + backupPath);
                }
            }
        }

    }
