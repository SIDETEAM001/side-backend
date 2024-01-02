package com.sideteam.groupsaver.domain.hobby.repository;

import com.sideteam.groupsaver.domain.hobby.domain.HobbyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyMemberRepository extends JpaRepository<HobbyMember, Long> {
}
