package com.sideteam.groupsaver.domain.club_schedule.dto;

import java.time.LocalDateTime;

public record ClubScheduleRequestDto(
        String title,
        String description,
        long maxParticipation,
        String location,
        LocalDateTime meetAt,
        long participationFee
) {
}
