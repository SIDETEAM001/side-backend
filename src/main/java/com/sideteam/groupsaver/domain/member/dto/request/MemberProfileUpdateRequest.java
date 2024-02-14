package com.sideteam.groupsaver.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.JobMajor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_PATTERN;
import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_PATTERN_EXAMPLE;

@Schema(description = "사용자 프로필 수정 요청")
public record MemberProfileUpdateRequest(
        String nickname,
        @Schema(description = "생년월일", example = LOCAL_DATE_PATTERN_EXAMPLE)
        @JsonFormat(pattern = LOCAL_DATE_PATTERN)
        LocalDate birth,
        String profileImageUrl,
        JobMajor jobCategory,
        List<ClubCategoryMajor> clubCategories
) {
}
