package com.sideteam.groupsaver.domain.club_schedule.dto;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubScheduleMember;

public record ClubScheduleMemberDto(
        long memberId
) {
    public static ClubScheduleMemberDto from(ClubScheduleMember clubScheduleMember) {
        return new ClubScheduleMemberDto(clubScheduleMember.getMember().getId());
    }
}
