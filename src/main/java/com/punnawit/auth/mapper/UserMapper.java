package com.punnawit.auth.mapper;

import com.punnawit.auth.dto.response.RegisterResponse;
import com.punnawit.auth.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "name", target = "name")
    RegisterResponse toRegisterResponse(Users user);

}
