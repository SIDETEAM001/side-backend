package com.sideteam.groupsaver.domain.report.controller;

import com.sideteam.groupsaver.domain.report.dto.ReportUserRequest;
import com.sideteam.groupsaver.domain.report.service.ReportUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportUserController {
    private final ReportUserService reportUserService;

    @PostMapping("/user")
    public ResponseEntity<?> reportUser(@RequestBody ReportUserRequest request) {
        reportUserService.reportUser(request);
        return ResponseEntity.noContent().build();
    }
}
