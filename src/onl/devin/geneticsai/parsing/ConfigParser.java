package onl.devin.geneticsai.parsing;

public class ConfigParser implements FileParser {

    @Override
    public void parseLine(String line, int lineNumber) {
        String[] keyValuePair = line.split(" = ");
        if (keyValuePair.length <= 1) {
            return;
        }
        String key = keyValuePair[0];
        String value = keyValuePair[1];

        ConfigValue configValue = ConfigValue.valueOf(key);
        if (configValue != null) {
            try {
                configValue.setValue(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                System.out.println(key + " has an invalid value of " + value + ". Using default.");
            }
        } else {
            System.out.println(key + " was not found in the configuration options. Check config.txt for errors.");
        }
    }

}
