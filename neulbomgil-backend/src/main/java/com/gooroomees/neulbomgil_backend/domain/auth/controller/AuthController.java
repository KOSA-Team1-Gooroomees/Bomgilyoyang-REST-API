package com.gooroomees.neulbomgil_backend.domain.auth.controller;

import com.gooroomees.neulbomgil_backend.domain.auth.dto.AuthenticationResponse;
import com.gooroomees.neulbomgil_backend.domain.auth.dto.LoginRequest;
import com.gooroomees.neulbomgil_backend.domain.auth.dto.RegisterRequest;
import com.gooroomees.neulbomgil_backend.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login") // 일반 로그인
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /*/api/auth/admin/login	관리자 로그인
    /api/auth/kakao	카카오 로그인 (OAuth)
    /api/auth/password/reset-email	이메일 인증 (SMTP)
    /api/auth/logout	로그아웃*/
}
