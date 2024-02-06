package com.sideteam.groupsaver.domain.club.dto.request;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.location.dto.request.LocationInfoRequest;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubRequestDto (
    String name,
    String description,
    Integer memberMaxNumber,
    LocalDateTime startAt,
    String mainImage,
    ClubCategoryMajor categoryMajor,
    ClubCategorySub categorySub,
    ClubType type,
    ClubActivityType activityType,
    LocationInfoRequest locationInfo,
    String locationDetail
) {
    public static ClubRequestDto of(
            String name,
            String description,
            Integer memberMaxNumber,
            LocalDateTime startAt,
            String mainImage,
            ClubCategoryMajor categoryMajor,
            ClubCategorySub categorySub,
            ClubType type,
            ClubActivityType activityType,
            LocationInfoRequest locationInfo,
            String locationDetail) {
        return ClubRequestDto.builder()
                .name(name)
                .description(description)
                .memberMaxNumber(memberMaxNumber)
                .startAt(startAt)
                .mainImage(mainImage)
                .categoryMajor(categoryMajor)
                .categorySub(categorySub)
                .type(type)
                .activityType(activityType)
                .locationInfo(locationInfo)
                .locationDetail(locationDetail)
                .build();
    }
}
