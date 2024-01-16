package com.sideteam.groupsaver.domain.club.service;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClubSearchService {

    private final ClubRepository clubRepository;

    public Slice<ClubInfoResponse> searchByText(String text, Pageable pageable) {
        return clubRepository.findByNameContaining(text, pageable).map(ClubInfoResponse::of);
    }

    public Slice<ClubInfoResponse> searchByCoordinate(Double longitude, Double latitude, Integer radiusMeter, Pageable pageable) {
        return clubRepository.findByLocation(longitude, latitude, radiusMeter, pageable)
                .map(ClubInfoResponse::of);
    }

    public Slice<ClubInfoResponse> searchByCategoriesAndType(
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub,
            ClubType type, Pageable pageable) {

        return Optional.ofNullable(categorySub)
                .map(sub -> findByCategorySubAndType(sub, type, pageable))
                .or(() -> Optional.ofNullable(categoryMajor)
                        .map(major -> findByCategoryMajorAndType(major, type, pageable))
                )
                .or(() -> Optional.ofNullable(category)
                        .map(cat -> findByCategoryAndType(cat, type, pageable))
                )
                .orElseGet(() -> findAllClubs(pageable));
    }


    private Slice<ClubInfoResponse> findByCategorySubAndType(ClubCategorySub categorySub, ClubType type, Pageable pageable) {
        if (type == null) {
            return clubRepository.findByCategorySub(categorySub, pageable).map(ClubInfoResponse::of);
        }

        return clubRepository.findByCategorySubAndType(categorySub, type, pageable).map(ClubInfoResponse::of);
    }

    private Slice<ClubInfoResponse> findByCategoryMajorAndType(ClubCategoryMajor categoryMajor, ClubType type, Pageable pageable) {
        if (type == null) {
            return clubRepository.findByCategoryMajor(categoryMajor, pageable).map(ClubInfoResponse::of);
        }

        return clubRepository.findByCategoryMajorAndType(categoryMajor, type, pageable).map(ClubInfoResponse::of);
    }

    private Slice<ClubInfoResponse> findByCategoryAndType(ClubCategory category, ClubType type, Pageable pageable) {
        if (type == null) {
            return clubRepository.findByCategory(category, pageable).map(ClubInfoResponse::of);
        }
        return clubRepository.findByCategoryAndType(category, type, pageable).map(ClubInfoResponse::of);
    }

    private Slice<ClubInfoResponse> findAllClubs(Pageable pageable) {
        return clubRepository.findAll(pageable).map(ClubInfoResponse::of);
    }

}
