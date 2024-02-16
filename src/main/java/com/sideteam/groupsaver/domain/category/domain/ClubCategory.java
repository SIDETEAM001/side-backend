package com.sideteam.groupsaver.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.INVALID_CATEGORY;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubCategory {

    DEVELOP("자기계발"),
    HOBBY("취미"),
    ;

    private final String type;


    @JsonCreator
    public static ClubCategory of(final String type) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new BusinessException(INVALID_CATEGORY, type + ", 존재하지 않는 카테고리입니다."));
    }

    @JsonValue
    public String getJsonValue() {
        return type;
    }

}
