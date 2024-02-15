package com.sideteam.groupsaver.domain.club.dto.response;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.location.domain.Location;

public record ClubSimpleInfoDto(
        long id,
        String name,
        int memberCurrentNumber,
        int memberMaxNumber,
        ClubType type,
        ClubActivityType activityType,
        String description,
        ClubCategoryMajor categoryMajor,
        String mainImageUrl,
        LocationInfo location
) {
    public record LocationInfo(
            long id,
            String name,
            String region1,
            String region2,
            String region3,
            String region4
    ) {
        static LocationInfo of(Location location) {
            if (location == null) {
                return empty();
            }
            return new LocationInfo(location.getId(), location.getName(),
                    location.getRegion1().getName(), location.getRegion2(), location.getRegion3(), location.getRegion4());
        }

        static LocationInfo empty() {
            return new LocationInfo(0, "없음", "", "", "", "");
        }
    }

    public static ClubSimpleInfoDto of(Club club) {
        return new ClubSimpleInfoDto(
                club.getId(),
                club.getName(),
                club.getMemberCurrentNumber(),
                club.getMemberMaxNumber(),
                club.getType(),
                club.getActivityType(),
                club.getDescription(),
                club.getCategoryMajor(),
                club.getMainImage(),
                LocationInfo.of(club.getLocation())
        );
    }

}
