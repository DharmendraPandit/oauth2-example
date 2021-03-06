package com.example.auth;

public enum UserErrorMessages {
    DUPLICATE_USER("User already exists");

    private String message;

    UserErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
