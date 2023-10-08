package com.sideteam.groupsaver.persistence.repository.member;

import com.sideteam.groupsaver.persistence.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String userName);
}