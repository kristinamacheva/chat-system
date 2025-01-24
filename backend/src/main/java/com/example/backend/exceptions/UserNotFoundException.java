package com.example.backend.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("This user doesn't exist");
    }
}
