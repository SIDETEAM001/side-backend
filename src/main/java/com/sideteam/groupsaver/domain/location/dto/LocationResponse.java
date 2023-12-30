package com.sideteam.groupsaver.domain.location.dto;

import com.sideteam.groupsaver.domain.location.domain.Location;

public record LocationResponse(
        String addressName,
        Double longitude,
        Double latitude
) {
    public static LocationResponse of(String addressName, String x, String y) {
        return new LocationResponse(
                addressName,
                Double.parseDouble(x), Double.parseDouble(y));
    }

    public static LocationResponse of(String addressName, Double x, Double y) {
        return new LocationResponse(addressName, x, y);
    }

    public static LocationResponse of(Location location) {
        return new LocationResponse(
                location.getName(),
                location.getLocationCoordinate().getLongitude(), location.getLocationCoordinate().getLatitude());
    }
}
