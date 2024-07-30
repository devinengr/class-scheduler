package onl.devin.geneticsai.parsing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    /**
     * Read the given file and parse each line using
     * the provided parser. The parser stores the data
     * according to its specification.
     * @param filePath relative file path
     * @param parser parser object that processes the file
     */
    public void parseFile(String filePath, FileParser parser) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                parser.parseLine(line, lineNumber);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
