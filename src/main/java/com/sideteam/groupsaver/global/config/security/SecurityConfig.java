package com.sideteam.groupsaver.global.config.security;

import com.sideteam.groupsaver.global.auth.AuthConstants;
import com.sideteam.groupsaver.global.auth.entrypoint.AuthEntryPointJwt;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@EnableMethodSecurity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
class SecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;

    private final SecurityAdapterConfig securityAdapterConfig;


    private static final String[] publicEndpoints = {
            // API
            "/api/v1/auth/**",
            "/api/v1/test/**",
    };

    private static final String[] publicReadOnlyPublicEndpoints = {
    };


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(getPublicStaticPath()).permitAll()
                        .requestMatchers(convertToAntPathMatcher(publicEndpoints)).permitAll()
                        .requestMatchers(convertToAntPathMatcher(publicReadOnlyPublicEndpoints, HttpMethod.GET)).permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint(unauthorizedHandler)
                );

        httpSecurity.apply(securityAdapterConfig);

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() { // Localhost 환경 cors
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
        configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION, AuthConstants.REFRESH_TOKEN));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private AntPathRequestMatcher getPublicStaticPath() {
        return new AntPathRequestMatcher(PathRequest.toStaticResources().atCommonLocations().toString());
    }

    private AntPathRequestMatcher[] convertToAntPathMatcher(String[] paths) {
        return Stream.of(paths)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
    }

    private AntPathRequestMatcher[] convertToAntPathMatcher(String[] paths, HttpMethod httpMethod) {
        return Stream.of(paths)
                .map(path -> new AntPathRequestMatcher(path, httpMethod.name()))
                .toArray(AntPathRequestMatcher[]::new);
    }

}
