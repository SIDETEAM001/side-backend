package com.sideteam.groupsaver.global.exception.notification;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum NotificationErrorCode implements ErrorCode {
    NOT_FOUND_NOTIFICATION(NOT_FOUND, "찾을 수 없는 알림입니다")
    ;
    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
