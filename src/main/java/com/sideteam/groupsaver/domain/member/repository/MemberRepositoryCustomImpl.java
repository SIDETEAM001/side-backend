package com.sideteam.groupsaver.domain.member.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.member.domain.MemberActive;
import com.sideteam.groupsaver.domain.member.dto.response.MyProfileResponse;
import com.sideteam.groupsaver.domain.member.dto.response.QMyProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sideteam.groupsaver.domain.category.domain.ClubCategory.DEVELOP;
import static com.sideteam.groupsaver.domain.category.domain.ClubCategory.HOBBY;
import static com.sideteam.groupsaver.domain.club.domain.QClubMember.clubMember;
import static com.sideteam.groupsaver.domain.join.domain.QClubBookmark.clubBookmark;
import static com.sideteam.groupsaver.domain.join.domain.QWantClubCategory.wantClubCategory;
import static com.sideteam.groupsaver.domain.member.domain.QMember.member;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public MyProfileResponse findMyProfileByMemberId(Long memberId) {
        JPQLQuery<Long> clubMemberCountSubQuery = JPAExpressions
                .select(clubMember.id.count())
                .from(clubMember)
                .where(clubMember.member.id.eq(memberId));

        JPQLQuery<Long> clubBookmarkCountSubQuery = JPAExpressions
                .select(clubBookmark.id.count())
                .from(clubBookmark)
                .where(clubBookmark.member.id.eq(memberId));

        List<ClubCategoryMajor> clubCategoryMajors = findAllCategoryMajorByMemberId(memberId);

        return queryFactory.select(new QMyProfileResponse(
                        member.id,
                        member.phoneNumber.phoneNumber,
                        member.nickname,
                        member.email,
                        member.birth,
                        member.profileUrl,
                        member.jobCategory,
                        Expressions.constant(filterCategories(clubCategoryMajors, DEVELOP)),
                        Expressions.constant(filterCategories(clubCategoryMajors, HOBBY)),
                        ExpressionUtils.as(clubMemberCountSubQuery, "myClubCount"),
                        ExpressionUtils.as(clubBookmarkCountSubQuery, "clubBookmarkCount")
                ))
                .from(member)
                .where(member.id.eq(memberId), member.activeStatus.eq(MemberActive.ACTIVE))
                .fetchOne();
    }


    private List<ClubCategoryMajor> findAllCategoryMajorByMemberId(Long memberId) {
        return queryFactory
                .select(wantClubCategory.categoryMajor)
                .from(wantClubCategory)
                .where(wantClubCategory.member.id.eq(memberId))
                .fetch();
    }

    private List<ClubCategoryMajor> filterCategories(List<ClubCategoryMajor> clubCategoryMajors, ClubCategory clubCategory) {
        return clubCategoryMajors.stream()
                .filter(clubCategoryMajor -> clubCategoryMajor.getCategory().equals(clubCategory))
                .toList();
    }

}
