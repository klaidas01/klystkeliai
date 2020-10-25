package logging;

import java.util.ArrayList;

public class ConsoleLogAdapter implements ILogger {

    private ConsoleLogger consoleLogger = ConsoleLogger.getInstance();

    @Override
    public void addMessage(String message) {
        messages.add(message);
        logInfo(message);
    }

    @Override
    public void logInfo(String message) {
        consoleLogger.logInfo(message);
    }

    @Override
    public ArrayList<String> getMessages() {
        return messages;
    }
}
