package eu.europa.eudi.utils;

import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Constructor;

public class YmlLoader {
    public static <T> T load(String file, Class<T> clazz) {

        LoadSettings settings = LoadSettings.builder().build();
        Load load = new Load(settings);

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(file);

        if (is == null) {
            throw new RuntimeException("YML not found: " + file);
        }

        Object data = load.loadFromInputStream(is);

        // Map YAML to POJO manually
        return YmlMapper.map(data, clazz);
    }
}
