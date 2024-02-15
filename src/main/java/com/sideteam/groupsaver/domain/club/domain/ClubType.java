package com.sideteam.groupsaver.domain.club.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CLUB_TYPE_NOT_FOUND;

@Getter
public enum ClubType {
    SHORT("단기"),
    LONG("장기"),
    ONE("원데이"),
    ;

    private final String type;

    ClubType(String type) {
        this.type = type;
    }


    @JsonCreator
    public static ClubType getClubType(String type) {
        return Arrays.stream(ClubType.values())
                .filter(clubType -> clubType.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new BusinessException(CLUB_TYPE_NOT_FOUND, CLUB_TYPE_NOT_FOUND.getDetail()));
    }

    @JsonValue
    public String getJsonValue() {
        return type;
    }

}
