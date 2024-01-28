package com.sideteam.groupsaver.domain.chat.dto.response;

import com.sideteam.groupsaver.domain.chat.Chat;
import com.sideteam.groupsaver.domain.chat.dto.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatResponse(
        Long memberId,
        String nickname,
        String profileImgUrl,
        MessageType type,
        LocalDateTime createdAt,
        String content
) {
    public static ChatResponse of(Chat chat) {
        return ChatResponse.builder()
                .memberId(chat.getSender().getId())
                .nickname(chat.getSender().getNickname())
                .profileImgUrl(chat.getSender().getProfileUrl())
                .type(chat.getType())
                .createdAt(chat.getCreatedAtLocalDateTime())
                .content(chat.getContent())
                .build();
    }

}
