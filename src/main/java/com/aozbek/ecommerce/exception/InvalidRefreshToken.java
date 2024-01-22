package com.aozbek.ecommerce.exception;

public class InvalidRefreshToken extends RuntimeException {
    public InvalidRefreshToken() {
        super("Refresh token is invalid.");
    }
}
