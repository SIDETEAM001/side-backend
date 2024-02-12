package com.sideteam.groupsaver.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CATEGORY_SUB_NOT_FOUND;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubCategorySub {

    // 취미
    MUSIC(ClubCategoryMajor.MUSIC, "음악"),
    INSTRUMENT(ClubCategoryMajor.MUSIC, "악기"),
    MUSIC_ETC(ClubCategoryMajor.MUSIC, "음악 · 악기_기타", true),

    MOVIE(ClubCategoryMajor.CULTURE, "영화"),
    CONCERT(ClubCategoryMajor.CULTURE, "콘서트"),
    MUSICAL(ClubCategoryMajor.CULTURE, "뮤지컬"),
    EXHIBITION(ClubCategoryMajor.CULTURE, "전시회"),
    FESTIVAL(ClubCategoryMajor.CULTURE, "축제"),
    CULTURE_ETC(ClubCategoryMajor.CULTURE, "문화 · 예술_기타", true),

    GYM(ClubCategoryMajor.EXERCISE, "헬스"),
    GOLF(ClubCategoryMajor.EXERCISE, "골프"),
    MOUNTAIN(ClubCategoryMajor.EXERCISE, "등산"),
    RUNNING(ClubCategoryMajor.EXERCISE, "러닝"),
    BIKE(ClubCategoryMajor.EXERCISE, "자전거"),
    BADMINTON(ClubCategoryMajor.EXERCISE, "배드민턴"),
    BOWLING(ClubCategoryMajor.EXERCISE, "볼링"),
    CLIMBING(ClubCategoryMajor.EXERCISE, "클라이밍"),
    SPORT(ClubCategoryMajor.EXERCISE, "경기관람"),
    EXERCISE_ETC(ClubCategoryMajor.EXERCISE, "운동 · 스포츠_기타", true),

    TRIP(ClubCategoryMajor.TRIP, "여행"),
    DRIVE(ClubCategoryMajor.TRIP, "드라이브"),
    PICNIC(ClubCategoryMajor.TRIP, "피크닉"),
    CAMPING(ClubCategoryMajor.TRIP, "캠핑"),
    TRIP_ETC(ClubCategoryMajor.TRIP, "여행 · 드라이브_기타", true),

    DANCE(ClubCategoryMajor.DANCE, "방송댄스"),
    BALLET(ClubCategoryMajor.DANCE, "발레"),
    MODERN_DANCE(ClubCategoryMajor.DANCE, "무용"),
    DANCE_ETC(ClubCategoryMajor.DANCE, "댄스 · 무용_기타", true),

    DRAWING(ClubCategoryMajor.CRAFT, "드로잉"),
    WOOD(ClubCategoryMajor.CRAFT, "목공"),
    CALLIGRAPHY(ClubCategoryMajor.CRAFT, "캘리그라피"),
    FLOWER(ClubCategoryMajor.CRAFT, "꽃꽂이"),
    CRAFT_ETC(ClubCategoryMajor.CRAFT, "공예_기타", true),

    FRIEND(ClubCategoryMajor.FRIEND, "사교"),
    MEETING(ClubCategoryMajor.FRIEND, "소개팅"),
    GAME(ClubCategoryMajor.FRIEND, "게임"),
    FRIEND_ETC(ClubCategoryMajor.FRIEND, "사교 · 동네친구_기타", true),

    RESTAURANT(ClubCategoryMajor.HOT, "맛집"),
    CAFE(ClubCategoryMajor.HOT, "카페"),
    WINE(ClubCategoryMajor.HOT, "와인 · 칵테일"),
    HOT_ETC(ClubCategoryMajor.HOT, "핫플투어_기타", true),

    BOOK(ClubCategoryMajor.BOOK, "독서"),
    WRITING(ClubCategoryMajor.BOOK, "작문"),
    EMOTION(ClubCategoryMajor.BOOK, "심리학"),
    ECONOMY(ClubCategoryMajor.BOOK, "경제"),
    BOOK_ETC(ClubCategoryMajor.BOOK, "독서 · 인문학_기타", true),

    ENG(ClubCategoryMajor.LANGUAGE_HOBBY, "영어"),
    CHI(ClubCategoryMajor.LANGUAGE_HOBBY, "중국어"),
    JPA(ClubCategoryMajor.LANGUAGE_HOBBY, "일본어"),
    GEM(ClubCategoryMajor.LANGUAGE_HOBBY, "독일어"),
    FRC(ClubCategoryMajor.LANGUAGE_HOBBY, "프랑스어"),
    LANGUAGE_ETC(ClubCategoryMajor.LANGUAGE_HOBBY, "외국어 · 언어_기타", true),

    PHOTO(ClubCategoryMajor.PHOTO, "사진"),
    VIDEO(ClubCategoryMajor.PHOTO, "영상"),
    PHOTO_ETC(ClubCategoryMajor.PHOTO, "사진 · 영상_기타", true),

    COOK(ClubCategoryMajor.ETC_HOBBY, "요리"),
    VOLUNTEER(ClubCategoryMajor.ETC_HOBBY, "봉사활동"),
    PET(ClubCategoryMajor.ETC_HOBBY, "반려동물"),
    ETC_ETC(ClubCategoryMajor.ETC_HOBBY, "그 외_기타", true),
    ;

    private final ClubCategoryMajor major;
    private final String name;
    private final boolean etc;

    ClubCategorySub(ClubCategoryMajor major, String name) {
        this.major = major;
        this.name = name;
        this.etc = false;
    }

    public ClubCategory getCategory() {
        return this.major.getCategory();
    }

    @JsonCreator
    public static ClubCategorySub getClubCategorySub(String sub) {
        if (sub == null) {
            return null;
        }
        for (ClubCategorySub clubCategorySub : ClubCategorySub.values()) {
            if (clubCategorySub.getName().equals(sub)) {
                return clubCategorySub;
            }
        }
        throw new BusinessException(CATEGORY_SUB_NOT_FOUND, CATEGORY_SUB_NOT_FOUND.getDetail());
    }
}
