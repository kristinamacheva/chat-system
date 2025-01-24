package com.example.backend.exceptions;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {
        super("User does not have the required role to perform this operation.");
    }
}
