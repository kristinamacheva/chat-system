package com.example.backend.exceptions;

public class ChannelNotFoundException extends RuntimeException {

    public ChannelNotFoundException(int id) {
        super("A channel with this id doesn't exist: " + id);
    }
}
