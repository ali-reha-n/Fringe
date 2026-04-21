package com.fringe.model;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String email;
    private String passwordHash;
    private String bio;
    private String profilePicture;
    private String createdAt;

    public User() {}

    public User(int id, String username, String email, String passwordHash,
                String bio, String profilePicture, String createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.bio = bio;
        this.profilePicture = profilePicture;
        this.createdAt = createdAt;
    }

    public int getId()
    { return id; }
    public void setId(int id)
    { this.id = id; }

    public String getUsername()
    { return username; }
    public void setUsername(String username)
    { this.username = username; }

    public String getEmail()
    { return email; }
    public void setEmail(String email)
    { this.email = email; }

    public String getPasswordHash()
    { return passwordHash; }
    public void setPasswordHash(String passwordHash)
    { this.passwordHash = passwordHash; }

    public String getBio()
    { return bio; }
    public void setBio(String bio)
    { this.bio = bio; }

    public String getProfilePicture()
    { return profilePicture; }
    public void setProfilePicture(String profilePicture)
    { this.profilePicture = profilePicture; }

    public String getCreatedAt()
    { return createdAt; }
    public void setCreatedAt(String createdAt)
    { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id == ((User) o).id;
    }

    @Override
    public int hashCode()
    { return Objects.hash(id); }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "'}";
    }
}
