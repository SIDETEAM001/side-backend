package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClubFacade {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;

    @PreAuthorize("isAuthenticated() AND (( #memberId.toString() == principal.username ) OR hasRole('ADMIN'))")
    public ClubInfoResponse createClubAndJoin(ClubRequest clubRequest, Long memberId) {
        ClubInfoResponse clubInfoResponse = clubService.createClub(clubRequest);
        clubMemberService.joinClub(clubInfoResponse.id(), memberId, ClubMemberRole.LEADER);
        return clubInfoResponse;
    }

}
