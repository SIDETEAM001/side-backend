package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.service.ClubBookmarkService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club")
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs/bookmarks")
@RestController
public class ClubBookmarkController {

    private final ClubBookmarkService clubBookmarkService;

    @Operation(summary = "모임 찜하기")
    @PostMapping("/{clubId}")
    public ResponseEntity<Void> bookmarkClub(
            @MemberIdParam Long memberId,
            @PathVariable Long clubId) {
        clubBookmarkService.bookmarkClub(memberId, clubId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "모임 찜 삭제")
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> deleteClubBookmark(
            @MemberIdParam Long memberId,
            @PathVariable Long clubId) {
        clubBookmarkService.deleteClubBookmark(memberId, clubId);
        return ResponseEntity.noContent().build();
    }


}
