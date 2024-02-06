package com.sideteam.groupsaver.domain.club.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.location.dto.request.LocationInfoRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN;
import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN_EXAMPLE;

public record ClubRequest(
        @Length(min = 1, max = 100)
        String name,
        @Length(min = 10, max = 1000)
        String description,
        @Min(value = 1, message = "최소 모임 인원수는 1명 이상이어야 합니다")
        Integer memberMaxNumber,
        @Schema(description = "모임 시작일", example = LOCAL_DATE_TIME_PATTERN_EXAMPLE, type = "string")
        @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
        LocalDateTime startAt,
        String mainImage,
        String categoryMajor,
        String categorySub,
        @NotNull
        String type,
        @NotNull
        String activityType,
        LocationInfoRequest locationInfo,
        String locationDetail
) {

}
