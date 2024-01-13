package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.service.ClubMemberService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/club-members")
@RestController
public class ClubMemberController {

    private final ClubMemberService clubMemberService;


    @PostMapping("/{clubId}")
    public ResponseEntity<Void> joinClub(
            @PathVariable("clubId") Long clubId,
            @MemberIdParam Long memberId) {
        clubMemberService.joinClub(clubId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> leaveClub(
            @PathVariable("clubId") Long clubId,
            @MemberIdParam Long memberId) {
        clubMemberService.leaveClub(clubId, memberId);
        return ResponseEntity.noContent().build();
    }

}
