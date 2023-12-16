package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.Club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT isStatus FROM Club WHERE id = :clubId")
    boolean findStatusByClubId(@Param("clubId") long clubId);

    @Query("UPDATE Club SET isStatus = false WHERE id = :clubId")
    void updateIsStatusByClubId(@Param("clubId") long clubId);
}