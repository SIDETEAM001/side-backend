package com.sideteam.groupsaver.domain.location.dto.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public record KakaoCoordinate2RegionResponse(
        List<Document> documents,
        Meta meta
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Document(
            String code,
            String region3depthName,
            Double x,
            Double y,
            String regionType,
            String addressName,
            String region2depthName,
            String region1depthName,
            String region4depthName
    ) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(
            Integer totalCount
    ) {
    }

}

