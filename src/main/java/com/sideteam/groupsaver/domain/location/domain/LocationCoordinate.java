package com.sideteam.groupsaver.domain.location.domain;

import com.sideteam.groupsaver.domain.location.service.LocationUtils;
import com.sideteam.groupsaver.global.util.SpatialExpressions;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

import static com.sideteam.groupsaver.domain.location.service.LocationUtils.SRID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LocationCoordinate {

    @Column(nullable = false, columnDefinition = "POINT SRID " + SRID)
    private Point coord; // 경도, 위도 (longitude, latitude) 정보


    public Double getLongitude() {
        return coord.getCoordinate().x;
    }

    public Double getLatitude() {
        return coord.getCoordinate().y;
    }

    public static LocationCoordinate of(String longitude, String latitude) {
        return new LocationCoordinate(Double.parseDouble(longitude), Double.parseDouble(latitude));
    }

    public static LocationCoordinate of(Double longitude, Double latitude) {
        return new LocationCoordinate(longitude, latitude);
    }


    private LocationCoordinate(Double longitude, Double latitude) {
        this.coord = SpatialExpressions.createPoint(
                LocationUtils.roundCoordinate(longitude),
                LocationUtils.roundCoordinate(latitude), SRID);
    }

}
