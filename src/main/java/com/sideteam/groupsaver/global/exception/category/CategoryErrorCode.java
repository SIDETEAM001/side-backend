package com.sideteam.groupsaver.global.exception.category;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum CategoryErrorCode implements ErrorCode {
    CATEGORY_MAJOR_NOT_FOUND(NOT_FOUND, "찾을 수 없는 대분류입니다"),
    CATEGORY_SUB_NOT_FOUND(NOT_FOUND, "찾을 수 없는 소분류입니다"),
    ACTIVITY_TYPE_NOT_FOUND(NOT_FOUND, "찾을 수 없는 활동 분야입니다"),
    CLUB_TYPE_NOT_FOUND(NOT_FOUND, "찾을 수 없는 모임 기간 분야입니다"),
    ;
    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
