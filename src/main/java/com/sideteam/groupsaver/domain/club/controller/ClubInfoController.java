package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.service.ClubSearchService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.sideteam.groupsaver.domain.location.service.LocationUtils.DEFAULT_LATITUDE_STRING;
import static com.sideteam.groupsaver.domain.location.service.LocationUtils.DEFAULT_LONGITUDE_STRING;

@Tag(name = "Club")
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-info")
@RestController
public class ClubInfoController {

    private final ClubSearchService clubSearchService;


    @GetMapping("/text")
    public ResponseEntity<Slice<ClubInfoResponse>> searchByText(
            @RequestParam("query") String text,
            Pageable pageable) {
        return ResponseEntity.ok(clubSearchService.searchByText(text, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Slice<ClubInfoResponse>> search(
            @RequestParam(value = "query", defaultValue = "") String text,
            @RequestParam(value = "longitude", defaultValue = DEFAULT_LONGITUDE_STRING) Double longitude,
            @RequestParam(value = "latitude", defaultValue = DEFAULT_LATITUDE_STRING) Double latitude,

            @Parameter(description = "검색 범위 (meter 단위)")
            @RequestParam(value = "radius", defaultValue = "20000") Integer radiusMeter,

            @RequestParam(value = "category", required = false) ClubCategory category,
            @RequestParam(value = "major", required = false) ClubCategoryMajor categoryMajor,
            @RequestParam(value = "sub", required = false) ClubCategorySub categorySub,
            @RequestParam(value = "type", required = false) ClubType type,
            Pageable pageable) {
        return ResponseEntity.ok(clubSearchService.search(
                text,
                longitude, latitude, radiusMeter,
                category, categoryMajor, categorySub, type, pageable));
    }

}
