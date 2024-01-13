package com.sideteam.groupsaver.domain.club.dto.response;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClubInfoResponse(
        Long id,
        String name,
        String description,
        Integer memberCurrentNumber,
        Integer memberMaxNumber,
        ClubType clubType,
        String mainImage,
        Long creatorId,
        ClubCategoryMajor category,
        Boolean isActive,
        LocalDateTime startAt,
        ClubActivityType activityType
) {
    public static ClubInfoResponse of(Club club) {
        return ClubInfoResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .memberCurrentNumber(club.getMemberCurrentNumber())
                .memberMaxNumber(club.getMemberMaxNumber())
                .clubType(club.getType())
                .description(club.getDescription())
                .category(club.getCategoryMajor())
                .mainImage(club.getMainImage())
                .isActive(club.isActive())
                .startAt(club.getStartAt())
                .activityType(club.getActivityType())
                .build();
    }

}
