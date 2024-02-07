package com.sideteam.groupsaver.domain.location.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.Region1Type;
import com.sideteam.groupsaver.global.util.QueryUtils;
import com.sideteam.groupsaver.global.util.SpatialExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sideteam.groupsaver.domain.location.domain.QLocation.location;
import static com.sideteam.groupsaver.domain.location.service.LocationUtils.SRID;
import static java.util.Collections.emptyList;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class LocationCustomRepositoryImpl implements LocationCustomRepository {

    private final JPAQueryFactory queryFactory;
    private static final int limit = 10;

    @Override
    public List<Location> findAllByNameContains(String text) {
        if (!hasText(text)) {
            return emptyList();
        }

        if (Region1Type.matchesRegion1TypeName(text)) {
            return queryFactory
                    .selectFrom(location)
                    .where(location.region1.eq(Region1Type.of(text)))
                    .limit(limit)
                    .fetch();
        }

        return Region1Type.findStartWithRegion1Name(text)
                .map(region1Type -> findByRegionNameWithRegion1(text, region1Type))
                .orElseGet(() -> findByRegionName(text));
    }

    @Override
    public List<Location> findAllWithInCircleArea(double longitude, double latitude, int radiusMeter) {
        return queryFactory
                .selectFrom(location)
                .where(SpatialExpressions.within(longitude, latitude, SRID, location.locationCoordinate.coord, radiusMeter))
                .fetch();
    }


    private List<Location> findByRegionNameWithRegion1(String text, Region1Type region1Type) {
        return queryFactory
                .selectFrom(location)
                .where(QueryUtils.contains(location.region2, location.region3, location.region4,
                                Region1Type.removeRegion1InString(text, region1Type)),
                        location.region1.eq(region1Type)
                )
                .limit(limit)
                .fetch();
    }

    private List<Location> findByRegionName(String text) {
        return queryFactory
                .selectFrom(location)
                .where(QueryUtils.contains(location.region2, location.region3, location.region4,
                        Region1Type.removeRegion1InString(text)))
                .limit(limit)
                .fetch();
    }

}
