package com.sideteam.groupsaver.domain.club_schedule.controller;

import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleRequestDto;
import com.sideteam.groupsaver.domain.club_schedule.dto.ClubScheduleResponseDto;
import com.sideteam.groupsaver.domain.club_schedule.service.ClubScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/club-schedule")
@RestController
public class ClubScheduleController {

    private final ClubScheduleService clubScheduleService;

    @GetMapping("/{clubScheduleId}")
    public ResponseEntity<ClubScheduleResponseDto> getSchedule(@PathVariable Long clubScheduleId) {
        return ResponseEntity.ok(clubScheduleService.getSchedule(clubScheduleId));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<ClubScheduleResponseDto>> getSchedules(
            @PathVariable Long clubId,
            Pageable pageable) {
        return ResponseEntity.ok(clubScheduleService.getScheduleByClubId(clubId, pageable));
    }

    @PostMapping("/club/{clubId}")
    public ResponseEntity<ClubScheduleResponseDto> createSchedule(
            @PathVariable Long clubId,
            @RequestBody ClubScheduleRequestDto clubScheduleRequestDto) {
        return ResponseEntity.ok(clubScheduleService.createSchedule(clubId, clubScheduleRequestDto));
    }

    @PostMapping("/{clubScheduleId}")
    public ResponseEntity<ClubScheduleResponseDto> updateSchedule(
            @PathVariable Long clubScheduleId,
            @RequestBody ClubScheduleRequestDto clubScheduleRequestDto) {
        return ResponseEntity.ok(clubScheduleService.updateSchedule(clubScheduleId, clubScheduleRequestDto));
    }

    @DeleteMapping("/{clubScheduleId}")
    public ResponseEntity<Void> getSchedules(@PathVariable Long clubScheduleId) {
        clubScheduleService.deleteSchedule(clubScheduleId);
        return ResponseEntity.noContent().build();
    }

}
