package com.aozbek.ecommerce.service;

import com.aozbek.ecommerce.dto.AuthenticationResponse;
import com.aozbek.ecommerce.dto.RefreshTokenRequestDto;
import com.aozbek.ecommerce.exception.InvalidRefreshToken;
import com.aozbek.ecommerce.model.RefreshToken;
import com.aozbek.ecommerce.model.User;
import com.aozbek.ecommerce.model.UserDetailsImp;
import com.aozbek.ecommerce.repository.RefreshTokenRepository;
import com.aozbek.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(UserRepository userRepository,
                               RefreshTokenRepository refreshTokenRepository,
                               JwtService jwtService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUsername(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(Duration.ofDays(30)))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void checkExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new InvalidRefreshToken();
        }
    }

    public AuthenticationResponse refreshJwtToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(refreshTokenRequestDto.getToken()).orElseThrow(InvalidRefreshToken::new);
        checkExpiration(refreshToken);
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(new UserDetailsImp(user));
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
