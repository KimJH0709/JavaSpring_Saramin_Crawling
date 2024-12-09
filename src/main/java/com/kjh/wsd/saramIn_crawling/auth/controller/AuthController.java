package com.kjh.wsd.saramIn_crawling.auth.controller;

import com.kjh.wsd.saramIn_crawling.auth.dto.*;
import com.kjh.wsd.saramIn_crawling.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);  // 로그인 성공 후 JWT 토큰 반환
    }

    // 리프레시 토큰
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        String newToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(newToken);
    }

    // 프로필 업데이트 (비밀번호 변경)
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdateRequest updateRequest) {
        authService.updateProfile(updateRequest);  // 현재 로그인된 사용자의 비밀번호 변경
        return ResponseEntity.ok("Profile updated successfully.");
    }
}
