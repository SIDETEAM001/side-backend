package com.sideteam.groupsaver.global.exception.club;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubScheduleErrorCode implements ErrorCode {

    CLUB_SCHEDULE_NOT_FOUND(NOT_FOUND, "찾을 수 없는 모임 일정입니다"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
