package com.sideteam.groupsaver.domain.join.repository;

import com.sideteam.groupsaver.domain.join.domain.HobbyBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyBookmarkRepository extends JpaRepository<HobbyBookmark, Long> {
}