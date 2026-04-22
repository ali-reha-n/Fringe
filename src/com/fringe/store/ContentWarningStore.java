package com.fringe.store;

import com.fringe.model.Book;
import com.fringe.util.IdGenerator;
import com.fringe.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class BookStore {

    private static final String FILE = "data/books.txt";

    // Format: id|title|author|genre|description|coverImageUrl|publishedYear|isbn|avgRating

    private String serialize(Book b) {
        return b.getId() + "|" +
                b.getTitle() + "|" +
                b.getAuthor() + "|" +
                b.getGenre() + "|" +
                (b.getDescription() == null ? "null" : b.getDescription()) + "|" +
                (b.getCoverImageUrl() == null ? "null" : b.getCoverImageUrl()) + "|" +
                b.getPublishedYear() + "|" +
                (b.getIsbn() == null ? "null" : b.getIsbn()) + "|" +
                b.getAvgRating();
    }

    private Book deserialize(String line) {
        String[] f = line.split("\\|", 9);
        return new Book(
                Integer.parseInt(f[0]),
                f[1],
                f[2],
                f[3],
                f[4].equals("null") ? null : f[4],
                f[5].equals("null") ? null : f[5],
                Integer.parseInt(f[6]),
                f[7].equals("null") ? null : f[7],
                Double.parseDouble(f[8])
        );
    }

    public List<Book> getAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<Book> books = new ArrayList<>();
        for (String line : lines) books.add(deserialize(line));
        return books;
    }

    private void writeAll(List<Book> books) {
        List<String> lines = new ArrayList<>();
        for (Book b : books) lines.add(serialize(b));
        FileStore.writeLines(FILE, lines);
    }

    public Book addBook(String title, String author, String genre, String description,
                        String coverImageUrl, int publishedYear, String isbn) {
        if (!ValidationUtil.isNotBlank(title))   throw new IllegalArgumentException("Title cannot be blank.");
        if (!ValidationUtil.isNotBlank(author))  throw new IllegalArgumentException("Author cannot be blank.");
        if (!ValidationUtil.isNotBlank(genre))   throw new IllegalArgumentException("Genre cannot be blank.");

        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        Book book = new Book(id, title, author, genre,
                description, coverImageUrl, publishedYear, isbn, 0.0);
        FileStore.appendLine(FILE, serialize(book));
        return book;
    }

    public Optional<Book> findById(int id) {
        return getAll().stream().filter(b -> b.getId() == id).findFirst();
    }

    public List<Book> getByGenre(String genre) {
        return getAll().stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public List<Book> searchByTitle(String query) {
        String q = query.toLowerCase();
        return getAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String query) {
        String q = query.toLowerCase();
        return getAll().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<Book> searchAll(String query) {
        String q = query.toLowerCase();
        List<Book> result = new ArrayList<>();
        List<Integer> seen = new ArrayList<>();
        for (Book b : getAll()) {
            if (b.getTitle().toLowerCase().contains(q) ||
                    b.getAuthor().toLowerCase().contains(q) ||
                    b.getGenre().toLowerCase().contains(q)) {
                if (!seen.contains(b.getId())) {
                    result.add(b);
                    seen.add(b.getId());
                }
            }
        }
        return result;
    }

    public List<String> getAllGenres() {
        return getAll().stream()
                .map(Book::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public void updateAvgRating(int bookId) {
        List<String> ratingLines = FileStore.readLines("data/ratings.txt");
        int total = 0, count = 0;
        for (String line : ratingLines) {
            String[] f = line.split("\\|");
            if (Integer.parseInt(f[2]) == bookId) {
                total += Integer.parseInt(f[3]);
                count++;
            }
        }
        double avg = count == 0 ? 0.0 : (double) total / count;

        List<Book> books = getAll();
        for (Book b : books) {
            if (b.getId() == bookId) {
                b.setAvgRating(Math.round(avg * 10.0) / 10.0);
                break;
            }
        }
        writeAll(books);
    }
}

