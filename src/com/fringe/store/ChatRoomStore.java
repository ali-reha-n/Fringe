package com.fringe.store;

import com.fringe.model.ChatRoom;
import com.fringe.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatRoomStore {

    private static final String FILE = "data/chat_rooms.txt";

    // Format: id|bookId|name|createdAt

    private String serialize(ChatRoom r) {
        return r.getId() + "|" +
                (r.getBookId() == null ? "null" : r.getBookId()) + "|" +
                r.getName() + "|" +
                r.getCreatedAt();
    }

    private ChatRoom deserialize(String line) {
        String[] f = line.split("\\|", 4);
        return new ChatRoom(
                Integer.parseInt(f[0]),
                f[1].equals("null") ? null : f[1],
                f[2],
                f[3]
        );
    }

    private List<ChatRoom> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<ChatRoom> rooms = new ArrayList<>();
        for (String line : lines) rooms.add(deserialize(line));
        return rooms;
    }

    public List<ChatRoom> getAll() {
        return readAll();
    }

    public Optional<ChatRoom> findById(int id) {
        return readAll().stream().filter(r -> r.getId() == id).findFirst();
    }

    public ChatRoom getOrCreateRoomForBook(int bookId, String bookTitle) {
        Optional<ChatRoom> existing = readAll().stream()
                .filter(r -> r.getBookId() != null && r.getBookId().equals(String.valueOf(bookId)))
                .findFirst();

        if (existing.isPresent()) return existing.get();

        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        ChatRoom room = new ChatRoom(id, String.valueOf(bookId),
                bookTitle + " Discussion", LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(room));
        return room;
    }

    public ChatRoom createGeneralRoom(String name) {
        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        ChatRoom room = new ChatRoom(id, null, name, LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(room));
        return room;
    }
}
