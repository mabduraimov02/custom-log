package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Запись в файл с ротацией
 */
public class FileLogWriter implements LogWriter {
    private final String logFilePath;
    private static final long MAX_FILE_SIZE = 1024 * 1024; // 1MB

    public FileLogWriter(String logFilePath) {
        this.logFilePath = "logs/" + logFilePath;
        ensureLogDirectoryExists();
    }

    private void ensureLogDirectoryExists() {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
    }

    @Override
    public void write(String message) {
        rotateLogIfNecessary();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка записи в лог-файл: " + e.getMessage());
        }
    }

    private void rotateLogIfNecessary() {
        File logFile = new File(logFilePath);
        if (logFile.exists() && logFile.length() > MAX_FILE_SIZE) {
            File rotatedFile = new File(logFilePath + ".1");
            logFile.renameTo(rotatedFile);
        }
    }
}

