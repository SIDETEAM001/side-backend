package com.sideteam.groupsaver.domain.report.controller;

import com.sideteam.groupsaver.domain.report.dto.ReportClubRequest;
import com.sideteam.groupsaver.domain.report.service.ReportClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportClubController {
    private final ReportClubService reportClubService;

    @PostMapping("/club")
    public ResponseEntity<?> reportClub(@RequestBody ReportClubRequest request) {
        reportClubService.report(request);
        return ResponseEntity.noContent().build();
    }
}
