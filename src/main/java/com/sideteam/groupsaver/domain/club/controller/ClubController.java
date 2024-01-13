package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.service.ClubFacade;
import com.sideteam.groupsaver.domain.club.service.ClubService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ClubFacade clubFacade;

    @PostMapping
    public ResponseEntity<ClubInfoResponse> createClub(@Valid @RequestBody ClubRequest clubRequest) {
        return ResponseEntity.ok(clubFacade.createClubAndJoin(clubRequest));
    }

    @PatchMapping("/{clubId}")
    public ResponseEntity<ClubInfoResponse> updateClubInformation(
            @PathVariable("clubId") Long clubId,
            @Valid @RequestBody ClubRequest clubRequest,
            @MemberIdParam Long memberId) {
        return ResponseEntity.ok(clubService.updateClub(clubId, clubRequest, memberId));
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClub(
            @PathVariable("clubId") Long clubId,
            @MemberIdParam Long memberId) {
        clubService.deactivateClub(clubId, memberId);
        return ResponseEntity.noContent().build();
    }


}
