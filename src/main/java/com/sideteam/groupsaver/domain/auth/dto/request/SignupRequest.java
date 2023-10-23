package com.sideteam.groupsaver.domain.auth.dto.request;

import com.sideteam.groupsaver.global.auth.AuthConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record SignupRequest(

        @NotBlank(message = "전화번호는 공백이 아니어야 합니다")
        String phoneNumber,

        @NotBlank(message = "닉네임은 공백이 아니어야 합니다")
        String nickname,

        @NotBlank(message = "이메일은 공백이 아니어야 합니다")
        @Email(message = "이메일 주소에 @ 기호와 도메인 명이 필요합니다")
        String email,

        @NotBlank(message = "비밀번호는 공백이 아니어야 합니다")
        @Pattern(message = "비밀번호는 숫자, 영어, 특수문자를 포함해야 합니다", regexp = AuthConstants.passwordRegexp)
        @Size(min = 6, max = 64, message = "비밀번호의 길이는 6 ~ 64자 입니다")
        String password

) {
}
