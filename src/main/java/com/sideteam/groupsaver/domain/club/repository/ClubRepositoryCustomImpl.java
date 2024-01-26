package com.sideteam.groupsaver.domain.club.repository;

import com.querydsl.core.types.Expression;
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
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
                .select(club)
                .from(club)
                .join(club.location, location).fetchJoin()
                .where(hasCategoryAndTextWithin(text, longitude, latitude, radiusMeter, category, categoryMajor, categorySub, type))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream().map(ClubInfoResponse::of).toList();

        JPQLQuery<Club> count = queryFactory
                .select(club)
                .from(club)
                .where(hasCategoryAndTextWithin(text, longitude, latitude, radiusMeter, category, categoryMajor, categorySub, type));

        return QueryUtils.createPage(clubs, pageable, count::fetchCount);
    }


    public Page<ClubInfoResponse> randomClub(
            Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub, ClubType type) {

        List<ClubInfoResponse> clubs = queryFactory
                .select(club)
                .from(club)
                .join(club.location, location).fetchJoin()
                .where(hasClubCategoryWithin(longitude, latitude, radiusMeter, category, categoryMajor, categorySub, type))
                .limit(30L)
                .fetch()
                .stream().map(ClubInfoResponse::of).toList();

        return new PageImpl<>(clubs);
    }


    private BooleanExpression within(Double longitude, Double latitude, Integer radiusMeter) {
        Point point = SpatialExpressions.createPoint(longitude, latitude, SRID);
        Expression<Geometry> buffer = SpatialExpressions.buffer(point, radiusMeter);

        return SpatialExpressions.contains(buffer, location.locationCoordinate.coord);
    }


    private BooleanExpression[] hasClubCategory(ClubCategory category, ClubCategoryMajor categoryMajor,
                                                ClubCategorySub categorySub, ClubType type) {
        return new BooleanExpression[]{eq(club.category, category), eq(club.categoryMajor, categoryMajor),
                eq(club.categorySub, categorySub), eq(club.type, type)};
    }

    private BooleanExpression[] hasClubCategoryWithin(
            Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub, ClubType type) {

        BooleanExpression[] isIn = new BooleanExpression[]{club.isActive, within(longitude, latitude, radiusMeter)};

        return (BooleanExpression[]) Stream.concat(Arrays.stream(isIn),
                Arrays.stream(hasClubCategory(category, categoryMajor, categorySub, type))).toArray();
    }

    private BooleanExpression[] hasCategoryAndTextWithin(
            String text, Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub, ClubType type) {
        BooleanExpression[] isIn = new BooleanExpression[]{club.isActive,
                QueryUtils.contains(club.name, text), within(longitude, latitude, radiusMeter)};
        return (BooleanExpression[]) Stream.concat(Arrays.stream(isIn),
                Arrays.stream(hasClubCategory(category, categoryMajor, categorySub, type))).toArray();
    }

}
