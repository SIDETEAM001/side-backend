package com.sideteam.groupsaver.domain.member.controller;

import com.sideteam.groupsaver.domain.member.dto.response.MyBookmarkClubResponse;
import com.sideteam.groupsaver.domain.member.dto.response.MyClubResponse;
import com.sideteam.groupsaver.domain.member.service.MemberClubService;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class MyClubController {

    private final MemberClubService memberClubService;


    @Operation(summary = "자신의 가입 중인 모임 목록 조회")
    @GetMapping("/me/clubs")
    public ResponseEntity<Slice<MyClubResponse>> getMyClubs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable) {
        return ResponseEntity.ok(memberClubService.getMyClubs(userDetails.getId(), pageable));
    }

    @Operation(summary = "자신의 찜한 모임 목록 조회")
    @GetMapping("/me/clubs/bookmarks")
    public ResponseEntity<Slice<MyBookmarkClubResponse>> getMyBookmarkClubs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable) {
        return ResponseEntity.ok(memberClubService.getMyBookmarkClubs(userDetails.getId(), pageable));
    }

}
