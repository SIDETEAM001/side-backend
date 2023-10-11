package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmailOrNickname(String email, String nickname);

}
