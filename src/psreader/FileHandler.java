package psreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler {

    private static final String configFile="notes.conf";
    
    public static String loadPath() {
        String filePath = null;
        try {
            File file = new File(configFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            filePath = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    public static boolean savePath(File filePath) {
        if (filePath==null) {
            return false;
        }
        try {
            PrintWriter file = new PrintWriter(configFile);
            file.print(filePath);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
