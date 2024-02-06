package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClubFacade {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;
    private final ClubAssembler clubAssembler;

    public ClubInfoResponse createClubAndJoin(ClubRequest clubRequest) {
        ClubInfoResponse clubInfoResponse = clubService.createClub(clubAssembler.toClubRequestDto(clubRequest));
        clubMemberService.joinClub(clubInfoResponse.id(), clubInfoResponse.creatorId(), ClubMemberRole.LEADER);
        return clubInfoResponse;
    }

    public ClubInfoResponse updateClubInfo(long clubId, ClubRequest clubRequest, long memberId) {
        return clubService.updateClub(clubId, clubAssembler.toClubRequestDto(clubRequest), memberId);
    }

}
