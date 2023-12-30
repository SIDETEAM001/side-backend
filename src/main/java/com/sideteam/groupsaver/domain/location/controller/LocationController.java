package com.sideteam.groupsaver.domain.location.controller;

import com.sideteam.groupsaver.domain.location.dto.CoordinateRequest;
import com.sideteam.groupsaver.domain.location.dto.LocationResponse;
import com.sideteam.groupsaver.domain.location.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Valid @RequestBody CoordinateRequest coordinateRequest) {
        return ResponseEntity.ok(locationService.searchByCoordinate(coordinateRequest));
    }

}
