package com.sideteam.groupsaver.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank
        @Email(message = "입력한 이메일 주소에 @ 기호와 도메인 명이 필요합니다")
        String email,
        @NotBlank
        @Size(min = 8, max = 16, message = "비밀번호의 길이는 8 ~ 16자입니다")
        String password
) {
}
