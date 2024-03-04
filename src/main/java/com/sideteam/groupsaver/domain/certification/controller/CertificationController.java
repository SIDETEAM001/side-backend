package com.sideteam.groupsaver.domain.certification.controller;

import com.sideteam.groupsaver.domain.certification.dto.response.PhoneSendResponse;
import com.sideteam.groupsaver.domain.certification.dto.response.PhoneVerifyResponse;
import com.sideteam.groupsaver.domain.certification.service.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/certifications")
@RestController
public class CertificationController {

    private final CertificationService certificationService;

    @Operation(summary = "인증번호 전송")
    @PostMapping("/phones")
    public ResponseEntity<PhoneSendResponse> sendCertificationCode(String phoneNumber) {
        return ResponseEntity.ok(certificationService.sendCertificationCode(phoneNumber));
    }

    @Operation(summary = "인증번호 확인")
    @PostMapping("/phones/verify")
    public ResponseEntity<PhoneVerifyResponse> verifyCertificationCode(String phoneNumber, String code) {
        return ResponseEntity.ok(certificationService.verifyCertificationCode(phoneNumber, code));
    }

}
