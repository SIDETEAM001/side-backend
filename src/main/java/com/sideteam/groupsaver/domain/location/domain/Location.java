package com.sideteam.groupsaver.domain.location.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.location.Region1TypeAttributeConverter;
import com.sideteam.groupsaver.domain.location.dto.LocationResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private LocationCoordinate locationCoordinate;

    private String name;

    @Convert(converter = Region1TypeAttributeConverter.class)
    private Region1Type region1; // 시도
    private String region2; // 구군
    private String region3; // 동읍리


    public static Location of(LocationResponse locationResponse) {
        return Location.of(
                locationResponse.longitude(), locationResponse.latitude(),
                locationResponse.addressName(),
                locationResponse.region1(), locationResponse.region2(), locationResponse.region3()
        );
    }


    public static Location of(
            Double longitude, Double latitude,
            String locationName,
            String region1, String region2, String region3) {
        return new Location(
                LocationCoordinate.of(longitude, latitude),
                locationName,
                Region1Type.of(region1), region2, region3);
    }


    private Location(
            LocationCoordinate locationCoordinate,
            String name,
            Region1Type region1, String region2, String region3) {
        this.locationCoordinate = locationCoordinate;
        this.name = name;
        this.region1 = region1;
        this.region2 = region2;
        this.region3 = region3;
    }

}
