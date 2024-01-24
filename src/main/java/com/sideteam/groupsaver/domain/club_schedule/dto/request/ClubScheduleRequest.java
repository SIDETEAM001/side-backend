package com.sideteam.groupsaver.domain.club_schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.global.util.ProjectTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN;
import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN_EXAMPLE;

public record ClubScheduleRequest(
        @Size(min = 1, max = 100)
        String title,
        @Size(min = 1, max = 1000)
        String description,
        @Min(value = 1, message = "최소 1명 이상 참여할 수 있어야 합니다")
        Long maxParticipation,
        String location,
        @Schema(description = "모임 일정 만남 시간", example = LOCAL_DATE_TIME_PATTERN_EXAMPLE, type = "string")
        @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
        LocalDateTime meetAt,
        @Min(value = 0, message = "참가비는 0원 이상이어야 합니다")
        Long participationFee
) {
}
