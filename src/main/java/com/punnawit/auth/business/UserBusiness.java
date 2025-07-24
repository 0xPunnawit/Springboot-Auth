package com.punnawit.auth.business;

import com.punnawit.auth.dto.request.RegisterRequest;
import com.punnawit.auth.dto.response.RegisterResponse;
import com.punnawit.auth.entity.Users;
import com.punnawit.auth.mapper.UserMapper;
import com.punnawit.auth.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserBusiness {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserBusiness(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public RegisterResponse register(RegisterRequest request) {

        Users register = userService.register(request);

        return userMapper.toRegisterResponse(register);

    }

}
