package com.sideteam.groupsaver.domain.likes.controller;


import com.sideteam.groupsaver.domain.likes.dto.request.LikesRequestDto;
import com.sideteam.groupsaver.domain.likes.service.LikesService;
import com.sideteam.groupsaver.domain.report.dto.ReportClubRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/{id}")
    public ResponseEntity<?> likesQna(@PathVariable Long id, @RequestBody LikesRequestDto likesRequestDto) {
        return ResponseEntity.ok(likesService.likesQna(id, likesRequestDto));
    }
}
