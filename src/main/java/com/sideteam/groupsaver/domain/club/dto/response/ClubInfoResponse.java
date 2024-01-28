package com.sideteam.groupsaver.domain.club.dto.response;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.location.domain.Location;
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
        ClubCategory category,
        ClubCategoryMajor categoryMajor,
        ClubCategorySub categorySub,
        Boolean isActive,
        LocalDateTime startAt,
        ClubActivityType activityType,
        LocationInfo location
) {
    public record LocationInfo(
            Long id,
            String name,
            String region1,
            String region2,
            String region3) {
        static LocationInfo of(Location location) {
            return new LocationInfo(location.getId(), location.getName(),
                    location.getRegion1().getName(), location.getRegion2(), location.getRegion3());
        }
    }

    public static ClubInfoResponse of(Club club) {
        return ClubInfoResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .memberCurrentNumber(club.getMemberCurrentNumber())
                .memberMaxNumber(club.getMemberMaxNumber())
                .clubType(club.getType())
                .description(club.getDescription())
                .category(club.getCategory())
                .categoryMajor(club.getCategoryMajor())
                .categorySub(club.getCategorySub())
                .mainImage(club.getMainImage())
                .isActive(club.isActive())
                .startAt(club.getStartAt())
                .activityType(club.getActivityType())
                .creatorId(club.getCreator().getId())
                .location(LocationInfo.of(club.getLocation()))
                .build();
    }

}
