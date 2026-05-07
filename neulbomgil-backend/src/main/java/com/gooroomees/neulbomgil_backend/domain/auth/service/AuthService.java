package com.gooroomees.neulbomgil_backend.domain.auth.service;

import com.gooroomees.neulbomgil_backend.domain.auth.dto.AuthenticationResponse;
import com.gooroomees.neulbomgil_backend.domain.auth.dto.LoginRequest;
import com.gooroomees.neulbomgil_backend.domain.auth.dto.RegisterRequest;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.Role;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.Status;
import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.auth.repository.UserAuthRepository;
import com.gooroomees.neulbomgil_backend.global.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAuthRepository userAuthRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest registerRequest) {
        UserAuth userAuth = UserAuth.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .status(Status.ACTIVE) // 테스트를 위해 ACTIVE로 설정, 추후 메일 인증해야 활성화로 교체
                .build();
        userAuthRepository.save(userAuth);

        return "User registered";
    }

    // public String verify() { }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserAuth userAuth = userAuthRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!userAuth.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("Account is not active");
        }

        String token = jwtProvider.generateToken(userAuth);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

/*/api/auth/admin/login	관리자 로그인
    // /api/auth/kakao	카카오 로그인 (OAuth)
/api/auth/password/reset-email	이메일 인증 (SMTP)
/api/auth/logout	로그아웃*/
}
