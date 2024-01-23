package com.sideteam.groupsaver.domain.club.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.location.dto.request.LocationInfoRequest;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN;
import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN_EXAMPLE;

public record ClubRequest(
        @Size(min = 1, max = 100)
        String name,
        @Size(min = 10, max = 1000)
        String description,
        @Min(value = 1, message = "최소 모임 인원수는 1명 이상이어야 합니다")
        Integer memberMaxNumber,
        @Schema(description = "모임 시작일", example = LOCAL_DATE_TIME_PATTERN_EXAMPLE, type = "string")
        @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN)
        LocalDateTime startAt,
        String mainImage,
        ClubCategoryMajor categoryMajor,
        ClubCategorySub categorySub,
        @NotNull
        ClubType type,
        @NotNull
        ClubActivityType activityType,
        @NotNull
        LocationInfoRequest locationInfo,
        String locationDetail
) {

    @Hidden
    @AssertTrue(message = "sub 카테고리의 major과, major 카테고리가 일치해아 합니다")
    public boolean isSameCategoryMajorAndCategorySubMajor() {
        return categoryMajor == null || categorySub == null || categoryMajor == categorySub.getMajor();
    }

    @Hidden
    @AssertTrue(message = "카테고리 Major / Sub, 둘 중 적어도 하나는 null이 아니어야 합니다")
    public boolean isCategoryAtLeastExist() {
        return categoryMajor != null || categorySub != null;
    }

    @Hidden
    public ClubCategoryMajor getMajor() {
        if (this.categorySub != null) {
            return categorySub.getMajor();
        }
        return this.categoryMajor;
    }

}
