package com.sideteam.groupsaver.domain.club.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.Getter;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.ACTIVITY_TYPE_NOT_FOUND;
import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CATEGORY_SUB_NOT_FOUND;

@Getter
public enum ClubActivityType {
    ONLINE("온라인"),
    OFFLINE("오프라인"),
    ;

    private final String activityType;

    ClubActivityType(String activityType) {
        this.activityType = activityType;
    }

    @JsonCreator
    public static ClubActivityType getClubActivityType(String activityType) {
        for (ClubActivityType clubCategorySub : ClubActivityType.values()) {
            if (clubCategorySub.getActivityType().equals(activityType)) {
                return clubCategorySub;
            }
        }
        throw new BusinessException(ACTIVITY_TYPE_NOT_FOUND, ACTIVITY_TYPE_NOT_FOUND.getDetail());
    }
}
