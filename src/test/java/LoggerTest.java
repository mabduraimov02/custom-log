import org.example.ConsoleLogWriter;
import org.example.FileLogWriter;
import org.example.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerTest {
    private Logger logger;
    private Path logFilePath;

    @BeforeEach
    void setUp() {
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        logger = Logger.getInstance(new FileLogWriter("test.log"));
        logFilePath = Paths.get("logs/test.log");
    }


    @AfterEach
    void tearDown() throws IOException {
        // Очищаем файл после тестов
        Files.deleteIfExists(logFilePath);
    }

    @Test
    void testSingletonInstance() {
        Logger anotherLogger = Logger.getInstance(new ConsoleLogWriter());
        assertSame(logger, anotherLogger, "Logger должен быть синглтоном");
    }

    @Test
    void testLogInfo() throws IOException {
        logger.logInfo("Test INFO message");

        // Проверяем, что сообщение записано в файл
        assertTrue(Files.exists(logFilePath), "Файл лога должен существовать");
        String content = Files.readString(logFilePath);
        assertTrue(content.contains("INFO"), "Лог должен содержать уровень INFO");
        assertTrue(content.contains("Test INFO message"), "Лог должен содержать сообщение");
    }

    @Test
    void testLogWarning() throws IOException, InterruptedException {
        logger.logWarning("Test WARNING message");

        Thread.sleep(100); // Ждем завершения записи

        // Проверяем, что сообщение записано в файл
        String content = Files.readString(logFilePath);
        assertTrue(content.contains("WARNING"), "Лог должен содержать уровень WARNING");
        assertTrue(content.contains("Test WARNING message"), "Лог должен содержать сообщение");
    }

    @Test
    void testLogError() throws IOException, InterruptedException {
        logger.logError("Test ERROR message");

        Thread.sleep(100); // Ждем завершения записи

        // Проверяем, что сообщение записано в файл
        String content = Files.readString(logFilePath);
        assertTrue(content.contains("ERROR"), "Лог должен содержать уровень ERROR");
        assertTrue(content.contains("Test ERROR message"), "Лог должен содержать сообщение");
    }

    @Test
    void testLogRotation() throws IOException, InterruptedException {
        // Заполняем лог большим количеством данных
        for (int i = 0; i < 10000; i++) {
            logger.logInfo("Message " + i);
        }

        Thread.sleep(100); // Ждем ротации

        // Проверяем, что создался новый файл
        Path rotatedFilePath = Paths.get("logs/test.log.1");
        assertTrue(Files.exists(rotatedFilePath), "Должен создаться файл test.log.1 после ротации");
    }
}

