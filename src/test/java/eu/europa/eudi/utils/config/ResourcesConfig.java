package eu.europa.eudi.utils.config;

import java.nio.file.Paths;


/**
 * The {@link ResourcesConfig} exposes all application resources (dirs, paths, etc.)
 */
public class ResourcesConfig {

    private String getTestResourcesPath() {
       return System.getProperty("user.dir") + "\\preAuthorizationCodeSameDevice.feature\\resources";
    }

    private String getEnvPath() {
        return System.getProperty("user.dir");
    }

    public String getAbsolutePath() {
        String absPath = Paths.get(".")
                .toAbsolutePath().normalize().toString().replace("\\", "/");

        String modulePath = this.getClass().getClassLoader().getResource(".").getPath();
        modulePath = modulePath.replace("\\", "/");
        modulePath = modulePath.replace("/target/preAuthorizationCodeSameDevice.feature-classes", "");
        modulePath = modulePath.replace(absPath, "");
        modulePath = modulePath.replace("//", "/");

        return absPath + modulePath;
    }

    public String getTargetPath() {
        String absPath = Paths.get(".")
                .toAbsolutePath().normalize().toString().replace("\\", "/");

        String modulePath = this.getClass().getClassLoader().getResource(".").getPath();
        modulePath = modulePath.replace("\\", "/");
        modulePath = modulePath.replace(absPath, "");
        modulePath = modulePath.replace("//", "/");

        return absPath + modulePath;
    }

    public String getEnvironmentPropertiesPath() {
            if (System.getProperty("env.properties") == null)
                return getEnvPath() + "/env.properties";
            else
                return getAbsolutePath() + System.getProperty("env.properties");

    }

}
