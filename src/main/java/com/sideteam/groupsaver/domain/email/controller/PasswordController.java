package com.sideteam.groupsaver.domain.email.controller;

import com.sideteam.groupsaver.domain.email.dto.request.ChangePwRequest;
import com.sideteam.groupsaver.domain.email.dto.request.CheckPwEmailCodeRequest;
import com.sideteam.groupsaver.domain.email.dto.request.PwRequest;
import com.sideteam.groupsaver.domain.email.service.PasswordService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pw")
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/sendEmail")
    public HttpStatus sendAuthCode(@RequestBody PwRequest pwRequest) throws MessagingException, IOException {
        passwordService.sendEmail(pwRequest);
        return HttpStatus.OK;
    }

    @PostMapping("/checkCode")
    public HttpStatus checkCode(@RequestBody CheckPwEmailCodeRequest codeRequest) {
        passwordService.checkCode(codeRequest);
        return HttpStatus.OK;
    }

    @PostMapping("/changePw")
    public HttpStatus changePw(@RequestBody ChangePwRequest changePwRequest) {
        passwordService.changePassword(changePwRequest);
        return HttpStatus.OK;
    }
}
