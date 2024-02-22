package com.sideteam.groupsaver.domain.club_schedule.controller;

import com.sideteam.groupsaver.domain.club_schedule.dto.request.ClubScheduleRequest;
import com.sideteam.groupsaver.domain.club_schedule.dto.response.ClubScheduleResponse;
import com.sideteam.groupsaver.domain.club_schedule.service.ClubScheduleService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Club")
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-schedules")
@RestController
@Slf4j
public class ClubScheduleController {

    private final ClubScheduleService clubScheduleService;

    @GetMapping("/{clubScheduleId}")
    public ResponseEntity<ClubScheduleResponse> getSchedule(@PathVariable Long clubScheduleId) {
        return ResponseEntity.ok(clubScheduleService.getSchedule(clubScheduleId));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<Page<ClubScheduleResponse>> getSchedules(
            @PathVariable Long clubId,
            @MemberIdParam Long memberId,
            Pageable pageable) {
        return ResponseEntity.ok(clubScheduleService.getScheduleByClubId(clubId, memberId, pageable));
    }

    @PostMapping("/club/{clubId}")
    public ResponseEntity<ClubScheduleResponse> createSchedule(
            @PathVariable Long clubId,
            @Valid @RequestBody ClubScheduleRequest clubScheduleRequest,
            @MemberIdParam Long memberId) {
        return ResponseEntity.ok(clubScheduleService.createSchedule(clubId, clubScheduleRequest, memberId));
    }

    @PatchMapping("/{clubScheduleId}")
    public ResponseEntity<ClubScheduleResponse> updateSchedule(
            @PathVariable Long clubScheduleId,
            @Valid @RequestBody ClubScheduleRequest clubScheduleRequest,
            @MemberIdParam Long memberId) {
        return ResponseEntity.ok(clubScheduleService.updateSchedule(
                clubScheduleId, clubScheduleRequest, memberId));
    }

    @DeleteMapping("/{clubScheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long clubScheduleId,
            @MemberIdParam Long memberId) {
        clubScheduleService.deleteSchedule(clubScheduleId, memberId);
        return ResponseEntity.noContent().build();
    }

}
