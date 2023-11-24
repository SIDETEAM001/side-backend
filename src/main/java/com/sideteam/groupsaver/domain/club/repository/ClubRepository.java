package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
