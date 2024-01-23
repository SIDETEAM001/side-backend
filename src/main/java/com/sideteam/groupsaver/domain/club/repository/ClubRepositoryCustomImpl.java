package com.sideteam.groupsaver.domain.club.repository;

import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.category.domain.ClubCategorySub;
import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.response.ClubInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sideteam.groupsaver.domain.club.domain.QClub.club;
import static com.sideteam.groupsaver.domain.location.domain.QLocation.location;
import static com.sideteam.groupsaver.domain.location.service.LocationUtils.SRID;
import static com.sideteam.groupsaver.global.util.QueryUtils.*;

@RequiredArgsConstructor
@Repository
public class ClubRepositoryCustomImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClubInfoResponse> search(
            String text,
            Double longitude, Double latitude, Integer radiusMeter,
            ClubCategory category, ClubCategoryMajor categoryMajor, ClubCategorySub categorySub,
            ClubType type, Pageable pageable) {

        List<ClubInfoResponse> clubs = queryFactory
                .select(club)
                .from(club)
                .join(club.location, location).fetchJoin()
                .where(contains(club.name, text),
                        containsLocation(longitude, latitude, radiusMeter),
                        eq(club.category, category), eq(club.categoryMajor, categoryMajor),
                        eq(club.categorySub, categorySub), eq(club.type, type))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(club.memberCurrentNumber.asc())
                .fetch()
                .stream().map(ClubInfoResponse::of).toList();

        JPQLQuery<Club> count = queryFactory
                .select(club)
                .from(club)
                .where(contains(club.name, text),
                        containsLocation(longitude, latitude, radiusMeter),
                        eq(club.category, category), eq(club.categoryMajor, categoryMajor),
                        eq(club.categorySub, categorySub), eq(club.type, type));

        return createPage(clubs, pageable, count::fetchCount);
    }


    private BooleanTemplate containsLocation(Double longitude, Double latitude, Integer radiusMeter) {
        return Expressions.booleanTemplate(
                "ST_Contains(ST_Buffer(ST_GeomFromText(concat('POINT(',{0},' ',{1},')'), {2}), {3}), {4}) ",
                latitude, longitude, SRID, radiusMeter, location.locationCoordinate.coord);
    }

}
