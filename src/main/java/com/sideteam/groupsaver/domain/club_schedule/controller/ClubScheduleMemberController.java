package com.sideteam.groupsaver.domain.club_schedule.controller;

import com.sideteam.groupsaver.domain.club_schedule.dto.response.ClubScheduleMemberResponse;
import com.sideteam.groupsaver.domain.club_schedule.service.ClubScheduleMemberService;
import com.sideteam.groupsaver.global.auth.userdetails.CustomUserDetails;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static java.util.Objects.requireNonNullElse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-schedule-member")
@RestController
public class ClubScheduleMemberController {

    private final ClubScheduleMemberService clubScheduleMemberService;

    @GetMapping("/{clubScheduleId}")
    public ResponseEntity<Page<ClubScheduleMemberResponse>> getClubScheduleMembers(
            @PathVariable Long clubScheduleId,
            Pageable pageable) {
        return ResponseEntity.ok(clubScheduleMemberService.getClubScheduleMembers(clubScheduleId, pageable));
    }

    @PostMapping("/{clubScheduleId}")
    public ResponseEntity<Void> joinIntoClubSchedule(
            @PathVariable Long clubScheduleId,
            @MemberIdParam Long memberId) {
        log.info("!! , {}", memberId);
        clubScheduleMemberService.joinSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{clubScheduleId}")
    public ResponseEntity<Void> leaveFromClubSchedule(
            @PathVariable Long clubScheduleId,
            @MemberIdParam Long memberId) {
        clubScheduleMemberService.leaveSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

}
