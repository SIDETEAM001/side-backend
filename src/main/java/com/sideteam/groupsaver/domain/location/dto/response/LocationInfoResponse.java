package com.sideteam.groupsaver.domain.location.dto.response;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.Region1Type;

public record LocationInfoResponse(
        Long id,
        String name,
        Region1Type region1,
        String region2,
        String region3,
        String region4,
        Double longitude,
        Double latitude,
        boolean isValidLocation
) {

    public static LocationInfoResponse of(Location location) {
        if (location == null) {
            return empty();
        }
        return new LocationInfoResponse(
                location.getId(), location.getName(),
                location.getRegion1(), location.getRegion2(), location.getRegion3(), location.getRegion4(),
                location.getLocationCoordinate().getLongitude(), location.getLocationCoordinate().getLatitude(),
                true);
    }

    public static LocationInfoResponse empty() {
        return new LocationInfoResponse(null, "",
                null, null, null, null,
                null, null, false);
    }

}
