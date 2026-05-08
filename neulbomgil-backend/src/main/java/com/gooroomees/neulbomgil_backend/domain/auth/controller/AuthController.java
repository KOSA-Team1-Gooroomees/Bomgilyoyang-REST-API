package com.gooroomees.neulbomgil_backend.domain.auth.controller;

import com.gooroomees.neulbomgil_backend.domain.auth.dto.*;
import com.gooroomees.neulbomgil_backend.domain.auth.service.AuthService;
import com.gooroomees.neulbomgil_backend.domain.auth.service.EmailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login") // 일반 로그인
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        JwtTokenResponse jwtTokenResponse = authService.login(request);

        // 리프레스 토큰을 HttpOnly 쿠키에 저장
        ResponseCookie cookie = ResponseCookie.from("refresh_token", jwtTokenResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true) // HTTPS 환경 권장
                .path("/api/auth/refresh") // 갱신 경로에서만 쿠키 전송
                .maxAge(604800000)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new LoginResponse(jwtTokenResponse.getAccessToken()));
    }

    /*/api/auth/admin/login	관리자 로그인
    /api/auth/kakao	카카오 로그인 (OAuth)
    /api/auth/password/reset-email	이메일 인증 (SMTP)
    /api/auth/logout	로그아웃*/

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authHeader,
            @CookieValue(name = "refresh_token") String refreshToken,
            HttpServletResponse response) {
        String accessToken = authHeader.substring(7);

        authService.logout(refreshToken);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0) // 쿠키 즉시 만료
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh") // 액세스 토큰 재발급
    public ResponseEntity<CreateAccessTokenResponse> createAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = authService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {

        // TODO: 저장소(Redis 또는 DB)에서 token을 조회합니다.
        // String email = redisTemplate.opsForValue().get(token);

        boolean isValidToken = true; // 실제로는 조회 결과를 바탕으로 판단

        if (isValidToken) {
            // TODO: 인증 성공 로직 처리 (예: DB의 회원 상태를 '인증됨'으로 변경)
            // TODO: 처리 완료 후 사용된 토큰 삭제

            return ResponseEntity.ok("이메일 인증이 완료되었습니다. 창을 닫고 로그인을 진행해주세요.");
            // 참고: 클라이언트(프론트엔드) 화면으로 보내고 싶다면
            // ResponseEntity.status(HttpStatus.FOUND).location(URI.create("프론트엔드 URL")).build();
            // 와 같이 리다이렉트 시킬 수도 있습니다.
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("유효하지 않거나 만료된 인증 링크입니다.");
        }
    }
}
