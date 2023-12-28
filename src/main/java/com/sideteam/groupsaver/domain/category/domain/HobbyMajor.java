package com.sideteam.groupsaver.domain.category.domain;

import lombok.Getter;

@Getter
public enum HobbyMajor {
    MUSIC("음악"),
    CULTURE("문화"),
    EXERCISE("운동"),
    TRIP("여행"),
    DANCE("댄스"),
    CRAFT("공예"),
    FRIEND("사교"),
    HOT("핫플"),
    BOOK("독서"),
    LANGUAGE("외국어"),
    PHOTO("사진"),
    ETC("기타")
    ;

    private final String categoryType;

    HobbyMajor(String categoryType) {
        this.categoryType = categoryType;
    }
}
