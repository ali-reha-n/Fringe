package com.fringe.model;

import java.util.Objects;

public class ReadList {
    private int id;
    private int userId;
    private int bookId;
    private String status;
    private String addedAt;

    public ReadList() {}

    public ReadList(int id, int userId, int bookId, String status, String addedAt) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
        this.addedAt = addedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAddedAt() { return addedAt; }
    public void setAddedAt(String addedAt) { this.addedAt = addedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReadList)) return false;
        return id == ((ReadList) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ReadList{id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", status='" + status + "'}";
    }
}
