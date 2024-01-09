package com.sideteam.groupsaver.global.exception.file;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum FileErrorCode implements ErrorCode {

    INVALID_EXTENSION(BAD_REQUEST, "허용된 확장자가 아닌 파일입니다, 허용된 확장자만 가능합니다"),
    FILE_ID_NOT_FOUND(NOT_FOUND, "찾을 수 없는 파일입니다"),

    FAILED_S3_UPLOAD(UNPROCESSABLE_ENTITY, "업로드에 실패한 이미지입니다"),

    ;

    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }
}
