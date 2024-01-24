package com.sideteam.groupsaver.domain.email.controller;

import com.sideteam.groupsaver.domain.email.dto.request.ChangePwPhoneRequest;
import com.sideteam.groupsaver.domain.email.service.PasswordPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phone/pw")
public class PasswordPhoneController {
    private final PasswordPhoneService passwordPhoneService;

    @PostMapping("/changePw")
    public HttpStatus changePassword(@RequestBody ChangePwPhoneRequest phoneRequest) {
        passwordPhoneService.changePassword(phoneRequest);
        return HttpStatus.OK;
    }
}
