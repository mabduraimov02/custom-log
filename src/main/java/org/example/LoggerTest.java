package org.example;

/**
 * Тест логгера
 */
public class LoggerTest {

    public static void main(String[] args) {
        LogWriter logWriter = "file".equals(LoggerConfig.get("log.destination", "console"))
                ? new FileLogWriter("test.log") : new ConsoleLogWriter();

        Logger logger = Logger.getInstance(logWriter);
        LoggerDecorator decoratedLogger = new LoggerDecorator(logger).withSession("12345").withUser("admin");

        decoratedLogger.log(LogLevel.INFO, "Приложение запущено");
        decoratedLogger.log(LogLevel.WARNING, "Память на сервере заканчивается");
        decoratedLogger.log(LogLevel.ERROR, "Ошибка при сохранении данных в базу");
    }
}
