package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.exception.club.ClubErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.function.BooleanSupplier;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_MEMBER_DO_NOT_HAVE_PERMISSION;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Page<ClubMember> findByMemberAndStatus(Member member, ClubMemberStatus status, Pageable pageable);

    boolean existsByClubIdAndMemberId(Long clubId, Long memberId);

    void deleteByClubIdAndMemberId(Long clubId, Long memberId);

    @Query("SELECT CASE WHEN COUNT(cm) >= c.memberMaxNumber THEN true ELSE false END " +
            "FROM Club c JOIN ClubMember cm ON c.id = cm.club.id WHERE cm.club.id = :clubId ")
    boolean hasExceededMemberLimit(Long clubId);

    @Query("SELECT CASE WHEN COUNT(*) >= 1 THEN true ELSE false END " +
            "FROM Club c JOIN ClubMember cm ON c.id = cm.club.id WHERE cm.club.id = :clubId AND cm.member.id = :memberId AND cm.role = :role")
    boolean hasClubRole(Long clubId, Long memberId, ClubMemberRole role);

    @Query("SELECT cm.member FROM ClubMember cm WHERE cm.club.id = :clubId")
    Page<Member> findAllMembersByClubId(Long clubId, Pageable pageable);

    @Query("SELEcT cm.member FROM ClubMember cm WHERE cm.club.id = :clubId")
    List<Member> findAllMembersByClubId(Long clubId);

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
