package com.sideteam.groupsaver.domain.email.controller;

import com.sideteam.groupsaver.domain.email.dto.request.FindEmailByPhoneRequest;
import com.sideteam.groupsaver.domain.email.dto.response.FindEmailResponse;
import com.sideteam.groupsaver.domain.email.service.EmailPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phone/email")
public class EmailPhoneController {
    private final EmailPhoneService emailPhoneService;

    @PostMapping("/findEmail")
    public ResponseEntity<FindEmailResponse> findEmail(@RequestBody FindEmailByPhoneRequest request) {
        return ResponseEntity.ok(emailPhoneService.findEmail(request));
    }
}
