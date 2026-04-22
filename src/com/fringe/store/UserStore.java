package com.fringe.store;

import com.fringe.model.User;
import com.fringe.util.IdGenerator;
import com.fringe.util.PasswordUtil;
import com.fringe.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStore {

    private static final String FILE = "data/users.txt";

    // Format: id|username|email|passwordHash|bio|profilePicture|createdAt

    private String serialize(User u) {
        return u.getId() + "|" +
                u.getUsername() + "|" +
                u.getEmail() + "|" +
                u.getPasswordHash() + "|" +
                (u.getBio() == null ? "null" : u.getBio()) + "|" +
                (u.getProfilePicture() == null ? "null" : u.getProfilePicture()) + "|" +
                u.getCreatedAt();
    }

    private User deserialize(String line) {
        String[] f = line.split("\\|", 7);
        return new User(
                Integer.parseInt(f[0]),
                f[1],
                f[2],
                f[3],
                f[4].equals("null") ? null : f[4],
                f[5].equals("null") ? null : f[5],
                f[6]
        );
    }

    private List<User> readAll() {
        List<String> lines = FileStore.readLines(FILE);
        List<User> users = new ArrayList<>();
        for (String line : lines) users.add(deserialize(line));
        return users;
    }

    private void writeAll(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) lines.add(serialize(u));
        FileStore.writeLines(FILE, lines);
    }

    public User register(String username, String email, String password) {
        if (!ValidationUtil.isValidUsername(username))
            throw new IllegalArgumentException("Invalid username. Use 3-20 alphanumeric characters or underscores.");
        if (!ValidationUtil.isValidEmail(email))
            throw new IllegalArgumentException("Invalid email address.");
        if (!ValidationUtil.isValidPassword(password))
            throw new IllegalArgumentException("Password must be at least 8 characters.");

        List<String> lines = FileStore.readLines(FILE);
        for (String line : lines) {
            String[] f = line.split("\\|", 7);
            if (f[1].equalsIgnoreCase(username))
                throw new IllegalStateException("Username already taken.");
            if (f[2].equalsIgnoreCase(email))
                throw new IllegalStateException("Email already registered.");
        }

        int id = IdGenerator.nextId(lines);
        User user = new User(id, username, email, PasswordUtil.hash(password),
                null, null, LocalDateTime.now().toString());
        FileStore.appendLine(FILE, serialize(user));
        return user;
    }

    public User login(String usernameOrEmail, String password) {
        List<User> users = readAll();
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(usernameOrEmail) ||
                    u.getEmail().equalsIgnoreCase(usernameOrEmail)) {
                if (!PasswordUtil.verify(password, u.getPasswordHash()))
                    throw new IllegalArgumentException("Wrong password.");
                return u;
            }
        }
        throw new IllegalArgumentException("User not found.");
    }

    public Optional<User> findById(int id) {
        return readAll().stream().filter(u -> u.getId() == id).findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return readAll().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return readAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public User updateProfile(int userId, String bio, String profilePicture) {
        List<User> users = readAll();
        for (User u : users) {
            if (u.getId() == userId) {
                u.setBio(bio);
                u.setProfilePicture(profilePicture);
                writeAll(users);
                return u;
            }
        }
        throw new IllegalArgumentException("User not found.");
    }
}
