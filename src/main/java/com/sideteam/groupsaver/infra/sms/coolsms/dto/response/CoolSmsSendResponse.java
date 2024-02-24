package com.sideteam.groupsaver.infra.sms.coolsms.dto.response;

public record CoolSmsSendResponse(
        String accountId,
        String country,
        String groupId,
        String messageId,
        String from,
        String to,
        String type,
        String statusMessage,
        String statusCode
) {
    public boolean isSuccess() {
        return "2000".equals(statusCode);
    }
}
