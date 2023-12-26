package com.sideteam.groupsaver.domain.join.repository;

import com.sideteam.groupsaver.domain.join.domain.WantHobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantHobbyRepository extends JpaRepository<WantHobby, Long> {
}
