package com.aditya.user_service.service;

import com.aditya.user_service.config.JwtUtil;
import com.aditya.user_service.dto.LoginRequest;
import com.aditya.user_service.dto.RegisterRequest;
import com.aditya.user_service.entity.User;
import com.aditya.user_service.exception.CustomException;
import com.aditya.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    public String register(RegisterRequest request) throws CustomException {

        log.info("Registering user with email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException("User Already Exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        log.info("User Saved Successfully with id: {}", user.getId());

        return "User Created";
    }

    public String login(LoginRequest request) throws CustomException {

        log.info("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException("User not found")
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}