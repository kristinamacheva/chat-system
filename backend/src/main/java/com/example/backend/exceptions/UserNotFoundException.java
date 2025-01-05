package com.example.backend.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("A user with this id doesn't exist: " + id);
    }
}
