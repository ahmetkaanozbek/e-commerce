package com.aozbek.ecommerce.exception;

public class UsernameAlreadyExists extends RuntimeException {
    public UsernameAlreadyExists() {
        super("This username is not available.");
    }
}
