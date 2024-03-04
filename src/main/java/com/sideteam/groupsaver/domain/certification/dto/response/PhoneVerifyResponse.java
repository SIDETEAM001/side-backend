package com.sideteam.groupsaver.domain.certification.dto.response;

import com.sideteam.groupsaver.domain.certification.dto.VerifyResult;

public record PhoneVerifyResponse(
        VerifyResult status
) {
    public static PhoneVerifyResponse success() {
        return new PhoneVerifyResponse(VerifyResult.SUCCESS);
    }

    public static PhoneVerifyResponse fail() {
        return new PhoneVerifyResponse(VerifyResult.FAIL);
    }
}
