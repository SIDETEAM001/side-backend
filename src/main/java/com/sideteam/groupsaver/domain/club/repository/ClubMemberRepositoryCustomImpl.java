package com.sideteam.groupsaver.domain.club.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sideteam.groupsaver.domain.club.domain.QClub.club;
import static com.sideteam.groupsaver.domain.club.domain.QClubMember.clubMember;

@RequiredArgsConstructor
@Repository
public class ClubMemberRepositoryCustomImpl implements ClubMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean hasExceededMemberLimit(Long clubId) {
        return Boolean.TRUE.equals(queryFactory
                .select(clubMember.count()
                        .goe(club.memberMaxNumber.longValue()))
                .from(clubMember)
                .join(clubMember.club).on(clubMember.club.id.eq(clubId))
                .fetchOne());
    }

    @Override
    public boolean hasClubRole(Long clubId, Long memberId, ClubMemberRole role) {
        return Boolean.TRUE.equals(queryFactory
                .select(clubMember.count()
                        .goe(1L))
                .from(clubMember)
                .join(clubMember.club).on(clubMember.club.id.eq(clubId))
                .where(clubMember.member.id.eq(memberId)
                        .and(clubMember.role.eq(role)))
                .limit(1)
                .fetchOne());
    }

}
