package com.fringe.model;

import java.util.Objects;

public class ContentWarning {
    private int id;
    private int bookId;
    private String warningType;
    private int addedBy;

    public ContentWarning() {}

    public ContentWarning(int id, int bookId, String warningType, int addedBy) {
        this.id = id;
        this.bookId = bookId;
        this.warningType = warningType;
        this.addedBy = addedBy;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getWarningType() { return warningType; }
    public void setWarningType(String warningType) { this.warningType = warningType; }

    public int getAddedBy() { return addedBy; }
    public void setAddedBy(int addedBy) { this.addedBy = addedBy; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentWarning)) return false;
        return id == ((ContentWarning) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "ContentWarning{id=" + id + ", bookId=" + bookId + ", type='" + warningType + "'}";
    }
}
