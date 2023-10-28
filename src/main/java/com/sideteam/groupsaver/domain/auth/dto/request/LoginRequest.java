package com.sideteam.groupsaver.domain.auth.dto.request;

import com.sideteam.groupsaver.global.auth.AuthConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "입력한 이메일 주소는 공백이 아니어야 합니다")
        @Email(message = "입력한 이메일 주소에 @ 기호와 도메인 명이 필요합니다")
        String email,

        @NotBlank(message = "입력한 비밀번호는 공백이 아니어야 합니다")
        @Size(min = 6, message = "비밀번호의 길이는 최소 6자 입니다")
        String password

) {
}
