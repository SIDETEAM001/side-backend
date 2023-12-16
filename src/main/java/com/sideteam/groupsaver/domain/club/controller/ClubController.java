package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.domain.ClubActivityType;
import com.sideteam.groupsaver.domain.club.domain.ClubMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/join/{clubId}")
    public HttpStatus joinTheClub(@PathVariable("clubId") Long clubId) {
        service.joinTheClub(clubId);
        return HttpStatus.OK;
    }

    @PatchMapping("/updateDescription/{clubId}")
    public HttpStatus updateClubDescription(@PathVariable("clubId") Long clubId, @RequestBody RequestUpdateDescription dto) {
        service.updateDescription(clubId, dto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteClub/{clubId}")
    public HttpStatus deleteClub(@PathVariable("clubId") Long clubId) {
        service.deleteClub(clubId);
        return HttpStatus.OK;
    }

    record ResponseClubId(
            long clubId
    ) {
        public ResponseClubId dtoToResponse(Long clubId) {
            return new ResponseClubId(clubId);
        }
    }

    public record RequestUpdateDescription(
            String description
    ) {

    }
}