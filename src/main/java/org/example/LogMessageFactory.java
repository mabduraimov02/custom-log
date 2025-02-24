package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Фабрика для создания лог-сообщений
 */
public class LogMessageFactory {
    public static String createMessage(LogLevel level, String message) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +
                " [" + level + "] " + message;
    }
}
