package com.kjh.wsd.saramIn_crawling.auth.service;

import com.kjh.wsd.saramIn_crawling.auth.dto.*;
import com.kjh.wsd.saramIn_crawling.auth.exception.*;
import com.kjh.wsd.saramIn_crawling.auth.security.JwtUtil;
import com.kjh.wsd.saramIn_crawling.user.model.User;
import com.kjh.wsd.saramIn_crawling.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // 회원가입
    public void registerUser(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    // 로그인
    public void loginUser(String username, String password, HttpServletResponse response) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid username"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String accessToken = jwtUtil.generateToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        addCookie(response, "ACCESS_TOKEN", accessToken, jwtUtil.getAccessTokenExpiry());
        addCookie(response, "REFRESH_TOKEN", refreshToken, jwtUtil.getRefreshTokenExpiry());
    }

    // 리프레시 토큰 처리
    public void refreshAccessToken(String refreshToken, HttpServletResponse response) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidCredentialsException("Invalid or expired refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken);
        userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String newAccessToken = jwtUtil.generateToken(username);

        addCookie(response, "ACCESS_TOKEN", newAccessToken, jwtUtil.getAccessTokenExpiry());
    }

    public void updateProfile(ProfileUpdateRequest updateRequest, String accessToken) {
        if (!jwtUtil.validateToken(accessToken)) {
            throw new InvalidCredentialsException("Invalid or expired access token");
        }

        String username = jwtUtil.extractUsername(accessToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (updateRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
        }
        userRepository.save(user);
    }

    // 쿠키 생성 메서드
    private void addCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
}
