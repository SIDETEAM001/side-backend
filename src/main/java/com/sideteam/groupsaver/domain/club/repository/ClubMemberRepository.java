package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Page<ClubMember> findByMemberAndStatus(Member member, ClubMemberStatus status, Pageable pageable);

    boolean existsByMemberIdAndClubId(Long memberId, Long clubId);

}
