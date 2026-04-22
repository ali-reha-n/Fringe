package com.fringe.store;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileStore {

    public static List<String> readLines(String filePath) {
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) return new ArrayList<>();
            List<String> lines = Files.readAllLines(path);
            List<String> result = new ArrayList<>();
            for (String line : lines) {
                if (line != null && !line.trim().isEmpty()) result.add(line);
            }
            return result;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeLines(String filePath, List<String> lines) {
        try {
            Path path = Path.of(filePath);
            Files.createDirectories(path.getParent());
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + filePath, e);
        }
    }

    public static void appendLine(String filePath, String line) {
        try {
            Path path = Path.of(filePath);
            Files.createDirectories(path.getParent());
            Files.writeString(path, line + System.lineSeparator(),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to append to file: " + filePath, e);
        }
    }
}
