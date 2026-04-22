package com.fringe.store;

import com.fringe.model.Rating;
import com.fringe.util.IdGenerator;
import com.fringe.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RatingStore {

    private static final String FILE = "data/ratings.txt";

    // Format: id|userId|bookId|stars|createdAt

    private String serialize(Rating r) {
        return r.getId() + "|" + r.getUserId() + "|" +
                r.getBookId() + "|" + r.getStars() + "|" + r.getCreatedAt();
    }

    private Rating deserialize(String line) {
        String[] f = line.split("\\|", 5);
        return new Rating(
                Integer.parseInt(f[0]),
                Integer.parseInt(f[1]),
                Integer.parseInt(f[2]),
                Integer.parseInt(f[3]),
                f[4]
        );
    }

    private List<Rating> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<Rating> ratings = new ArrayList<>();
        for (String line : lines) ratings.add(deserialize(line));
        return ratings;
    }

    private void writeAll(List<Rating> ratings) {
        List<String> lines = new ArrayList<>();
        for (Rating r : ratings) lines.add(serialize(r));
        FileStore.writeLines(FILE, lines);
    }

    public void rateBook(int userId, int bookId, int stars) {
        if (!ValidationUtil.isValidRating(stars))
            throw new IllegalArgumentException("Stars must be between 1 and 5.");

        List<Rating> ratings = readAll();
        for (Rating r : ratings) {
            if (r.getUserId() == userId && r.getBookId() == bookId) {
                r.setStars(stars);
                writeAll(ratings);
                new BookStore().updateAvgRating(bookId);
                return;
            }
        }

        // No existing rating — add new
        int id = IdGenerator.nextId(FileStore.readLines(FILE));
        Rating rating = new Rating(id, userId, bookId, stars, LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(rating));
        new BookStore().updateAvgRating(bookId);
    }

    public Optional<Rating> getUserRating(int userId, int bookId) {
        return readAll().stream()
                .filter(r -> r.getUserId() == userId && r.getBookId() == bookId)
                .findFirst();
    }

    public double getAverageForBook(int bookId) {
        List<Rating> ratings = readAll().stream()
                .filter(r -> r.getBookId() == bookId)
                .collect(Collectors.toList());
        if (ratings.isEmpty()) return 0.0;
        int sum = ratings.stream().mapToInt(Rating::getStars).sum();
        return (double) sum / ratings.size();
    }
}