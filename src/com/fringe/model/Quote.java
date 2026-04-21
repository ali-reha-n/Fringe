package com.fringe.model;

import java.util.Objects;

public class Quote {
    private int id;
    private int userId;
    private int bookId;
    private String text;
    private String pageNumber;
    private int likes;
    private String createdAt;

    public Quote() {}

    public Quote(int id, int userId, int bookId, String text,
                 String pageNumber, int likes, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.text = text;
        this.pageNumber = pageNumber;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getPageNumber() { return pageNumber; }
    public void setPageNumber(String pageNumber) { this.pageNumber = pageNumber; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quote)) return false;
        return id == ((Quote) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Quote{id=" + id + ", userId=" + userId + ", bookId=" + bookId + "}";
    }
}
