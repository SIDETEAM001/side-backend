package com.sideteam.groupsaver.domain.email.dto.request;

import lombok.Getter;

@Getter
public class CheckEmailCodeRequest {
        private String email;
        private String code;
}
