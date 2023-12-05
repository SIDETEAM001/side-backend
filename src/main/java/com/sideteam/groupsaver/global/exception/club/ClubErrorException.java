package com.sideteam.groupsaver.global.exception.club;

import lombok.Getter;

@Getter
public class ClubErrorException extends RuntimeException {

    private final ClubErrorCode errorCode;
    private final String causedBy;

    public ClubErrorException(ClubErrorCode errorCode, String causedBy) {
        this.errorCode = errorCode;
        this.causedBy = causedBy;
    }

    @Override
    public String toString() {
        return "CLUB 에러 코드: " + errorCode + ", 사유: " + causedBy;
    }
}
