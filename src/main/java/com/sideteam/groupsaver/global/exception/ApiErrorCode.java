package com.sideteam.groupsaver.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ApiErrorCode implements ErrorCode {

    FAILED_VALIDATION(BAD_REQUEST, "Request is invalid and falls outside the valid range"),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "Internal server error"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
