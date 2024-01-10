package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.dto.response.LocationResponse;

import java.util.List;

public interface LocationClient {

    List<LocationResponse> fetchByAddressName(String address);

    List<LocationResponse> fetchByCoordinate(Double longitude, Double latitude);
}
