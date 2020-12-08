package logging;

import java.io.IOException;

public abstract class LogHandler {

    protected LogHandler next;

    public LogHandler() {
    }

    public abstract void LogInfo(String message, String fileName) throws IOException;

    public abstract void setNextChain(LogHandler next);
}
