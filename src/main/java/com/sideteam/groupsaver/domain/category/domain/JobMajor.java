package com.sideteam.groupsaver.domain.category.domain;

import lombok.Getter;

@Getter
public enum JobMajor {
    MANAGEMENT("경영"),
    DATA("개발"),
    MARKETING("데이터"),
    DESIGN("디자인"),
    MEDIA("미디어"),
    DELIVERY("물류"),
    FINANCE("금융"),
    MONEY("재무"),
    HUMAN("인사"),
    SALE("영업"),
    MEDIC("의료"),
    RESEARCH("연구"),
    ENGINE("설계"),
    QUALITY("품질"),
    EDU("교육"),
    GYM("체육"),
    LAW("법률"),
    PUBLIC("공공"),
    SERVICE("서비스"),
    ETC("기타")
    ;

    private final String category;

    JobMajor(String category) {
        this.category = category;
    }
}
