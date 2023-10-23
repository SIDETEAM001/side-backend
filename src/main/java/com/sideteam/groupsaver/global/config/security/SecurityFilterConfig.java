package com.sideteam.groupsaver.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideteam.groupsaver.global.auth.jwt.JwtTokenProvider;
import com.sideteam.groupsaver.global.auth.userdetails.UserDetailsServiceImpl;
import com.sideteam.groupsaver.global.filter.AuthenticationCheckFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
class SecurityFilterConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationCheckFilter authenticationCheckFilter() {
        return new AuthenticationCheckFilter(userDetailsService, jwtTokenProvider, objectMapper);
    }

}
