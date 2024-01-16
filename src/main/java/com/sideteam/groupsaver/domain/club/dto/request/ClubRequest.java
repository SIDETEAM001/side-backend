package com.sideteam.groupsaver.domain.club.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.location.dto.request.LocationInfoRequest;
import com.sideteam.groupsaver.global.util.ProjectTimeFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ClubRequest(
        // TODO : Enum validation check logic
        String name,
        String description,
        @Min(value = 1, message = "최소 모임 인원수는 1명 이상이어야 합니다")
        Integer memberMaxNumber,
        @JsonFormat(pattern = ProjectTimeFormat.LOCAL_DATE_TIME_PATTERN)
        LocalDateTime startAt,
        String mainImage,
        ClubCategoryMajor categoryMajor,
        ClubCategorySub categorySub,
        ClubType type,
        ClubActivityType activityType,
        @NotNull
        LocationInfoRequest locationInfo,
        String locationDetail
) {

    @AssertTrue(message = "sub 카테고리의 major과, major 카테고리가 일치해아 합니다")
    public boolean isSameCategoryMajorAndCategorySubMajor() {
        return categoryMajor == null || categorySub == null || categoryMajor == categorySub.getMajor();
    }

    @AssertTrue(message = "카테고리 Major / Sub, 둘 중 적어도 하나는 null이 아니어야 합니다")
    public boolean isCategoryAtLeastExist() {
        return categoryMajor != null || categorySub != null;
    }


    public ClubCategoryMajor getMajor() {
        if (this.categorySub != null) {
            return categorySub.getMajor();
        }
        return this.categoryMajor;
    }

}
