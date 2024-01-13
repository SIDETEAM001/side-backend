package com.sideteam.groupsaver.domain.category.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ClubCategory {

    DEVELOP("자기계발"),
    HOBBY("취미"),
    ;

    private final String type;

}
