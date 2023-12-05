package com.sideteam.groupsaver.domain.join.repository;

import com.sideteam.groupsaver.domain.join.domain.WantDevelop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantDevelopRepository extends JpaRepository<WantDevelop, Long> {
}
