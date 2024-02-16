package com.sideteam.groupsaver.domain.category.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static com.sideteam.groupsaver.global.exception.category.CategoryErrorCode.CATEGORY_MAJOR_NOT_FOUND;

@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
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
    ETC("기타"),
    ;

    private final String name;


    @JsonCreator
    public static JobMajor getJobMajor(final String major) {
        return Optional.ofNullable(major)
                .map(m -> Arrays.stream(JobMajor.values())
                        .filter(jobMajor -> jobMajor.name.equals(m))
                        .findFirst()
                        .orElseThrow(() -> new BusinessException(CATEGORY_MAJOR_NOT_FOUND, major + ", 해당 직종을 찾을 수 없습니다.")))
                .orElse(null);
    }

    @JsonValue
    public String getJsonValue() {
        return name;
    }

}
