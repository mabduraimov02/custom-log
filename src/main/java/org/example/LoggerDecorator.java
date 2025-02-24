package org.example;

/**
 * Декоратор для логгера, добавляющий информацию (например, sessionId, username)
 */
public class LoggerDecorator {
    private final Logger logger;
    private String sessionId;
    private String username;

    public LoggerDecorator(Logger logger) {
        this.logger = logger;
    }

    public LoggerDecorator withSession(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public LoggerDecorator withUser(String username) {
        this.username = username;
        return this;
    }

    public void log(LogLevel level, String message) {
        if (sessionId != null) message = "[Session: " + sessionId + "] " + message;
        if (username != null) message = "[User: " + username + "] " + message;
        logger.log(level, message);
    }
}
