package com.sideteam.groupsaver.domain.location.repository;

import com.sideteam.groupsaver.domain.location.domain.Location;

import java.util.List;

public interface LocationCustomRepository {

    List<Location> findAllByNameContains(String text);

    List<Location> findAllWithInCircleArea(double longitude, double latitude, int radiusMeter);

}
