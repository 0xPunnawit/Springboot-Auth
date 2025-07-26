package com.punnawit.auth.mapper;

import com.punnawit.auth.dto.response.RegisterResponse;
import com.punnawit.auth.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    RegisterResponse toRegisterResponse(Users users);

}
