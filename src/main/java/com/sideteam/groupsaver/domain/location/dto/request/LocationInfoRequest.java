package com.sideteam.groupsaver.domain.location.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sideteam.groupsaver.domain.location.converter.LocationInfoDeserializer;
import com.sideteam.groupsaver.domain.location.dto.response.LocationInfoResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "위치 정보",
        example = "{ 'location': '경기도 성남' }"
                + " 또는 "
                + "{ 'longitude': 126.997, 'latitude': 37.547 }",
        type = "object")
@JsonDeserialize(using = LocationInfoDeserializer.class)
public record LocationInfoRequest(
        @Hidden
        Long id,
        @Hidden
        String name,
        @Hidden
        String region1,
        @Hidden
        String region2,
        @Hidden
        String region3,
        Double longitude,
        Double latitude
) {
    public static LocationInfoRequest of(LocationInfoResponse locationInfoResponse) {
        return new LocationInfoRequest(locationInfoResponse.id(), locationInfoResponse.name(),
                locationInfoResponse.region1(), locationInfoResponse.region2(), locationInfoResponse.region3(),
                locationInfoResponse.longitude(), locationInfoResponse.latitude());
    }
}
