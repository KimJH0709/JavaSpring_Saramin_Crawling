package com.kjh.wsd.saramIn_crawling.auth;

import com.kjh.wsd.saramIn_crawling.model.User;
import com.kjh.wsd.saramIn_crawling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            throw new RuntimeException("Invalid username or password.");
        }

        User user = existingUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password.");
        }

        // JWT 토큰 발급
        return jwtUtil.generateToken(user.getUsername());
    }
}
