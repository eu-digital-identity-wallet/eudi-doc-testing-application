package eu.europa.eudi.utils.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YmlMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T map(Object data, Class<T> clazz) {
        return mapper.convertValue(data, clazz);
    }
}
