package server;

import java.util.ArrayList;

public class Logger {
    private static Logger instance = null;
    private ArrayList<String> messages = new ArrayList<>();

    private Logger() {
        System.out.println("Singleton initialized");
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        System.out.println("Returning instance");
        return instance;
    }

    public void addMessage(String message) {
        messages.add(message);
        logInfo(message);
    }

    public void logInfo(String message) {
        System.out.println("Logger: " + message);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
