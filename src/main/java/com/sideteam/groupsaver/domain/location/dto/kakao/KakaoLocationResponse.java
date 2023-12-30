package com.sideteam.groupsaver.domain.location.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public record KakaoLocationResponse(
        List<Document> documents,
        Meta meta
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Document(
            Address address,
            String addressType,
            Double x,
            Double y,
            String addressName,
            RoadAddress roadAddress) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Address(
            String mountainYn,
            String hCode,
            String mainAddressNo,
            Double x,
            String subAddressNo,
            Double y,
            String addressName,
            @JsonProperty("region_1depth_name")
            String region1depthName,
            @JsonProperty("region_2depth_name")
            String region2depthName,
            @JsonProperty("region_3depth_h_name")
            String region3depthHName,
            @JsonProperty("region_3depth_name")
            String region3depthName,
            String bCode,
            RoadAddress roadAddress
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record RoadAddress(
            String roadName,
            String mainBuildingNo,
            String buildingName,
            String region3depthName,
            String undergroundYn,
            String subBuildingNo,
            Double x,
            Double y,
            String addressName,
            String region2depthName,
            String zoneNo,
            String region1depthName
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(
            Integer totalCount,
            Boolean isEnd,
            Integer pageableCount
    ) {
    }
}
