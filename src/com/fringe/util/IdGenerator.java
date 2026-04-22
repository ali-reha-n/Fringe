package com.fringe.util;

import java.util.List;

public class IdGenerator {

    public static int nextId(List<String> lines) {
        int maxId = 0;
        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) continue;
            try {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0].trim());
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {
                // skip malformed lines
            }
        }
        return maxId + 1;
    }
}
