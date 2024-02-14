package com.sideteam.groupsaver.domain.member.controller;

import com.sideteam.groupsaver.domain.member.dto.request.MemberProfileUpdateRequest;
import com.sideteam.groupsaver.domain.member.dto.response.MemberProfileResponse;
import com.sideteam.groupsaver.domain.member.dto.response.MyProfileResponse;
import com.sideteam.groupsaver.domain.member.service.MemberProfileService;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class MemberProfileController {

    private final MemberProfileService memberProfileService;


    @Operation(summary = "자신의 프로필 조회")
    @GetMapping("/me/profiles")
    public ResponseEntity<MyProfileResponse> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(memberProfileService.getMyProfile(userDetails.getId()));
    }

    @Operation(summary = "사용자 프로필 조회")
    @GetMapping("/{memberId}/profiles")
    public ResponseEntity<MemberProfileResponse> getMemberProfile(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberProfileService.getMemberProfile(memberId));
    }

    @Operation(summary = "자신의 프로필 수정")
    @PutMapping("/me/profiles")
    public ResponseEntity<MyProfileResponse> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MemberProfileUpdateRequest updateRequest) {
        return ResponseEntity.ok(memberProfileService.updateMemberProfile(userDetails.getId(), updateRequest));
    }

    @Operation(summary = "사용자 프로필 수정")
    @PutMapping("/profiles")
    public ResponseEntity<MyProfileResponse> updateMemberProfile(
            @MemberIdParam Long memberId,
            @RequestBody MemberProfileUpdateRequest updateRequest) {
        return ResponseEntity.ok(memberProfileService.updateMemberProfile(memberId, updateRequest));
    }

}
