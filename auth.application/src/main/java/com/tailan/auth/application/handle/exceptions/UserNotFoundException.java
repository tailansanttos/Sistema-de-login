package com.tailan.auth.application.handle.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super(email);
    }
}
