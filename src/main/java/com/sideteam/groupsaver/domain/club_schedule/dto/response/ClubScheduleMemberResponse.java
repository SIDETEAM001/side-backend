package com.sideteam.groupsaver.domain.club_schedule.dto.response;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;

public record ClubScheduleMemberResponse(
        long memberId
) {
    public static ClubScheduleMemberResponse from(ClubScheduleMember clubScheduleMember) {
        return new ClubScheduleMemberResponse(clubScheduleMember.getMember().getId());
    }
}
