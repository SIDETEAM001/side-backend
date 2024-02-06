package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequest;
import com.sideteam.groupsaver.domain.club.dto.request.ClubRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ClubAssembler {
    public ClubRequestDto toClubRequestDto(ClubRequest clubRequest) {
        ClubCategoryMajor major = ClubCategoryMajor.getCategory(clubRequest.categoryMajor());
        return ClubRequestDto.of(
                clubRequest.name(),
                clubRequest.description(),
                clubRequest.memberMaxNumber(),
                clubRequest.startAt(),
                clubRequest.mainImage(),
                major,
                getSubCategory(clubRequest.categorySub(), major),
                ClubType.getType(clubRequest.type()),
                ClubActivityType.getType(clubRequest.activityType()),
                clubRequest.locationInfo(),
                clubRequest.locationDetail()
        );
    }

    private ClubCategorySub getSubCategory(String sub, ClubCategoryMajor major) {
        if (sub == null) {
            return null;
        }
        ClubCategorySub category = ClubCategorySub.getCategory(sub);
        if (major != category.getMajor()) {
            throw new IllegalArgumentException();
        }
        return category;
    }
}
