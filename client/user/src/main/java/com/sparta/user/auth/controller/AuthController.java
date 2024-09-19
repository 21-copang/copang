package com.sparta.user.auth.controller;

import com.sparta.user.auth.dto.UserLoginRequest;
import com.sparta.user.auth.dto.UserSignupRequest;
import com.sparta.user.auth.service.AuthService;
import com.sparta.user.common.ApiResponse;
import com.sparta.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ApiResponse<?> signup(@Valid @RequestBody UserSignupRequest request) {
        User user = authService.signup(request);
        return ApiResponse.success("OK", user.getId(), "회원가입 성공");
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody UserLoginRequest request) {
        String accessToken = authService.login(request);
        return ApiResponse.success("OK", accessToken, "로그인 성공");
    }

    @GetMapping("/getPermission/{id}")
    public String getPermission(@PathVariable UUID id) {
        return authService.getPermission(id).name();
    }

}
