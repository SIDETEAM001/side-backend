package com.sideteam.groupsaver.domain.email.dto.request;

import lombok.Getter;

@Getter
public class ChangePwRequest {
        private String email;
        private String password;
}
