package com.sideteam.groupsaver.domain.chat.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "chatMember_id")
    private ChatMember chatMember;
    private String message;

    private ChatMessage(ChatMember chatMember, String message) {
        this.chatMember = chatMember;
        this.message = message;
    }

    public static ChatMessage of(ChatMember chatMember, String message) {
        return new ChatMessage(chatMember, message);
    }
}