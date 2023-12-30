package com.sideteam.groupsaver.domain.location.domain;

import com.sideteam.groupsaver.domain.location.dto.CoordinateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private LocationCoordinate locationCoordinate;

    private String name;

    public static Location of(Double longitude, Double latitude, String locationName) {
        return new Location(
                LocationCoordinate.of(longitude, latitude),
                locationName);
    }

    private Location(LocationCoordinate locationCoordinate, String name) {
        this.locationCoordinate = locationCoordinate;
        this.name = name;
    }

}
