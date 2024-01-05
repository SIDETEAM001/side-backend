package com.sideteam.groupsaver.domain.location.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sideteam.groupsaver.domain.location.dto.response.LocationResponse;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;
import static io.micrometer.common.util.StringUtils.isNotBlank;

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
        public LocationResponse toLocationResponse() {
            return LocationResponse.of(
                    addressName,
                    address.region1depthName, address.region2depthName, address.getRegion3(),
                    x, y);
        }

        public boolean hasRegion3Value() {
            return !(isBlank(address.region3depthHName()) && isBlank(address.region3depthName()));
        }
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
            @JsonProperty("region_3depth_name")
            String region3depthName,
            @JsonProperty("region_3depth_h_name")
            String region3depthHName,
            String bCode,
            RoadAddress roadAddress
    ) {
        public String getRegion3() {
            if (isNotBlank(region3depthName)) {
                return region3depthName;
            }
            return region3depthHName;
        }
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
            String zoneNo,
            @JsonProperty("region_1depth_name")
            String region1depthName,
            @JsonProperty("region_2depth_name")
            String region2depthName
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
