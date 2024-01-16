package com.sideteam.groupsaver.global.exception;

import com.sideteam.groupsaver.global.exception.auth.AuthErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import com.sideteam.groupsaver.global.exception.club.ClubErrorException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthErrorException(AuthErrorException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(ex.getErrorCode(), ex, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(ex.getErrorCode(), ex, request);
    }

    @ExceptionHandler(ClubErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleClubErrorException(ClubErrorException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(ex.getErrorCode(), ex, request);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(AuthErrorCode.ACCESS_DENIED, ex, request);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(AuthErrorCode.FAILED_AUTHENTICATION, ex, request);
    }

    @ExceptionHandler(BadCredentialsException.class) // 비밀번호가 틀린 경우
    public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(AuthErrorCode.FAILED_AUTHENTICATION, ex, request);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class) // 내부적으로 인증 처리를 할 수 없는 경우
    public ResponseEntity<ApiErrorResponse> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(AuthErrorCode.FAILED_AUTHENTICATION, ex, request);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpClientErrorException(InsufficientAuthenticationException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(AuthErrorCode.FAILED_AUTHENTICATION, ex, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return ApiErrorResponse.toResponseEntity(ApiErrorCode.FAILED_VALIDATION, ex, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        log.error("GlobalExceptionHandler에 정의되지 않은 에러: " + ex.toString());
        return ApiErrorResponse.toResponseEntity(ApiErrorCode.SERVER_ERROR, ex, request);
    }

}
