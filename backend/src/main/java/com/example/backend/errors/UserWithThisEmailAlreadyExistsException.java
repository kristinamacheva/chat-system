package com.example.backend.errors;

public class UserWithThisEmailAlreadyExistsException extends RuntimeException {

    public UserWithThisEmailAlreadyExistsException(String email) {
        super("A user with this email already exists: " + email);
    }
}
