package com.sideteam.groupsaver.domain.email.dto.request;

import lombok.Getter;

@Getter
public class CheckPwEmailCodeRequest {
    private String email;
    private String code;
}
