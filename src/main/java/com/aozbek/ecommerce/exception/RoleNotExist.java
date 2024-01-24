package com.aozbek.ecommerce.exception;

public class RoleNotExist extends RuntimeException {
    public RoleNotExist() {
        super("No available role with this id.");
    }
}
