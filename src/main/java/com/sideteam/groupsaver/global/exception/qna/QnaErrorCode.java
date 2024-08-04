package com.sideteam.groupsaver.global.exception.qna;

import com.sideteam.groupsaver.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Getter
public enum QnaErrorCode implements ErrorCode {

    QNA_NOT_FOUND(NOT_FOUND, "게시글을 찾을 수 없습니다"),
    LIKE_ALREADY_EXIST(CONFLICT, "좋아요가 이미 있습니다"),
    LIKE_NOT_FOUND(NOT_FOUND, "좋아요을 찾을 수 없습니다"),
    LIKE_MEMBER_NOT_MATCH(FORBIDDEN, "사용자가 일치하지 않습니다");


    private final HttpStatus httpStatus;
    private final String detail;

    @Override
    public String getName() {
        return this.name();
    }

}
