package com.sideteam.groupsaver.domain.location.dto.response;

import com.sideteam.groupsaver.domain.location.domain.Location;

import static java.lang.Math.round;

public record LocationResponse(
        String addressName,
        String region1,
        String region2,
        String region3,
        Double longitude,
        Double latitude
) {

    public static LocationResponse of(String addressName,
                                      String region1, String region2, String region3,
                                      Double x, Double y) {
        return new LocationResponse(
                addressName,
                region1, region2, region3,
                x, y);
    }

    public static LocationResponse of(String addressName,
                                      String region1, String region2, String region3,
                                      String x, String y) {
        return LocationResponse.of(
                addressName,
                region1, region2, region3,
                Double.parseDouble(x), Double.parseDouble(y));
    }

    public static LocationResponse of(Location location) {
        return LocationResponse.of(
                location.getName(),
                location.getRegion1().getName(), location.getRegion2(), location.getRegion3(),
                location.getLocationCoordinate().getLongitude(), location.getLocationCoordinate().getLatitude());
    }
}
