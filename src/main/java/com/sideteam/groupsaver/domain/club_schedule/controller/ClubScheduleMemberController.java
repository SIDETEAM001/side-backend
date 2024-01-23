package com.sideteam.groupsaver.domain.club_schedule.controller;

import com.sideteam.groupsaver.domain.club_schedule.dto.response.ClubScheduleMemberResponse;
import com.sideteam.groupsaver.domain.club_schedule.service.ClubScheduleMemberService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-schedule-members")
@RestController
public class ClubScheduleMemberController {

    private final ClubScheduleMemberService clubScheduleMemberService;

    @GetMapping("/{clubScheduleId}")
    public ResponseEntity<Slice<ClubScheduleMemberResponse>> getClubScheduleMembers(
            @PathVariable Long clubScheduleId,
            Pageable pageable) {
        return ResponseEntity.ok(clubScheduleMemberService.getClubScheduleMembers(clubScheduleId, pageable));
    }

    @PostMapping("/{clubScheduleId}")
    public ResponseEntity<Void> joinClubSchedule(
            @PathVariable Long clubScheduleId,
            @MemberIdParam Long memberId) {
        clubScheduleMemberService.joinSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{clubScheduleId}")
    public ResponseEntity<Void> leaveClubSchedule(
            @PathVariable Long clubScheduleId,
            @MemberIdParam Long memberId) {
        clubScheduleMemberService.leaveSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

}
