package com.gooroomees.neulbomgil_backend.domain.auth.service;

import com.gooroomees.neulbomgil_backend.domain.auth.dto.JwtTokenResponse;
import com.gooroomees.neulbomgil_backend.domain.auth.dto.LoginRequest;
import com.gooroomees.neulbomgil_backend.domain.auth.dto.RegisterRequest;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.RefreshToken;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.Role;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.Status;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.RefreshTokenRepository;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserAuthRepository;
import com.gooroomees.neulbomgil_backend.global.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String register(RegisterRequest registerRequest) {
        UserAuth user = UserAuth.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .role(Role.USER)
                .status(Status.ACTIVE) // 테스트를 위해 ACTIVE로 설정, 추후 메일 인증해야 활성화로 교체
                .build();
        userRepository.save(user);

        return "User registered";
    }

    // public String verify() { }

    public JwtTokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserAuth user = userRepository.findByEmail(request.getEmail()).orElseThrow();
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

/*/api/auth/admin/login	관리자 로그인
    // /api/auth/kakao	카카오 로그인 (OAuth)
/api/auth/password/reset-email	이메일 인증 (SMTP)
    /api/auth/logout	로그아웃*/
    public void logout(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken))
            throw new IllegalArgumentException("토큰이 아닌 값이 넘어옴");

        // 토큰의 type 체크
        if (!jwtProvider.isRefreshToken(refreshToken))
            throw new IllegalArgumentException("해당 토큰은 refresh 토큰이 아님");

        Long userId = jwtProvider.extractUserId(refreshToken);
        refreshTokenRepository.deleteById(userId);
    }

    // 전달받은 리프레시 토큰을 검사하고 유효하면 액세스 토큰을 생성해줌
    public String createNewAccessToken(String refreshToken) {
        if (!jwtProvider.isTokenValid(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        UserAuth user = userService.findById(userId);

        return jwtProvider.generateAccessToken(user);
    }

}
