package com.sideteam.groupsaver.domain.club_schedule.dto;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;

import java.time.LocalDateTime;

public record ClubScheduleResponseDto(
        long clubScheduleId,
        long clubId,
        String title,
        LocalDateTime meetAt
) {
    public static ClubScheduleResponseDto from(ClubSchedule schedule) {
        return new ClubScheduleResponseDto(
                schedule.getId(),
                schedule.getClub().getId(),
                schedule.getTitle(),
                schedule.getMeetAt());
    }
}
