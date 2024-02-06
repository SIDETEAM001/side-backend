package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import lombok.Getter;

@Getter
public enum ClubType {
    SHORT("단기"),
    LONG("장기"),
    ONE("원데이")
    ;

    private final String clubType;

    ClubType(String clubType) {
        this.clubType = clubType;
    }

    public static ClubType getType(String type) {
        for (ClubType clubType : values()) {
            if (clubType.getClubType().equals(type)) {
                return clubType;
            }
        }
        throw new IllegalArgumentException();
    }
}