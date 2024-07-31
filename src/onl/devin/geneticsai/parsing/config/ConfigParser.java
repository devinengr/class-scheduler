package onl.devin.geneticsai.parsing.config;

import onl.devin.geneticsai.parsing.FileParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class ConfigParser implements FileParser {

    @Override
    public void parseLine(String line, int lineNumber) {
        if (line.startsWith("#")) {
            return;
        }

        String[] keyValuePair = line.split(" = ");
        if (keyValuePair.length <= 1) {
            return;
        }
        String key = keyValuePair[0];
        String value = keyValuePair[1];

        ConfigValue configValue = null;
        ConfigValueCSV csvValue = null;
        ConfigKeyModel modelValue = null;

        try {
            configValue = ConfigValue.valueOf(key);
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        try {
            csvValue = ConfigValueCSV.valueOf(key);
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        try {
            modelValue = ConfigKeyModel.valueOf(key);
        } catch (IllegalArgumentException e) {
            // do nothing
        }

        if (configValue != null) {
            try {
                configValue.setValue(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                System.out.println(key + " has an invalid value of " + value + ".");
                System.exit(0);
            }
        } else if (csvValue != null) {
            csvValue.setPath(value);
        } else if (modelValue != null) {
            try {
                ConfigValueModel model = ConfigValueModel.valueOf(value);
                modelValue.setModel(model.getModel());
            } catch (IllegalArgumentException e) {
                System.out.println(value + " is not a valid model. See the README for valid model types.");
                System.exit(0);
            }

        } else {
            System.out.println(key + " was not found in the configuration options. Check config.txt for errors.");
            System.exit(0);
        }
    }

}
