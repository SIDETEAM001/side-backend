package com.sideteam.groupsaver.domain.join.repository;

import com.sideteam.groupsaver.domain.join.domain.ClubBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubBookmarkRepository extends JpaRepository<ClubBookmark, Long> {
    @EntityGraph(attributePaths = {"club.location"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<ClubBookmark> findByMemberId(Long memberId, Pageable pageable);

    void deleteByMemberIdAndClubId(Long memberId, Long clubId);

}
