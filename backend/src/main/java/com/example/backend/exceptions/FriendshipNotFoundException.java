package com.example.backend.exceptions;

public class FriendshipNotFoundException extends RuntimeException {

    public FriendshipNotFoundException() {
        super("Friendship not found");
    }
}
