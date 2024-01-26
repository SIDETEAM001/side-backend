package com.sideteam.groupsaver.domain.firebase.controller;

import com.sideteam.groupsaver.domain.firebase.dto.CreateFcmTokenDto;
import com.sideteam.groupsaver.domain.firebase.dto.DeleteFcmTokenRequest;
import com.sideteam.groupsaver.domain.firebase.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
public class FcmTokenController {
    private final FcmTokenService fcmTokenService;

    @PostMapping("/submit")
    public HttpStatus submitFcmToken(@RequestBody CreateFcmTokenDto dto) {
        fcmTokenService.createFcmToken(dto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete")
    public HttpStatus deleteFcmToken(@RequestBody DeleteFcmTokenRequest request) {
        fcmTokenService.deleteFcmToken(request);
        return HttpStatus.OK;
    }
}