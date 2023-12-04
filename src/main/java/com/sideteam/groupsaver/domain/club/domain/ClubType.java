package com.sideteam.groupsaver.domain.club.domain;

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
}