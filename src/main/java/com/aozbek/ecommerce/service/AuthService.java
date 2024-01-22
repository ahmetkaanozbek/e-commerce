package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.AuthenticationResponse;
import com.aozbek.ecommerce.dto.LoginRequest;
import com.aozbek.ecommerce.dto.RegisterRequest;
import com.aozbek.ecommerce.exception.UsernameAlreadyExists;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.model.UserDetailsImp;
import com.aozbek.ecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void signup(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        User user = userRepository.getUserByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("No user exists with this username.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        UserDetailsImp userDetailsImp = new UserDetailsImp(user);
        String jwtToken = jwtService.generateToken(userDetailsImp);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User authenticatedUser = userRepository.getUserByUsername(username);
        if (authenticatedUser == null) {
            throw new UsernameNotFoundException("No user exists with this username.");
        }
        return authenticatedUser;
    }
}

