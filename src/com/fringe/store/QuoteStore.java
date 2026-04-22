package com.fringe.store;

import com.fringe.model.Quote;
import com.fringe.util.IdGenerator;
import com.fringe.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QuoteStore {

    private static final String FILE = "data/quotes.txt";

    // Format: id|userId|bookId|text|pageNumber|likes|createdAt

    private String serialize(Quote q) {
        return q.getId() + "|" +
                q.getUserId() + "|" +
                q.getBookId() + "|" +
                q.getText() + "|" +
                (q.getPageNumber() == null ? "null" : q.getPageNumber()) + "|" +
                q.getLikes() + "|" +
                q.getCreatedAt();
    }

    private Quote deserialize(String line) {
        String[] f = line.split("\\|", 7);
        return new Quote(
                Integer.parseInt(f[0]),
                Integer.parseInt(f[1]),
                Integer.parseInt(f[2]),
                f[3],
                f[4].equals("null") ? null : f[4],
                Integer.parseInt(f[5]),
                f[6]
        );
    }

    private List<Quote> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<Quote> quotes = new ArrayList<>();
        for (String line : lines) quotes.add(deserialize(line));
        return quotes;
    }

    private void writeAll(List<Quote> quotes) {
        List<String> lines = new ArrayList<>();
        for (Quote q : quotes) lines.add(serialize(q));
        FileStore.writeLines(FILE, lines);
    }

    public Quote postQuote(int userId, int bookId, String text, String pageNumber) {
        if (!ValidationUtil.isNotBlank(text))
            throw new IllegalArgumentException("Quote text cannot be blank.");

        List<String> lines = FileStore.readLines(FILE);
        int id = IdGenerator.nextId(lines);
        Quote quote = new Quote(id, userId, bookId, text,
                pageNumber, 0, LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(quote));
        return quote;
    }

    public List<Quote> getByBookId(int bookId) {
        return readAll().stream()
                .filter(q -> q.getBookId() == bookId)
                .collect(Collectors.toList());
    }

    public List<Quote> getByUserId(int userId) {
        return readAll().stream()
                .filter(q -> q.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public List<Quote> getFeed() {
        return readAll().stream()
                .sorted(Comparator.comparing(Quote::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public void likeQuote(int quoteId) {
        List<Quote> quotes = readAll();
        for (Quote q : quotes) {
            if (q.getId() == quoteId) {
                q.setLikes(q.getLikes() + 1);
                writeAll(quotes);
                return;
            }
        }
        throw new IllegalArgumentException("Quote not found.");
    }

    public void deleteQuote(int quoteId, int userId) {
        List<Quote> quotes = readAll();
        Quote target = null;
        for (Quote q : quotes) {
            if (q.getId() == quoteId) { target = q; break; }
        }
        if (target == null) throw new IllegalArgumentException("Quote not found.");
        if (target.getUserId() != userId) throw new IllegalStateException("Not your quote.");
        quotes.remove(target);
        writeAll(quotes);
    }
}