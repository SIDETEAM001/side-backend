package com.sideteam.groupsaver.domain.category.service;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.repository.ClubCategoryRepository;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final ClubCategoryRepository categoryRepository;

    public void createClubCategory(DevelopMajor categoryType, Club club) {
        ClubCategory entity = ClubCategory.of(club, categoryType);
        categoryRepository.save(entity);
    }

    public List<ClubResponseDto> getDevelopClubList(DevelopMajor category, ClubType type) {
        List<ClubCategory> clubList = categoryRepository.findAllByCategoryAndType(category);
        return ClubResponseDto.listOf(clubList);
    }
}