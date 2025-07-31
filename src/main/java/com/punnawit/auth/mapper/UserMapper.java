package com.punnawit.auth.mapper;

import com.punnawit.auth.dto.response.ProfileResponse;
import com.punnawit.auth.dto.response.auth.RegisterResponse;
import com.punnawit.auth.entity.Role;
import com.punnawit.auth.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RegisterResponse toRegisterResponse(Users user);

    ProfileResponse toProfileResponse(Users user);

    default String map(Role role) {
        return role != null ? role.getRole().name() : null;  // จะคืนค่าชื่อของ Role เป็น String
    }
}
