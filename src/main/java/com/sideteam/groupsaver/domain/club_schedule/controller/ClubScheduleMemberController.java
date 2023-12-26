package com.sideteam.groupsaver.domain.club_schedule.controller;

import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleMemberDto;
import com.sideteam.groupsaver.domain.club_schedule.service.ClubScheduleMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/club-schedule-member")
@RestController
public class ClubScheduleMemberController {

    private final ClubScheduleMemberService clubScheduleMemberService;

    @GetMapping
    public ResponseEntity<Page<ClubScheduleMemberDto>> getClubScheduleMembers(
            @RequestParam("clubScheduleId") Long clubScheduleId,
            Pageable pageable) {
        return ResponseEntity.ok(clubScheduleMemberService.getClubScheduleMembers(clubScheduleId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> joinIntoClubSchedule(
            @RequestParam("clubScheduleId") Long clubScheduleId,
            @RequestParam("memberId") Long memberId) {
        clubScheduleMemberService.joinSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> leaveFromClubSchedule(
            @RequestParam("clubScheduleId") Long clubScheduleId,
            @RequestParam("memberId") Long memberId) {
        clubScheduleMemberService.leaveSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

}
