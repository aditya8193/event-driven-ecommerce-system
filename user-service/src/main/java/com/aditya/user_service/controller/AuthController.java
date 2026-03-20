package com.aditya.user_service.controller;

import com.aditya.user_service.dto.ApiResponse;
import com.aditya.user_service.dto.LoginRequest;
import com.aditya.user_service.dto.RegisterRequest;
import com.aditya.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {

        return ApiResponse.<String>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.CREATED.value())
                .message("User Registered Successfully")
                .data(userService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {

        return ApiResponse.<String>builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Login Successful")
                .data(userService.login(request))
                .build();
    }
}