package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubCreateDto;
import com.sideteam.groupsaver.domain.club.dto.ClubResponseDto;
import com.sideteam.groupsaver.domain.club.gateway.ServiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/club")
@RequiredArgsConstructor
public class ClubController {
    private final ServiceGateway gateway;

    @PostMapping("/create")
    public ResponseEntity<Long> createClub(@RequestBody ClubCreateDto dto) {
        return ResponseEntity.ok(gateway.createClub(dto));
    }

    @PostMapping("/join/{clubId}")
    public HttpStatus joinTheClub(@PathVariable("clubId") Long clubId) {
       gateway.joinTheClub(clubId);
        return HttpStatus.OK;
    }

    @PatchMapping("/updateDescription/{clubId}")
    public HttpStatus updateClubDescription(@PathVariable("clubId") Long clubId, @RequestBody RequestUpdateDescription dto) {
        gateway.updateDescriptionOfTheClub(clubId, dto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteClub/{clubId}")
    public HttpStatus deleteClub(@PathVariable("clubId") Long clubId) {
        gateway.deleteTheClub(clubId);
        return HttpStatus.OK;
    }

    @GetMapping("/getClubList/{category}/{type}")
    public ResponseEntity<List<ClubResponseDto>> findClubList(
            @PathVariable("category") DevelopMajor category,
            @PathVariable("type") ClubType type) {
        return ResponseEntity.ok(gateway.findDevelopClubList(category, type));
    }

    public record RequestUpdateDescription(
            String description
    ) {

    }
}