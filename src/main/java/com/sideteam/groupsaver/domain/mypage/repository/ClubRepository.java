package com.sideteam.groupsaver.domain.mypage.repository;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.mypage.domain.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    Page<Club> findByMember(Member member, Pageable pageable);
}
