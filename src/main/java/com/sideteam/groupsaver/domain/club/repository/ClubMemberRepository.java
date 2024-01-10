package com.sideteam.groupsaver.domain.club.repository;

import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.global.exception.club.ClubErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_MEMBER_DO_NOT_HAVE_PERMISSION;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    Page<ClubMember> findByMemberAndStatus(Member member, ClubMemberStatus status, Pageable pageable);
    ClubMember findByMemberIdAndClubId(Long memberId, Long clubId);
    boolean existsByMemberIdAndClubId(Long memberId, Long clubId);

    default void throwIfMemberNotInClub(Long memberId, Long clubId) {
        if (!existsByMemberIdAndClubId(memberId, clubId)) {
            throw new ClubErrorException(CLUB_MEMBER_DO_NOT_HAVE_PERMISSION, "멤버 ID: " + memberId + ", 모임 ID: " + clubId);
        }
    }

}
