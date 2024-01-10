package com.sideteam.groupsaver.domain.chat.dto;

import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageDto {
    private String message;
    private String nickName;
    private String profileImage;
    private boolean isMine;
    private ClubMemberRole role;
    private LocalDateTime createAt;

    private ChatMessageDto(String message, String nickName, String profileImage, boolean isMine, ClubMemberRole role, LocalDateTime createAt) {
        this.message = message;
        this.nickName = nickName;
        this.profileImage = profileImage;
        this.isMine = isMine;
        this.role = role;
        this.createAt = createAt;
    }

    public static ChatMessageDto of(String message, String nickName, String profileImage, boolean isMine, ClubMemberRole role, LocalDateTime createAt) {
        return new ChatMessageDto(message, nickName, profileImage, isMine, role, createAt);
    }
}