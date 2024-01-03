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
import com.sideteam.groupsaver.domain.hobby.dto.HobbyResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HobbyCategoryService {
    private final HobbyCategoryRepository categoryRepository;

    public HobbyCategory createHobbyCategory(HobbySub categoryType, Hobby hobby) {
        HobbyCategory entity = HobbyCategory.of(hobby, categoryType);
        return categoryRepository.save(entity);
    }

    public List<HobbyResponseDto> getHobbyList(HobbySub sub, ClubType type) {
        HobbyCategory category = categoryRepository.findBySub(sub);
        return HobbyResponseDto.listOf(category.getHobbyList(), type);
    }

    public HobbyCategory checkACategory(HobbySub sub, Hobby hobby) {
        try {
            HobbyCategory category = categoryRepository.findBySub(sub);
            category.addAHobby(hobby);
            return category;
        } catch (NullPointerException e1) {
            return createHobbyCategory(sub, hobby);
        }
    }
}
