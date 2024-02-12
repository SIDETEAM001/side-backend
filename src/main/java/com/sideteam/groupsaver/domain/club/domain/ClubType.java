package com.sideteam.groupsaver.domain.club.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.Getter;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CATEGORY_SUB_NOT_FOUND;
import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CLUB_TYPE_NOT_FOUND;

@Getter
public enum ClubType {
    SHORT("단기"),
    LONG("장기"),
    ONE("원데이")
    ;

    private final String type;

    ClubType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static ClubType getClubType(String type) {
        for (ClubType clubType : ClubType.values()) {
            if (clubType.getType().equals(type)) {
                return clubType;
            }
        }
        throw new BusinessException(CLUB_TYPE_NOT_FOUND, CLUB_TYPE_NOT_FOUND.getDetail());
    }
}