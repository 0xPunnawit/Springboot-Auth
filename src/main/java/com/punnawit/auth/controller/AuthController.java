package com.punnawit.auth.controller;

import com.punnawit.auth.business.UserBusiness;
import com.punnawit.auth.dto.request.LoginRequest;
import com.punnawit.auth.dto.request.RegisterRequest;
import com.punnawit.auth.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserBusiness userBusiness;

    public AuthController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse register = userBusiness.register(request);
        System.out.println("user: " + register);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userBusiness.login(request);
        return ResponseEntity.ok(token);
    }

}
