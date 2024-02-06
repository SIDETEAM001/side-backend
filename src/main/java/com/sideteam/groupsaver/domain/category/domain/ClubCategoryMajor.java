package com.sideteam.groupsaver.domain.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.sideteam.groupsaver.domain.category.domain.ClubCategory.DEVELOP;
import static com.sideteam.groupsaver.domain.category.domain.ClubCategory.HOBBY;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubCategoryMajor {

    // 자기 계발
    PROJECT(DEVELOP, "사이드프로젝트"),
    CHANGE(DEVELOP, "이직준비"),
    STUDY(DEVELOP, "스터디/자격증"),
    MONEY(DEVELOP, "재테크"),
    LANGUAGE_DEVELOP(DEVELOP, "어학"),
    ETC_DEVELOP(DEVELOP, "기타"),

    // 취미
    MUSIC(HOBBY, "음악"),
    CULTURE(HOBBY, "문화"),
    EXERCISE(HOBBY, "운동"),
    TRIP(HOBBY, "여행"),
    DANCE(HOBBY, "댄스"),
    CRAFT(HOBBY, "공예"),
    FRIEND(HOBBY, "사교"),
    HOT(HOBBY, "핫플"),
    BOOK(HOBBY, "독서"),
    LANGUAGE_HOBBY(HOBBY, "외국어"),
    PHOTO(HOBBY, "사진"),
    ETC_HOBBY(HOBBY, "기타"),

    ;

    private final ClubCategory category;
    private final String name;

    public static ClubCategoryMajor getCategory(String categoryName) {
        for (ClubCategoryMajor major : values()) {
            if (major.getName().equals(categoryName)) {
                return major;
            }
        }
        throw new IllegalArgumentException();
    }

}
