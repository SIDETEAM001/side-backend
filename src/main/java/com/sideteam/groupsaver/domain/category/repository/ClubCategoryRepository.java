package com.sideteam.groupsaver.domain.category.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubCategoryRepository extends JpaRepository<ClubCategory, Long> {
    ClubCategory findByMajor(DevelopMajor major);

    boolean existsByMajor(DevelopMajor major);
}