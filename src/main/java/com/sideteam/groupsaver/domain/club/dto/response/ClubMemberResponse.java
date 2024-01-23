package com.sideteam.groupsaver.domain.club.dto.response;

import com.sideteam.groupsaver.domain.member.domain.Member;

public record ClubMemberResponse(
        Long memberId,
        String memberProfileUrl,
        String nickname
) {
    public static ClubMemberResponse from(Member member) {
        return new ClubMemberResponse(
                member.getId(),
                member.getProfileUrl(),
                member.getNickname()
        );
    }
}
