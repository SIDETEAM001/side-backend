package com.sideteam.groupsaver.domain.location.controller;

import com.sideteam.groupsaver.domain.location.dto.response.CoordinateDistanceResponse;
import com.sideteam.groupsaver.domain.location.dto.response.LocationResponse;
import com.sideteam.groupsaver.domain.location.service.LocationService;
import com.sideteam.groupsaver.domain.location.service.LocationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;


    @GetMapping("/search")
    public ResponseEntity<List<LocationResponse>> searchByName(@RequestParam("address") String address) {
        return ResponseEntity.ok(locationService.searchByName(address));
    }

    @GetMapping("/coordinate")
    public ResponseEntity<List<LocationResponse>> getLocationByCoordinate(
            @RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude) {
        return ResponseEntity.ok(locationService.searchByCoordinate(longitude, latitude));
    }

    @GetMapping("/compare")
    public ResponseEntity<CoordinateDistanceResponse> compareCoordinates(
            @RequestParam("longitude1") Double longitude1, @RequestParam("latitude1") Double latitude1,
            @RequestParam("longitude2") Double longitude2, @RequestParam("latitude2") Double latitude2) {
        double distance = LocationUtils.calculateHaversineDistanceKm(latitude1, longitude1, latitude2, longitude2);
        return ResponseEntity.ok(new CoordinateDistanceResponse(distance));
    }

}
