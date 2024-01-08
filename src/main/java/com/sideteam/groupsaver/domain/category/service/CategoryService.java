package com.sideteam.groupsaver.domain.category.service;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.domain.HobbyCategory;
import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import com.sideteam.groupsaver.domain.category.repository.ClubCategoryRepository;
import com.sideteam.groupsaver.domain.category.repository.HobbyCategoryRepository;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubResponseDto;
import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final ClubCategoryRepository categoryRepository;

    public ClubCategory createClubCategory(DevelopMajor categoryType, Club club) {
        ClubCategory entity = ClubCategory.of(club, categoryType);
        return categoryRepository.save(entity);
    }

    public List<ClubResponseDto> getDevelopClubList(DevelopMajor major, ClubType type) {
        ClubCategory category = categoryRepository.findByMajor(major);
        return ClubResponseDto.listOf(category.getClubList(), type);
    }

    public ClubCategory checkACategory(DevelopMajor major, Club club) {
        if (!categoryRepository.existsByMajor(major)) {
            return createClubCategory(major, club);
        }
        ClubCategory category = categoryRepository.findByMajor(major);
        category.addAClub(club);
        return category;
    }
}