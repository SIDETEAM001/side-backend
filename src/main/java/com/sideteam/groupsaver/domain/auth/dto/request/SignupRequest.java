package com.sideteam.groupsaver.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record SignupRequest(
        @NotBlank
        String nickname,

        @NotBlank
        @Email(message = "입력한 이메일 주소에 @ 기호와 도메인 명이 필요합니다")
        String email,

        @NotBlank
        String password
) {
}
