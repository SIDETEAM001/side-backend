package com.sideteam.groupsaver.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static com.sideteam.groupsaver.domain.category.domain.ClubCategory.DEVELOP;
import static com.sideteam.groupsaver.domain.category.domain.ClubCategory.HOBBY;
import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CATEGORY_MAJOR_NOT_FOUND;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubCategoryMajor {

    // 자기 계발
    PROJECT(DEVELOP, "사이드 프로젝트"),
    CHANGE(DEVELOP, "이직준비"),
    STUDY(DEVELOP, "스터디 · 자격증"),
    MONEY(DEVELOP, "재테크"),
    LANGUAGE_DEVELOP(DEVELOP, "어학"),
    ETC_DEVELOP(DEVELOP, "기타"),

    // 취미
    MUSIC(HOBBY, "음악 · 악기"),
    CULTURE(HOBBY, "문화 · 예술"),
    EXERCISE(HOBBY, "운동 · 스포츠"),
    TRIP(HOBBY, "여행 · 드라이브"),
    DANCE(HOBBY, "댄스 · 무용"),
    CRAFT(HOBBY, "공예"),
    FRIEND(HOBBY, "사교 · 동네친구"),
    HOT(HOBBY, "핫플투어"),
    BOOK(HOBBY, "독서 · 인문학"),
    LANGUAGE_HOBBY(HOBBY, "외국어 · 언어"),
    PHOTO(HOBBY, "사진 · 영상"),
    ETC_HOBBY(HOBBY, "그 외"),

    ;

    private final ClubCategory category;
    private final String name;


    @JsonCreator
    public static ClubCategoryMajor getClubCategoryMajor(final String major) {
        return Optional.ofNullable(major)
                .map(m -> Arrays.stream(ClubCategoryMajor.values())
                        .filter(clubCategoryMajor -> clubCategoryMajor.name.equals(m))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(CATEGORY_MAJOR_NOT_FOUND, major + ", 해당하는 카테고리가 없습니다.")))
                .orElse(null);
    }

    @JsonValue
    public String getJsonValue() {
        return name;
    }

}
