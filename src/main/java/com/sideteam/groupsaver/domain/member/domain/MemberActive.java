package com.sideteam.groupsaver.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum MemberActive {
    ACTIVE("활동중"),
    EXIT("탈퇴"),
    SUSPEND("정지"),
    ;

    private final String active;
}
