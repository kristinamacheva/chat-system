package com.example.backend.exceptions;

public class FriendInvitationAlreadyExistsException extends RuntimeException {

    public FriendInvitationAlreadyExistsException() {
        super("Friend invitation already exists");
    }
}
