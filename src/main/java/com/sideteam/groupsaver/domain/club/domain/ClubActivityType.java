package com.sideteam.groupsaver.domain.club.domain;

import lombok.Getter;

@Getter
public enum ClubActivityType {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ;

    private final String activityType;

    ClubActivityType(String activityType) {
        this.activityType = activityType;
    }
}
