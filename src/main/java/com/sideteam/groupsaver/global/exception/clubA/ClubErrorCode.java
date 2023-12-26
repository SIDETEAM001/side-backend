package com.sideteam.groupsaver.global.exception.clubA;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ClubErrorCode implements ErrorCode {

    CLUB_SCHEDULE_NOT_FOUND(NOT_FOUND, "찾을 수 없는 모임 일정입니다"),
    CLUB_NOT_FOUND(NOT_FOUND, "찾을 수 없는 모임입니다"),
    CLUB_MEMBER_IS_FULL(NOT_FOUND, "모임의 정원이 다 찼습니다."),
    CLUB_MEMBER_ALREADY_EXIST(NOT_FOUND, "이미 가입되어 있습니다."),
    MEMBER_DO_NOT_HAVE_PERMISSION(NOT_FOUND, "회원에게 권한이 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
