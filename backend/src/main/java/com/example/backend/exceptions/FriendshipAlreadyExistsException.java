package com.example.backend.exceptions;

public class FriendshipAlreadyExistsException extends RuntimeException {

    public FriendshipAlreadyExistsException() {
        super("The users are already friends");
    }
}
