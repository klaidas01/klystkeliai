package logging;


public class ConsoleLogger {

    private static ConsoleLogger instance = null;

    private ConsoleLogger() {
        System.out.println("ConsoleLog Singleton initialized");
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
