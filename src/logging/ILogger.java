package logging;

import java.io.IOException;
import java.util.ArrayList;

interface ILogger  {

    ArrayList<String> messages = new ArrayList<>();

    void addMessage(String message) throws IOException;

    void logInfo(String message) throws IOException;

    ArrayList<String> getMessages();

}
