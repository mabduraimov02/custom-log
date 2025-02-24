package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Singleton-логгер с асинхронной записью
 */
public class Logger {
    private static volatile Logger instance;
    private final LogWriter logWriter;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private Logger(LogWriter logWriter) {
        this.logWriter = logWriter;
    }

    public static Logger getInstance(LogWriter logWriter) {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger(logWriter);
                }
            }
        }
        return instance;
    }

    public void log(LogLevel level, String message) {
        String logMessage = LogMessageFactory.createMessage(level, message);
        executor.submit(() -> logWriter.write(logMessage));
    }

    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }


    public void logWarning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }
}
