package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.dto.CoordinateRequest;
import com.sideteam.groupsaver.domain.location.dto.LocationResponse;

import java.util.List;

public interface LocationService {

    /**
     * 주소로 위치 정보들을 조회해서 리스트로 반환
     *
     * @param address - 주소 문자열
     * @return - 주소 이름, 주소 좌표 리스트
     */
    List<LocationResponse> searchByName(String address);

    /**
     * 좌표로 현재 주변 위치 주소를 반환
     *
     * @param longitude - 경도
     * @param latitude  - 위도
     * @return - 주변 위치 주소 이름, 좌표 리스트
     */
    List<LocationResponse> searchByCoordinate(Double longitude, Double latitude);

    /**
     * 좌표로 현재 주변 위치 주소를 반환
     *
     * @param coordinateRequest - 좌표 정보 DTO
     * @return - 주변 위치 주소 이름, 좌표 리스트
     */
    List<LocationResponse> searchByCoordinate(CoordinateRequest coordinateRequest);
}
