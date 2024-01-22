package com.sideteam.groupsaver.domain.randomNickname.controller;

import com.sideteam.groupsaver.domain.randomNickname.dto.NicknameResponseDto;
import com.sideteam.groupsaver.domain.randomNickname.service.NicknameService;
import com.sideteam.groupsaver.global.config.swagger.DisableSwaggerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nickname")
public class NicknameController {
    private final NicknameService nickNameService;

    @DisableSwaggerSecurity
    @GetMapping("/getNickname")
    public ResponseEntity<NicknameResponseDto> getNickName() {
        return new ResponseEntity<NicknameResponseDto>(nickNameService.createNickName(), HttpStatus.CREATED);
    }
}