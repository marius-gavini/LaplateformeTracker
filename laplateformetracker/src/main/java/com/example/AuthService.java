package com.example;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private static final Map<String, User> USERS = new HashMap<>();

    static {
        USERS.put("admin", new User("admin", "admin", "ADMIN"));
        USERS.put("eleve", new User("eleve", "eleve", "STUDENT"));
    }

    public static User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        User user = USERS.get(username.trim());
        if (user == null) {
            return null;
        }

        return user.getPassword().equals(password) ? user : null;
    }
}
