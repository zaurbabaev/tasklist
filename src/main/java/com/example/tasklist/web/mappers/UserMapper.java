package com.example.tasklist.web.mappers;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.web.dto.user.UserDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {

    @Override
    @InheritConfiguration
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toEntity(UserDto dto);

    @Override
    @InheritInverseConfiguration
    UserDto toDto(User entity);
}
