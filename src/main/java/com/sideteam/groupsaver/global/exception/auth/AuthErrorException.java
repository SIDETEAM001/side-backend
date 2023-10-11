package com.sideteam.groupsaver.global.exception.auth;

import lombok.Getter;

@Getter
public class AuthErrorException extends RuntimeException {

    private final AuthErrorCode errorCode;
    private final String causedBy;

    public AuthErrorException(AuthErrorCode errorCode, String causedBy) {
        this.errorCode = errorCode;
        this.causedBy = causedBy;
    }

    @Override
    public String toString() {
        return "AUTH 에러 코드: " + errorCode + ", 사유: " + causedBy;
    }
}
