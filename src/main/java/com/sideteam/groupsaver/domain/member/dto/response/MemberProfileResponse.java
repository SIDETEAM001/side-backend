package com.sideteam.groupsaver.domain.member.dto.response;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.member.domain.Member;

import java.util.List;

public record MemberProfileResponse(
        long id,
        String nickname,
        String profileImageUrl,
        List<ClubCategoryMajor> clubCategories
) {
    public static MemberProfileResponse of(Member member) {
        return new MemberProfileResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileUrl(),
                member.getClubCategories());
    }
}
