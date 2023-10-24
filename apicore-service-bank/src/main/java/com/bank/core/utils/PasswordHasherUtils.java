package com.bank.core.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Utility class for encrypting and verifying passwords using the BCrypt hashing algorithm.
 */
@Component
public class PasswordHasherUtils {

    /**
     * Encrypts a plaintext password using the BCrypt hashing algorithm.
     *
     * @param password The plaintext password to be encrypted.
     * @return The hashed and salted password.
     */
    public static String encryptPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

    /**
     * Verifies if a plaintext password matches a previously hashed and salted password.
     *
     * @param password       The plaintext password to be verified.
     * @param hashedPassword The previously hashed and salted password.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}

