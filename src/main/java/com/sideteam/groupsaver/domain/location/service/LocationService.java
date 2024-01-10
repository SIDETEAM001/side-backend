package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.LocationCoordinate;
import com.sideteam.groupsaver.domain.location.dto.response.LocationResponse;
import com.sideteam.groupsaver.domain.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationClient locationClient;


    /**
     * 주소로 위치 정보들을 조회해서 리스트로 반환
     *
     * @param address - 주소 문자열
     * @return - 주소 이름, 주소 좌표 리스트
     */
    public List<LocationResponse> searchByName(String address) {

        List<Location> locations = locationRepository.findAllByNameContains(address);
        if (!locations.isEmpty()) {
            return locations.stream()
                    .map(LocationResponse::of)
                    .distinct()
                    .toList();
        }

        List<LocationResponse> locationResponses = locationClient.fetchByAddressName(address);
        saveAllLocations(locationResponses);
        return locationResponses;
    }

    /**
     * 좌표로 현재 주변 위치 주소를 반환
     *
     * @param longitude - 경도
     * @param latitude  - 위도
     * @return - 주변 위치 주소 이름, 좌표 리스트
     */
    public List<LocationResponse> searchByCoordinate(Double longitude, Double latitude) {

        List<Location> locations =
                locationRepository.findAllWithInCircleArea(LocationCoordinate.of(longitude, latitude));
        if (!locations.isEmpty()) {
            return locations.stream()
                    .map(LocationResponse::of)
                    .toList();
        }

        List<LocationResponse> locationResponses = locationClient.fetchByCoordinate(longitude, latitude);
        saveAllLocations(locationResponses);
        return locationResponses;
    }


    private void saveAllLocations(List<LocationResponse> locationResponses) {
        locationRepository.saveAll(locationResponses.stream()
                .map(Location::of)
                .toList());
    }

}
