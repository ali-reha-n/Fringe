package com.fringe.model;

import java.util.Objects;

public class Rating {
    private int id;
    private int userId;
    private int bookId;
    private int stars;
    private String createdAt;

    public Rating() {}

    public Rating(int id, int userId, int bookId, int stars, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        return id == ((Rating) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Rating{id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", stars=" + stars + "}";
    }
}
