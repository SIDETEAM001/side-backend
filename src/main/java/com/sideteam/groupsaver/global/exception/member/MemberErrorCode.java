package com.sideteam.groupsaver.global.exception.member;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberErrorCode implements ErrorCode {

    // 400
    DUPLICATED_EMAIL(BAD_REQUEST, "기존에 존재하는 이메일은 사용할 수 없습니다"),

    MEMBER_NOT_FOUND(NOT_FOUND, "찾을 수 없는 사용자입니다"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }

}
