package com.sideteam.groupsaver.domain.location.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sideteam.groupsaver.domain.location.converter.LocationInfoDeserializer;
import com.sideteam.groupsaver.domain.location.dto.response.LocationInfoResponse;

@JsonDeserialize(using = LocationInfoDeserializer.class)
public record LocationInfoRequest(
        Long id,
        String name,
        String region1,
        String region2,
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
