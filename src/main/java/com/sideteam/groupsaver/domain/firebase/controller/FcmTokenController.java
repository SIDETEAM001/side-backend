package com.sideteam.groupsaver.domain.firebase.controller;

import com.sideteam.groupsaver.domain.firebase.dto.CreateFcmTokenDto;
import com.sideteam.groupsaver.domain.firebase.dto.DeleteFcmTokenRequest;
import com.sideteam.groupsaver.domain.firebase.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
public class FcmTokenController {
    private final FcmTokenService fcmTokenService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitFcmToken(@RequestBody CreateFcmTokenDto dto) {
        fcmTokenService.createFcmToken(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFcmToken(@RequestBody DeleteFcmTokenRequest request) {
        fcmTokenService.deleteFcmToken(request);
        return ResponseEntity.noContent().build();
    }
}