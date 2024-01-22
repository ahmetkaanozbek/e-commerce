package com.aozbek.ecommerce.controller;

import com.aozbek.ecommerce.dto.AuthenticationResponse;
import com.aozbek.ecommerce.dto.LoginRequest;
import com.aozbek.ecommerce.dto.RefreshTokenRequestDto;
import com.aozbek.ecommerce.dto.RegisterRequest;
import com.aozbek.ecommerce.service.AuthService;
import com.aozbek.ecommerce.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/auth")
public class AuthenticationController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(
            AuthService authService,
            RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body("A registration has been made successfully.");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshJwtToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        AuthenticationResponse authenticationResponse = refreshTokenService.refreshJwtToken(refreshTokenRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }
}