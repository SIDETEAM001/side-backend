package com.sideteam.groupsaver.domain.club_schedule.controller;

import com.sideteam.groupsaver.domain.club_schedule.dto.request.ClubScheduleRequest;
import com.sideteam.groupsaver.domain.club_schedule.dto.response.ClubScheduleResponse;
import com.sideteam.groupsaver.domain.club_schedule.service.ClubScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/club-schedule")
@RestController
public class ClubScheduleController {

    private final ClubScheduleService clubScheduleService;

    @GetMapping("/{clubScheduleId}")
    public ResponseEntity<ClubScheduleResponse> getSchedule(@PathVariable Long clubScheduleId) {
        return ResponseEntity.ok(clubScheduleService.getSchedule(clubScheduleId));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<Page<ClubScheduleResponse>> getSchedules(
            @PathVariable Long clubId,
            Pageable pageable) {
        return ResponseEntity.ok(clubScheduleService.getScheduleByClubId(clubId, pageable));
    }

    @PostMapping("/club/{clubId}")
    public ResponseEntity<ClubScheduleResponse> createSchedule(
            @PathVariable Long clubId,
            @Valid @RequestBody ClubScheduleRequest clubScheduleRequestDto) {
        return ResponseEntity.ok(clubScheduleService.createSchedule(clubId, clubScheduleRequestDto));
    }

    @PatchMapping("/{clubScheduleId}")
    public ResponseEntity<ClubScheduleResponse> updateSchedule(
            @PathVariable Long clubScheduleId,
            @Valid @RequestBody ClubScheduleRequest clubScheduleRequestDto) {
        return ResponseEntity.ok(clubScheduleService.updateSchedule(clubScheduleId, clubScheduleRequestDto));
    }

    @DeleteMapping("/{clubScheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long clubScheduleId) {
        clubScheduleService.deleteSchedule(clubScheduleId);
        return ResponseEntity.noContent().build();
    }

}
