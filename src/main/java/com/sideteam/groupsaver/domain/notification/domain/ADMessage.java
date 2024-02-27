package com.sideteam.groupsaver.domain.notification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ad_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ADMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
}
