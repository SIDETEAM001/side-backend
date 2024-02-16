package com.sideteam.groupsaver.domain.auth.dto.response;

import com.sideteam.groupsaver.domain.member.domain.Member;

public record SignupResult(
        Long id,
        String nickname,
        String profileUrl
) {
    public static SignupResult of(Member member) {
        return new SignupResult(member.getId(), member.getNickname(), member.getProfileUrl());
    }
}
