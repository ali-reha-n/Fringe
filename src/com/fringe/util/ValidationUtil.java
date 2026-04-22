package com.fringe.util;

public class ValidationUtil {

    public static boolean isValidUsername(String u) {
        if (u == null) return false;
        return u.matches("^[a-zA-Z0-9_]{3,20}$");
    }

    public static boolean isValidEmail(String e) {
        if (e == null) return false;
        int atIndex = e.indexOf('@');
        if (atIndex < 1) return false;
        String afterAt = e.substring(atIndex + 1);
        return afterAt.contains(".");
    }

    public static boolean isValidPassword(String p) {
        return p != null && p.length() >= 8;
    }

    public static boolean isNotBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isValidRating(int stars) {
        return stars >= 1 && stars <= 5;
    }
}
