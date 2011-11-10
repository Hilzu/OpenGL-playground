package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Util {
    public static String readStringFromFile(String path) {
        File fileHandle = new File(path);
        BufferedReader inputReader = null;
        try {
            inputReader = new BufferedReader(new FileReader(fileHandle));
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            System.exit(1);
        }

        String line = null;
        StringBuilder string = new StringBuilder();

        try {
            try {
                while (true) {

                    line = inputReader.readLine();

                    if (line == null) {
                        break;
                    }

                    string.append(line);
                    string.append(System.getProperty("line.separator"));
                }
            } finally {
                inputReader.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        }

        return string.toString();
    }
}
