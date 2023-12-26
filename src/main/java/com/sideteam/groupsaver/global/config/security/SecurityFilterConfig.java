package com.sideteam.groupsaver.global.config.security;

import com.sideteam.groupsaver.global.auth.entrypoint.AuthEntryPointJwt;
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
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthenticationCheckFilter authenticationCheckFilter() {
        return new AuthenticationCheckFilter(userDetailsService, jwtTokenProvider, authEntryPointJwt);
    }

}
