package com.sideteam.groupsaver.domain.member.dto.response;

import com.sideteam.groupsaver.domain.club.dto.response.ClubSimpleInfoDto;
import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;

public record MyBookmarkClubResponse(
        ClubSimpleInfoDto club
) {

    public static MyBookmarkClubResponse of(ClubBookmark clubBookmark) {
        return new MyBookmarkClubResponse(
                ClubSimpleInfoDto.of(clubBookmark.getClub())
        );
    }
}
