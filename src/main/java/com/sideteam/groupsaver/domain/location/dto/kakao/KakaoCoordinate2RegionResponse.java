package com.sideteam.groupsaver.domain.location.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sideteam.groupsaver.domain.location.dto.LocationResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public record KakaoCoordinate2RegionResponse(
        List<Document> documents,
        Meta meta
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Document(
            String code,
            Double x,
            Double y,
            String regionType,
            String addressName,
            @JsonProperty("region_1depth_name")
            String region1depthName,
            @JsonProperty("region_2depth_name")
            String region2depthName,
            @JsonProperty("region_3depth_name")
            String region3depthName,
            @JsonProperty("region_4depth_name")
            String region4depthName
    ) {
        public LocationResponse toLocationResponse() {
            return LocationResponse.of(
                    addressName,
                    region1depthName, region2depthName, region3depthName,
                    x, y);
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(
            Integer totalCount
    ) {
    }

}

