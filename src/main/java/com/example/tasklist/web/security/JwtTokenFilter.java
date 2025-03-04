package com.example.tasklist.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @SneakyThrows
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) {
        String bearerToken = ((HttpServletRequest) request)
                .getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null && jwtTokenProvider.isValid(bearerToken)) {
            Authentication authentication =
                    jwtTokenProvider.getAuthentication(bearerToken);
            if (authentication != null) {
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
