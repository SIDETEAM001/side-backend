package com.sideteam.groupsaver.domain.club_schedule.dto;

import java.time.LocalDateTime;

public record ClubScheduleRequestDto(
        String title,
        LocalDateTime meetAt
) {
}
