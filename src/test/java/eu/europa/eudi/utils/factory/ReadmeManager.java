package eu.europa.eudi.utils.factory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
    public class ReadmeManager {

        public static final String FEATURE_FILES_DIR = "src/test/resources/features";
        public static final String BACKUP_DIR = "src/test/resources/backupreadme";
        public static final String BACKUP_LOGS_DIR = "src/test/resources/backup_logs";
        public static final String SHELL_SCRIPT = "./local-execution.cmd";
        public static List<Path> readmePaths = new ArrayList<>();
        public static List<Path> logsPaths = new ArrayList<>();
        public static Map<Path, Path> backupPaths = new HashMap<>();
        public static Map<Path, Path> backupLogsPaths = new HashMap<>();  // Map to track log backups

        public static void main(String[] args) {
            try {
                createBackupDirIfNotExists();
                createBackupLogsDirIfNotExists();  // Ensure backup directory for logs exists
                removeReadmeFiles(Paths.get(FEATURE_FILES_DIR));
                removeLogsFiles(Paths.get(FEATURE_FILES_DIR));  // Remove and backup logs
                runShellScript();
                restoreReadmeFiles();
                restoreLogsFiles();  // Restore logs after script execution
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void createBackupDirIfNotExists() throws IOException {
            Path backupDir = Paths.get(BACKUP_DIR);
            if (Files.notExists(backupDir)) {
                Files.createDirectories(backupDir);
            }
        }

        public static void createBackupLogsDirIfNotExists() throws IOException {
            Path backupLogsDir = Paths.get(BACKUP_LOGS_DIR);
            if (Files.notExists(backupLogsDir)) {
                Files.createDirectories(backupLogsDir);
            }
        }

        public static void removeReadmeFiles(Path dir) throws IOException {
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

        public static void removeLogsFiles(Path dir) throws IOException {
            Files.walk(dir)
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase("logs"))
                    .forEach(path -> {
                        try {
                            // Store the original path for logs
                            logsPaths.add(path);

                            // Create a unique backup path relative to the FEATURE_FILES_DIR
                            Path relativePath = dir.relativize(path);
                            Path backupLogsPath = Paths.get(BACKUP_LOGS_DIR, relativePath.toString());
                            backupLogsPaths.put(path, backupLogsPath);

                            // Ensure the backup directory structure exists
                            Files.createDirectories(backupLogsPath.getParent());

                            // Copy the logs directory to the backup location
                            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                    Path destination = backupLogsPath.resolve(path.relativize(file));
                                    Files.createDirectories(destination.getParent());
                                    Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);
                                    return FileVisitResult.CONTINUE;
                                }

                                @Override
                                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                                    return FileVisitResult.CONTINUE;
                                }
                            });

                            // Delete the original logs directory
                            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                    Files.delete(file);
                                    return FileVisitResult.CONTINUE;
                                }

                                @Override
                                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                                    Files.delete(dir);
                                    return FileVisitResult.CONTINUE;
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
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

        public static void restoreReadmeFiles() throws IOException {
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

        public static void restoreLogsFiles() throws IOException {
            for (Path sourcePath : logsPaths) {
                Path backupLogsPath = backupLogsPaths.get(sourcePath);
                if (Files.exists(backupLogsPath)) {
                    // Restore the logs directory
                    Files.walkFileTree(backupLogsPath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Path destination = sourcePath.resolve(backupLogsPath.relativize(file));
                            Files.createDirectories(destination.getParent());
                            Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    System.out.println("Restored logs from " + backupLogsPath + " to " + sourcePath);

                    // Delete the backup logs after restoring
                    Files.walkFileTree(backupLogsPath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    System.out.println("Deleted backup logs from " + backupLogsPath);
                } else {
                    System.err.println("Backup logs do not exist: " + backupLogsPath);
                }
            }
        }

    }
