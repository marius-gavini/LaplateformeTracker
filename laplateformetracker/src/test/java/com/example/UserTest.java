package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void gettersShouldReturnConstructorValues() {
        User user = new User("alice", "secret-hash", "admin");

        assertEquals("alice", user.getUsername());
        assertEquals("secret-hash", user.getPassword());
        assertEquals("admin", user.getRole());
    }
}
