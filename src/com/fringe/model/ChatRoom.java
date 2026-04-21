package com.fringe.model;

import java.util.Objects;

public class ChatRoom {
    private int id;
    private String bookId;
    private String name;
    private String createdAt;

    public ChatRoom() {}

    public ChatRoom(int id, String bookId, String name, String createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id; }
    public void setId(int id) { this.id = id; }

    public String getBookId() {
        return bookId; }
    public void setBookId(String bookId) {
        this.bookId = bookId; }

    public String getName() {
        return name; }
    public void setName(String name) {
        this.name = name; }

    public String getCreatedAt() {
        return createdAt; }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoom)) return false;
        return id == ((ChatRoom) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ChatRoom{id=" + id + ", name='" + name + "', bookId='" + bookId + "'}";
    }
}
