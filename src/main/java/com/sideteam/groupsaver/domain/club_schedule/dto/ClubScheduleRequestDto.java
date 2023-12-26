package com.sideteam.groupsaver.domain.club_schedule.dto;

public record ClubScheduleRequestDto(
        String title,
        String description,
        long maxParticipation,
        String location,
        String meetAt,
//        @JsonFormat(shape = JsonFormat.Shape.STRING,
//                pattern = "yyyy-MM-dd HH:mm:ss",
//                timezone = "Asia/Seoul")
//        LocalDateTime meetAt,
        long participationFee
) {
}
