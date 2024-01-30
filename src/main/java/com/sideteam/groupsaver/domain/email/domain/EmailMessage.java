package com.sideteam.groupsaver.domain.email.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class EmailMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private final String email;
    private final String title;
    private final String message;

    @Builder
    protected EmailMessage(String email, String title, String message) {
        this.email = email;
        this.title = title;
        this.message = message;
    }

    public static EmailMessage of(String email, String title, String message) {
        return EmailMessage.builder()
                .email(email)
                .title(title)
                .message(message)
                .build();
    }
}
