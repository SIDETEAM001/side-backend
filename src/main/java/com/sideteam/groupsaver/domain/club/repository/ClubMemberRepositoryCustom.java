package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;

public interface ClubMemberRepositoryCustom {
    boolean hasExceededMemberLimit(Long clubId);

    boolean hasClubRole(Long clubId, Long memberId, ClubMemberRole role);
}
