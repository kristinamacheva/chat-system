package com.example.backend.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String role) {
        super("The role doesn't exist: " + role);
    }
}
