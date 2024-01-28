package com.sideteam.groupsaver.domain.club_schedule.dto.response;

import com.sideteam.groupsaver.domain.member.domain.Member;

public record ClubScheduleMemberResponse(
        Long memberId,
        String memberProfileUrl,
        String nickname
) {
    public static ClubScheduleMemberResponse from(Member member) {
        return new ClubScheduleMemberResponse(
                member.getId(),
                member.getProfileUrl(),
                member.getNickname()
        );
    }
}
