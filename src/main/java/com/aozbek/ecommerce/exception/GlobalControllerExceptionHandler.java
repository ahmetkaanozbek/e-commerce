package com.aozbek.ecommerce.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(CategoryNotExist.class)
    public ResponseEntity<String> handleCategoryNotExist(CategoryNotExist ex) {
        log.error("CategoryNotExist exception has been occurred. Message: "  + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotExist.class)
    public ResponseEntity<String> handleProductNotExist(ProductNotExist ex) {
        log.error("ProductNotExist exception has been occurred. Message: "  + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CartItemNotExist.class)
    public ResponseEntity<String> handleCartItemNotExist(CartItemNotExist ex) {
        log.error("CartItemNotExist exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<String> handleUsernameAlreadyExists(UsernameAlreadyExists ex) {
        log.error("UsernameAlreadyExists exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidRefreshToken.class)
    public ResponseEntity<String> handleInvalidRefreshToken(InvalidRefreshToken ex) {
        log.error("InvalidRefreshToken exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAction.class)
    public ResponseEntity<String> handleUnauthorizedAction(UnauthorizedAction ex) {
        log.error("UnauthorizedAction exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(RoleNotExist.class)
    public ResponseEntity<String> handleRoleNotExist(RoleNotExist ex) {
        log.error("RoleNotExist exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotExist.class)
    public ResponseEntity<String> handleUserNotExist(UserNotExist ex) {
        log.error("UserNotExist exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = "Error message: " + error.getDefaultMessage();
            errors.add(errorMessage);
        });
        log.error("MethodArgumentNotValid exception has been occurred. Message: " + errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
