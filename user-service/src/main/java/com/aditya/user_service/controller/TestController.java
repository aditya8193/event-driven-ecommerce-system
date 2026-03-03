package com.aditya.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello User!";
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello Admin!";
    }
}