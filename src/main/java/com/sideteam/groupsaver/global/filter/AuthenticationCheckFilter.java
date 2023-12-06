package com.sideteam.groupsaver.global.filter;

import com.sideteam.groupsaver.global.auth.jwt.JwtTokenProvider;
import com.sideteam.groupsaver.global.auth.userdetails.UserDetailsServiceImpl;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationCheckFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationEntryPoint authenticationEntryPoint;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            checkAccessToken(request);
        } catch (AuthErrorException authErrorException) {
            log.warn("auth 인증에 실패했습니다!", authErrorException);
            authenticationEntryPoint.commence(request, response, authErrorException);
            return;
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }


    private void checkAccessToken(HttpServletRequest request) {
        final String accessToken = JwtTokenProvider.parseJwtFromRequest(request);

        if (StringUtils.hasText(accessToken)) {
            final String subject = jwtTokenProvider.getSubject(accessToken);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            setAuthentication(request, userDetails);
        }
    }

    private void setAuthentication(final HttpServletRequest request, final UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
