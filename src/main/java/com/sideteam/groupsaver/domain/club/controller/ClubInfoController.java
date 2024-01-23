package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.service.ClubSearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Club")
@RequiredArgsConstructor
@RequestMapping("/api/v1/club-info")
@RestController
public class ClubInfoController {

    private final ClubSearchService clubSearchService;


    @GetMapping("/search")
    public ResponseEntity<Slice<ClubInfoResponse>> searchByText(
            @RequestParam("query") String text,
            Pageable pageable) {
        return ResponseEntity.ok(clubSearchService.searchByText(text, pageable));
    }

    @GetMapping("/coordinate")
    public ResponseEntity<Slice<ClubInfoResponse>> searchByCoordinate(
            @RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude,
            @RequestParam(value = "radius", defaultValue = "20000") Integer radiusMeter,
            Pageable pageable) {
        return ResponseEntity.ok(clubSearchService.searchByCoordinate(longitude, latitude, radiusMeter, pageable));
    }

    @GetMapping("/categories")
    public ResponseEntity<Slice<ClubInfoResponse>> searchByCategories(
            @RequestParam(value = "category", required = false) ClubCategory category,
            @RequestParam(value = "major", required = false) ClubCategoryMajor categoryMajor,
            @RequestParam(value = "sub", required = false) ClubCategorySub categorySub,
            @RequestParam(value = "type", required = false) ClubType type,
            Pageable pageable) {
        return ResponseEntity.ok(clubSearchService.searchByCategoriesAndType(category, categoryMajor, categorySub, type, pageable));
    }

}
