package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/club")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService service;

    @PostMapping("/create")
    public ResponseEntity<ResponseClubId> createClub(@RequestBody ClubCreateDto dto) {
        return ResponseEntity.ok(new ResponseClubId(service.createClub(dto)));
    }

    record ResponseClubId(
            long clubId
    ) {
        public ResponseClubId dtoToResponse(long clubId) {
            return new ResponseClubId(clubId);
        }
    }
}