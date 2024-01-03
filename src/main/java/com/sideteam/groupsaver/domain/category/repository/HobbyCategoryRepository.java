package com.sideteam.groupsaver.domain.category.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.category.domain.HobbyCategory;
import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyCategoryRepository extends JpaRepository<HobbyCategory, Long> {
    HobbyCategory findBySub(HobbySub sub);
}