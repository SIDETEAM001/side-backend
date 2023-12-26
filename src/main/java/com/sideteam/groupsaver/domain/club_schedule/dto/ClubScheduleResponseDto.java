package com.sideteam.groupsaver.domain.club_schedule.dto;

import com.sideteam.groupsaver.domain.club_schedule.domain.ClubSchedule;

import java.time.format.DateTimeFormatter;

public record ClubScheduleResponseDto(
        long clubScheduleId,
        long clubId,
        String title,
        String meetAt
) {
    public static ClubScheduleResponseDto from(ClubSchedule schedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new ClubScheduleResponseDto(
                schedule.getId(),
                schedule.getClub().getId(),
                schedule.getTitle(),
                schedule.getMeetAt().format(formatter));
    }
}
