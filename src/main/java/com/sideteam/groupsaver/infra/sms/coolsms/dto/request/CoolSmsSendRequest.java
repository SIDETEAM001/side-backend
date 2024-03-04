package com.sideteam.groupsaver.infra.sms.coolsms.dto.request;

public record CoolSmsSendRequest(
        Message message
) {
    public static CoolSmsSendRequest of(String from, String to, String text) {
        return new CoolSmsSendRequest(new Message(from, to, text));
    }

    public record Message(
            String from, // "029302266",
            String to, // "01000000001",
            String text // "내용"
    ) {
    }
}
