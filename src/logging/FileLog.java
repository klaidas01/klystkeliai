package logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLog {
    private static FileLog instance = null;
    private FileLog() {
        System.out.println("FileLog Singleton initialized");
    }


    public static synchronized FileLog getInstance() {
        if (instance == null) {
            instance = new FileLog();
        }
        System.out.println(" File LOG - Returning instance");
        return instance;
    }

    public void LogInfo(String message, String fileName) throws IOException {
        File yourFile = new File(fileName);
        yourFile.createNewFile();
        try {
            FileWriter myWriter = new FileWriter(fileName,true);
            myWriter.write(message);
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred when logging to file\n");
            e.printStackTrace();
        }
    }
}
