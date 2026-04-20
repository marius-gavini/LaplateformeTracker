package com.example;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void hashPasswordShouldReturnValidBCryptHash() {
        String plainPassword = "testPassword123";
        String hash = AuthService.hashPassword(plainPassword);

        assertNotNull(hash);
        assertNotEquals(plainPassword, hash);
        assertTrue(BCrypt.checkpw(plainPassword, hash));
    }

    @Test
    void hashPasswordShouldGenerateDifferentHashesForSamePassword() {
        String plainPassword = "samePassword";

        String hash1 = AuthService.hashPassword(plainPassword);
        String hash2 = AuthService.hashPassword(plainPassword);

        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotEquals(hash1, hash2, "Two generated hashes should not be identical because salts are different.");
        assertTrue(BCrypt.checkpw(plainPassword, hash1));
        assertTrue(BCrypt.checkpw(plainPassword, hash2));
    }
}
