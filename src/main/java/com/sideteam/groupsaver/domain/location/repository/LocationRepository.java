package com.sideteam.groupsaver.domain.location.repository;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.LocationCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationCustomRepository {

    default List<Location> findAllWithInCircleArea(LocationCoordinate locationCoordinate) {
        final int defaultRadiusMeter = 10;
        return findAllWithInCircleArea(
                locationCoordinate.getLongitude(), locationCoordinate.getLatitude(), defaultRadiusMeter);
    }

}
