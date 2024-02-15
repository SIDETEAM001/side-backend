package com.sideteam.groupsaver.domain.join.repository;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.join.domain.WantClubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WantClubCategoryRepository extends JpaRepository<WantClubCategory, Long> {

    @Query("SELECT wcc.categoryMajor FROM WantClubCategory wcc WHERE wcc.member.id = :memberId")
    List<ClubCategoryMajor> findAllCategoryMajorByMemberId(@Param("memberId") Long memberId);

    void deleteAllByMemberId(@Param("memberId") Long memberId);

}
