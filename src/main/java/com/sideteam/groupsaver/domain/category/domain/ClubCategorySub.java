package com.sideteam.groupsaver.domain.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubCategorySub {

    // 취미
    MUSIC(ClubCategoryMajor.MUSIC, "음악"),
    INSTRUMENT(ClubCategoryMajor.MUSIC, "악기"),
    MUSIC_ETC(ClubCategoryMajor.MUSIC, "기타"),

    MOVIE(ClubCategoryMajor.CULTURE, "영화"),
    CONCERT(ClubCategoryMajor.CULTURE, "콘서트"),
    MUSICAL(ClubCategoryMajor.CULTURE, "뮤지컬"),
    EXHIBITION(ClubCategoryMajor.CULTURE, "전시회"),
    FESTIVAL(ClubCategoryMajor.CULTURE, "축제"),
    CULTURE_ETC(ClubCategoryMajor.CULTURE, "기타"),

    GYM(ClubCategoryMajor.EXERCISE, "헬스"),
    GOLF(ClubCategoryMajor.EXERCISE, "골프"),
    MOUNTAIN(ClubCategoryMajor.EXERCISE, "등산"),
    RUNNING(ClubCategoryMajor.EXERCISE, "러닝"),
    BIKE(ClubCategoryMajor.EXERCISE, "자전거"),
    BADMINTON(ClubCategoryMajor.EXERCISE, "배드민턴"),
    BOWLING(ClubCategoryMajor.EXERCISE, "볼링"),
    CLIMBING(ClubCategoryMajor.EXERCISE, "클라이밍"),
    SPORT(ClubCategoryMajor.EXERCISE, "경기관람"),
    EXERCISE_ETC(ClubCategoryMajor.EXERCISE, "기타"),

    TRIP(ClubCategoryMajor.TRIP, "여행"),
    DRIVE(ClubCategoryMajor.TRIP, "드라이브"),
    PICNIC(ClubCategoryMajor.TRIP, "피크닉"),
    CAMPING(ClubCategoryMajor.TRIP, "캠핑"),
    TRIP_ETC(ClubCategoryMajor.TRIP, "기타"),

    DANCE(ClubCategoryMajor.DANCE, "방송댄스"),
    BALLET(ClubCategoryMajor.DANCE, "발레"),
    MODERN_DANCE(ClubCategoryMajor.DANCE, "무용"),
    DANCE_ETC(ClubCategoryMajor.DANCE, "기타"),

    DRAWING(ClubCategoryMajor.CRAFT, "드로잉"),
    WOOD(ClubCategoryMajor.CRAFT, "목공"),
    CALLIGRAPHY(ClubCategoryMajor.CRAFT, "캘리그라피"),
    FLOWER(ClubCategoryMajor.CRAFT, "꽃꽂이"),
    CRAFT_ETC(ClubCategoryMajor.CRAFT, "기타"),

    FRIEND(ClubCategoryMajor.FRIEND, "사교"),
    MEETING(ClubCategoryMajor.FRIEND, "소개팅"),
    GAME(ClubCategoryMajor.FRIEND, "게임"),
    FRIEND_ETC(ClubCategoryMajor.FRIEND, "기타"),

    RESTAURANT(ClubCategoryMajor.HOT, "맛집"),
    CAFE(ClubCategoryMajor.HOT, "카페"),
    WINE(ClubCategoryMajor.HOT, "와인 / 칵테일"),
    HOT_ETC(ClubCategoryMajor.HOT, "기타"),

    BOOK(ClubCategoryMajor.BOOK, "독서"),
    WRITING(ClubCategoryMajor.BOOK, "작문"),
    EMOTION(ClubCategoryMajor.BOOK, "심리학"),
    ECONOMY(ClubCategoryMajor.BOOK, "경제"),
    BOOK_ETC(ClubCategoryMajor.BOOK, "기타"),

    ENG(ClubCategoryMajor.LANGUAGE_HOBBY, "영어"),
    CHI(ClubCategoryMajor.LANGUAGE_HOBBY, "중국어"),
    JPA(ClubCategoryMajor.LANGUAGE_HOBBY, "일본어"),
    GEM(ClubCategoryMajor.LANGUAGE_HOBBY, "독일어"),
    FRC(ClubCategoryMajor.LANGUAGE_HOBBY, "프랑스어"),
    LANGUAGE_ETC(ClubCategoryMajor.LANGUAGE_HOBBY, "기타"),

    PHOTO(ClubCategoryMajor.PHOTO, "사진"),
    VIDEO(ClubCategoryMajor.PHOTO, "영상"),
    PHOTO_ETC(ClubCategoryMajor.PHOTO, "기타"),

    COOK(ClubCategoryMajor.ETC_HOBBY, "요리"),
    VOLUNTEER(ClubCategoryMajor.ETC_HOBBY, "봉사활동"),
    PET(ClubCategoryMajor.ETC_HOBBY, "반려동물"),
    ETC_ETC(ClubCategoryMajor.ETC_HOBBY, "기타"),
    ;

    private final ClubCategoryMajor major;
    private final String name;

    public ClubCategory getCategory() {
        return this.major.getCategory();
    }

}
