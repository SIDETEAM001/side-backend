package com.sideteam.groupsaver.global.exception.club;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ClubErrorCode implements ErrorCode {

    // 404
    NOT_FOUND_CLUB(NOT_FOUND, "모임을 찾을 수 없습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}

