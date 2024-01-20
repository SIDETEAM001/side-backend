package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.dto.response.ClubMemberResponse;
import com.sideteam.groupsaver.domain.club.service.ClubMemberService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club")
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-members")
@RestController
public class ClubMemberController {

    private final ClubMemberService clubMemberService;


    @GetMapping("/{clubId}")
    public ResponseEntity<Slice<ClubMemberResponse>> getClubMembers(
            @PathVariable("clubId") Long clubId,
            @MemberIdParam Long memberId,
            Pageable pageable) {
        return ResponseEntity.ok(clubMemberService.getClubMembers(clubId, memberId, pageable));
    }

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
