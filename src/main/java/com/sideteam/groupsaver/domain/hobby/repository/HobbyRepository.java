package com.sideteam.groupsaver.domain.hobby.repository;

import com.sideteam.groupsaver.domain.hobby.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
