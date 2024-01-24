package com.aozbek.ecommerce.exception;

public class UserNotExist extends RuntimeException{
    public UserNotExist() {
        super("No User exists with this id.");
    }
}
