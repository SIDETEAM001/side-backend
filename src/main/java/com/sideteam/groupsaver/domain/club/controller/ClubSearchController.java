package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.dto.response.ClubSearchHistoryResponse;
import com.sideteam.groupsaver.domain.club.service.ClubSearchService;
import com.sideteam.groupsaver.domain.club.service.SearchHistoryService;
import com.sideteam.groupsaver.global.resolver.member_info.MemberIdParam;
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

import static com.sideteam.groupsaver.domain.location.service.LocationUtils.*;

@Tag(name = "Club")
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-search")
@RestController
public class ClubSearchController {

    private final ClubSearchService clubSearchService;
    private final SearchHistoryService searchHistoryService;

    @GetMapping
    public ResponseEntity<Slice<ClubInfoResponse>> search(
            @RequestParam(value = "query", defaultValue = "") String text,
            @RequestParam(value = "longitude", defaultValue = DEFAULT_LONGITUDE_STRING) Double longitude,
            @RequestParam(value = "latitude", defaultValue = DEFAULT_LATITUDE_STRING) Double latitude,

            @Parameter(description = "검색 범위 (meter 단위)")
            @RequestParam(value = "radius", defaultValue = DEFAULT_RADIUS_METER_STRING) Integer radiusMeter,

            @RequestParam(value = "category", required = false) ClubCategory category,
            @RequestParam(value = "major", required = false) ClubCategoryMajor categoryMajor,
            @RequestParam(value = "sub", required = false) ClubCategorySub categorySub,
            @RequestParam(value = "type", required = false) ClubType type,
            @MemberIdParam Long memberId,
            Pageable pageable
    ) {
        searchHistoryService.saveSearchWord(text, memberId);

        return ResponseEntity.ok(clubSearchService.search(
                text,
                longitude, latitude, radiusMeter,
                category, categoryMajor, categorySub, type,
                pageable));
    }

    @GetMapping("/history")
    public ResponseEntity<ClubSearchHistoryResponse> getRecentSearchHistory(@MemberIdParam Long memberId) {
        return ResponseEntity.ok(searchHistoryService.getRecentSearchHistory(memberId));
    }

}
