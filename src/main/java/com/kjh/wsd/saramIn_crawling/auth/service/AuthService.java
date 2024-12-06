package com.kjh.wsd.saramIn_crawling.auth.service;

import com.kjh.wsd.saramIn_crawling.auth.exception.InvalidCredentialsException;
import com.kjh.wsd.saramIn_crawling.auth.security.JwtUtil;
import com.kjh.wsd.saramIn_crawling.auth.exception.UserNotFoundException;
import com.kjh.wsd.saramIn_crawling.user.model.User;
import com.kjh.wsd.saramIn_crawling.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public String registerUser(String username, String email, String password) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return "User registered successfully.";
    }

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid username"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return jwtUtil.generateToken(username);
    }
}
