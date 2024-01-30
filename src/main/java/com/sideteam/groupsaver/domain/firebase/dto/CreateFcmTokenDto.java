package com.sideteam.groupsaver.domain.firebase.dto;

import lombok.Getter;

@Getter
public class CreateFcmTokenDto {
    private String email;
    private String token;
}