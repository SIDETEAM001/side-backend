package com.sideteam.groupsaver.global.exception.auth;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthErrorException extends AuthenticationException {

    private final AuthErrorCode errorCode;
    private final String causedBy;

    public AuthErrorException(AuthErrorCode errorCode, String causedBy) {
        super(causedBy, null);
        this.errorCode = errorCode;
        this.causedBy = causedBy;
    }

    @Override
    public String toString() {
        return "AUTH 에러 코드: " + errorCode + ", 사유: " + causedBy;
    }
}
