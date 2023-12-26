package com.sideteam.groupsaver.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String causedBy;

    public BusinessException(ErrorCode errorCode, String causedBy) {
        this.errorCode = errorCode;
        this.causedBy = causedBy;
    }

    @Override
    public String toString() {
        return "AUTH 에러 코드: " + errorCode + ", 사유: " + causedBy;
    }

}
