package com.sideteam.groupsaver.domain.auth.dto.response;

import com.sideteam.groupsaver.global.auth.AuthConstants;

public record TokenDto(
        String accessToken,
        String refreshToken,
        String type
) {
    public TokenDto(String accessToken, String refreshToken) {
        this(accessToken, refreshToken, AuthConstants.TOKEN_TYPE);
    }
}
