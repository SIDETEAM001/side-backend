package com.sideteam.groupsaver.domain.chat.dto;

public record WebSocketIdentity(
        Long memberId,
        String nickname,
        String profileUrl
) {
}
