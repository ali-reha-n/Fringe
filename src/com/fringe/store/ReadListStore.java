package com.fringe.store;

import com.fringe.model.ReadList;
import com.fringe.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReadListStore {

    private static final String FILE = "data/readlists.txt";
    private static final List<String> VALID_STATUSES = List.of("WANT_TO_READ", "READING", "READ");

    // Format: id|userId|bookId|status|addedAt

    private String serialize(ReadList r) {
        return r.getId() + "|" + r.getUserId() + "|" +
                r.getBookId() + "|" + r.getStatus() + "|" + r.getAddedAt();
    }

    private ReadList deserialize(String line) {
        String[] f = line.split("\\|", 5);
        return new ReadList(
                Integer.parseInt(f[0]),
                Integer.parseInt(f[1]),
                Integer.parseInt(f[2]),
                f[3],
                f[4]
        );
    }

    private List<ReadList> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<ReadList> list = new ArrayList<>();
        for (String line : lines) list.add(deserialize(line));
        return list;
    }

    private void writeAll(List<ReadList> entries) {
        List<String> lines = new ArrayList<>();
        for (ReadList r : entries) lines.add(serialize(r));
        FileStore.writeLines(FILE, lines);
    }

    public void addOrUpdate(int userId, int bookId, String status) {
        if (!VALID_STATUSES.contains(status))
            throw new IllegalArgumentException("Status must be WANT_TO_READ, READING, or READ.");

        List<ReadList> entries = readAll();
        for (ReadList r : entries) {
            if (r.getUserId() == userId && r.getBookId() == bookId) {
                r.setStatus(status);
                writeAll(entries);
                return;
            }
        }

        int id = IdGenerator.nextId(FileStore.readLines(FILE));
        ReadList entry = new ReadList(id, userId, bookId, status, LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(entry));
    }

    public void remove(int userId, int bookId) {
        List<ReadList> entries = readAll();
        entries.removeIf(r -> r.getUserId() == userId && r.getBookId() == bookId);
        writeAll(entries);
    }

    public List<ReadList> getByUserId(int userId) {
        return readAll().stream()
                .filter(r -> r.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public List<ReadList> getByStatus(int userId, String status) {
        return getByUserId(userId).stream()
                .filter(r -> r.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public Optional<ReadList> getEntry(int userId, int bookId) {
        return readAll().stream()
                .filter(r -> r.getUserId() == userId && r.getBookId() == bookId)
                .findFirst();
    }
}

