package com.sideteam.groupsaver.domain.club.controller;

import com.sideteam.groupsaver.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/club")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService service;
}