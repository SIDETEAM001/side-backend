package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    ClubMember findByMemberIdAndClubId(Long memberId, Long clubId);

    Page<ClubMember> findByMemberAndStatus(Member member, ClubMemberStatus status, Pageable pageable);

    boolean existsByMemberIdAndClubId(Long memberId, long clubId);
}
