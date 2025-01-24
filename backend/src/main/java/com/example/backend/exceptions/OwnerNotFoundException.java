package com.example.backend.exceptions;

public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException(int id) {
        super("Owner not found for channel " + id);
    }
}
