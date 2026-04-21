package com.fringe.model;

import java.util.Objects;

public class Review {
    private int id;
    private int userId;
    private int bookId;
    private String content;
    private String createdAt;
    private String updatedAt;

    public Review() {}

    public Review(int id, int userId, int bookId, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id; }
    public void setId(int id) {
        this.id = id; }

    public int getUserId() {
        return userId; }
    public void setUserId(int userId) {
        this.userId = userId; }

    public int getBookId() {
        return bookId; }
    public void setBookId(int bookId) {
        this.bookId = bookId; }

    public String getContent() {
        return content; }
    public void setContent(String content) {
        this.content = content; }

    public String getCreatedAt() {
        return createdAt; }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt; }

    public String getUpdatedAt() {
        return updatedAt; }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        return id == ((Review) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); }

    @Override
    public String toString() {
        return "Review{id=" + id + ", userId=" + userId + ", bookId=" + bookId + "}";
    }
}
