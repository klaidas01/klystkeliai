package logging;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FileLog extends LogHandler {
    private static FileLog instance = null;
    public FileLog() {
        super();
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
        if(!fileName.equals(" ")) {
            File yourFile = new File(fileName);
            yourFile.createNewFile();
            try {
                FileWriter myWriter = new FileWriter(fileName, true);
                myWriter.write(message);
                myWriter.write("\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred when logging to file\n");
                e.printStackTrace();
            }
        }
    }

    public Iterator<String> getIterator(String fileName) throws FileNotFoundException {
        List<String> info = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                info.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return info.iterator();
    }


    @Override
    public void setNextChain(LogHandler next) {
        this.next = next;
    }
}
