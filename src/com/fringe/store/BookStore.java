package com.fringe.store;

import com.fringe.model.ContentWarning;
import com.fringe.util.IdGenerator;
import com.fringe.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ContentWarningStore {

    private static final String FILE = "data/content_warnings.txt";

    // Format: id|bookId|warningType|addedBy

    private String serialize(ContentWarning c) {
        return c.getId() + "|" + c.getBookId() + "|" + c.getWarningType() + "|" + c.getAddedBy();
    }

    private ContentWarning deserialize(String line) {
        String[] f = line.split("\\|", 4);
        return new ContentWarning(
                Integer.parseInt(f[0]),
                Integer.parseInt(f[1]),
                f[2],
                Integer.parseInt(f[3])
        );
    }

    private List<ContentWarning> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<ContentWarning> list = new ArrayList<>();
        for (String line : lines) list.add(deserialize(line));
        return list;
    }

    private void writeAll(List<ContentWarning> warnings) {
        List<String> lines = new ArrayList<>();
        for (ContentWarning c : warnings) lines.add(serialize(c));
        FileStore.writeLines(FILE, lines);
    }

    public void addWarning(int bookId, String warningType, int addedBy) {
        if (!ValidationUtil.isNotBlank(warningType))
            throw new IllegalArgumentException("Warning type cannot be blank.");

        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        ContentWarning warning = new ContentWarning(id, bookId, warningType.trim(), addedBy);
        FileStore.appendLine(FILE, serialize(warning));
    }

    public List<ContentWarning> getByBookId(int bookId) {
        return readAll().stream()
                .filter(c -> c.getBookId() == bookId)
                .collect(Collectors.toList());
    }

    public void deleteWarning(int id) {
        List<ContentWarning> warnings = readAll();
        warnings.removeIf(c -> c.getId() == id);
        writeAll(warnings);
    }
}
