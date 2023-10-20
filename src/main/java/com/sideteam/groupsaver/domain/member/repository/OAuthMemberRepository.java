package com.sideteam.groupsaver.domain.member.repository;

import com.sideteam.groupsaver.domain.member.domain.OAuthMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthMemberRepository extends JpaRepository<OAuthMember, Long> {
    Optional<OAuthMember> findByEmail(String email);
}