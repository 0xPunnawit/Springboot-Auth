package com.punnawit.auth.controller;

import com.punnawit.auth.business.UserBusiness;
import com.punnawit.auth.dto.request.auth.LoginRequest;
import com.punnawit.auth.dto.request.auth.RegisterRequest;
import com.punnawit.auth.dto.response.auth.RegisterResponse;
import com.punnawit.auth.exception.BaseException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult result) throws BaseException {

        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            result.getAllErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append(", ");
            });
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        RegisterResponse register = userBusiness.register(request);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userBusiness.login(request);
        return ResponseEntity.ok(token);
    }

}
