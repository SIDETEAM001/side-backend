package com.sideteam.groupsaver.domain.location.repository;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.LocationCoordinate;
import com.sideteam.groupsaver.domain.location.service.LocationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByNameContains(String name);

    @Query(value =
            "SELECT * " +
            "FROM location lo " +
            "WHERE ST_CONTAINS(ST_BUFFER( ST_SRID(POINT(:longitude, :latitude)," + LocationUtils.SRID +
            " ), :radius), lo.coord)",
            nativeQuery = true)
    List<Location> findAllWithInCircleArea(
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("radius") int radiusMeter);

    default List<Location> findAllWithInCircleArea(LocationCoordinate locationCoordinate) {
        final int defaultRadiusMeter = 10;
        return findAllWithInCircleArea(
                locationCoordinate.getLongitude(), locationCoordinate.getLatitude(), defaultRadiusMeter);
    }

}
