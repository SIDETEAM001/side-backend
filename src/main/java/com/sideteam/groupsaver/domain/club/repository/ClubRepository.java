package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.Club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Modifying
    @Query("UPDATE Club c SET c.isStatus = false WHERE c.id = :clubId")
    void updateIsStatusByClubId(@Param("clubId") long clubId);
}