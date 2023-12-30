package com.sideteam.groupsaver.domain.location.repository;

import com.sideteam.groupsaver.domain.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByNameContains(String name);

    @Query("SELECT l " +
            "FROM Location l " +
            "WHERE l.locationCoordinate.latitude=:longitude AND l.locationCoordinate.latitude=:latitude")
    List<Location> findByLongitudeAndLatitude(Double longitude, Double latitude);

}
