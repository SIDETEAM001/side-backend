package com.sideteam.groupsaver.domain.location.dto;

import com.sideteam.groupsaver.domain.location.domain.Location;

public record LocationData(
        String name,
        String region1,
        String region2,
        String region3,
        Double longitude,
        Double latitude
) {

    public static LocationData of(String locationName,
                                  String region1, String region2, String region3,
                                  Double x, Double y) {
        return new LocationData(
                locationName,
                region1, region2, region3,
                x, y);
    }

    public static LocationData of(String locationName,
                                  String region1, String region2, String region3,
                                  String x, String y) {
        return LocationData.of(
                locationName,
                region1, region2, region3,
                Double.parseDouble(x), Double.parseDouble(y));
    }

    public static LocationData of(Location location) {
        return LocationData.of(
                location.getName(),
                location.getRegion1().getName(), location.getRegion2(), location.getRegion3(),
                location.getLocationCoordinate().getLongitude(), location.getLocationCoordinate().getLatitude());
    }
}
