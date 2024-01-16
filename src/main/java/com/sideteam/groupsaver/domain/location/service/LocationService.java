package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.LocationCoordinate;
import com.sideteam.groupsaver.domain.location.dto.LocationData;
import com.sideteam.groupsaver.domain.location.dto.response.LocationInfoResponse;
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
    public List<LocationInfoResponse> searchByName(String address) {

        List<Location> locations = locationRepository.findAllByNameContains(address);
        if (!locations.isEmpty()) {
            return locations.stream()
                    .map(LocationInfoResponse::of)
                    .distinct()
                    .toList();
        }

        return saveAllLocations(locationClient.fetchByAddressName(address));
    }

    /**
     * 좌표로 현재 주변 위치 주소를 반환
     *
     * @param longitude - 경도
     * @param latitude  - 위도
     * @return - 주변 위치 주소 이름, 좌표 리스트
     */
    public List<LocationInfoResponse> searchByCoordinate(Double longitude, Double latitude) {

        List<Location> locations =
                locationRepository.findAllWithInCircleArea(LocationCoordinate.of(longitude, latitude));
        if (!locations.isEmpty()) {
            return locations.stream()
                    .map(LocationInfoResponse::of)
                    .toList();
        }

        return saveAllLocations(locationClient.fetchByCoordinate(longitude, latitude));
    }


    private List<LocationInfoResponse> saveAllLocations(List<LocationData> locationResponse) {
        List<Location> savedLocations = locationRepository.saveAll(locationResponse.stream()
                .map(Location::of)
                .toList());

        return savedLocations.stream()
                .map(LocationInfoResponse::of)
                .toList();
    }

}
