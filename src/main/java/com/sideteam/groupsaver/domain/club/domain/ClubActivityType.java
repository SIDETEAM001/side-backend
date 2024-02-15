package com.sideteam.groupsaver.domain.club.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.ACTIVITY_TYPE_NOT_FOUND;

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
    public static ClubActivityType getClubActivityType(final String activityType) {
        return Arrays.stream(ClubActivityType.values())
                .filter(clubCategorySub -> clubCategorySub.getActivityType().equals(activityType))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ACTIVITY_TYPE_NOT_FOUND, activityType + ", 해당하는 활동 유형을 찾을 수 없습니다."));
    }

    @JsonValue
    public String getJsonValue() {
        return activityType;
    }

}
