package com.sideteam.groupsaver.domain.location.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationCoordinate {

    private Double longitude; // 경도
    private Double latitude; // 위도


    public static LocationCoordinate of(String x, String y) {
        return new LocationCoordinate(Double.parseDouble(x), Double.parseDouble(y));
    }

    public static LocationCoordinate of(Double longitude, Double latitude) {
        return new LocationCoordinate(longitude, latitude);
    }


    private LocationCoordinate(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
