package com.sideteam.groupsaver.domain.randomNickname.controller;

import com.sideteam.groupsaver.domain.randomNickname.dto.NicknameResponseDto;
import com.sideteam.groupsaver.domain.randomNickname.service.NicknameService;
import com.sideteam.groupsaver.global.config.swagger.DisableSwaggerSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/nickname")
public class NicknameController {
    private final NicknameService nickNameService;

    @DisableSwaggerSecurity
    @GetMapping("/getNickname")
    public ResponseEntity<NicknameResponseDto> getNickName() {
        return new ResponseEntity<>(nickNameService.createNickName(), HttpStatus.CREATED);
    }

    @DisableSwaggerSecurity
    @PostMapping("/checkDuplication")
    public ResponseEntity<Boolean> checkNickname(@RequestBody NickNameDto nickname) {
        return ResponseEntity.ok(nickNameService.checkNickname(nickname.get()));
    }

    record NickNameDto(
            String nickname
    ) {
        public String get() {
            return nickname;
        }
    }

}