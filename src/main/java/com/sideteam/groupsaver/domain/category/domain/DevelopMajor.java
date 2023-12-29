package com.sideteam.groupsaver.domain.category.domain;

import lombok.Getter;

@Getter
public enum DevelopMajor {
    PROJECT("사이드프로젝트"),
    CHANGE("이직준비"),
    STUDY("스터디/자격증"),
    MONEY("재테크"),
    LANGUAGE("어학"),
    ETC("기타"),
    ;

    private final String categoryType;

    DevelopMajor(String categoryType) {
        this.categoryType = categoryType;
    }
}
