package com.sideteam.groupsaver.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum MemberGender {
    M("남성"),
    F("여성"),
    ;

    private final String value;


    private static MemberGender of(final String input) {
        return Arrays.stream(MemberGender.values())
                .filter(memberGender -> memberGender.value.equals(input))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(input + ", 존재하지 않는 성별입니다."));
    }

    @JsonCreator
    public MemberGender from(final String value) {
        return MemberGender.of(value);
    }

    @JsonValue
    public String getJsonValue() {
        return value;
    }

}
