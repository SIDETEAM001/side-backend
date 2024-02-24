package com.sideteam.groupsaver.domain.certification.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum VerifyResult {
    SUCCESS("success"),
    FAIL("fail"),
    ;

    private final String status;
}
