package com.sideteam.groupsaver.global.config.security;

import com.sideteam.groupsaver.domain.member.domain.MemberRole;
import com.sideteam.groupsaver.global.auth.userdetails.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
public class SecurityAuthConfig {

    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = getRoleHierarchy();
        log.info("Role Hierarchy: {}", hierarchy);
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }


    private String getRoleHierarchy() {
        List<String> roles = new ArrayList<>(MemberRole.ROLES.rank());
        Collections.reverse(roles);

        return IntStream.range(0, roles.size() - 1)
                .mapToObj(i -> roles.get(i) + " > " + roles.get(i + 1))
                .collect(joining(" "));
    }

}
