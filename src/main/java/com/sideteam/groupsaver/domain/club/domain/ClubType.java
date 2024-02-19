package com.sideteam.groupsaver.domain.club.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.util.converter.param.ParamCreator;
import lombok.Getter;

import java.util.Arrays;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CLUB_TYPE_NOT_FOUND;

@Getter
public enum ClubType {
    ONE("원데이"),
    SHORT("단기"),
    LONG("지속형"),
    ;

    private final String type;

    ClubType(String type) {
        this.type = type;
    }

    @ParamCreator
    @JsonCreator
    public static ClubType fromValue(String value) {
        return Arrays.stream(ClubType.values())
                .filter(clubType -> clubType.getType().equals(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException(CLUB_TYPE_NOT_FOUND, value + "는 존재하지 않는 club type입니다"));
    }

    @JsonValue
    public String getJsonValue() {
        return type;
    }

}
