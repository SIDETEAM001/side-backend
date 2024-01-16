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

    MEMBER_DO_NOT_HAVE_PERMISSION(UNAUTHORIZED, "회원에게 권한이 없습니다."),

    CLUB_SCHEDULE_NOT_FOUND(NOT_FOUND, "찾을 수 없는 모임 일정입니다"),

    CLUB_SCHEDULE_IS_FULL(UNPROCESSABLE_ENTITY, "모임의 정원이 다 찼습니다."),
    CLUB_SCHEDULE_MEMBER_ALREADY_EXIST(UNPROCESSABLE_ENTITY, "이미 가입되어 있습니다."),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
