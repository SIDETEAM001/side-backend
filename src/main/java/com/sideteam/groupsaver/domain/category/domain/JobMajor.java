package com.sideteam.groupsaver.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.Getter;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CATEGORY_MAJOR_NOT_FOUND;

@Getter
public enum JobMajor {
    MANAGEMENT("기획 · 전략 · 경영"),
    DEVELOP("개발"),
    DATA("데이터 · AI · ML"),
    MARKETING("마케팅 · 광고 · 홍보"),
    DESIGN("디자인"),
    MEDIA("미디어 · 전시 · 예술"),
    DELIVERY("유통 · 물류"),
    FINANCE("금융"),
    MONEY("재무 · 회계 · 세무"),
    HUMAN("인사 · 노무"),
    SALE("영업 · 고객"),
    MEDIC("의료 · 바이오 · 제약"),
    RESEARCH("연구 · RND"),
    ENGINE("엔지니어링 · 설계"),
    QUALITY("품질 · 생산"),
    EDU("교육"),
    GYM("체육 · 스포츠직"),
    LAW("법률 · 법무직"),
    PUBLIC("공공 · 복지"),
    SERVICE("서비스직"),
    ETC("기타")
    ;

    private final String category;

    JobMajor(String category) {
        this.category = category;
    }

    @JsonCreator
    public static JobMajor getJobMajor(String major) {
        if (major == null) {
            return null;
        }
        for (JobMajor jobMajor : JobMajor.values()) {
            if (jobMajor.getCategory().equals(major)) {
                return jobMajor;
            }
        }
        throw new BusinessException(CATEGORY_MAJOR_NOT_FOUND, CATEGORY_MAJOR_NOT_FOUND.getDetail());
    }
}
