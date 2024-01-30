package com.sideteam.groupsaver.global.exception.firebase;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FirebaseErrorCode implements ErrorCode {

    FAILED_INIT_FIREBASE_APP(INTERNAL_SERVER_ERROR, "Failed to create Firebase app"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}