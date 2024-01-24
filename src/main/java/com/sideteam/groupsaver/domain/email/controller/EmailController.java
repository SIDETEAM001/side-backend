package com.sideteam.groupsaver.domain.email.controller;

import com.sideteam.groupsaver.domain.email.dto.request.CheckEmailCodeRequest;
import com.sideteam.groupsaver.domain.email.dto.request.EmailRequest;
import com.sideteam.groupsaver.domain.email.dto.request.FindEmailByPhoneRequest;
import com.sideteam.groupsaver.domain.email.dto.response.FindEmailResponse;
import com.sideteam.groupsaver.domain.email.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendEmail")
    public HttpStatus sendAuthCode(@RequestBody EmailRequest emailRequestDto) throws MessagingException, IOException {
        emailService.sendAuthCode(emailRequestDto);
        return HttpStatus.OK;
    }

    @PostMapping("/checkCode")
    public ResponseEntity<FindEmailResponse> checkCode(@RequestBody CheckEmailCodeRequest codeRequest) {
        return ResponseEntity.ok(emailService.checkAuthCode(codeRequest));
    }
}
