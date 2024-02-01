package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.join.repository.WantClubCategoryRepository;
import com.sideteam.groupsaver.global.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClubRecommendService {

    private static final int MAX_COUNT = 50;
    private static final Pageable pageable = PageRequest.of(0, MAX_COUNT, Sort.by("randomId"));

    private final ClubRepository clubRepository;
    private final WantClubCategoryRepository wantClubCategoryRepository;

    private final RandomUtils randomUtils;

    @PreAuthorize("@authorityChecker.hasAuthority(#memberId)")
    public Page<ClubInfoResponse> getRecommendedClubs(
            Long memberId, Double longitude, Double latitude, Integer radiusMeter,
            ClubType type) {

        List<ClubCategoryMajor> categoryMajors = wantClubCategoryRepository.findAllCategoryMajorByMemberId(memberId);

        return clubRepository.searchInCategories(longitude, latitude, radiusMeter, categoryMajors, type, pageable, randomUtils.getRandomValue());
    }

}
