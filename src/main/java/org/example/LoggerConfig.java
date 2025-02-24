package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Чтение конфигурации
 */
public class LoggerConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = LoggerConfig.class.getClassLoader().getResourceAsStream("logger.properties")) {
            if (input == null) {
                throw new IOException("Файл logger.properties не найден в resources/");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
