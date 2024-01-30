package com.sideteam.groupsaver.global.exception.websocket;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum WebSocketErrorCode implements ErrorCode {

    FAILED_CONVERT(INTERNAL_SERVER_ERROR, "Failed to convert data"),
    SESSION_IS_NULL(INTERNAL_SERVER_ERROR, "Session is null"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
