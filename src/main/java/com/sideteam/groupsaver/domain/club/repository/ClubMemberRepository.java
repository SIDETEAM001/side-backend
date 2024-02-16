package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.exception.club.ClubErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.function.BooleanSupplier;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_MEMBER_DO_NOT_HAVE_PERMISSION;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long>, ClubMemberRepositoryCustom {

    @EntityGraph(attributePaths = {"club.location"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<ClubMember> findByMemberIdAndStatus(Long memberId, ClubMemberStatus status, Pageable pageable);

    boolean existsByClubIdAndMemberId(Long clubId, Long memberId);

    void deleteByClubIdAndMemberId(Long clubId, Long memberId);

    @Query("SELECT cm.member FROM ClubMember cm WHERE cm.club.id = :clubId")
    Page<Member> findAllMembersByClubId(Long clubId, Pageable pageable);

    default void throwIfMemberNotInClub(Long memberId, Long clubId) {
        if (!existsByClubIdAndMemberId(clubId, memberId)) {
            throw new ClubErrorException(CLUB_MEMBER_DO_NOT_HAVE_PERMISSION, "멤버 ID: " + memberId + ", 모임 ID: " + clubId);
        }
    }

    default boolean isLeader(Long clubId, Long memberId) {
        return hasClubRole(clubId, memberId, ClubMemberRole.LEADER);
    }

    default BooleanSupplier isInClub(Long clubId, Long memberId) {
        return () -> existsByClubIdAndMemberId(clubId, memberId);
    }

}
