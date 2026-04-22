package com.fringe.store;

import com.fringe.model.Message;
import com.fringe.util.IdGenerator;
import com.fringe.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageStore {

    private static final String FILE = "data/messages.txt";

    // Format: id|roomId|senderId|content|sentAt

    private String serialize(Message m) {
        return m.getId() + "|" + m.getRoomId() + "|" +
                m.getSenderId() + "|" + m.getContent() + "|" + m.getSentAt();
    }

    private Message deserialize(String line) {
        String[] f = line.split("\\|", 5);
        return new Message(
                Integer.parseInt(f[0]),
                Integer.parseInt(f[1]),
                Integer.parseInt(f[2]),
                f[3],
                f[4]
        );
    }

    private List<Message> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<Message> messages = new ArrayList<>();
        for (String line : lines) messages.add(deserialize(line));
        return messages;
    }

    public Message sendMessage(int roomId, int senderId, String content) {
        if (!ValidationUtil.isNotBlank(content))
            throw new IllegalArgumentException("Message content cannot be blank.");

        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        Message message = new Message(id, roomId, senderId, content, LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(message));
        return message;
    }

    public List<Message> getByRoomId(int roomId) {
        return readAll().stream()
                .filter(m -> m.getRoomId() == roomId)
                .sorted(Comparator.comparing(Message::getSentAt))
                .collect(Collectors.toList());
    }

    public List<Message> getNewMessages(int roomId, String afterTimestamp) {
        return readAll().stream()
                .filter(m -> m.getRoomId() == roomId && m.getSentAt().compareTo(afterTimestamp) > 0)
                .sorted(Comparator.comparing(Message::getSentAt))
                .collect(Collectors.toList());
    }
}
