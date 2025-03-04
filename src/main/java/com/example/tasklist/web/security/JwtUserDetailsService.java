package com.example.tasklist.web.security;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(final String username) {
        User user = userService.getByUsername(username);
        return JwtEntityFactory.create(user);
    }
}
