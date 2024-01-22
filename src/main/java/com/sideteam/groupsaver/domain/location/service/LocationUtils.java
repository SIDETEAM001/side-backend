package com.sideteam.groupsaver.domain.location.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.Math.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationUtils {

    public static final int SRID = 4326; // Spatial Reference System ID

    public static final String DEFAULT_LOCATION = "서울 중구 명동1가";
    public static final double DEFAULT_LONGITUDE = 126.98464;
    public static final double DEFAULT_LATITUDE = 37.56429;
    public static final String DEFAULT_LONGITUDE_STRING = "126.98464";
    public static final String DEFAULT_LATITUDE_STRING = "37.56429";


    private static final double EARTH_RADIUS_KM = 6371.0; // 지구 반지름(km)
    private static final double ROUND_POSITION = 1E+5;

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

    public static double roundCoordinate(Double coordinate) {
        return Math.round(coordinate * ROUND_POSITION) / ROUND_POSITION;
    }

}
