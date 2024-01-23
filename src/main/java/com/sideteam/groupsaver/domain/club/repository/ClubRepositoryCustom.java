package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubRepositoryCustom {

    Page<ClubInfoResponse> search(
            String text,
            Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub,
            ClubType type, Pageable pageable);
}
