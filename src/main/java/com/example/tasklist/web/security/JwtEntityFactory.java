package com.example.tasklist.web.security;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtEntityFactory {

    public static JwtEntity create(final User user) {
        return JwtEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .password(user.getPassword())
                .authorities(mapToGrantedAuthorities(
                        new ArrayList<>(user.getRoles())
                ))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(
            final List<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
