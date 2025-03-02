package com.example.tasklist.web.dto.user;

import com.example.tasklist.web.validation.OnCreate;
import com.example.tasklist.web.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @NotNull(message = "Id must be not null.",
            groups = OnUpdate.class)
    Long id;

    @NotNull(message = "Name must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    String name;

    @NotNull(message = "Username must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be smaller than 255 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.",
            groups = {OnCreate.class, OnUpdate.class})
    String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.",
            groups = OnCreate.class)
    String passwordConfirmation;
}
