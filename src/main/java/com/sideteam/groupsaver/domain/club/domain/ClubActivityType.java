package com.sideteam.groupsaver.domain.club.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum ClubActivityType {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ;

    private final String activityType;

    ClubActivityType(String activityType) {
        this.activityType = activityType;
    }

    public static ClubActivityType getType(String type) {
        for (ClubActivityType activityType : values()) {
            if (Objects.equals(activityType.getActivityType(), type)) {
                return activityType;
            }
        }
        throw new IllegalArgumentException();
    }
}
