package eu.europa.eudi.data.yml;

import java.util.Map;

public class FormYml {
    public String screen;
    public Map<String, FieldCfg> fields;

    public static class FieldCfg {
        public String value;
        public boolean required;
    }
}
