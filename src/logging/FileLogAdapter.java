package logging;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FileLogAdapter implements ILogger{

    private String fileName = "loginfo.txt";
    FileLog fileLog = FileLog.getInstance();

    @Override
    public void addMessage(String message) throws IOException {
        messages.add(message);
        logInfo(message);
    }

    @Override
    public void logInfo(String message) throws IOException {
        fileLog.LogInfo(message, fileName);
    }

    @Override
    public Iterator<String> getMessages() throws FileNotFoundException {
        return fileLog.getIterator(fileName);
    }
}
