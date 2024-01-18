package com.sideteam.groupsaver.global.config.security;

import com.sideteam.groupsaver.global.auth.AuthConstants;
import com.sideteam.groupsaver.global.auth.entrypoint.AuthEntryPointJwt;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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

import java.util.List;
import java.util.stream.Stream;

@EnableMethodSecurity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
public class SecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;

    private final SecurityAdapterConfig securityAdapterConfig;

    @Value("${app.allow-origins}")
    private List<String> corsOrigins;

    private static final String[] publicEndpoints = {
            // API
            "/api/v1/auth/**",
            "/api/v1/test/**",

            // Swagger
            "/v3/api-docs/**",
            "/swagger-ui/**", // /swagger-ui/index.html
            "/swagger-resources/**",
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
                        .requestMatchers(getPublicEndpoints()).permitAll()
                        .requestMatchers(getReadOnlyPublicEndpoints()).permitAll()
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
        configuration.setAllowedOrigins(corsOrigins);
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "PUT", "PATCH", "DELETE"));
        configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION, AuthConstants.REFRESH_TOKEN));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private AntPathRequestMatcher getPublicStaticPath() {
        return new AntPathRequestMatcher(PathRequest.toStaticResources().atCommonLocations().toString());
    }

    private AntPathRequestMatcher[] getPublicEndpoints() {
        return Stream.of(publicEndpoints)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
    }

    private AntPathRequestMatcher[] getReadOnlyPublicEndpoints() {
        return Stream.of(SecurityConfig.publicReadOnlyPublicEndpoints)
                .map(path -> new AntPathRequestMatcher(path, HttpMethod.GET.name()))
                .toArray(AntPathRequestMatcher[]::new);
    }

}
