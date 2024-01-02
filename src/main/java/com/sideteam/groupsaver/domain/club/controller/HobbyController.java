package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.category.domain.DevelopMajor;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.club.dto.ClubResponseDto;
import com.sideteam.groupsaver.domain.club.dto.HobbyCreateDto;
import com.sideteam.groupsaver.domain.club.gateway.ClubGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hobby")
public class HobbyController {
    private final ClubGateway gateway;

    @PostMapping("/create")
    public ResponseEntity<Long> createClub(@RequestBody HobbyCreateDto dto) {
        return null;
    }

    @GetMapping("/getClubList/{category}/{type}")
    public ResponseEntity<List<ClubResponseDto>> findClubList(
            @PathVariable("category") DevelopMajor category,
            @PathVariable("type") ClubType type) {
        return ResponseEntity.ok(gateway.findDevelopClubList(category, type));
    }
}