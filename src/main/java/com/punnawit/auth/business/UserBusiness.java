package com.punnawit.auth.business;

import com.punnawit.auth.Util.JwtUtil;
import com.punnawit.auth.dto.request.auth.LoginRequest;
import com.punnawit.auth.dto.request.auth.RegisterRequest;
import com.punnawit.auth.dto.response.ProfileResponse;
import com.punnawit.auth.dto.response.auth.RegisterResponse;
import com.punnawit.auth.entity.Users;
import com.punnawit.auth.exception.BaseException;
import com.punnawit.auth.exception.UserException;
import com.punnawit.auth.mapper.UserMapper;
import com.punnawit.auth.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public UserBusiness(UserService userService, UserMapper userMapper, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    // ============ REGISTER ============
    public RegisterResponse register(RegisterRequest request) throws BaseException {

        Users user = userService.register(request);

        return userMapper.toRegisterResponse(user);
    }

    // ============ LOGIN ============
    public String login(LoginRequest request) {
        Optional<Users> byEmail = userService.findByEmail(request.getEmail());
        if (byEmail.isEmpty()) {
            throw new IllegalArgumentException("Email not found");
        }

        Users users = byEmail.get();
        if (!userService.matchPassword(request.getPassword(), users.getPassword())) {
            throw new IllegalArgumentException("Password not match");
        }

        return jwtUtil.tokenize(users);
    }

    // ============ PROFILE ============
    public ProfileResponse profile(Authentication authentication) {

        String userId = authentication.getName();

        Users user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userMapper.toProfileResponse(user);
    }


}
