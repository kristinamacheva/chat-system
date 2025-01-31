package com.example.backend.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for hashing passwords using BCrypt.
 */
public class PasswordUtils {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
