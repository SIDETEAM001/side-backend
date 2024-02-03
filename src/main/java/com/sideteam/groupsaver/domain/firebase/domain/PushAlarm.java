package com.sideteam.groupsaver.domain.firebase.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class PushAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fcmToken_id")
    private FcmToken fcmToken;

    @Builder
    protected PushAlarm(String message, FcmToken fcmToken) {
        this.message = message;
        this.fcmToken = fcmToken;
    }

    public static PushAlarm of(String message, FcmToken fcmToken) {
        return PushAlarm.builder()
                .message(message)
                .fcmToken(fcmToken)
                .build();
    }
}
