package com.gooroomees.neulbomgil_backend.domain.auth.service;

import com.gooroomees.neulbomgil_backend.domain.auth.dto.*;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.*;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.AuthTokenRepository;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.RefreshTokenRepository;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserAuthRepository;
import com.gooroomees.neulbomgil_backend.global.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RefreshTokenService refreshTokenService;
    private final UserAuthRepository userAuthRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthService userAuthService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailService emailService;
    private final AuthTokenRepository authTokenRepository;

    @Transactional
    public String register(RegisterRequest registerRequest) {
        UserAuth user = UserAuth.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .role(Role.USER)
                .status(Status.INACTIVE)
                .build();
        userAuthRepository.save(user);
        emailService.sendAuthLink(user.getUserId());

        return "User registered. Please check your email for verification.";
    }

    @Transactional
    public void verifyEmail(String token) {
        AuthToken authToken = authTokenRepository.findByAuthTokenAndType(token, TokenType.SIGNUP)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증 토큰입니다."));

        if (authToken.getExpiration().isBefore(LocalDateTime.now())) {
            authTokenRepository.delete(authToken);
            throw new IllegalArgumentException("만료된 인증 토큰입니다.");
        }

        UserAuth user = userAuthService.findById(authToken.getUserId());
        user.activate();
        userAuthRepository.save(user);
        authTokenRepository.delete(authToken);
    }

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        UserAuth user = userAuthRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        emailService.sendPasswordResetLink(user.getUserId());
    }

    @Transactional
    public void resetPassword(PasswordUpdateRequest request) {
        AuthToken authToken = authTokenRepository.findByAuthTokenAndType(request.getToken(), TokenType.PASSWORD_RESET)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않거나 만료된 비밀번호 초기화 토큰입니다."));

        if (authToken.getExpiration().isBefore(LocalDateTime.now())) {
            authTokenRepository.delete(authToken);
            throw new IllegalArgumentException("만료된 인증 토큰입니다.");
        }

        UserAuth user = userAuthService.findById(authToken.getUserId());
        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        userAuthRepository.save(user);
        authTokenRepository.delete(authToken);
    }

    public JwtTokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserAuth user = userAuthRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("Account is not active");
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);

        refreshTokenRepository.save(new RefreshToken(user.getUserId(), refreshToken));

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken))
            throw new IllegalArgumentException("토큰이 아닌 값이 넘어옴");

        if (!jwtProvider.isRefreshToken(refreshToken))
            throw new IllegalArgumentException("해당 토큰은 refresh 토큰이 아님");

        Long userId = jwtProvider.extractUserId(refreshToken);
        refreshTokenRepository.deleteById(userId);
    }

    public String createNewAccessToken(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        UserAuth user = userAuthService.findById(userId);

        return jwtProvider.generateAccessToken(user);
    }
}
