package logging;


import java.io.IOException;

public class ConsoleLogger extends LogHandler{

    private static ConsoleLogger instance = null;

    public ConsoleLogger() {
        System.out.println("ConsoleLog Singleton initialized");
    }

    @Override
    public void LogInfo(String message, String fileName) throws IOException {
        if(fileName.isEmpty()) {
            logInfo(message);
        }else{
            next.LogInfo(message, fileName);
        }
    }

    @Override
    public void setNextChain(LogHandler next) {
        this.next = next;
    }

    public static synchronized ConsoleLogger getInstance() {
        if (instance == null) {
            instance = new ConsoleLogger();
        }
        System.out.println("Console Log - Returning instance");
        return instance;
    }


    public void logInfo(String message) {
        System.out.println("Console logger: " + message);
    }
}
