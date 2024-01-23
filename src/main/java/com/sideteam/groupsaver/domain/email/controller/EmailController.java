package com.sideteam.groupsaver.domain.email.controller;

import com.sideteam.groupsaver.domain.email.dto.EmailRequestDto;
import com.sideteam.groupsaver.domain.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/findEmail")
    public HttpStatus sendAuthCode(@RequestBody EmailRequestDto emailRequestDto) {
        emailService.sendAuthCode(emailRequestDto);
        return HttpStatus.OK;
    }
}
