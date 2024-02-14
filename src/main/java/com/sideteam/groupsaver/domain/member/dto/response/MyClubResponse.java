package com.sideteam.groupsaver.domain.member.dto.response;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.dto.response.ClubSimpleInfoDto;

public record MyClubResponse(
        ClubSimpleInfoDto club,
        boolean isLeader
) {

    public static MyClubResponse of(ClubMember clubMember) {
        return new MyClubResponse(
                ClubSimpleInfoDto.of(clubMember.getClub()),
                clubMember.isLeader()
        );
    }

}
