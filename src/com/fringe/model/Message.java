package com.fringe.model;

import java.util.Objects;

public class Message {
    private int id;
    private int roomId;
    private int senderId;
    private String content;
    private String sentAt;

    public Message() {}

    public Message(int id, int roomId, int senderId, String content, String sentAt) {
        this.id = id;
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.sentAt = sentAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSentAt() { return sentAt; }
    public void setSentAt(String sentAt) { this.sentAt = sentAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        return id == ((Message) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Message{id=" + id + ", roomId=" + roomId + ", senderId=" + senderId + "}";
    }
}

