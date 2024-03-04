package com.sideteam.groupsaver.domain.certification.dto.response;

import java.time.Duration;

public record PhoneSendResponse(
        String phoneNumber,
        Duration expireTime
) {
}
