package com.sideteam.groupsaver.global.exception.location;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum LocationErrorCode implements ErrorCode {

    UNSUPPORTED_AREA(BAD_REQUEST, "the given location is not in the service area"),
    FAILED_API_REQUEST(BAD_REQUEST, "api request failed"),
    FAILED_API_CONNECTION(INTERNAL_SERVER_ERROR, "api response failed"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }

}
