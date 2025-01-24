package com.example.backend.exceptions;

public class MembershipNotFoundException extends RuntimeException {

    public MembershipNotFoundException(int channelId, int userId) {
        super("Membership not found for channel " + channelId + " and user " + userId);
    }
}
