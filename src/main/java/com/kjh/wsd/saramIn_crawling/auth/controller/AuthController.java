package com.kjh.wsd.saramIn_crawling.auth.controller;

import com.kjh.wsd.saramIn_crawling.auth.dto.*;
import com.kjh.wsd.saramIn_crawling.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Parameter(description = "회원가입 요청 데이터", required = true)
            @RequestBody RegisterRequest registerRequest
    ) {
        authService.registerUser(registerRequest);
        return ResponseEntity.ok("User registered successfully.");
    }

    @Operation(summary = "로그인", description = "사용자 이름과 비밀번호로 로그인하고 JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "로그인 요청 데이터", required = true)
            @RequestBody LoginRequest loginRequest
    ) {
        String token = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "리프레시 토큰", description = "기존 토큰을 기반으로 새로운 JWT 토큰을 발급받습니다.")
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(
            @Parameter(description = "Authorization 헤더에 전달된 리프레시 토큰", required = true)
            @RequestHeader("Authorization") String refreshToken
    ) {
        String newToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(newToken);
    }

    @Operation(summary = "비밀번호 변경", description = "현재 사용자의 비밀번호를 업데이트합니다.")
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @Parameter(description = "프로필 업데이트 요청 데이터", required = true)
            @RequestBody ProfileUpdateRequest updateRequest
    ) {
        authService.updateProfile(updateRequest);
        return ResponseEntity.ok("Profile updated successfully.");
    }
}
