package onl.devin.geneticsai.parsing.config;

import onl.devin.geneticsai.parsing.FileParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class ConfigParser implements FileParser {

    @Override
    public void parseLine(String line, int lineNumber) {
        String[] keyValuePair = line.split(" = ");
        if (keyValuePair.length <= 1) {
            return;
        }
        String key = keyValuePair[0];
        String value = keyValuePair[1];

        ConfigValue configValue = null;
        ConfigValueCSV csvValue = null;

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

        if (configValue != null) {
            try {
                configValue.setValue(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                System.out.println(key + " has an invalid value of " + value + ".");
                System.exit(0);
            }
        } else if (csvValue != null) {
            if (new File(value).isFile()) {
                csvValue.setPath(value);
            } else {
                System.out.println("Could not find " + value + ". Make sure it exists and you have permission to read it.");
                System.exit(0);
            }
        } else {
            System.out.println(key + " was not found in the configuration options. Check config.txt for errors.");
            System.exit(0);
        }
    }

}
