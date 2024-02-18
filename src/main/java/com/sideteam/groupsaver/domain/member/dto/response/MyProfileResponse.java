package com.sideteam.groupsaver.domain.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.JobMajor;

import java.time.LocalDate;
import java.util.List;

public record MyProfileResponse(
        long id,
        String phoneNumber,
        String nickname,
        String email,
        LocalDate birth,
        String profileImageUrl,
        JobMajor jobMajor,
        List<ClubCategoryMajor> develops,
        List<ClubCategoryMajor> hobbies,
        long myClubCount,
        long clubBookmarkCount
) {
    @QueryProjection
    public MyProfileResponse {
    }
}
