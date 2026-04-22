package com.fringe.store;

import com.fringe.model.Review;
import com.fringe.util.IdGenerator;
import com.fringe.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewStore {

    private static final String FILE = "data/reviews.txt";

    // Format: id|userId|bookId|content|createdAt|updatedAt

    private String serialize(Review r) {
        return r.getId() + "|" +
                r.getUserId() + "|" +
                r.getBookId() + "|" +
                r.getContent() + "|" +
                r.getCreatedAt() + "|" +
                (r.getUpdatedAt() == null ? "null" : r.getUpdatedAt());
    }

    private Review deserialize(String line) {
        String[] f = line.split("\\|", 6);
        return new Review(
                Integer.parseInt(f[0]),
                Integer.parseInt(f[1]),
                Integer.parseInt(f[2]),
                f[3],
                f[4],
                f[5].equals("null") ? null : f[5]
        );
    }

    private List<Review> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<Review> reviews = new ArrayList<>();
        for (String line : lines) reviews.add(deserialize(line));
        return reviews;
    }

    private void writeAll(List<Review> reviews) {
        List<String> lines = new ArrayList<>();
        for (Review r : reviews) lines.add(serialize(r));
        FileStore.writeLines(FILE, lines);
    }

    public Review addReview(int userId, int bookId, String content) {
        if (!ValidationUtil.isNotBlank(content))
            throw new IllegalArgumentException("Review content cannot be blank.");

        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        Review review = new Review(id, userId, bookId, content,
                LocalDateTime.now().toString(), null);
        FileStore.appendLine(FILE, serialize(review));
        return review;
    }

    public List<Review> getByBookId(int bookId) {
        return readAll().stream()
                .filter(r -> r.getBookId() == bookId)
                .collect(Collectors.toList());
    }

    public List<Review> getByUserId(int userId) {
        return readAll().stream()
                .filter(r -> r.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Review updateReview(int reviewId, int userId, String newContent) {
        if (!ValidationUtil.isNotBlank(newContent))
            throw new IllegalArgumentException("Review content cannot be blank.");

        List<Review> reviews = readAll();
        for (Review r : reviews) {
            if (r.getId() == reviewId) {
                if (r.getUserId() != userId)
                    throw new IllegalStateException("Not your review.");
                r.setContent(newContent);
                r.setUpdatedAt(LocalDateTime.now().toString());
                writeAll(reviews);
                return r;
            }
        }
        throw new IllegalArgumentException("Review not found.");
    }

    public void deleteReview(int reviewId, int userId) {
        List<Review> reviews = readAll();
        Review target = null;
        for (Review r : reviews) {
            if (r.getId() == reviewId) { target = r; break; }
        }
        if (target == null) throw new IllegalArgumentException("Review not found.");
        if (target.getUserId() != userId) throw new IllegalStateException("Not your review.");
        reviews.remove(target);
        writeAll(reviews);
    }
}
