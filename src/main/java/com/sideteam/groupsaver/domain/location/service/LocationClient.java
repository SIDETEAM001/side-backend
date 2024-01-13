package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.dto.LocationData;

import java.util.List;

public interface LocationClient {

    List<LocationData> fetchByAddressName(String address);

    List<LocationData> fetchByCoordinate(Double longitude, Double latitude);
}
