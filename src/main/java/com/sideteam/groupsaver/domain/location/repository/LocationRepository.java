package com.sideteam.groupsaver.domain.location.repository;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.LocationCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByNameContains(String name);

    List<Location> findByLocationCoordinate(LocationCoordinate locationCoordinate);

}
