package com.sideteam.groupsaver.global.resolver.access_token;

import java.time.Instant;

public record AccessToken(
        String accessToken,
        String subject,
        Instant expiryDate
) {
}
