package com.sideteam.groupsaver.domain.location.dto.response;

import com.sideteam.groupsaver.domain.location.domain.Location;

public record LocationInfoResponse(
        Long id,
        String name,
        String region1,
        String region2,
        String region3,
        Double longitude,
        Double latitude
) {

    public static LocationInfoResponse of(Location location) {
        return new LocationInfoResponse(
                location.getId(),
                location.getName(),
                location.getRegion1().getName(), location.getRegion2(), location.getRegion3(),
                location.getLocationCoordinate().getLongitude(), location.getLocationCoordinate().getLatitude());

    }

}
