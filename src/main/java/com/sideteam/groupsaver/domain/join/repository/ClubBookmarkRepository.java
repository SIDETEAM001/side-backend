package com.sideteam.groupsaver.domain.join.repository;

import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import com.sideteam.groupsaver.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubBookmarkRepository extends JpaRepository<ClubBookmark, Long> {
    Page<ClubBookmark> findByMember(Member member, Pageable pageable);
}
