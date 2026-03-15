package com.javacore.spring_api_app.controller;

import com.javacore.spring_api_app.dto.request.user.LoginUserRequest;
import com.javacore.spring_api_app.dto.request.user.RegisterUserRequest;
import com.javacore.spring_api_app.dto.response.LoginUserResponse;
import com.javacore.spring_api_app.dto.response.RegisterUserResponse;
import com.javacore.spring_api_app.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterUserResponse register(@RequestBody @Valid RegisterUserRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginUserResponse login(@RequestBody @Valid LoginUserRequest request) {
        return authService.login(request);
    }
}