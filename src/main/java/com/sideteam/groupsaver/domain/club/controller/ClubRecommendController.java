package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.service.ClubRecommendService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sideteam.groupsaver.domain.location.service.LocationUtils.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clubs/recommends")
public class ClubRecommendController {

    private final ClubRecommendService clubRecommendService;

    @GetMapping
    public Slice<ClubInfoResponse> getRecommendations(
            @RequestParam(value = "longitude", defaultValue = DEFAULT_LONGITUDE_STRING) Double longitude,
            @RequestParam(value = "latitude", defaultValue = DEFAULT_LATITUDE_STRING) Double latitude,

            @Parameter(description = "검색 범위 (meter 단위)")
            @RequestParam(value = "radius", defaultValue = DEFAULT_RADIUS_METER_STRING) Integer radiusMeter,
            @RequestParam(value = "type") ClubType type,
            @MemberIdParam Long memberId) {

        return clubRecommendService.getRecommendedClubs(memberId, longitude, latitude, radiusMeter, type);
    }

}
