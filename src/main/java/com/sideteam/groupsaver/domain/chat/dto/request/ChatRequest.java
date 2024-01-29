package com.sideteam.groupsaver.domain.chat.dto.request;

import com.sideteam.groupsaver.domain.chat.dto.MessageType;

public record ChatRequest(
        MessageType type,
        Long memberId,
        String content
) {
}
