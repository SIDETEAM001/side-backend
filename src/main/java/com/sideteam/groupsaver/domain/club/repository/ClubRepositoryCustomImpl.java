package com.sideteam.groupsaver.domain.club.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import com.sideteam.groupsaver.global.util.QueryUtils;
import com.sideteam.groupsaver.global.util.SpatialExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sideteam.groupsaver.domain.club.domain.ClubActivityType.ONLINE;
import static com.sideteam.groupsaver.domain.club.domain.QClub.club;
import static com.sideteam.groupsaver.domain.location.domain.QLocation.location;
import static com.sideteam.groupsaver.domain.location.service.LocationUtils.SRID;
import static com.sideteam.groupsaver.global.util.QueryUtils.eq;

@RequiredArgsConstructor
@Repository
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClubInfoResponse> search(
            String text,
            Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub, ClubType type,
            Pageable pageable) {

        List<ClubInfoResponse> clubs = queryFactory
                .selectFrom(club)
                .join(club.location, location).fetchJoin()
                .where(hasCategoryAndTextWithin(text, longitude, latitude, radiusMeter, category, categoryMajor, categorySub, type))
                .orderBy(QueryUtils.getOrderSpecifiers(pageable, club))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream().map(ClubInfoResponse::of).toList();

        JPQLQuery<Club> count = queryFactory
                .selectFrom(club)
                .where(hasCategoryAndTextWithin(text, longitude, latitude, radiusMeter, category, categoryMajor, categorySub, type));

        return QueryUtils.createPage(clubs, pageable, count::fetchCount);
    }


    @Override
    public Page<ClubInfoResponse> searchInCategories(
            Double longitude, Double latitude, Integer radiusMeter,
            List<ClubCategoryMajor> categoryMajors, ClubType type,
            Pageable pageable, int randomValue) {

        List<ClubInfoResponse> clubs = queryFactory
                .selectFrom(club)
                .join(club.location, location).fetchJoin()
                .where(club.isActive, club.randomId.gt(randomValue),
                        club.activityType.eq(ONLINE)
                                .or(SpatialExpressions.within(longitude, latitude, SRID, location.locationCoordinate.coord, radiusMeter)),
                        isIn(categoryMajors), eq(club.type, type))
                .orderBy(QueryUtils.getOrderSpecifiers(pageable, club))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream().map(ClubInfoResponse::of).toList();
        return new PageImpl<>(clubs);
    }


    private BooleanExpression[] hasClubCategory(ClubCategory category, ClubCategoryMajor categoryMajor,
                                                ClubCategorySub categorySub, ClubType type) {
        return new BooleanExpression[]{eq(club.category, category), eq(club.categoryMajor, categoryMajor),
                eq(club.categorySub, categorySub), eq(club.type, type)};
    }

    private BooleanExpression[] hasClubCategoryWithin(
            Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub, ClubType type) {

        List<BooleanExpression> expressions = new ArrayList<>();
        expressions.add(club.isActive);
        expressions.add(SpatialExpressions.within(longitude, latitude, SRID, location.locationCoordinate.coord, radiusMeter));

        Collections.addAll(expressions, hasClubCategory(category, categoryMajor, categorySub, type));

        return expressions.toArray(new BooleanExpression[0]);
    }


    private BooleanExpression[] hasCategoryAndTextWithin(
            String text, Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub, ClubType type) {

        List<BooleanExpression> expressions = new ArrayList<>();
        expressions.add(QueryUtils.contains(club.name, text));
        Collections.addAll(expressions, hasClubCategoryWithin(longitude, latitude, radiusMeter, category, categoryMajor, categorySub, type));
        return expressions.toArray(new BooleanExpression[0]);
    }

    private BooleanExpression isIn(List<ClubCategoryMajor> categoryMajors) {
        if (categoryMajors == null || categoryMajors.isEmpty()) {
            return null;
        }
        return club.categoryMajor.in(categoryMajors);
    }

}
