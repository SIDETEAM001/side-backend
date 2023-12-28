package com.sideteam.groupsaver.domain.category.domain;

import lombok.Getter;

@Getter
public enum HobbySub {
    MUSIC(HobbyMajor.MUSIC, "음악"),
    INSTRUMENT(HobbyMajor.MUSIC, "악기"),
    MUSIC_ETC(HobbyMajor.MUSIC, "기타"),

    MOVIE(HobbyMajor.CULTURE, "영화"),
    CONCERT(HobbyMajor.CULTURE, "콘서트"),
    MUSICAL(HobbyMajor.CULTURE, "뮤지컬"),
    EXHIBITION(HobbyMajor.CULTURE, "전시회"),
    FESTIVAL(HobbyMajor.CULTURE, "축제"),
    CULTURE_ETC(HobbyMajor.CULTURE, "기타"),

    GYM(HobbyMajor.EXERCISE, "헬스"),
    GOLF(HobbyMajor.EXERCISE, "골프"),
    MOUNTAIN(HobbyMajor.EXERCISE, "등산"),
    RUNNING(HobbyMajor.EXERCISE, "러닝"),
    BIKE(HobbyMajor.EXERCISE, "자전거"),
    BADMINTON(HobbyMajor.EXERCISE, "배드민턴"),
    BOWLING(HobbyMajor.EXERCISE, "볼링"),
    CLIMBING(HobbyMajor.EXERCISE, "클라이밍"),
    SPORT(HobbyMajor.EXERCISE, "경기관람"),
    EXERCISE_ETC(HobbyMajor.EXERCISE, "기타"),

    TRIP(HobbyMajor.TRIP, "여행"),
    DRIVE(HobbyMajor.TRIP, "드라이브"),
    PICNIC(HobbyMajor.TRIP, "피크닉"),
    CAMPING(HobbyMajor.TRIP, "캠핑"),
    TRIP_ETC(HobbyMajor.TRIP, "기타"),

    DANCE(HobbyMajor.DANCE, "방송댄스"),
    BALLET(HobbyMajor.DANCE, "발레"),
    MODERN_DANCE(HobbyMajor.DANCE, "무용"),
    DANCE_ETC(HobbyMajor.DANCE, "기타"),

    DRAWING(HobbyMajor.CRAFT, "드로잉"),
    WOOD(HobbyMajor.CRAFT, "드로잉"),
    CALLIGRAPHY(HobbyMajor.CRAFT, "드로잉"),
    FLOWER(HobbyMajor.CRAFT, "드로잉"),
    CRAFT_ETC(HobbyMajor.CRAFT, "기타"),

    FRIEND(HobbyMajor.FRIEND, "사교"),
    MEETING(HobbyMajor.FRIEND, "소개팅"),
    GAME(HobbyMajor.FRIEND, "게임"),
    FRIEND_ETC(HobbyMajor.FRIEND, "기타"),

    RESTARUNT(HobbyMajor.HOT, "맛집"),
    CAFE(HobbyMajor.HOT, "카페"),
    WINE(HobbyMajor.HOT, "와인 / 칵테일"),
    HOT_ETC(HobbyMajor.HOT, "기타"),

    BOOK(HobbyMajor.BOOK, "독서"),
    WRITING(HobbyMajor.BOOK, "작문"),
    EMOTION(HobbyMajor.BOOK, "심리학"),
    ECONOMY(HobbyMajor.BOOK, "경제"),
    BOOK_ETC(HobbyMajor.BOOK, "기타"),

    ENG(HobbyMajor.LANGUAGE, "영어"),
    CHI(HobbyMajor.LANGUAGE, "중국어"),
    JPA(HobbyMajor.LANGUAGE, "일본어"),
    GEM(HobbyMajor.LANGUAGE, "독일어"),
    FRC(HobbyMajor.LANGUAGE, "프랑스어"),
    LANGUAGE_ETC(HobbyMajor.LANGUAGE, "기타"),

    PHOTO(HobbyMajor.PHOTO, "사진"),
    VIDEO(HobbyMajor.PHOTO, "영상"),
    PHOTO_ETC(HobbyMajor.PHOTO, "기타"),

    COOK(HobbyMajor.ETC, "요리"),
    VOLUNTEER(HobbyMajor.ETC, "봉사활동"),
    PET(HobbyMajor.ETC, "반려동물"),
    ETC_ETC(HobbyMajor.ETC, "기타"),
    ;

    private final HobbyMajor hobbyMajor;
    private final String category;

    HobbySub(HobbyMajor hobbyMajor, String category) {
        this.hobbyMajor = hobbyMajor;
        this.category = category;
    }
}