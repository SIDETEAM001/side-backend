package com.sideteam.groupsaver.domain.hobby.controller;

import com.sideteam.groupsaver.domain.category.domain.HobbySub;
import com.sideteam.groupsaver.domain.club.domain.ClubType;
import com.sideteam.groupsaver.domain.hobby.dto.HobbyCreateDto;
import com.sideteam.groupsaver.domain.hobby.dto.HobbyResponseDto;
import com.sideteam.groupsaver.domain.hobby.gateway.HobbyGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hobby")
public class HobbyController {
    private final HobbyGateway gateway;

    @PostMapping("/create")
    public ResponseEntity<Long> createHobby(@RequestBody HobbyCreateDto dto) {
        return ResponseEntity.ok(gateway.createHobby(dto));
    }

    @PostMapping("/join/{hobbyId}")
    public HttpStatus joinTheHobby(@PathVariable("hobbyId") Long hobbyId) {
        gateway.joinTheHobby(hobbyId);
        return HttpStatus.OK;
    }

    @PatchMapping("/updateDescription/{hobbyId}")
    public HttpStatus updateClubDescription(@PathVariable("hobbyId") Long hobbyId, @RequestBody RequestUpdateDescription dto) {
        gateway.updateDescriptionOfTheHobby(hobbyId, dto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteHobby/{hobbyId}")
    public HttpStatus deleteHobby(@PathVariable("hobbyId") Long hobbyId) {
        gateway.deleteTheHobby(hobbyId);
        return HttpStatus.OK;
    }

    @GetMapping("/getHobbyList/{category}/{type}")
    public ResponseEntity<List<HobbyResponseDto>> findHobbyList(
            @PathVariable("category") HobbySub category,
            @PathVariable("type") ClubType type) {
        return ResponseEntity.ok(gateway.findHobbyList(category, type));
    }
    public record RequestUpdateDescription(
            String description
    ) {
    }
}