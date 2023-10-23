package com.sideteam.groupsaver.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sideteam.groupsaver.global.auth.jwt.JwtTokenProvider;
import com.sideteam.groupsaver.global.auth.userdetails.UserDetailsServiceImpl;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationCheckFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            checkAccessToken(request);
        } catch (AuthErrorException authErrorException) {
            log.warn("auth 인증에 실패했습니다!", authErrorException);
            sendError(response, authErrorException);
            return;
        } catch (Exception exception) {
            log.warn("인증에 실패했습니다!", exception);
            sendError(response, exception);
            return;
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

    private void sendError(HttpServletResponse response, AuthErrorException authErrorException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final var errorResponse = ErrorResponse.builder(authErrorException, authErrorException.getErrorCode().getHttpStatus(), authErrorException.getCausedBy())
                .titleMessageCode(authErrorException.getMessage())
                .detail(authErrorException.getCausedBy())
                .build();

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

    private void sendError(HttpServletResponse response, Exception exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final var errorResponse = ErrorResponse.builder(exception, HttpStatus.UNAUTHORIZED, "?")
                .titleMessageCode(exception.getMessage())
                .detail(exception.getMessage())
                .build();

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

}
