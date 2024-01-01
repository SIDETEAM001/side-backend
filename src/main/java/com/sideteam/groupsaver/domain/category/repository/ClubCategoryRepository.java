package com.sideteam.groupsaver.domain.category.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubCategoryRepository extends JpaRepository<ClubCategory, Long> {
    @Query("SELECT c FROM ClubCategory c WHERE c.category = :category")
    List<ClubCategory> findAllByCategoryAndType(DevelopMajor category);
}
