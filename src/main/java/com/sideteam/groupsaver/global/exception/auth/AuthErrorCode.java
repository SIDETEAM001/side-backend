package com.sideteam.groupsaver.global.exception.auth;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AuthErrorCode implements ErrorCode {

    // 401 UNAUTHORIZED: 인증되지 않은 사용자
    EMPTY_ACCESS_TOKEN(UNAUTHORIZED, "액세스 토큰이 필요합니다. (현재 빈 토큰)"),
    TAMPERED_ACCESS_TOKEN(UNAUTHORIZED, "서명이 조작된 토큰입니다"),
    EXPIRED_ACCESS_TOKEN(UNAUTHORIZED, "사용기간이 만료된 토큰입니다"),
    MALFORMED_ACCESS_TOKEN(UNAUTHORIZED, "비정상적인 토큰입니다"),
    WRONG_JWT_TOKEN(UNAUTHORIZED, "잘못된 토큰입니다(default)"),
    UNSUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원하지 않는 토큰입니다"),
    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED, "다시 로그인 해주세요. (없거나 삭제된 리프래시 토큰)"),
    FAILED_AUTHENTICATION(UNAUTHORIZED, "인증에 실패했습니다"),

    // 403 FORBIDDEN: 인증된 클라이언트가 권한이 없는 자원에 접근
    ACCESS_DENIED(FORBIDDEN, "접근할 수 없는 자료입니다"),
    FORBIDDEN_ACCESS_TOKEN(FORBIDDEN, "해당 토큰에는 엑세스 권한이 없습니다"),
    MISMATCHED_REFRESH_TOKEN(FORBIDDEN, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
