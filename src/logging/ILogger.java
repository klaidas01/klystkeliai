package logging;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

interface ILogger  {

    ArrayList<String> messages = new ArrayList<>();

    void addMessage(String message) throws IOException;

    void logInfo(String message) throws IOException;

    Iterator<String> getMessages() throws FileNotFoundException;

}
