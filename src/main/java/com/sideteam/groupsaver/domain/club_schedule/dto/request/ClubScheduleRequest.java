package com.sideteam.groupsaver.domain.club_schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.global.util.ProjectTimeFormat;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public record ClubScheduleRequest(
        String title,
        String description,
        @Min(value = 1, message = "최소 1명 이상 참여할 수 있어야 합니다")
        Long maxParticipation,
        String location,
        @JsonFormat(pattern = ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN, timezone = "Asia/Seoul")
        LocalDateTime meetAt,
        @Min(value = 0, message = "참가비는 0원 이상이어야 합니다")
        Long participationFee
) {
}
