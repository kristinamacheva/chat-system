package com.example.backend.exceptions;

public class FriendInvitationNotFoundException extends RuntimeException {

    public FriendInvitationNotFoundException(int id) {
        super("An invitation with this id doesn't exist: " + id);
    }
}
