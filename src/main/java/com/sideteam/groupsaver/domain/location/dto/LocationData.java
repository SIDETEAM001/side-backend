package com.sideteam.groupsaver.domain.location.dto;

import com.sideteam.groupsaver.domain.location.domain.Region1Type;

import static org.springframework.util.StringUtils.hasText;

public record LocationData(
        String name,
        Region1Type region1,
        String region2,
        String region3,
        String region4,
        Double longitude,
        Double latitude
) {

    public static LocationData of(String region1, String region2, String region3, String region4,
                                  Double longitude, Double latitude) {
        Region1Type region1Type = Region1Type.of(region1);
        return new LocationData(
                region1Type.getName() + " " + region2 + " " + region3 + (hasText(region4) ? " " + region4 : ""),
                region1Type, region2, region3, region4,
                longitude, latitude);
    }

    public static LocationData of(String region1, String region2, String region3, String region4,
                                  String x, String y) {
        return LocationData.of(
                region1, region2, region3, region4,
                Double.parseDouble(x), Double.parseDouble(y));
    }

}
