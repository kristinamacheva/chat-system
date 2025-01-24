package com.example.backend.exceptions;

public class MembershipAlreadyExistsException extends RuntimeException {

    public MembershipAlreadyExistsException() {
        super("The user is a member of the channel");
    }
}
