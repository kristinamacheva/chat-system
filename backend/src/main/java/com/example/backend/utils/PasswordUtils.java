package com.example.backend.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
