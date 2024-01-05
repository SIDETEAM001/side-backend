package com.sideteam.groupsaver.domain.location.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.Math.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationUtils {

    private static final double EARTH_RADIUS_KM = 6371.0; // 지구 반지름(km)
    private static final int ROUND_POSITION = 100_000; // 소수점 6자리

    public static double calculateHaversineDistanceKm(
            double latitude1, double longitude1, double latitude2, double longitude2) {

        double deltaLatitude = toRadians(abs(latitude1 - latitude2));
        double deltaLongitude = toRadians(abs(longitude1 - longitude2));

        double sinDeltaLatitude = sin(deltaLatitude / 2);
        double sinDeltaLongitude = sin(deltaLongitude / 2);

        double squareRoot = sqrt(
                sinDeltaLatitude * sinDeltaLatitude +
                        cos(toRadians(latitude1)) * cos(toRadians(latitude2)) * sinDeltaLongitude * sinDeltaLongitude);

        return 2 * EARTH_RADIUS_KM * asin(squareRoot);
    }

    public static Double roundCoordinate(Double coordinate) {
        return (double) Math.round(coordinate * ROUND_POSITION) / ROUND_POSITION;
    }


    public static void main(String[] args) {
        double distance = calculateHaversineDistanceKm(37.547889, 126.997128, 35.158874, 129.043846);
        System.out.println("Haversine Distance: " + distance + " km");
    }

}
